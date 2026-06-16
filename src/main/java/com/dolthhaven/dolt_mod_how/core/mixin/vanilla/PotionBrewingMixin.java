package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @WrapWithCondition(method = "addMix", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private static <E> boolean DoltModHow$NoMoreAwkwardness(List<E> instance, E e, @Local(argsOnly = true, ordinal = 1) Potion potion) {
        return !(DMHConfig.COMMON.noMoreAwkwardPotions.get()) || (potion != Potions.AWKWARD);
    }

    @ModifyArgs(method = "addMix", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Mix;<init>(Lnet/minecraftforge/registries/IForgeRegistry;Ljava/lang/Object;Lnet/minecraft/world/item/crafting/Ingredient;Ljava/lang/Object;)V"))
    private static void DoltModHow$NoMoreAwkwardness(Args args) {
        if (args.get(0) == Potions.AWKWARD && DMHConfig.COMMON.noMoreAwkwardPotions.get()) {
            args.set(0, Potions.WATER);
        }
    }
}
