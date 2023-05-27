package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DoltModHowEvent {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();


        if (stack.getItem() instanceof HoeItem && player.isCrouching()) {
            if (level.getBlockState(pos).is(Blocks.FARMLAND)) {
                stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(LivingEntity.getEquipmentSlotForItem(stack)));
                level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
                player.swing(event.getHand());
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
            else if (level.getBlockState(pos).is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
                stack.hurtAndBreak(1, player, onBroken -> onBroken.broadcastBreakEvent(LivingEntity.getEquipmentSlotForItem(stack)));
                level.setBlock(pos, ModBlocks.RICH_SOIL.get().defaultBlockState() , 2);
                player.swing(event.getHand());
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerDropEvent(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                return;
            }

            event.getDrops().stream().map(ItemEntity::getItem).filter(i -> i.getEnchantmentLevel(DMHEnchants.BOUNDING.get()) > 0).forEach(i -> player.getInventory().add(i));
        }
    }

    // Some credit to team Cofh's soulbound effect
    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player nw = event.getEntity();
            Player old = event.getOriginal();

            List<ItemStack> items = old.getInventory().items;

            for (ItemStack stackie : items.stream().filter(i -> i.getEnchantmentLevel(DMHEnchants.BOUNDING.get()) >= 1).toList()) {
                if (!nw.getInventory().add(stackie)) {
                    nw.drop(stackie, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockRemapEvent(MissingMappingsEvent event) {
        List<MissingMappingsEvent.Mapping<Block>> mappings = event.getMappings(ForgeRegistries.Keys.BLOCKS, "connectedglass");
        ImmutableMap.Builder<ResourceLocation, Block> building = new ImmutableMap.Builder<>();

        for (DyeColor color : DyeColor.values()) {
            building.put(res("borderless_glass_" + color.getName()),
                    Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_stained_glass"))));
            building.put(res("borderless_glass_" + color.getName() + "_pane"),
                    Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft", color.getName() + "_stained_glass_pane"))));

        }

        building.put(res("borderless_glass"), Blocks.GLASS);
        building.put(res("borderless_glass_pane"), Blocks.GLASS_PANE);
        building.put(res("tinted_borderless_glass"), Blocks.TINTED_GLASS);

        building.put(new ResourceLocation("abundant_atmosphere", "mud_lantern"), DMHBlocks.MUD_LANTERN.get());

        Map<ResourceLocation, Block> blockRemapping = building.build();

        for (MissingMappingsEvent.Mapping<Block> mapping : mappings) {
            Block block = blockRemapping.get(mapping.getKey());
            if (block != null) {
                if (ForgeRegistries.BLOCKS.getKey(block) != null) {
                    mapping.remap(block);
                }
            }
        }

    }

    public static ResourceLocation res(String hi) {
        return new ResourceLocation("connectedglass", hi);
    }
}
