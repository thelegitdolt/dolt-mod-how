package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {
    @Inject(method = "thunderHit", at = @At("HEAD"))
    private void DoltModHow$ChargedCreeperDontDespawn(ServerLevel p_32286_, LightningBolt p_32287_, CallbackInfo ci) {
        Creeper me = (Creeper) (Object) this;
        me.setPersistenceRequired();
    }
}
