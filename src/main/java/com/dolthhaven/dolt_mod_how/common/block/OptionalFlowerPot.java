package com.dolthhaven.dolt_mod_how.common.block;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.jetbrains.annotations.NotNull;

public class OptionalFlowerPot extends FlowerPotBlock {
    private static final Block DEFAULT = Blocks.POPPY;
    private ResourceLocation loc;

    public OptionalFlowerPot(ResourceLocation loc, Properties props) {
        super(DEFAULT, props);
        this.loc = loc;
    }

    @Override
    public @NotNull Block getContent() {
        Block block = Util.getPotentialBlock(this.loc);
        if (block == null) {
            return DEFAULT;
        }
        return block;
    }

}
