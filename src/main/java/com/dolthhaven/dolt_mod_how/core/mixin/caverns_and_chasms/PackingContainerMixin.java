package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.teamabnormals.caverns_and_chasms.common.item.PackingContainerItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackingContainerItem.class)
public class PackingContainerMixin {
    @Mutable
    @Shadow @Final public static int MAX_WEIGHT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void hi(CallbackInfo ci) {
        MAX_WEIGHT = DMHConfig.COMMON.stackSizeForSpeciesCrankbow.get() * 8;
    }

    @ModifyConstant(method = "getWeight", constant = @Constant(intValue = 64), remap = false)
    private static int constant(int constant) {
        return DMHConfig.COMMON.stackSizeForSpeciesCrankbow.get();
    }

    @ModifyConstant(method = {"getBarWidth", "overrideStackedOnOther", "appendHoverText"}, constant = @Constant(intValue = 512))
    private int yeah512Lol(int constant) {
        return DMHConfig.COMMON.stackSizeForSpeciesCrankbow.get() * 8;
    }

    @ModifyConstant(method = {"add", "getFullnessDisplay"}, constant = @Constant(intValue = 512), remap = false)
    private static int yeah512LolButNotStatic(int constant) {
        return DMHConfig.COMMON.stackSizeForSpeciesCrankbow.get()  * 8;
    }
}
