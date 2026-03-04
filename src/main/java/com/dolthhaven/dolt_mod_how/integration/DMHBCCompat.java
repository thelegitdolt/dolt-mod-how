package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidType;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;
import umpaz.brewinandchewin.common.block.CoasterBlock;
import umpaz.brewinandchewin.common.fluid.AlcoholFluidType;
import umpaz.brewinandchewin.common.registry.BnCBlocks;
import umpaz.brewinandchewin.common.registry.BnCEffects;

import java.util.function.Supplier;

public class DMHBCCompat {
    public static final Supplier<Block> WARDENZOLA = () ->
            new CheeseWheelBlock(DMHItems.WARDENZOLA_WEDGE, BlockBehaviour.Properties.copy(Blocks.CAKE));

    public static final Supplier<FluidType> TEQUILA_FLUID_TYPE = () -> new AlcoholFluidType(0xff3df0f2);

    public static Block coaster() {
        return BnCBlocks.COASTER.get();
    }

    public static BlockState blankCoasterState() {
        return coaster().defaultBlockState().setValue(CoasterBlock.INVISIBLE, true).setValue(CoasterBlock.SIZE, 0);
    }

    public static int tipsyEffectLevel(Player player) {
        MobEffectInstance instance = player.getEffect(BnCEffects.TIPSY.get());
        if (instance == null) return -1;
        else return instance.getAmplifier();
    }
}
