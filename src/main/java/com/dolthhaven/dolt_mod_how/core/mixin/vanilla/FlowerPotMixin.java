package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerPotBlock.class)
public class FlowerPotMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
    private void DoltModHow$FlowerPotPutStuffBack(Player instance, InteractionHand interactionHand, ItemStack itemStack, Operation<Void> original) {
        if (DMHConfig.COMMON.lessAnnoyingItemReclaim.get()) {
            if (!instance.getInventory().add(itemStack)) {
                instance.drop(itemStack, true);
            }
        }
        else {
            original.call(instance, interactionHand, itemStack);
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;)V"))
    private void DoltModHow$FlowerPotPlaySoundYay(BlockState p_53540_, Level level, BlockPos p_53542_, Player player, InteractionHand p_53544_, BlockHitResult result, CallbackInfoReturnable<InteractionResult> cir) {
        level.playSound(player, result.getBlockPos(), SoundEvents.MOSS_PLACE, SoundSource.BLOCKS);
    }
}
