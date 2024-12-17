package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.soytutta.mynethersdelight.common.block.PowderyFlowerBlock;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderyFlowerBlock.class)
public class PowderyFlowerBlockMixin extends BambooSaplingBlock {
    public PowderyFlowerBlockMixin(Properties p_48957_) {
        super(p_48957_);
    }

    @Inject(method = "getCloneItemStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void DoltModHow$CopyPowderyCaneLol(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player, CallbackInfoReturnable<ItemStack> cir) {
        if (DMHConfig.COMMON.killBulletPepperPlacement.get()) cir.setReturnValue(new ItemStack(MNDItems.POWDER_CANNON.get()));
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean bool) {
        if (DMHConfig.COMMON.killBulletPepperPlacement.get()) return false;
        else return super.isValidBonemealTarget(level, pos, state, bool);
    }
}
