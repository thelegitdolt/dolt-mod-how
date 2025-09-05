package com.dolthhaven.dolt_mod_how.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class NetworkChestBlock extends AbstractChestBlock<NetworkChestBlockEntity> {
    protected NetworkChestBlock(Properties properties, Supplier<BlockEntityType<? extends NetworkChestBlockEntity>> chestEntity) {
        super(properties, chestEntity);
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState p_48679_, Level p_48680_, BlockPos p_48681_, boolean p_48682_) {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }


}
