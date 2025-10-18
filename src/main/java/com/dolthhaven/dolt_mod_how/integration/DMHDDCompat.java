package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.yirmiri.dungeonsdelight.common.entity.misc.CleaverEntity;
import net.yirmiri.dungeonsdelight.common.item.CleaverItem;
import net.yirmiri.dungeonsdelight.core.registry.DDEntities;
import net.yirmiri.dungeonsdelight.core.registry.DDItems;
import net.yirmiri.dungeonsdelight.core.registry.DDSounds;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

public class DMHDDCompat {
    public static void makeCleaverAndThrowIt(ItemStack stack, Player player, Level level, double attackDamage, Consumer<? super CleaverEntity> postOps) {
        CleaverEntity cleaver = new CleaverEntity(DDEntities.CLEAVER.get(), level, player, stack.copy());
        cleaver.setItem(stack.copy());
        applyEffects(stack, cleaver);
        cleaver.setBaseDamage(cleaver.getBaseDamage() + attackDamage);
        cleaver.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, ((CleaverItem) DDItems.NETHERITE_CLEAVER.get()).range, 1.0F);
        postOps.accept(cleaver);
        if (player.getAbilities().instabuild) {
            cleaver.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        level.addFreshEntity(cleaver);
        cleaver.setOwner(player);
        level.playSound(null, cleaver, DDSounds.CLEAVER_THROW.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
    }

    @Unique
    private static void applyEffects(ItemStack stack, CleaverEntity cleaver) {
        int sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
        int fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int ballistic = stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get());



        if (sharpness > 0) {
            cleaver.setBaseDamage(cleaver.getBaseDamage() + (double)sharpness * (double)0.5F + (double)0.5F);
        }

        if (fireAspect > 0) {
            cleaver.setRemainingFireTicks(fireAspect * 40 + cleaver.getRemainingFireTicks());
        }

        if (ballistic > 1) {
            cleaver.setSerratedLevel(ballistic - 1);
        }
    }
}
