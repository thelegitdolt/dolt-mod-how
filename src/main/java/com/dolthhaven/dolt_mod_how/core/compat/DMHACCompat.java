package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.other.DoltModHowEvent;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.item.RadioactiveOnDestroyedBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraftforge.fml.ModList;

public class DMHACCompat {

    public static boolean explodePricklyCan(ItemStack stack, int index, RandomizableContainerBlockEntity can) {
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

    public static void registerUnRust() {
        if (ModList.get().isLoaded(Util.Constants.ALEXS_CAVES)) {
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL.get(), ACBlockRegistry.SCRAP_METAL.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL_PLATE.get(), ACBlockRegistry.SCRAP_METAL_PLATE.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_BARREL.get(), ACBlockRegistry.METAL_BARREL.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCAFFOLDING.get(), ACBlockRegistry.METAL_SCAFFOLDING.get());
            DoltModHowEvent.UNRUST_MAP.put( ACBlockRegistry.RUSTY_REBAR.get(), ACBlockRegistry.METAL_REBAR.get());
        }
    }
}
