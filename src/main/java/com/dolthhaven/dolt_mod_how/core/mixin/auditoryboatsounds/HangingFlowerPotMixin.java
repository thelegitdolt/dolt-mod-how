package com.dolthhaven.dolt_mod_how.core.mixin.auditoryboatsounds;

import net.mehvahdjukaar.supplementaries.common.block.blocks.HangingFlowerPotBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingFlowerPotBlock.class)
public class HangingFlowerPotMixin {
    @Inject(method = "use",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;",
                    shift = At.Shift.BEFORE
            )
    )
    private void DoltModHow$FlowerPotMakesSound(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModList.get().isLoaded("auditory")) {
            level.playSound(player, blockPos, SoundEvents.HANGING_ROOTS_PLACE, SoundSource.BLOCKS, 1.0f, 0.8f + level.random.nextFloat() * 0.4F);
        }
    }
}
