package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.network.DMHPacketHandler;
import com.dolthhaven.dolt_mod_how.core.network.S2CRustScrapePacket;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHMowziesMobsCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHNeapolitanCompat;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHRightClickEvent {
    public static final Map<Block, Block> RAKE_MAP = new HashMap<>();
    public static final Map<Block, Block> TILL_MAP = new HashMap<>();
    public static final Map<Block, Block> UNRUST_MAP = new HashMap<>();
    public static final Map<Item, Pair<Supplier<Boolean>, BlockItem>> ITEM_PLACE_MAP = new HashMap<>();

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockHitResult result = event.getHitVec();
        ItemStack stack = event.getItemStack();

        handleBulletPepper(event);
        handleAlphacenePath(event);
        handleUntillFarmland(event);
        tryUnrustRustyStuff(event);
        potStrawberry(event);
        rakeSand(event, level, player, hand, stack, pos, result);
    }

    public static void rakeSand(PlayerInteractEvent.RightClickBlock event, Level level, Player player, InteractionHand hand, ItemStack stack, BlockPos pos, BlockHitResult result) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS)) {
            return;
        }

        BlockState state = level.getBlockState(pos);
        Item rake = DMHUtils.getPotentialItem(DMHUtils.Constants.SAND_RAKE);

        Block rakedSand;
        if (DMHMowziesMobsCompat.isRakedSand(state.getBlock())) {
            rakedSand = state.getBlock();
        } else {
            rakedSand = RAKE_MAP.get(state.getBlock());
        }
        if (rakedSand == null) return;
        boolean isRake = stack.is(rake);
        boolean isHoe = stack.canPerformAction(ToolActions.HOE_TILL) && DMHConfig.COMMON.hoesRakeSand.get();

        if (isRake && rakedSand.builtInRegistryHolder().key().location().getNamespace().equals(DMHUtils.Constants.MOWZIES_MOBS)) {
            return;
        }

        BlockPlaceContext context = fromEvent(event);

        if (isRake || isHoe) {
            BlockState rakedState = rakedSand.getStateForPlacement(context);
            if (rakedState != null && !DMHMowziesMobsCompat.sameRakedState(state, rakedState)) {
                DMHMowziesMobsCompat.playSandRakeSound(level, player, pos);
                if (!level.isClientSide) {
                    level.setBlock(pos, rakedState, Block.UPDATE_ALL_IMMEDIATE);
                    rakedSand.onPlace(rakedState, level, pos, rakedState, false);
                    DMHMowziesMobsCompat.updateRakedSand(rakedSand, rakedState, level, pos, false);
                    context.getItemInHand().hurtAndBreak(1, player, (p_43122_) -> p_43122_.broadcastBreakEvent(context.getHand()));
                }

                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
            }
        }
    }

    public static void registerBlockPlacing() {
        putIfNotNull(ITEM_PLACE_MAP, DMHUtils.getPotentialItem(DMHUtils.Constants.WARDENZOLA),
                DMHConfig.COMMON.wheelifiedWardenzola,
                (BlockItem) DMHBlocks.WARDENZOLA.get().asItem());
        putIfNotNull(ITEM_PLACE_MAP, Items.BONE, DMHConfig.COMMON.shouldPlaceBonePilesWithNormalBones,
                (BlockItem) DMHUtils.getPotentialItem(DMHUtils.Constants.JNE, "bone_rod"));
    }

    public static void registerHoeTills() {
        TILL_MAP.put(Blocks.FARMLAND, Blocks.DIRT);
        TILL_MAP.put(ModBlocks.RICH_SOIL_FARMLAND.get(), ModBlocks.RICH_SOIL.get());
        TILL_MAP.put(ModRegistry.RAKED_GRAVEL.get(), Blocks.GRAVEL);

        if (DMHConfig.COMMON.hoesRakeSand.get()) {
            putIfNotNull(TILL_MAP, DMHUtils.getPotentialBlock(DMHUtils.Constants.RAKED_SAND), Blocks.SAND);
            putIfNotNull(TILL_MAP, DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_RAKED_SAND), Blocks.RED_SAND);
            putIfNotNull(TILL_MAP, DMHBlocks.ARID_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.ARID_SAND));
            putIfNotNull(TILL_MAP, DMHBlocks.RED_ARID_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_ARID_SAND));
            putIfNotNull(TILL_MAP, DMHBlocks.ASHEN_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.ASHEN_SAND));
        }
    }

    public static void registerUnRust() {
        if (DMHUtils.alexCavesLoaded()) DMHACCompat.registerUnRust();
    }

    public static void registerRakeables() {
        if (ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS)) {
            Block arid_sand = DMHUtils.getPotentialBlock(DMHUtils.Constants.ARID_SAND);
            Block red_arid_sand = DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_ARID_SAND);
            Block ashen_sand = DMHUtils.getPotentialBlock(DMHUtils.Constants.ASHEN_SAND);

            Block raked_sand = DMHUtils.getPotentialBlock(DMHUtils.Constants.RAKED_SAND);
            Block raked_red_sand = DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_RAKED_SAND);

            putIfNotNull(RAKE_MAP, arid_sand, DMHBlocks.ARID_RAKED_SAND.get());
            putIfNotNull(RAKE_MAP, red_arid_sand, DMHBlocks.RED_ARID_RAKED_SAND.get());
            putIfNotNull(RAKE_MAP, ashen_sand, DMHBlocks.ASHEN_RAKED_SAND.get());
            putIfNotNull(RAKE_MAP, Blocks.SAND, raked_sand);
            putIfNotNull(RAKE_MAP, Blocks.RED_SAND, raked_red_sand);
        }
    }

    private static <A, B> void putIfNotNull(Map<A, B> map, A key, B val) {
        if (key != null && val != null) map.put(key, val);
    }

    private static <A, B, C> void putIfNotNull(Map<A, Pair<B, C>> map, A key, B val, C thing) {
        if (key != null && val != null && thing != null) map.put(key, Pair.of(val, thing));
    }


    public static BlockState copyStates(BlockState first, BlockState template) {
        for (Property prop : template.getProperties()) {
            first = first.hasProperty(prop) ? first.setValue(prop, template.getValue(prop)) : first;
        }
        return first;
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
        if (!DMHConfig.COMMON.doUntillableFarmland.get()) return;

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
        if (!DMHUtils.alexCavesLoaded()) return;

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (stack.canPerformAction(ToolActions.AXE_SCRAPE) && UNRUST_MAP.containsKey(state.getBlock())) {
            for (Direction dir : Direction.Plane.HORIZONTAL)
                if (DMHACCompat.isAcid(level.getBlockState(pos.relative(dir)))) return;


            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockState newState = UNRUST_MAP.get(state.getBlock()).defaultBlockState();

            newState = copyStates(newState, state);

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }

            level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);

            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS);
            DMHPacketHandler.CHANNEL.send(PacketDistributor.DIMENSION.with(() -> level.dimension()), new S2CRustScrapePacket(pos));

            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            if (player != null) {
                stack.hurtAndBreak(1, player, (p_150686_) -> p_150686_.broadcastBreakEvent(event.getHand()));
            }
            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }

    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMHConfig.COMMON.killBulletPepperPlacement.get() || !ModList.get().isLoaded(DMHUtils.Constants.MY_NETHERS_DELIGHT))
            return;

        ItemStack stack = event.getItemStack();
        Item bulletPepper = DMHUtils.getPotentialItem(DMHUtils.Constants.BULLET_PEPPER);
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(Event.Result.DENY);
        }
    }

    private static void potStrawberry(PlayerInteractEvent.RightClickBlock event) {
        if (ModList.get().isLoaded(DMHUtils.Constants.NEAPOLITAN)) {
            ItemStack stack = event.getItemStack();
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);

            Item strawberryPip = DMHUtils.getPotentialItem(DMHUtils.Constants.STRAWBERRY_PIPS);
            if (state.is(Blocks.FLOWER_POT) && stack.is(strawberryPip)) {
                BlockState newState = (DMHNeapolitanCompat.isWhiteStrawberry(level, pos) ?
                        DMHBlocks.POTTED_WHITE_STRAWBERRIES : DMHBlocks.POTTED_STRAWBERRIES).get().defaultBlockState();

                level.setBlock(pos, newState, 3);
                player.awardStat(Stats.POT_FLOWER);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                level.playSound(player, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
            }
        }
    }

    private static BlockPlaceContext fromEvent(PlayerInteractEvent.RightClickBlock event) {
        return new BlockPlaceContext(event.getEntity(), event.getHand(), event.getItemStack(), event.getHitVec());
    }
}
