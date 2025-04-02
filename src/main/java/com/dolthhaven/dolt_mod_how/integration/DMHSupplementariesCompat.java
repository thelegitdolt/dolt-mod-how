package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.mehvahdjukaar.moonlight.api.set.BlocksColorAPI;
import net.mehvahdjukaar.moonlight.core.set.BlocksColorInternal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;

import java.util.EnumMap;
import java.util.List;

public class DMHSupplementariesCompat {
    private static void block(EnumMap<DyeColor, Block> map, DyeColor color, String string) {
        map.put(color, DMHUtils.getPotentialBlock(new ResourceLocation(string)));
    }

    private static void item(EnumMap<DyeColor, Item> map, DyeColor color, String string) {
        map.put(color, DMHUtils.getPotentialItem(new ResourceLocation(string)));
    }

    public static void registerClayworksStuff() {
        if (!ModList.get().isLoaded(DMHUtils.Constants.CLAYWORKS)) {
            return;
        }

        List<String> dyeDepot = List.of("rose", "maroon", "ginger", "tan", "beige", "coral", "olive", "forest", "verdant", "amber", "teal", "mint", "aqua", "slate", "navy", "indigo");

        EnumMap<DyeColor, Block> concretes = new EnumMap<>(DyeColor.class), concretePowders = new EnumMap<>(DyeColor.class);
        EnumMap<DyeColor, Item> concretesItems = new EnumMap<>(DyeColor.class), concretesPowderItems = new EnumMap<>(DyeColor.class);

        for (DyeColor val : BlocksColorInternal.VANILLA_COLORS) {
            block(concretes, val, val + "_concrete");
            block(concretePowders, val, val + "_concrete_powder");
            item(concretesItems, val, val + "_concrete");
            item(concretesPowderItems, val, val + "_concrete_powder");
        }

        if (ModList.get().isLoaded(DMHUtils.Constants.DYE_DEPOT)) {
            for (DyeColor color : BlocksColorInternal.MODDED_COLORS) {
                if (dyeDepot.contains(color.toString())) {
                    block(concretes, color, DMHUtils.Constants.DYE_DEPOT + ":" + color + "_concrete");
                    block(concretePowders, color, DMHUtils.Constants.DYE_DEPOT + ":" + color + "_concrete_powder");
                    item(concretesItems, color, DMHUtils.Constants.DYE_DEPOT + ":" +  color + "_concrete");
                    item(concretesPowderItems, color, DMHUtils.Constants.DYE_DEPOT + ":" +  color + "_concrete_powder");
                }
            }
        }

        BlocksColorAPI.registerBlockColorSet(new ResourceLocation("minecraft", "concrete"), concretes, DMHUtils.getPotentialBlock(DMHUtils.Constants.CONCRETE));
        BlocksColorAPI.registerBlockColorSet(new ResourceLocation("minecraft", "concrete_powder"), concretePowders, DMHUtils.getPotentialBlock(DMHUtils.Constants.CONCRETE_POWDER));

        BlocksColorAPI.registerItemColorSet(new ResourceLocation("minecraft", "concrete"), concretesItems, DMHUtils.getPotentialItem(DMHUtils.Constants.CONCRETE));
        BlocksColorAPI.registerItemColorSet(new ResourceLocation("minecraft", "concrete_powder"), concretesPowderItems, DMHUtils.getPotentialItem(DMHUtils.Constants.CONCRETE_POWDER));

    }
}
