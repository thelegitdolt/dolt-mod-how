package com.dolthhaven.dolt_mod_how.core;

import com.dolthhaven.dolt_mod_how.core.registry.DMHRecipeSerializer;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShulkerBoxColoring;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.fml.ModList;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DyeDepotShulkerColoring extends ShulkerBoxColoring {
    public DyeDepotShulkerColoring(ResourceLocation p_248647_, CraftingBookCategory p_250756_) {
        super(p_248647_, p_250756_);
    }

    @Override
    public boolean matches(CraftingContainer p_44324_, Level p_44325_) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.DYE_DEPOT)) return false;

        int i = 0;
        int j = 0;

        for(int k = 0; k < p_44324_.getContainerSize(); ++k) {
            ItemStack itemstack = p_44324_.getItem(k);
            if (!itemstack.isEmpty()) {
                if (Block.byItem(itemstack.getItem()) instanceof ShulkerBoxBlock) {
                    ++i;
                } else {
                    if (DyeDepotCompat.getDyeDepotDyeColor(itemstack.getItem()) == null) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack shulker = ItemStack.EMPTY;
        String dye = null;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                String dyeColor = DyeDepotCompat.getDyeDepotDyeColor(item);
                if (dyeColor != null) {
                    dye = dyeColor;
                }
                else if (Block.byItem(item) instanceof ShulkerBoxBlock) {
                    shulker = stack;
                }
            }
        }

        ItemStack newShulker = new ItemStack(DMHUtils.getPotentialItem(DMHUtils.Constants.DYE_DEPOT, dye + "_shulker_box"));
        if (shulker.hasTag()) {
            newShulker.setTag(shulker.getTag().copy());
        }

        return newShulker;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DMHRecipeSerializer.DYE_DEPOT_SHULKER_COLORING.get();
    }
}
