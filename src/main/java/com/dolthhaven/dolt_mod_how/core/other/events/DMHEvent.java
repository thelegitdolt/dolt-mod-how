package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.common.item.RecoveryCompassItem;
import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.dolthhaven.dolt_mod_how.integration.AmendmentsBugfix;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHFTGUCompat;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.tag.ModTags;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHEvent {
    private static final UniformInt COMMON_ORE = UniformInt.of(0, 2);
    private static final UniformInt RARE_ORE = UniformInt.of(1, 3);

    public static void avoidDragonBreath(EntityJoinLevelEvent event) {
    }

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
        Entity entity = event.getEntity();

        if (entity.level() instanceof ServerLevel SL && event.getSource().getEntity() instanceof LivingEntity attacker) {

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
        if (event.getPlayer() instanceof ServerPlayer serverPlayer && !(event.getState().getBlock() instanceof BeehiveBlock)) {
            CriteriaTriggers.BEE_NEST_DESTROYED.trigger(serverPlayer, event.getState(), serverPlayer.getItemInHand(MAIN_HAND), 0);
        }

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
    public static void doStuff(PlayerInteractEvent.RightClickItem event) {
        Item self = event.getItemStack().getItem();
        if (self == Items.RECOVERY_COMPASS && event.getEntity().isShiftKeyDown()) {
            InteractionResultHolder<ItemStack> newResult = RecoveryCompassItem.use(event.getLevel(), event.getEntity(), event.getHand());
            if (newResult.getResult().consumesAction()) {
                event.setCancellationResult(newResult.getResult());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void spawnCuts(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getItemInHand(MAIN_HAND);
        BlockState state = event.getState();
        if (ModList.get().isLoaded(DMHUtils.Constants.ALEXS_CAVES) && stack.is(ModTags.KNIVES)) {
            ItemStack dropStack = DMHACCompat.getMeatDropWhenBroken(state);
            if (dropStack == null) {
                return;
            }

            Block.popResourceFromFace(player.level(), event.getPos(), Direction.UP, dropStack);
        }
    }

    @SubscribeEvent
    public static void entitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
        if (ModList.get().isLoaded(DMHUtils.Constants.FTGU) && DMHConfig.COMMON.doltChargedCreeperTweaks.get()) {
            if (event.getSpawnType() == MobSpawnType.NATURAL &&
                    !DMHFTGUCompat.shouldSpawnChargedCreeper(event.getLevel().getLevel(), event.getEntity().blockPosition(), event.getEntity())) {
                event.setSpawnCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void doomedEffectEvent(MobEffectEvent.Applicable event) {
        if (ModList.get().isLoaded(DMHUtils.Constants.FTGU)) {
            if (DMHConfig.COMMON.doltChargedCreeperTweaks.get() && DMHFTGUCompat.isDoomedEffect(event.getEffectInstance())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.AMENDMENTS)) return;
        BlockState state = event.getState();
        Player player = event.getPlayer();
        if (player.getMainHandItem().is(ModTags.KNIVES) && AmendmentsBugfix.isDoubleCakeBlock(state)) {
            Block doubleCake = state.getBlock();
            ResourceLocation idrl = DMHUtils.getBlockId(doubleCake);
            if (idrl == null) return;

            String id = idrl.getPath().replace("/double_", ":");
            Block block = DMHUtils.getPotentialBlock(new ResourceLocation(id));
            Item sliceItem = DMHUtils.getPotentialItem(AmendmentsBugfix.CAKE_SLICE_MAP.get(id));
            if (sliceItem == null || block == null) return;

            int bites = state.getValue(CakeBlock.BITES);

            Block.popResource((Level) event.getLevel(), event.getPos(), new ItemStack(sliceItem, 14 - bites));

        }

    }
}