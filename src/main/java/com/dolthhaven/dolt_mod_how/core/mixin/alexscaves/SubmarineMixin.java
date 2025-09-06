package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SubmarineEntity.class)
public class SubmarineMixin {
    @ModifyArg(method = "interact", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/SubmarineEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private SoundEvent DoltModHow$SameSoundEffectPlease(SoundEvent soundEvent) {
        if (soundEvent == ACSoundRegistry.SUBMARINE_REPAIR.get()) {
            SoundEvent newEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(DMHUtils.Constants.CAVERNS_AND_CHASMS, "entity.copper_golem.repair"));
            soundEvent = newEvent == null ? soundEvent : newEvent;
        }
        return soundEvent;
    }
}
