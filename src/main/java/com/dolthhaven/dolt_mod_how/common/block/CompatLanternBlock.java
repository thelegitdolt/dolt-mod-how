package com.dolthhaven.dolt_mod_how.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CompatLanternBlock extends LanternBlock {

    // Credit: Sheddmer
    protected static final VoxelShape AABB = Shapes.or(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D), Block.box(5.0D, 11.0D, 5.0D, 11.0D, 13.0D, 11.0D));
    protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D), Block.box(5.0D, 11.0D, 5.0D, 11.0D, 13.0D, 11.0D));


    public CompatLanternBlock(Properties props) {
        super(props);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_AABB : AABB;
    }
}
