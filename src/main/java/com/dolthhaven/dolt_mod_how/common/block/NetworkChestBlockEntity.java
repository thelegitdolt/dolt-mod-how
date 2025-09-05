package com.dolthhaven.dolt_mod_how.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NetworkChestBlockEntity extends BlockEntity implements LidBlockEntity {
    public NetworkChestBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public float getOpenNess(float p_59604_) {
        return 0;
    }
}
