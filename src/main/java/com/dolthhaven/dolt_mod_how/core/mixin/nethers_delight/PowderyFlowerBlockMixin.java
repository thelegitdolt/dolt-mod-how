package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.soytutta.mynethersdelight.common.block.PowderyFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
        Item item = DMHUtils.getPotentialItem(DMHUtils.Constants.MY_NETHERS_DELIGHT, "powder_cannon");

        if (item == null) return;
        if (DMHConfig.COMMON.killBulletPepperPlacement.get()) cir.setReturnValue(new ItemStack(item));
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean bool) {
        if (DMHConfig.COMMON.killBulletPepperPlacement.get()) return false;
        else return super.isValidBonemealTarget(level, pos, state, bool);
    }
}
