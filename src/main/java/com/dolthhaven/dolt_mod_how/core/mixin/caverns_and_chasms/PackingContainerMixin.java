package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.teamabnormals.caverns_and_chasms.common.item.PackingContainerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PackingContainerItem.class)
public class PackingContainerMixin {
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
