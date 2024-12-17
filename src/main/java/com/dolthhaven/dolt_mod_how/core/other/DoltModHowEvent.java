package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.List;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DoltModHowEvent {
    private static final UniformInt COMMON_ORE = UniformInt.of(0, 2);
    private static final UniformInt RARE_ORE = UniformInt.of(1, 3);


    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof ThrownTrident trident && trident.isChanneling()) {
            if (event.getRayTraceResult() instanceof BlockHitResult result) {
                Level level = trident.level();
                if (!level.isThundering())
                    return;
                BlockPos pos = result.getBlockPos();
                if (!level.canSeeSky(pos.above()))
                    return;

                if (level.getBlockState(pos).is(DMHTags.CHANNELS_LIGHTNING)) {
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                    if (bolt == null)
                        return;

                    bolt.moveTo(Vec3.atBottomCenterOf(pos.above()));
                    bolt.setCause(event.getProjectile().getOwner() instanceof ServerPlayer player ? player : null);
                    level.addFreshEntity(bolt);
                    level.playSound(null, pos, SoundEvents.TRIDENT_THUNDER, SoundSource.WEATHER, 5.0F, 1.0F);
                }
            }
        }
    }



    @SubscribeEvent
    public static void removePoisonIfPlayerKillsArthropodWithBOA(LivingDeathEvent event) {
        Entity enty = event.getEntity();
        if (enty.level() instanceof ServerLevel SL && event.getSource().getEntity() instanceof LivingEntity attacker) {

            boolean shouldRemovePoison = attacker.hasEffect(MobEffects.POISON)
                    && attacker.getItemInHand(MAIN_HAND).getAllEnchantments().containsKey(Enchantments.BANE_OF_ARTHROPODS)
                    && event.getEntity().getMobType() == MobType.ARTHROPOD;

            if (shouldRemovePoison) {
                attacker.removeEffect(MobEffects.POISON);
                for (int i = 0; i < 7; i++) {
                    SL.sendParticles(DMHParticles.POISON_HEART.get(), attacker.getRandomX(0.5f), attacker.getRandomY(), attacker.getRandomZ(0.5), 1,
                            0, 0, 0, 0);;
                }
                SL.playSound(attacker, attacker.getOnPos(), SoundEvents.ALLAY_ITEM_TAKEN, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerDropEvent(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                return;
            }

            event.getDrops().stream().map(ItemEntity::getItem).filter(i -> i.getEnchantmentLevel(DMHEnchants.BOUNDING.get()) > 0).forEach(i -> player.getInventory().add(i));
        }
    }

    // Some credit to team Cofh's soulbound effect
    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player nw = event.getEntity();
            Player old = event.getOriginal();

            List<ItemStack> items = old.getInventory().items;

            for (ItemStack stackie : items.stream().filter(i -> i.getEnchantmentLevel(DMHEnchants.BOUNDING.get()) >= 1).toList()) {
                if (!nw.getInventory().add(stackie)) {
                    nw.drop(stackie, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBreakOreEvent(BlockEvent.BreakEvent event) {
        if (!DoltModHowConfig.COMMON.doMetalOresDropXP.get()) {
            return;
        }

        if (event.getLevel() instanceof ServerLevel level) {
            BlockState state = event.getState();
            if (!event.getPlayer().hasCorrectToolForDrops(state)) {
                return;
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer()) > 0) {
                return;
            }

            if (event.getExpToDrop() == 0) {
                if (state.is(DMHTags.COMMON_ORES)) {
                    int exp = COMMON_ORE.sample(level.getRandom());
                    event.setExpToDrop(exp);
                } else if (state.is(DMHTags.RARE_ORES)) {
                    event.setExpToDrop(RARE_ORE.sample(level.getRandom()));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerBreakCropsEvent(BlockEvent.BreakEvent event) {
        if (!DoltModHowConfig.COMMON.doCropBlocksDropXP.get()) {
            return;
        }

        if (event.getLevel() instanceof ServerLevel level) {
            BlockState state = event.getState();
            if (state.getBlock() instanceof CropBlock cropBlock &&
                    !state.is(DMHTags.NO_XP_CROPS) &&
                    cropBlock.isMaxAge(state)) {

                UniformInt crop_sampler = UniformInt.of(
                        DoltModHowConfig.COMMON.minCropXpDrops.get(),
                        DoltModHowConfig.COMMON.maxCropXpDrops.get());

                event.setExpToDrop(crop_sampler.sample(level.getRandom()));
            }
        }
    }

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        // bullet pepper
        handleBulletPepper(event);

        // alphacene path
        handleAlphacenePath(event);

        // untill farmland
        handleUntillFarmland(event);
    }

    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        Item bulletPepper = ForgeRegistries.ITEMS.getValue(Util.Constants.BULLET_PEPPER);
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(Event.Result.DENY);
        }
    }

    private static void handleAlphacenePath(PlayerInteractEvent.RightClickBlock event) {
        Block alphaceneGrass = ForgeRegistries.BLOCKS.getValue(Util.Constants.ALPHACENE_GRASS_BLOCK);
        if (alphaceneGrass == null) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (event.getFace() != Direction.DOWN && stack.canPerformAction(ToolActions.SHOVEL_FLATTEN) && !player.isSpectator() && level.isEmptyBlock(pos.above())) {
            if (state.is(alphaceneGrass)) {
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1, 1);
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, (damage) -> damage.broadcastBreakEvent(event.getHand()));
                    level.setBlock(pos, DMHBlocks.ALPHACENE_PATH.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                }
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
            }
        }
    }

    public static void handleUntillFarmland(PlayerInteractEvent.RightClickBlock event) {
        if (!DoltModHowConfig.COMMON.doUntillableFarmland.get()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();


        if (stack.canPerformAction(ToolActions.HOE_TILL) && player.isCrouching()) {
            if (level.getBlockState(pos).is(Blocks.FARMLAND)) {
                stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(LivingEntity.getEquipmentSlotForItem(stack)));
                level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
                player.swing(event.getHand());
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
            else if (level.getBlockState(pos).is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
                stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(LivingEntity.getEquipmentSlotForItem(stack)));
                level.setBlock(pos, ModBlocks.RICH_SOIL.get().defaultBlockState() , 2);
                player.swing(event.getHand());
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
