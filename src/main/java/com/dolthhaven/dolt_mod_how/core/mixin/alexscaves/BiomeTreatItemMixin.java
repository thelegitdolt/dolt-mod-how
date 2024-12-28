package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.github.alexmodguy.alexscaves.server.item.BiomeTreatItem;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BiomeTreatItem.class)
public class BiomeTreatItemMixin {
    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I"))
    public int DoltModHow$FoodValueIsAlwaysZeroShutUP(FoodData instance) {
        return DMHConfig.COMMON.actuallyGoodBiomeTreats.get() ? 0 : instance.getFoodLevel();
    }
}