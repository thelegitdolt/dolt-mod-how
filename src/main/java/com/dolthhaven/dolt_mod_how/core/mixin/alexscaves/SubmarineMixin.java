package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SubmarineEntity.class)
public class SubmarineMixin {
    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/SubmarineEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private void DoltModHow$SameSoundEffectPlease(SubmarineEntity instance, SoundEvent soundEvent, float v, float w, Operation<Void> original) {
        if (soundEvent == ACSoundRegistry.SUBMARINE_REPAIR.get()) {
            SoundEvent newEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(DMHUtils.Constants.CAVERNS_AND_CHASMS, "entity.copper_golem.repair"));
            soundEvent = newEvent == null ? soundEvent : newEvent;
        }
        original.call(instance, soundEvent, v, w);
    }
}
