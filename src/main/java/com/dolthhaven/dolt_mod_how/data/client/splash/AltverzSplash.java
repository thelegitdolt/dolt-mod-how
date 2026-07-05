package com.dolthhaven.dolt_mod_how.data.client.splash;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.teamabnormals.blueprint.client.screen.splash.Splash;
import net.minecraft.client.User;
import net.minecraft.util.RandomSource;

public enum AltverzSplash implements Splash {
    INSTANCE;

    public static final MapCodec<AltverzSplash> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public String getText(User user, RandomSource random) {
        return "";
    }

    @Override
    public boolean isRandom() {
        return true;
    }

    @Override
    public Codec<? extends Splash> codec() {
        return null;
    }

    public static String[] LEVEL_NAMES = {
        "Back to the Basics", "Newfound Power"
    };
}
