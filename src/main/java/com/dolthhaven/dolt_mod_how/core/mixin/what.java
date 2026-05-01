package com.dolthhaven.dolt_mod_how.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.spawn.registry.SpawnCreativeModeTab;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpawnCreativeModeTab.class)
public class what {
    @WrapOperation(method = "registerTabs", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeConfigSpec$BooleanValue;get()Ljava/lang/Object;"), remap = false)
    private static Object sex(ForgeConfigSpec.BooleanValue instance, Operation<Object> original) {
        return true;
    }
}
