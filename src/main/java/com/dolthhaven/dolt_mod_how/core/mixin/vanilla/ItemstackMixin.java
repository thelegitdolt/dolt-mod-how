package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.common.entity.ThrowableTankardEntity;
import com.dolthhaven.dolt_mod_how.core.registry.DMHSounds;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHBnCCompat;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemstackMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sex(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack handStack = player.getItemInHand(hand);
        if (DMHUtils.Constants.BnC_LOADED.get() && DMHBnCCompat.canShootTankard(player, handStack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), DMHSounds.TANKARD_SHOOTS.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                ThrowableTankardEntity tankard = new ThrowableTankardEntity(level, player);
                tankard.setItem(handStack);
                tankard.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 4F);
                level.addFreshEntity(tankard);
            }

            player.awardStat(Stats.ITEM_USED.get(handStack.getItem()));
            if (!player.getAbilities().instabuild) {
                handStack.shrink(1);
            }

            cir.setReturnValue(InteractionResultHolder.sidedSuccess(handStack, level.isClientSide()));
        }
    }
}
