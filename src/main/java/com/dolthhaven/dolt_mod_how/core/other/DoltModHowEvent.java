package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.compat.DMHACCompat;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DoltModHowEvent {
    private static final UniformInt COMMON_ORE = UniformInt.of(0, 2);
    private static final UniformInt RARE_ORE = UniformInt.of(1, 3);
    public static final Map<Block, Block> TILL_MAP = new HashMap<>();
    public static final Map<Block, Block> UNRUST_MAP = new HashMap<>();

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof ThrownTrident trident && trident.isChanneling()) {
            if (event.getRayTraceResult() instanceof BlockHitResult result) {
                Level level = trident.level();
                BlockPos pos = result.getBlockPos();
                if (!level.isThundering() || !level.canSeeSky(pos.above()))
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
                            0, 0, 0, 0);
                    ;
                }
                SL.playSound(attacker, attacker.getOnPos(), SoundEvents.ALLAY_ITEM_TAKEN, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBreakOreEvent(BlockEvent.BreakEvent event) {
        if (!DMHConfig.COMMON.doMetalOresDropXP.get()) {
            return;
        }

        if (event.getLevel() instanceof ServerLevel level) {
            BlockState state = event.getState();

            if (!event.getPlayer().hasCorrectToolForDrops(state) ||
                    EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer()) > 0) {
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
        if (!DMHConfig.COMMON.doCropBlocksDropXP.get()) {
            return;
        }

        if (event.getLevel() instanceof ServerLevel level) {
            BlockState state = event.getState();
            if (state.getBlock() instanceof CropBlock cropBlock &&
                    !state.is(DMHTags.NO_XP_CROPS) &&
                    cropBlock.isMaxAge(state)) {

                UniformInt crop_sampler = UniformInt.of(
                        DMHConfig.COMMON.minCropXpDrops.get(),
                        DMHConfig.COMMON.maxCropXpDrops.get());

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

        // rust stuff???
        tryUnrustRustyStuff(event);
    }

    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMHConfig.COMMON.killBulletPepperPlacement.get() || !ModList.get().isLoaded(DMHUtils.Constants.MY_NETHERS_DELIGHT))
            return;

        ItemStack stack = event.getItemStack();

        Item bulletPepper = ForgeRegistries.ITEMS.getValue(DMHUtils.Constants.BULLET_PEPPER);
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void blockPlacedEvent(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ServerPlayer player && player.level() instanceof ServerLevel serverLevel) {
            if (event.getPlacedBlock().is(DMHTags.NO_XP_REWARD_ON_PLACE) || !DMHConfig.COMMON.xpUponBlockPlace.get()) {
                return;
            }

            if (player.getRandom().nextInt(DMHConfig.COMMON.blockPlaceXpChance.get()) == 0) {
                ExperienceOrb.award(serverLevel, player.position(), 1);
            }
        }
    }

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isDamaged() && right.getItem().isValidRepairItem(left, right)) {
            ItemStack newLeft = left.copy();
            int dur = newLeft.getMaxDamage();
            int dam = newLeft.getDamageValue();
            newLeft.setDamageValue(Math.min(
                    dur, dam + (int) (dur / DMHConfig.COMMON.valuePerRepair.get())
            ));
            event.setOutput(newLeft);
            event.setResult(Event.Result.ALLOW);
        }
        else if (right.is(Items.ENCHANTED_BOOK) && DMHConfig.COMMON.muteExFriendlyAnvils.get()) {
            Map<Enchantment, Integer> bookEnchants = EnchantmentHelper.getEnchantments(right);
            Map<Enchantment, Integer> toolEnchants = EnchantmentHelper.getEnchantments(left);

            ItemStack newLeft = left.copy();
            List<Enchantment> toRemove = new ArrayList<>();
            for (Map.Entry<Enchantment, Integer> toolEnchantInstance : toolEnchants.entrySet()) {
                Enchantment enchant = toolEnchantInstance.getKey();
                for (Map.Entry<Enchantment, Integer> bookEnchantInstance : bookEnchants.entrySet()) {
                    Enchantment bookEnchant = bookEnchantInstance.getKey();
                    boolean toolHasStrongerEnchant = toolEnchants.containsKey(bookEnchant) && toolEnchants.get(bookEnchant) >= bookEnchants.get(bookEnchant);
                    if (!bookEnchant.canEnchant(left) || toolHasStrongerEnchant) {
                        continue;
                    }

                    if (!toolEnchantInstance.getKey().isCompatibleWith(enchant)) {
                        toRemove.add(enchant);
                    }
                    toolEnchants.put(bookEnchantInstance.getKey(), bookEnchantInstance.getValue());
                }
            }

            toolEnchants.entrySet().removeIf((map) -> toRemove.contains(map.getKey()));
            EnchantmentHelper.setEnchantments(toolEnchants, newLeft);
            event.setOutput(newLeft);
            event.setCost(4);
            event.setResult(Event.Result.ALLOW);
        }
    }

    private static void handleAlphacenePath(PlayerInteractEvent.RightClickBlock event) {
        Block alphaceneGrass = ForgeRegistries.BLOCKS.getValue(DMHUtils.Constants.ALPHACENE_GRASS_BLOCK);
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
        if (!DMHConfig.COMMON.doUntillableFarmland.get()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();

        if (stack.canPerformAction(ToolActions.HOE_TILL) && player.isCrouching() && event.getFace() != Direction.DOWN && !player.isSpectator()) {
            Block block = TILL_MAP.get(level.getBlockState(pos).getBlock());
            if (block == null || level.getEntitiesOfClass(Player.class, new AABB(pos)).contains(player) ||
                    !level.isEmptyBlock(pos.above())) return;

            if (!level.isClientSide()) {
                stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(LivingEntity.getEquipmentSlotForItem(stack)));
                level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
            }

            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);

            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }

    private static void tryUnrustRustyStuff(PlayerInteractEvent.RightClickBlock event) {
        if (!DMHUtils.alexCavesLoaded()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);


        if (stack.canPerformAction(ToolActions.AXE_SCRAPE) && UNRUST_MAP.containsKey(state.getBlock())) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                if (DMHACCompat.isAcid(level.getBlockState(pos.relative(dir)))) {
                    return;
                }
            }

            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockState newState = UNRUST_MAP.get(state.getBlock()).defaultBlockState();

            for (Property<?> anyProp : state.getProperties()) {
                newState = setGenericProperty(newState, anyProp, state.getValue(anyProp));
            }

            level.levelEvent(player, 3004, pos, 0);

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }

            level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            if (player != null) {
                stack.hurtAndBreak(1, player, (p_150686_) -> p_150686_.broadcastBreakEvent(event.getHand()));
            }

            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }


    public static void registerHoeTills() {
        TILL_MAP.put(Blocks.FARMLAND, Blocks.DIRT);
        TILL_MAP.put(ModBlocks.RICH_SOIL_FARMLAND.get(), ModBlocks.RICH_SOIL.get());
        TILL_MAP.put(ModRegistry.RAKED_GRAVEL.get(), Blocks.GRAVEL);
    }

    public static void registerUnRust() {
        if (DMHUtils.alexCavesLoaded()) {
            DMHACCompat.registerUnRust();
        }
    }

    private static <V extends Comparable<V>> BlockState setGenericProperty(BlockState state, Property<?> propName, V propertyValue) {
        try {
            Property<V> newProp = (Property<V>) propName;
            return state.setValue(newProp, propertyValue);
        } catch (ClassCastException | IllegalArgumentException e) {
            return state;
        }
    }
}
