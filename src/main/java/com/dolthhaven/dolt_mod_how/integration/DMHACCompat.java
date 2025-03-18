package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.other.DoltModHowEvent;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.AcidBlock;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.RadioactiveOnDestroyedBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        if (ModList.get().isLoaded(DMHUtils.Constants.ALEXS_CAVES)) {
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL.get(), ACBlockRegistry.SCRAP_METAL.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCRAP_METAL_PLATE.get(), ACBlockRegistry.SCRAP_METAL_PLATE.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_BARREL.get(), ACBlockRegistry.METAL_BARREL.get());
            DoltModHowEvent.UNRUST_MAP.put(ACBlockRegistry.RUSTY_SCAFFOLDING.get(), ACBlockRegistry.METAL_SCAFFOLDING.get());
            DoltModHowEvent.UNRUST_MAP.put( ACBlockRegistry.RUSTY_REBAR.get(), ACBlockRegistry.METAL_REBAR.get());
        }
    }


    public static ItemStack makeMapFromString(ResourceLocation loc) {
        ItemStack map = new ItemStack(ACItemRegistry.CAVE_MAP.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("BiomeTargetResourceKey", loc.toString());
        map.setTag(tag);
        return map;
    }

    public static boolean isAcid(BlockState state) {
        return state.getBlock() instanceof AcidBlock;
    }
}
