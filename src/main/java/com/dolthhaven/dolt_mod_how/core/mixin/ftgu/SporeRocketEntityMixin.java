package com.dolthhaven.dolt_mod_how.core.mixin.ftgu;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHSnSCompat;
import com.ninni.ftgu.server.entity.subentities.SporeRocketEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(SporeRocketEntity.class)
public abstract class SporeRocketEntityMixin extends Entity {
    public SporeRocketEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "explode", at = @At(value = "INVOKE", target = "Lcom/ninni/ftgu/server/entity/subentities/BaseSporeEntity;explode()V"), remap = false)
    private void DoltModHow$SporeRocketHasSpore(CallbackInfo ci) {
        if (ModList.get().isLoaded(DMHUtils.Constants.SAVAGE_AND_RAVAGE) && DMHConfig.COMMON.doltChargedCreeperTweaks.get())
            DMHSnSCompat.makeSporeCloud(this, 2, 2);
    }
}
