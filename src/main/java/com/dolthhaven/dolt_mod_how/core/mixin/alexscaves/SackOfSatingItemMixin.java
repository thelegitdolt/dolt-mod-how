package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.github.alexmodguy.alexscaves.server.item.SackOfSatingItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(SackOfSatingItem.class)
public class SackOfSatingItemMixin {
    @WrapOperation(method = "inventoryTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
    private void DoltModHow$SackOfSatingNoLongerRestoresSaturation(FoodData instance, int nut, float sat, Operation<Void> original) {
        if (sat > 0 && DMHConfig.COMMON.sackOfSatingNoRestoreSat.get()) {
            original.call(instance, nut, 0f);
        } else original.call(instance, nut, sat);
    }
}
