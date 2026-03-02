package com.dolthhaven.dolt_mod_how.core.other.util;

import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.world.entity.player.Player;

public class ThunderdomeUtil {
    private static final int TRUNCATOR = 0x3fffffff;

    public static boolean add(Player player, long gameTime) {
        int intTime = ((int) gameTime) & TRUNCATOR;
        int data = get(player);
        int elapsed = thirtyBitDifference(intTime, lastGameTime(data));
        int score = score(data);

        if (elapsed > 200) {
            score = 0;
        }

        int newScore = Math.min(score + 1, 3);
        set(player, encode(newScore, intTime));
        return newScore == 3;

    }

    private static int get(Player player) {
        return ((IDataManager) player).getValue(DMHTrackedData.THUNDERDOME_CHALLENGE_DATA);
    }

    private static int encode(int score, int gametime) {
        return (score << 30) | gametime;
    }

    private static void set(Player player, int num) {
        ((IDataManager) player).setValue(DMHTrackedData.THUNDERDOME_CHALLENGE_DATA, num);
    }

    public static void reset(Player player) {
       set(player, 0);
    }

    private static int score(int data) {
        return data >>> 30;
    }

    public static int score(Player player) {
        return get(player) >>> 30;
    }

    private static int lastGameTime(int data) {
        return data & TRUNCATOR;
    }

    private static int thirtyBitDifference(int now, int before) {
        return (now - before) & TRUNCATOR;
    }
}
