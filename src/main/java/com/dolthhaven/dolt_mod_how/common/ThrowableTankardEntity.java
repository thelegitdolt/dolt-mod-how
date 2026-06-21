package com.dolthhaven.dolt_mod_how.common;

import com.dolthhaven.dolt_mod_how.core.registry.DMHEntities;
import com.dolthhaven.dolt_mod_how.core.registry.DMHSounds;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import umpaz.brewinandchewin.common.registry.BnCItems;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ThrowableTankardEntity extends ThrowableItemProjectile {
    public ThrowableTankardEntity(EntityType<? extends ThrowableTankardEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrowableTankardEntity(Level level, LivingEntity entity) {
        super(DMHEntities.TANKARD.get(), entity, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return BnCItems.TANKARD.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        ItemStack entityStack = new ItemStack(this.getDefaultItem());
        if (id == 3) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, entityStack);

            for (int i = 0; i < 12; ++i) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F, ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F + (double) 0.1F, ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
        this.playSound(DMHSounds.TANKARD_HIT.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.playSound(DMHSounds.TANKARD_HIT.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.discard();
        }
    }
}
