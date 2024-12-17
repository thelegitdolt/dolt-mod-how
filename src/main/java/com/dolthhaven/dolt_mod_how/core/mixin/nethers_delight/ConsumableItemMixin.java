package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import net.minecraft.network.chat.Component;
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
        if (stack.is(MNDItems.MAGMA_CAKE_SLICE.get()) && DMHConfig.COMMON.frogsAreNotStupid.get()) {
            ci.cancel();
        }
    }
}
