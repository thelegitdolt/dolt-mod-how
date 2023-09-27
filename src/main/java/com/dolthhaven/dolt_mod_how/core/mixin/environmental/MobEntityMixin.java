package com.dolthhaven.dolt_mod_how.core.mixin.environmental;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
        if (ModList.get().isLoaded("environmental")) {
            double chance = dubs(this.position().distanceTo(new Vec3(0, 0, 0)));

            Item boots = ForgeRegistries.ITEMS.getValue(new ResourceLocation("environmental", "wanderer_boots"));
            if (random.nextDouble() < chance && boots != null) {
                this.setItemSlot(EquipmentSlot.FEET, new ItemStack(boots));
                this.armorDropChances[EquipmentSlot.FEET.getIndex()] = 1.0F;
            }
        }
    }

    @Unique
    private static double dubs(double distance) {
        return ((cubeRoot(distance / 4500.0)) / 30.0)
                + (Math.exp(   -square((distance - 24000.0) / 8000.0 )   ) / 20.0);
    }

    @Unique
    private static double square(double thing) {
        return thing * thing;
    }

    @Unique
    private static double cubeRoot(double thing) {
        return Math.pow(thing, 0.3333);
    }

}