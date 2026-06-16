package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BrewingStandMenu.class)
public class BrewingStandMenuMixin {
    @ModifyArgs(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/BrewingStandMenu$FuelSlot;<init>(Lnet/minecraft/world/Container;III)V"))
    private void sex(Args args) {
        if (DMHConfig.COMMON.brewingUnbloating.get()) {
            args.set(2, Integer.MAX_VALUE);
        }
    }
}
