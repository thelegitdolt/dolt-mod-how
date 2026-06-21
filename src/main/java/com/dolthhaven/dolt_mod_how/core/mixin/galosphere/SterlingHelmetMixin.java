package com.dolthhaven.dolt_mod_how.core.mixin.galosphere;

import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.orcinus.galosphere.items.SterlingHelmetItem;
import net.orcinus.galosphere.items.SterlingHorseArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SterlingHelmetItem.class, SterlingHorseArmorItem.class})
public class SterlingHelmetMixin extends Item {
    public SterlingHelmetMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    private void sex(ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.overrideOtherStackedOnMe(itemStack, itemStack2, slot, clickAction, player, slotAccess));
    }
}
