package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import umpaz.brewinandchewin.common.block.CoasterBlock;

@Pseudo
@Mixin(CoasterBlock.class)
public class CoasterBlockMixin extends Block {
    @Shadow @Final public static BooleanProperty INVISIBLE;

    @Shadow @Final public static IntegerProperty SIZE;

    public CoasterBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext p_60471_) {
        return state.getValue(INVISIBLE) && state.getValue(SIZE) == 0;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        super.tick(state, level, pos, source);
        if (state.getValue(INVISIBLE) && state.getValue(SIZE) == 0) {
            level.removeBlock(pos, false);
        }
    }
}
