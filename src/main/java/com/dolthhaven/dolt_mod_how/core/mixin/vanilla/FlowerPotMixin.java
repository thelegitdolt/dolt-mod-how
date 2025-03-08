package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
}
