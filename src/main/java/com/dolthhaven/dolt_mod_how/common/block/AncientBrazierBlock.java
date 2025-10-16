package com.dolthhaven.dolt_mod_how.common.block;

import com.teamabnormals.caverns_and_chasms.common.block.BrazierBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AncientBrazierBlock extends BrazierBlock {
    public AncientBrazierBlock(Properties properties) {
        super(0, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
    }
}
