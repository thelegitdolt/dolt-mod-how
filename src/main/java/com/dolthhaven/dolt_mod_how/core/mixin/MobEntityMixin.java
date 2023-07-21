package com.dolthhaven.dolt_mod_how.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {

    @Shadow
    @Final
    protected float[] armorDropChances;


    protected MobEntityMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "populateDefaultEquipmentSlots(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V", at = @At("TAIL"))
    private void DoltModHow$ChanceForWanderingBootsSpawn(RandomSource random, DifficultyInstance difficulty, CallbackInfo info) {
        if (this.getCommandSenderWorld() instanceof ServerLevel) {
            this.position().distanceTo(new Vec3(0, 0, 0));
            double distance = Math.sqrt((this.getOnPos().getX() * this.getOnPos().getX()) + (this.getOnPos().getZ() * this.getOnPos().getZ()));
            double chance = dubs(distance);
            if (random.nextDouble() < chance) {
                this.setItemSlot(EquipmentSlot.FEET, new ItemStack(EnvironmentalItems.WANDERER_BOOTS.get()));
                this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 1.0F;
            }
        }
    }

    private static double dubs(double distance) {
        return Math.min(1, Math.pow((distance / 4500.0), 0.333) / 30.0 + (Math.exp(
                - Math.pow((distance - 24000) / 8000.0, 2)) / 20.0));
    }

}