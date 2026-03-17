package com.dolthhaven.dolt_mod_how.core.mixin.supplementaries;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mehvahdjukaar.moonlight.api.block.ItemDisplayTile;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemDisplayTile.class)
public class ItemDisplayTileMixin {
    @WrapOperation(method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;I)Lnet/minecraft/world/InteractionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
    private void DoltModHow$FlowerPotPutStuffBack(Player instance, InteractionHand interactionHand, ItemStack itemStack, Operation<Void> original) {
        if (DMHConfig.COMMON.lessAnnoyingItemReclaim.get()) {
            DMHUtils.addToInvOrDrop(instance, itemStack);
        }
        else {
            original.call(instance, interactionHand, itemStack);
        }
    }

}
