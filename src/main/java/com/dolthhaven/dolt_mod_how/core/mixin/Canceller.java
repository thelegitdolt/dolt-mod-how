package com.dolthhaven.dolt_mod_how.core.mixin;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class Canceller implements MixinCanceller {
    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return mixinClassName.equals("com.ninni.etcetera.mixin.AbstractBlockStateMixin") ||
                mixinClassName.equals("com.teamabnormals.atmospheric.core.mixin.client.LevelRendererMixin") ||
                mixinClassName.equals("com.teamabnormals.environmental.core.mixin.PinkPetalsBlockMixin");
    }
}
