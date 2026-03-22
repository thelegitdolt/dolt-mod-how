package com.dolthhaven.dolt_mod_how.integration;

import net.mehvahdjukaar.amendments.common.block.DoubleCakeBlock;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class AmendmentsBugfix {
    public static Map<String, ResourceLocation> CAKE_SLICE_MAP = Util.make(new HashMap<>(), map -> {
        map.put("neapolitan:vanilla_cake", new ResourceLocation("abnormals_delight", "vanilla_cake_slice"));
        map.put("neapolitan:chocolate_cake", new ResourceLocation("abnormals_delight", "chocolate_cake_slice"));
        map.put("neapolitan:strawberry_cake", new ResourceLocation("abnormals_delight", "strawberry_cake_slice"));
        map.put("neapolitan:banana_cake", new ResourceLocation("abnormals_delight", "banana_cake_slice"));
        map.put("neapolitan:mint_cake", new ResourceLocation("abnormals_delight", "mint_cake_slice"));
        map.put("neapolitan:adzuki_cake", new ResourceLocation("abnormals_delight", "adzuki_cake_slice"));
        map.put("farmersrespite:coffee_cake", new ResourceLocation("farmersrespite", "coffee_cake_slice"));
        map.put("mynethersdelight:magma_cake_block", new ResourceLocation("mynethersdelight", "magma_cake_slice"));
        map.put("dungeonsdelight:monster_cake", new ResourceLocation("dungeonsdelight", "monster_cake_slice"));
    });

    public static boolean isDoubleCakeBlock(BlockState block) {
        return block.getBlock() instanceof DoubleCakeBlock;
    }
}
