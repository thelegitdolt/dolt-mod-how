package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockstateBaseMixin {
    @Shadow public abstract Block getBlock();

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void sex(CallbackInfoReturnable<Boolean> cir) {
        if (this.getBlock() == Blocks.MUD) cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void sex(ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!DMHUtils.alexCavesLoaded() || this.getBlock() != Blocks.MUD) return;
        Block muck = ForgeRegistries.BLOCKS.getValue(DMHUtils.Constants.MUCK);
        boolean randomCheck = random.nextInt(600) == 0;
        boolean isInAbyssalChasm = randomCheck && level.getBiome(pos).is(DMHUtils.Constants.ABYSSAL_CHASMS);
        boolean isReallyDeep = isInAbyssalChasm && pos.getY() < -16;
        boolean waterCheck = isReallyDeep && waterCheck(level, pos, random);
        if (waterCheck) {
            level.setBlock(pos, muck.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Unique
    private static boolean waterCheck(Level level, BlockPos pos, RandomSource random) {
        for (int i = 1; i <= 5; i++) {
            if (!level.getFluidState(pos.above(i)).is(Fluids.WATER)) return false;
        }
        List<Integer> list = Util.make(new ArrayList<>(15), ls -> {
            for (int i = 6; i < 20; i++) ls.add(i);
        });


        for (Integer toSample : sample(list, 5, random)) {
            if (!level.getFluidState(pos.above(toSample)).is(Fluids.WATER)) return false;
        }
        return true;
    }

    @Unique
    private static <E> List<E> sample(List<E> original, int amount, RandomSource random) {
        List<E> sampled = new ArrayList<>();
        List<E> copy = new ArrayList<>(original.size());
        copy.addAll(original);

        for (int i = 0; i < amount; i++) {
            if (copy.isEmpty()) break;
            sampled.add(copy.remove(random.nextInt(copy.size())));
        }
        return sampled;
    }
}
