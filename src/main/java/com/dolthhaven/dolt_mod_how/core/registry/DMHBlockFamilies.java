package com.dolthhaven.dolt_mod_how.core.registry;

import net.minecraft.data.BlockFamily;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHBlockFamilies {
    public static final BlockFamily ZINC_BRICKS_FAMILY = new BlockFamily.Builder(ZINC_BRICKS.get()).stairs(ZINC_BRICK_STAIRS.get()).slab(ZINC_BRICK_SLAB.get()).wall(ZINC_BRICK_WALL.get()).chiseled(CHISELED_ZINC_BRICKS.get()).getFamily();
}
