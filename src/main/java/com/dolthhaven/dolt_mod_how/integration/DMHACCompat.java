package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.AcidBlock;
import com.github.alexmodguy.alexscaves.server.block.DinosaurChopBlock;
import com.github.alexmodguy.alexscaves.server.block.ThinBoneBlock;
import com.github.alexmodguy.alexscaves.server.block.blockentity.MetalBarrelBlockEntity;
import com.github.alexmodguy.alexscaves.server.enchantment.ACEnchantmentRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.DesolateDaggerEntity;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.RadioactiveOnDestroyedBlockItem;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.ModList;

import java.util.Objects;

public class DMHACCompat {

    public static boolean explodePricklyCan(ItemStack stack, int index, RandomizableContainerBlockEntity can) {
        if (stack.getItem() instanceof RadioactiveOnDestroyedBlockItem radioactiveItem) {
            Level level = can.getLevel();
            BlockPos pos = can.getBlockPos();
            if (level != null) {
                can.removeItem(index, index);
                radioactiveItem
                        .onDestroyed(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack),
                                level.damageSources().cactus());
                level.destroyBlock(pos, true);
                return true;
            }
        }

        return false;
    }

    public static boolean isMetalBarrel(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof MetalBarrelBlockEntity;
    }

    public static Item getMeatItem(Block block) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.CAVE_DELIGHT)) return null;

        String path;
        if (block == ACBlockRegistry.COOKED_DINOSAUR_CHOP.get()) {
            path = "cooked_dino_cut";
        }
        else if (block == ACBlockRegistry.DINOSAUR_CHOP.get()){
            path = "raw_dino_cut";
        }
        else {
            return null;
        }
        return DMHUtils.getPotentialItem(new ResourceLocation(DMHUtils.Constants.CAVE_DELIGHT, path));
    }

    public static ItemStack getMeatDropWhenBroken(BlockState state) {
        if (state.getBlock() instanceof DinosaurChopBlock block) {
            int count = 4 - state.getValue(DinosaurChopBlock.BITES);
            Item item = getMeatItem(block);
            if (item == null) {
                return null;
            }
            return new ItemStack(item, count);
        }
        return null;
    }

    public static BlockState exhaustOneBite(BlockState state) {
        int bites = state.getValue(DinosaurChopBlock.BITES);
        if (bites == 3) {
            return ACBlockRegistry.THIN_BONE.get().defaultBlockState().setValue(ThinBoneBlock.AXIS, (state.getValue(DinosaurChopBlock.FACING)).getAxis());
        }
        else {
            return state.setValue(DinosaurChopBlock.BITES, bites + 1);
        }
    }

    public static void registerUnRust() {
        if (ModList.get().isLoaded(DMHUtils.Constants.ALEXS_CAVES)) {
            DMHRightClickEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL.get(), ACBlockRegistry.SCRAP_METAL.get());
            DMHRightClickEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL_PLATE.get(), ACBlockRegistry.SCRAP_METAL_PLATE.get());
            DMHRightClickEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_BARREL.get(), ACBlockRegistry.METAL_BARREL.get());
            DMHRightClickEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCAFFOLDING.get(), ACBlockRegistry.METAL_SCAFFOLDING.get());
            DMHRightClickEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_REBAR.get(), ACBlockRegistry.METAL_REBAR.get());
        }
    }


    public static ItemStack makeMapFromString(ResourceLocation loc) {
        ItemStack map = new ItemStack(ACItemRegistry.CAVE_MAP.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("BiomeTargetResourceKey", loc.toString());
        map.setTag(tag);
        return map;
    }

    public static boolean isAcid(BlockState state) {
        return state.getBlock() instanceof AcidBlock;
    }

    public static void summonHoveringKnives(Player player, LivingEntity entity, ItemStack stack, int doubleStab, int delayedLevel) {
        for(int i = 0; i < 1 + doubleStab; ++i) {
            DesolateDaggerEntity daggerEntity = ACEntityRegistry.DESOLATE_DAGGER.get().create(player.level());
            daggerEntity.setTargetId(entity.getId());
            daggerEntity.copyPosition(player);
            daggerEntity.setItemStack(stack);
            daggerEntity.orbitFor = (delayedLevel > 0 ? 40 : 20) + player.getRandom().nextInt(10);
            player.level().addFreshEntity(daggerEntity);
        }
    }

    public static boolean isDesolateDagger(Item item) {
        return item == ACItemRegistry.DESOLATE_DAGGER.get();
    }

    public static void saveToCleaver(Entity entity, ItemStack stack) {
        IDataManager cleaverData = (IDataManager) entity;
        cleaverData.setValue(DMHTrackedData.IS_DESOLATE_DAGGER, true);
        cleaverData.setValue(DMHTrackedData.LEVEL_IMPENDING_STAB, (byte) stack.getEnchantmentLevel(ACEnchantmentRegistry.IMPENDING_STAB.get()));
        cleaverData.setValue(DMHTrackedData.LEVEL_DOUBLE_STAB, (byte) stack.getEnchantmentLevel(ACEnchantmentRegistry.DOUBLE_STAB.get()));

    }
}
