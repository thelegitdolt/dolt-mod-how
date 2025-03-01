package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.List;

@Mixin(ConsumableItem.class)
public class ConsumableItemMixin {
    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$TOOLTIPSDIEDIEIDSNJDJKNEFNEKFNKWJNFKENKWENK(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {
        Item item = DMHUtils.getPotentialItem(DMHUtils.Constants.MY_NETHERS_DELIGHT, "magma_cake_slice");

        if (item == null) return;

        if (stack.is(item) && DMHConfig.COMMON.frogsAreNotStupid.get()) {
            ci.cancel();
        }
    }
}
