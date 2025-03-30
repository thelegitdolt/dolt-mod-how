package com.dolthhaven.dolt_mod_how.core.mixin.supplementaries;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "net.mehvahdjukaar.moonlight.core.set.BlocksColorInternal.ColoredSet")
public class BlockColorsInternalMixin {
    @ModifyArg(method = "computeDefault", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getOptional(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;"))
    private ResourceLocation DoltModHow$Concrete(ResourceLocation id) {
        if (ModList.get().isLoaded(DMHUtils.Constants.CLAYWORKS)) {
            if ((id.getNamespace().equals("minecraft") || id.getNamespace().equals("dye_depot")) && id.getPath().contains("concrete")) {
                id = DMHUtils.Constants.CONCRETE;
            }
        }
        return id;
    }
}
