package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.caverns_and_chasms.common.item.TetherPotionItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TetherPotionItem.class)
public class TetherPotionMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void DoltModHow$TakeIntoAccountStackables(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack handStack = player.getItemInHand(hand);
        EquipmentSlot slot = Mob.getEquipmentSlotForItem(handStack);
        ItemStack armorStack = player.getItemBySlot(slot);

        if (handStack.getCount() > 1 && !EnchantmentHelper.hasBindingCurse(armorStack)) {
            if (!ItemStack.isSameItemSameTags(handStack, armorStack)) {
                if (!armorStack.isEmpty()) {
                    ItemStack newArmorStack = armorStack.copyAndClear();
                    DMHUtils.addToInvOrDrop(player, newArmorStack);
                }

                player.setItemSlot(slot, handStack.split(1));
                cir.setReturnValue(InteractionResultHolder.sidedSuccess(handStack, level.isClientSide()));
            } else {
                cir.setReturnValue(InteractionResultHolder.fail(handStack));
            }
        }
    }
}
