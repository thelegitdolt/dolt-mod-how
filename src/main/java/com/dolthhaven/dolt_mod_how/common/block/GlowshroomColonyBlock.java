package com.dolthhaven.dolt_mod_how.common.block;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.content.world.module.GlimmeringWealdModule;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import javax.annotation.Nonnull;

public class GlowshroomColonyBlock extends MushroomColonyBlock {
    public GlowshroomColonyBlock(Properties properties) {
        super(properties, () -> GlimmeringWealdModule.glow_shroom.asItem());
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(Blocks.DEEPSLATE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState stateIn, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(12) == 0 && worldIn.getBlockState(pos.above()).isAir()) {
            worldIn.addParticle(ParticleTypes.END_ROD, (double)pos.getX() + 0.4 + rand.nextDouble() * 0.2, (double)pos.getY() + 0.5 + rand.nextDouble() * 0.1, (double)pos.getZ() + 0.4 + rand.nextDouble() * 0.2, (Math.random() - 0.5) * 0.04, (1.0 + Math.random()) * 0.02, (Math.random() - 0.5) * 0.04);
        }
    }
}