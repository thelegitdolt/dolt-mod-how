package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.moonlight.api.set.BlocksColorAPI;
import net.mehvahdjukaar.moonlight.core.set.BlocksColorInternal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;

import java.util.EnumMap;
import java.util.function.Function;

public class DMHSupplementariesCompat {
    private static void block(EnumMap<DyeColor, Block> map, DyeColor color, String string) {
        map.put(color, DMHUtils.getPotentialBlock(new ResourceLocation(string)));
    }

    private static void item(EnumMap<DyeColor, Item> map, DyeColor color, String string) {
        map.put(color, DMHUtils.getPotentialItem(new ResourceLocation(string)));
    }

    public static void registerColors() {
        registerConcrete();
        registerEncasedPipe();

    }

    private static void registerConcrete() {
        if (!ModList.get().isLoaded(DMHUtils.Constants.CLAYWORKS)) {
            return;
        }

        var concretes = makeBoth(color -> color + "_concrete", "minecraft", "dye_depot");
        var powders = makeBoth(color -> color + "_concrete_powder", "minecraft", "dye_depot");

        registerBoth(new ResourceLocation("minecraft", "concrete"), concretes, DMHUtils.Constants.CONCRETE);
        registerBoth(new ResourceLocation("minecraft", "concrete_powder"), powders, DMHUtils.Constants.CONCRETE_POWDER);
    }

    private static void registerEncasedPipe() {
        var pipes = makeBoth(color -> color + "_encased_pipe", "dolt_mod_how", "dolt_mod_how");
        registerBoth(new ResourceLocation("dolt_mod_how", "encased_pipe"), pipes, DMHUtils.Constants.ENCASED_PIPE);
    }

    private static void registerBoth(ResourceLocation name, Pair<EnumMap<DyeColor, Block>, EnumMap<DyeColor, Item>> maps, ResourceLocation location) {
        BlocksColorAPI.registerBlockColorSet(name, maps.getFirst(), DMHUtils.getPotentialBlock(location));
        BlocksColorAPI.registerItemColorSet(name, maps.getSecond(), DMHUtils.getPotentialItem(location));
    }

    public static EnumMap<DyeColor, Block> makeDyeMap(Function<String, String> colorIdDoer, String normalId, String dyeDepotId) {
        EnumMap<DyeColor, Block> map = new EnumMap<>(DyeColor.class);
        for (DyeColor val : BlocksColorInternal.VANILLA_COLORS) {
            block(map, val,  normalId + ":" + colorIdDoer.apply(val.getName()));
        }

        if (ModList.get().isLoaded(DMHUtils.Constants.DYE_DEPOT)) {
            for (DyeColor color : BlocksColorInternal.MODDED_COLORS) {
                if (DyeDepotCompat.getDyeDepotDye(color.getName()) != null) {
                    block(map, color, dyeDepotId + ":" + colorIdDoer.apply(color.getName()));
                }
            }
        }

        return map;
    }

    public static Pair<EnumMap<DyeColor, Block>, EnumMap<DyeColor, Item>> makeBoth(Function<String, String> colorIdDoer, String normalId, String dyeDepotId) {
        return Pair.of(makeDyeMap(colorIdDoer, normalId, dyeDepotId), makeItemDyeMap(colorIdDoer, normalId, dyeDepotId));
    }

    public static EnumMap<DyeColor, Item> makeItemDyeMap(Function<String, String> colorIdDoer, String normalId, String dyeDepotId) {
        EnumMap<DyeColor, Item> map = new EnumMap<>(DyeColor.class);
        for (DyeColor val : BlocksColorInternal.VANILLA_COLORS) {
            item(map, val,  normalId + ":" + colorIdDoer.apply(val.getName()));
        }

        if (ModList.get().isLoaded(DMHUtils.Constants.DYE_DEPOT)) {
            for (DyeColor color : BlocksColorInternal.MODDED_COLORS) {
                if (DyeDepotCompat.getDyeDepotDye(color.getName()) != null) {
                    item(map, color, dyeDepotId + ":" + colorIdDoer.apply(color.getName()));
                }
            }
        }

        return map;
    }
}
