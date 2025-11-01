package com.dolthhaven.dolt_mod_how.core.mixin.emi;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.quark.addons.oddities.inventory.BackpackMenu;

import java.util.List;
import java.util.Map;

@Pseudo
@Mixin(targets = "dev.emi.emi.registry.EmiRecipeFiller")
public class EmiRecipeFillerMixin {
    @Shadow public static Map<MenuType<?>, List<EmiRecipeHandler<? extends AbstractContainerMenu>>> handlers;

    @Inject(method = "getAllHandlers", at = @At(value = "HEAD"), cancellable = true)
    private static <T extends AbstractContainerMenu> void DoltModHow$Thing(AbstractContainerScreen<T> screen, CallbackInfoReturnable<List<EmiRecipeHandler<? extends AbstractContainerMenu>>> cir) {
        DoltModHow.LOGGER.info("HI HI HI HI. Notice me notice me notice me. Also sex");
        if (screen != null && screen.getMenu() instanceof BackpackMenu && handlers.containsKey(screen.getMenu())) {
            cir.setReturnValue(handlers.get(screen.getMenu()));
        }
    }
}
