package com.dolthhaven.dolt_mod_how.core.mixin;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.MathUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
        int difficultyChance = difficulty.getDifficulty().getId() + 1;

        double distance = Math.sqrt((this.getOnPos().getX() * this.getOnPos().getX()) + (this.getOnPos().getZ() * this.getOnPos().getZ()));
        double chance = difficultyChance * Math.min(Math.sqrt(distance) / 40, Math.log10(distance)) / 200.0;
        if (random.nextDouble() < chance) {

            this.setItemSlot(EquipmentSlot.FEET, new ItemStack(EnvironmentalItems.WANDERER_BOOTS.get()));
            this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 1.0F;
        }
    }

}