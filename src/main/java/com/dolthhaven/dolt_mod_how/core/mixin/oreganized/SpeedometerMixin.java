package com.dolthhaven.dolt_mod_how.core.mixin.oreganized;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import galena.oreganized.content.item.SpeedometerItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpeedometerItem.class)
public class SpeedometerMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private void sex(ItemCooldowns instance, Item item, int ticks, Operation<Void> original, @Local(argsOnly = true) Player player) {
        original.call(instance, item, ticks);
        if (player instanceof ServerPlayer serverPlayer) {
//            DMHCriteriaTriggers.SPEEDOMETER_SPEED.trigger(serverPlayer, (float) serverPlayer.getDeltaMovement().length());
        }
    }
}
