package com.dolthhaven.dolt_mod_how.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NetworkConduitItem extends Item {
    public NetworkConduitItem(Properties properties) {
        super(properties);
    }


    /**
     * Binds an empty Network Conduit to a Copper Chest
     * @return the bonded item.
     */
    public static ItemStack bind(ItemStack thisStack, GlobalPos pos) {
        return ItemStack.EMPTY;
    }

    /**
     * Links a bonded Network Conduit to a chest.
     * @return the linked item.
     */
    public static ItemStack link(ItemStack thisStack, GlobalPos pos) {
        return ItemStack.EMPTY;
    }
}
