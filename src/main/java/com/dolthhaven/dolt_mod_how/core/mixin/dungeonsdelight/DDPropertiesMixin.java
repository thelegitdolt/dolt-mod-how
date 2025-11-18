package com.dolthhaven.dolt_mod_how.core.mixin.dungeonsdelight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.yirmiri.dungeonsdelight.common.util.DDProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Mixin(DDProperties.class)
public class DDPropertiesMixin {
    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Rarity;create(Ljava/lang/String;Ljava/util/function/UnaryOperator;)Lnet/minecraft/world/item/Rarity;"))
    private static Rarity ImVeryMean(String name, UnaryOperator<Style> styleModifier, Operation<Rarity> original) {
        return Rarity.COMMON;
    }
}
