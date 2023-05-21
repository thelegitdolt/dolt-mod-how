package com.dolthhaven.dolt_mod_how.core.mixin;

import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlavoredCakeBlock.class)
public class FlavoredCakeBlockMixin {
    @Inject(method = "Lcom/teamabnormals/neapolitan/common/block/FlavoredCakeBlock;eatSlice(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/InteractionResult;", at = @At(value =  "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"))
    private void DoltModHow$CakeSound(LevelAccessor level, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<InteractionResult> cir) {
        level.playSound(player, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1.0f, 0.8f + level.getRandom().nextFloat() * 0.4F);
    }
}
