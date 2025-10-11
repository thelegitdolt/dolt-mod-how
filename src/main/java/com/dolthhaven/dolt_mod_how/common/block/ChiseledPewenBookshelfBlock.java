package com.dolthhaven.dolt_mod_how.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintChiseledBookShelfBlock;
import net.minecraft.world.phys.Vec2;

import java.util.Arrays;

public class ChiseledPewenBookshelfBlock extends BlueprintChiseledBookShelfBlock {
    public ChiseledPewenBookshelfBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getHitSlot(Vec2 vec2) {
        double x = vec2.x * 16;
        double y = vec2.y * 16;

        if (y > 8) {
            return thresholds(x, 5, 10);
        }
        else {
            return 3 + thresholds(x, 6, 11);
        }
    }

    private static int thresholds(double input, int x, int y) {
        int ret = 0;
        if (input > x) ret += 1;
        if (input > y) ret += 1;
        return ret;
    }
}
