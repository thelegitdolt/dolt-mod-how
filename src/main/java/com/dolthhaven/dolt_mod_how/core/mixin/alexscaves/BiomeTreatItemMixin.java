package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.github.alexmodguy.alexscaves.server.item.BiomeTreatItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(BiomeTreatItem.class)
public class BiomeTreatItemMixin {
    @WrapOperation(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I"))
    public int DoltModHow$FoodValueIsAlwaysZeroShutUP(FoodData instance, Operation<Integer> original) {
        if (DMHConfig.COMMON.actuallyGoodBiomeTreats.get()) {
            return 0;
        } else return original.call(instance);
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;canEat(Z)Z"))
    private boolean canAlwaysEat(Player instance, boolean p_36392_, Operation<Boolean> original) {
        return true;
    }
}