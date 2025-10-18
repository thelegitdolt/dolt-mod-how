package com.dolthhaven.dolt_mod_how.core.mixin.dungeonsdelight;

import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.yirmiri.dungeonsdelight.common.entity.misc.CleaverEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CleaverEntity.class)
public abstract class CleaverEntityMixin extends AbstractArrow {

    @Shadow public ItemStack cleaverItem;

    protected CleaverEntityMixin(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    @Shadow public abstract void playerTouch(Player player);

    @WrapOperation(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V"))
    private void thing(LivingEntity living, Entity owner, Operation<Void> original) {
        original.call(living, owner);

        IDataManager cleaverData = (IDataManager) this;
        boolean desolateDagger = cleaverData.getValue(DMHTrackedData.IS_DESOLATE_DAGGER);
        byte doubleStab = cleaverData.getValue(DMHTrackedData.LEVEL_DOUBLE_STAB);
        byte impendingStab = cleaverData.getValue(DMHTrackedData.LEVEL_IMPENDING_STAB);

        if (desolateDagger) {
            DMHACCompat.summonHoveringKnives((Player) owner, living, cleaverItem, doubleStab, impendingStab);
        }
    }
}
