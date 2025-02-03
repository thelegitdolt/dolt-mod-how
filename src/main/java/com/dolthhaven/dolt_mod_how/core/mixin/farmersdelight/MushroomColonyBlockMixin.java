package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

@Mixin(MushroomColonyBlock.class)
public class MushroomColonyBlockMixin extends BushBlock {

    public MushroomColonyBlockMixin(Properties p_51021_) {
        super(p_51021_);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
