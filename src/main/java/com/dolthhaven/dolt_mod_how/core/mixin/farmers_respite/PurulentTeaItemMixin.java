package com.dolthhaven.dolt_mod_how.core.mixin.farmers_respite;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.farmersrespite.common.item.PurulentTeaItem;

import java.util.List;

import static net.minecraft.world.effect.MobEffects.*;

@Mixin(PurulentTeaItem.class)
public class PurulentTeaItemMixin {

    @Shadow @Final private int effectBoost;

    @Inject(method = "affectConsumer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V",
    at = @At("TAIL"), remap = false)
    private void DoltModHow$PurulentGivesRandomEffects(ItemStack stack, Level level, LivingEntity consumer, CallbackInfo ci) {
        final List<MobEffect> POSSIBLE_EFFECTS = List.of(HUNGER, WEAKNESS, MOVEMENT_SLOWDOWN);

        consumer.addEffect(new MobEffectInstance(POSSIBLE_EFFECTS.get(level.getRandom().nextInt(POSSIBLE_EFFECTS.size())), this.effectBoost, 0));
    }
}
