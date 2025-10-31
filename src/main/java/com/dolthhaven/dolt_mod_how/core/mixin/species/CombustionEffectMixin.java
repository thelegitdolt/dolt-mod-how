package com.dolthhaven.dolt_mod_how.core.mixin.species;

import com.ninni.species.server.events.ModEvents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(ModEvents.class)
public class CombustionEffectMixin {
    @ModifyArg(method = "onLivingHurt", index = 5,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction" +
                            ";)Lnet/minecraft/world/level/Explosion;")
    )
    private Level.ExplosionInteraction thing(Level.ExplosionInteraction thing) {
        return Level.ExplosionInteraction.NONE;
    }
}

