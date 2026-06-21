package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.common.ThrowableTankardEntity;
import com.dolthhaven.dolt_mod_how.core.registry.DMHSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import umpaz.brewinandchewin.common.registry.BnCItems;

@Mixin(ItemStack.class)
public class ItemstackMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sex(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack thisStack = (ItemStack) (Object) this;  
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(BnCItems.TANKARD.get())) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), DMHSounds.TANKARD_SHOOTS.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                ThrowableTankardEntity tankard = new ThrowableTankardEntity(level, player);
                tankard.setItem(itemstack);
                tankard.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(tankard);
            }

            player.awardStat(Stats.ITEM_USED.get(thisStack.getItem()));
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide()));
        }
    }
}
