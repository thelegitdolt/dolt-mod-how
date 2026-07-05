package com.dolthhaven.dolt_mod_how.data.client.splash;

import com.teamabnormals.blueprint.client.screen.splash.Splash;
import net.minecraft.client.User;
import net.minecraft.util.RandomSource;

public interface LevelNameSplash extends Splash {
    String[] getLevels();

    String getGame();

    @Override
    default String getText(User user, RandomSource random) {
        return "Your Random %s Level Name is: %s".formatted(getGame(),
                getLevels()[random.nextInt(getLevels().length)].replace("[PLAYER]", user.getName()));
    }

    @Override
    default boolean isRandom() {
        return true;
    }
}
