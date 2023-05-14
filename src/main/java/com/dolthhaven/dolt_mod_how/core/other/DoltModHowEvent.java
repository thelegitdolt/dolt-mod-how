package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModBlocks;

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
}
