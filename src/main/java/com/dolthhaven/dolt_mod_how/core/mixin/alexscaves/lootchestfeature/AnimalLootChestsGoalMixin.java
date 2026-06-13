package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves.lootchestfeature;

import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.dolthhaven.dolt_mod_how.core.other.FleeingHolder;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHSpawnCompat;
import com.github.alexmodguy.alexscaves.server.entity.ai.AnimalLootChestsGoal;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(AnimalLootChestsGoal.class)
public abstract class AnimalLootChestsGoalMixin extends MoveToBlockGoal {
    public AnimalLootChestsGoalMixin(PathfinderMob p_25609_, double p_25610_, int p_25611_) {
        super(p_25609_, p_25610_, p_25611_);
    }

    @Shadow public abstract void stop();

    @Shadow @Final private Animal entity;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true, remap = false)
    private void DoltModHow$CannotUseWhenScared(CallbackInfoReturnable<Boolean> cir) {
        if (!DMHTrackedData.getVallumraptorChestData(this.entity)) cir.setReturnValue(false);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/navigation/PathNavigation;stop()V", shift = At.Shift.AFTER), remap = false, cancellable = true)
    private void DoltModHow$PreventChestOpening(CallbackInfo ci, @Local BlockEntity blockEntity) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.SPAWN)) return;
        if (!DMHSpawnCompat.hasOctopus(blockEntity)) return;

        if (this.entity.level() instanceof ServerLevel serverLevel) {
            RandomSource rand = this.entity.getRandom();
            for(int i = 0; i < 30; ++i) {
                serverLevel.sendParticles(ParticleTypes.SQUID_INK,
                        entity.position().x + rand.nextGaussian() * 0.5D, entity.position().y + 1.5 + rand.nextGaussian() * 0.5D, entity.position().z + rand.nextGaussian() * 0.5F,
                        0, 0.0F, 0.0F, 0.0F, 0.1F);
            }

            this.entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
            this.entity.playSound(DMHSpawnCompat.octopusSquirtSound(), 1.0F, 1.0F);
            if (this.entity instanceof FleeingHolder fleer) {
                this.stop();
                fleer.flee(this.blockPos);
            }
        }
        ci.cancel();
    }
}
