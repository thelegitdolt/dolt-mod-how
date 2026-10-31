package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.DinosaurChopBlock;
import com.github.alexmodguy.alexscaves.server.block.ThinBoneBlock;
import com.github.alexmodguy.alexscaves.server.entity.living.NucleeperEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.TremorzillaEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

public class DMHACCompat {
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
}
