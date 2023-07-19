package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.data.tag.CompatTags;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DoltModHowEvent {
    private static final Map<Block, UniformInt> XpList = new HashMap<>();

    private static final UniformInt COMMON_ORE = UniformInt.of(0, 3);
    private static final UniformInt RARE_ORE = UniformInt.of(1, 4);

    static {

        XpList.put(Blocks.COPPER_ORE, COMMON_ORE);
        XpList.put(Blocks.DEEPSLATE_COPPER_ORE, COMMON_ORE);
        XpList.put(Blocks.IRON_ORE, COMMON_ORE);
        XpList.put(Blocks.DEEPSLATE_IRON_ORE, COMMON_ORE);

        XpList.put(Blocks.GOLD_ORE, RARE_ORE);
        XpList.put(Blocks.DEEPSLATE_GOLD_ORE, RARE_ORE);
    }



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
            BlockState state = event.getState();
            if (!event.getPlayer().hasCorrectToolForDrops(state)) {
                return;
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer()) > 0) {
                return;
            }

            if (ModList.get().isLoaded("caverns_and_chasms")) {
                XpList.put(getPotentialBlock("caverns_and_chasms", "silver_ore"), RARE_ORE);
                XpList.put(getPotentialBlock("caverns_and_chasms", "deepslate_silver_ore"), RARE_ORE);
            }

            if (ModList.get().isLoaded("sullysmod")) {
                XpList.put(getPotentialBlock("sullysmod", "jade_ore"), COMMON_ORE);
                XpList.put(getPotentialBlock("sullysmod", "deepslate_jade_ore"), COMMON_ORE);
            }

            if (XpList.containsKey(state.getBlock())) {
                int exp = XpList.get(state.getBlock()).sample(level.getRandom());
                event.setExpToDrop(exp);
            }
        }
    }

    private static @Nullable Block getPotentialBlock(String path, String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(path, name));
    }

}
