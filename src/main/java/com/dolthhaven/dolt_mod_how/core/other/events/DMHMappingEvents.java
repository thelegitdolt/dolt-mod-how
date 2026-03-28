package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHMappingEvents {
    @SubscribeEvent
    public static void mappingsEvent(MissingMappingsEvent event) {
//        List<MissingMappingsEvent.Mapping<Block>> mappings = event.getAllMappings(ForgeRegistries.Keys.BLOCKS);
//
//        ImmutableMap<ResourceLocation, Block> mappers = Util.make(new ImmutableMap.Builder<ResourceLocation, Block>(), map -> {
//            putStuff(map, "stoneveil:stone_bricks_tiles", "caverns_and_chasms:cobblestone_bricks");
//            putStuff(map, "stoneveil:stone_bricks_tile_stairs", "caverns_and_chasms:cobblestone_brick_stairs");
//            putStuff(map, "stoneveil:stone_bricks_tile_slab", "caverns_and_chasms:cobblestone_brick_slab");
//            putStuff(map, "stoneveil:cracked_stone_bricks_tiles", "caverns_and_chasms:cobblestone_bricks");
//            putStuff(map, "stoneveil:cracked_stone_bricks_tile_stairs", "caverns_and_chasms:cobblestone_brick_stairs");
//            putStuff(map, "stoneveil:cracked_stone_bricks_tile_slab", "caverns_and_chasms:cobblestone_brick_slab");
//            putStuff(map, "stoneveil:mossy_stone_bricks_tiles", "caverns_and_chasms:mossy_cobblestone_bricks");
//            putStuff(map, "stoneveil:mossy_stone_bricks_tile_stairs", "caverns_and_chasms:mossy_cobblestone_brick_stairs");
//            putStuff(map, "stoneveil:mossy_stone_bricks_tile_slab", "caverns_and_chasms:mossy_cobblestone_brick_slab");
//
//            putStuff(map, "v_slab_compat:stoneveil/cracked_stone_bricks_tile_vertical_slab", "v_slab_compat:caverns_and_chasms/cobblestone_brick_vertical_slab");
//            putStuff(map, "v_slab_compat:stoneveil/mossy_stone_bricks_tile_vertical_slab", "v_slab_compat:caverns_and_chasms/mossy_cobblestone_brick_vertical_slab");
//            putStuff(map, "v_slab_compat:stoneveil/stone_bricks_tile_vertical_slab",  "v_slab_compat:caverns_and_chasms/cobblestone_brick_vertical_slab");
//            putStuff(map, "stoneveil:stone_pilar", "architects_palette:tuff_pillar");
//        }).build();
//
//        for (MissingMappingsEvent.Mapping<Block> mapping : mappings) {
//            Block block =mappers.get(mapping.getKey());
//            if (block != null) {
//                if (ForgeRegistries.BLOCKS.getKey(block) != null) {
//                    mapping.remap(block);
//                }
//            }
//        }
    }

    private static void putStuff(ImmutableMap.Builder<ResourceLocation, Block> map, String a, String b) {
        Block block = DMHUtils.getPotentialBlock(new ResourceLocation(b));
        if (block != null)
            map.put(new ResourceLocation(a), block);
    }
}
