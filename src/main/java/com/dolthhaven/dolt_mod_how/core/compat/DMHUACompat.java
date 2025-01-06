package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;
import static com.dolthhaven.dolt_mod_how.core.util.Util.Constants.*;


public class DMHUACompat {
    public static final Supplier<Block> POTTED_BEACHGRASS = () ->
            new FlowerPotBlock(Util.getPotentialBlock(BEACHGRASS), PropertyUtil.flowerPot());
    public static final Supplier<Block> POTTED_TALL_BEACHGRASS = () ->
            new FlowerPotBlock(Util.getPotentialBlock(TALL_BEACHGRASS), PropertyUtil.flowerPot());


}
