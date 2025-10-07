package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.common.item.RecoveryCompassItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemProperties.class)
public class ItemPropertiesMixin {
    @Inject(method = "net/minecraft/client/renderer/item/ItemProperties.lambda$static$12(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/core/GlobalPos;",
    at = @At("RETURN"), cancellable = true)
    private static void thing(ClientLevel level, ItemStack stack, Entity entity, CallbackInfoReturnable<GlobalPos> cir) {
        if (RecoveryCompassItem.isLocked(stack)) {
            cir.setReturnValue(RecoveryCompassItem.getLockedPosition(stack.getTag()));
        }
    }
}
