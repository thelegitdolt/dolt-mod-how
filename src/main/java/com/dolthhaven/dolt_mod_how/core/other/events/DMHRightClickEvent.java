package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHRightClickEvent {
    public static final Map<Block, Block> RAKE_MAP = new HashMap<>();
    public static final Map<Block, Block> TILL_MAP = new HashMap<>();

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockHitResult result = event.getHitVec();
        ItemStack stack = event.getItemStack();

        handleAlphacenePath(event);
        handleUntillFarmland(event);
    }




    public static void registerHoeTills() {
//        TILL_MAP.put(Blocks.FARMLAND, Blocks.DIRT);
//        TILL_MAP.put(ModBlocks.RICH_SOIL_FARMLAND.get(), ModBlocks.RICH_SOIL.get());
//        TILL_MAP.put(ModRegistry.RAKED_GRAVEL.get(), Blocks.GRAVEL);
//
//        if (DMHConfig.COMMON.hoesRakeSand.get()) {
//            putIfNotNull(TILL_MAP, DMHUtils.getPotentialBlock(DMHUtils.Constants.RAKED_SAND), Blocks.SAND);
//            putIfNotNull(TILL_MAP, DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_RAKED_SAND), Blocks.RED_SAND);
//            putIfNotNull(TILL_MAP, DMHBlocks.ARID_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.ARID_SAND));
//            putIfNotNull(TILL_MAP, DMHBlocks.RED_ARID_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.RED_ARID_SAND));
//            putIfNotNull(TILL_MAP, DMHBlocks.ASHEN_RAKED_SAND.get(), DMHUtils.getPotentialBlock(DMHUtils.Constants.ASHEN_SAND));
//        }
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

    private static BlockPlaceContext fromEvent(PlayerInteractEvent.RightClickBlock event) {
        return new BlockPlaceContext(event.getEntity(), event.getHand(), event.getItemStack(), event.getHitVec());
    }
}
