package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public static void onPlayerBreakBoringOreEvent(BlockEvent.BreakEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            List<Block> smallXp = new ArrayList<>(List.of(Blocks.COPPER_ORE, Blocks.IRON_ORE));
            smallXp.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("sullysmod", "jade_ore")));
            smallXp.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("sullysmod", "deepslate_jade_ore")));

            List<Block> bigXp = new ArrayList<>(List.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE));
            bigXp.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("caverns_and_chasms", "silver_ore")));
            bigXp.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("caverns_and_chasms", "deepslate_silver_ore")));


            BlockState state = event.getState();
            if (event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TieredItem tiered && TierSortingRegistry.isCorrectTierForDrops(tiered.getTier(), state)) {
                int experience = level.getRandom().nextInt(0, 3);
                if (smallXp.contains(state.getBlock())) {
                    if (tiered.getTier().equals(Tiers.GOLD)) {
                        experience = (int) Math.round(experience * 1.75);
                    }
                    state.getBlock().popExperience(level, event.getPos(), experience);
                }
                else if (bigXp.contains(state.getBlock())) {
                    experience++;
                    if (tiered.getTier().equals(Tiers.GOLD)) {
                        experience = (int) Math.round(experience * 1.75);
                    }
                    state.getBlock().popExperience(level, event.getPos(), experience);
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
