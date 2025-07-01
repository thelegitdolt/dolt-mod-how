package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin {
    @Shadow @Final public ProtectionEnchantment.Type type;

    @Inject(method = "getDamageProtection", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$IncreaseDamageYay(int level, DamageSource source, CallbackInfoReturnable<Integer> cir) {
        if (!DMHConfig.COMMON.damageReductions.get()) {
            return;
        }

        else if (this.type == ProtectionEnchantment.Type.EXPLOSION && source.is(DamageTypeTags.IS_EXPLOSION)) {
            return;
        }
        else if (this.type == ProtectionEnchantment.Type.FIRE && source.is(DamageTypeTags.IS_FIRE)) {
            return;
        }
        else if (this.type == ProtectionEnchantment.Type.PROJECTILE && source.is(DamageTypeTags.IS_PROJECTILE)) {
            return;
        }
        else if (this.type == ProtectionEnchantment.Type.FIRE && source.is(DamageTypeTags.IS_FIRE)) {
            return;
        }
        else if (this.type == ProtectionEnchantment.Type.FALL && source.is(DamageTypeTags.IS_FALL)) {
            cir.setReturnValue(level * 5);
        }

        if (level > 2 && this.type != ProtectionEnchantment.Type.FALL) {
            cir.setReturnValue(1);
        }
    }
}
