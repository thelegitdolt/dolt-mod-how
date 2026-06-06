package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSkeleton.class)
public abstract class SkeletonMixin extends Monster {
    protected SkeletonMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @ModifyReturnValue(method = "getArrow", at = @At("RETURN"), remap = false)
    private AbstractArrow sex(AbstractArrow arrow) {
        if (this.getY() < 20 && this.random.nextInt(3) == 0) {
            if (((AbstractSkeleton) (Object)this) instanceof Skeleton && arrow instanceof Arrow actualArrow) {
                actualArrow.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300));
            }
        }
        return arrow;
    }
}
