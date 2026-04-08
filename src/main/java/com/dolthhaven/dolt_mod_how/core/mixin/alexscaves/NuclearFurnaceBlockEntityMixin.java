package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NuclearFurnaceBlockEntity.class)
public class NuclearFurnaceBlockEntityMixin {
    @ModifyConstant(method = "getMaxFissionTime", constant = @Constant(floatValue = 6400.0f))
    private static float superCook(float constant) {
        return 2000 * 100;
    }
}
