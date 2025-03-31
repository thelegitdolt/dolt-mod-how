package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CakeBlock.class)
public class CakeMixin {
    @WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getFoodData()Lnet/minecraft/world/food/FoodData;"))
    private static FoodData DoltModHow$PlaySoundEat(Player instance, Operation<FoodData> original) {
        instance.playSound(SoundEvents.GENERIC_EAT);
        return original.call(instance);
    }
}
