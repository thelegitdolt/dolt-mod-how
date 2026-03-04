package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.other.util.ThunderdomeUtil;
import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.dolthhaven.dolt_mod_how.integration.DMHBCCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHSpeciesCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHAdvancementEvents {
    @SubscribeEvent
    public static void THUNDERDOME(LivingDeathEvent event) {
        if (!DMHUtils.cavernsChasmsLoaded()) return;

        Entity entity = event.getEntity();
        if (event.getSource().getEntity() instanceof Player player) {
            boolean hasMimed = false;
            if (DMHCCCompat.isMime(entity)) {
                for (ItemStack stack : entity.getArmorSlots()) {
                    if (!stack.isEmpty()) {
                        hasMimed = true;
                        break;
                    }
                }
            }

            if (hasMimed) {
                if (ThunderdomeUtil.add(player, entity.level().getGameTime()) && player instanceof ServerPlayer serverPlayer) {
                    DMHCriteriaTriggers.THUNDERDOME.trigger(serverPlayer);
                }
            } else {
                ThunderdomeUtil.reset(player);
            }
        }
    }

    @SubscribeEvent
    public static void bigGameHunter(LivingDeathEvent event) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.SPECIES) || !DMHUtils.cavernsChasmsLoaded()) return;

        Entity deadGuy = event.getEntity();
        Entity killer = event.getSource().getEntity();
        if (killer instanceof ServerPlayer player &&
                DMHSpeciesCompat.isBewereager(deadGuy) &&
                DMHCCCompat.isLargeArrow(event.getSource().getDirectEntity())) {
            boolean playerHasCrankbow =
                    DMHSpeciesCompat.isCrankbow(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()) ||
                    DMHSpeciesCompat.isCrankbow(player.getItemInHand(InteractionHand.OFF_HAND).getItem());
            if (playerHasCrankbow) DMHCriteriaTriggers.SLAY_BEWEREAGER_WITH_SILVER.trigger(player);
        }
    }

    @SubscribeEvent
    public static void grazeTheRoof(LivingDeathEvent event) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS)) return;

        Entity deadGuy = event.getEntity();
        Entity killer = event.getSource().getEntity();
        if (killer == null) return;

        ResourceLocation killerId = ForgeRegistries.ENTITY_TYPES.getKey(killer.getType());

        boolean validEntities = deadGuy.getType().is(DMHTags.HUMANOID_ZOMBIES) && killerId != null
                && killerId.equals(DMHUtils.Constants.FOLIAATH);
        if (!validEntities) return;

        boolean validPos = killer.getCommandSenderWorld().dimension() == Level.OVERWORLD &&
                killer.position().y > killer.level().getMaxBuildHeight() - 10;
        if (!validPos) return;

        if (killer.level().getNearestPlayer(killer, 20) instanceof ServerPlayer player) {
            DMHCriteriaTriggers.PVZ.trigger(player);
        }
    }

    @SubscribeEvent
    public static void notEndorsed(EntityMountEvent event) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.BREWING_AND_CHEWING) || event.isDismounting()) return;
        if (event.getEntityMounting() instanceof ServerPlayer player &&
                !event.getEntityBeingMounted().getType().is(DMHTags.HOSTILE_MOUNTS)) {
            int i = DMHBCCompat.tipsyEffectLevel(player);
            if (i > 2) {
                DMHCriteriaTriggers.DUI.trigger(player);
            }
        }
    }
}
