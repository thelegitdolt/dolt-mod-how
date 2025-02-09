package com.dolthhaven.dolt_mod_how.core.compat;

import com.github.alexmodguy.alexscaves.server.item.RadioactiveOnDestroyedBlockItem;
import com.ninni.etcetera.block.entity.PricklyCanBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DMHACCompat {

    public static boolean explodePricklyCan(ItemStack stack, int index, PricklyCanBlockEntity can) {
        if (stack.getItem() instanceof RadioactiveOnDestroyedBlockItem radioactiveItem) {
            Level level = can.getLevel();
            BlockPos pos = can.getBlockPos();
            if (level != null) {
                can.removeItem(index, index);
                radioactiveItem
                        .onDestroyed(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack),
                                level.damageSources().cactus());
                level.destroyBlock(pos, true);
                return true;
            }
        }

        return false;
    }
}
