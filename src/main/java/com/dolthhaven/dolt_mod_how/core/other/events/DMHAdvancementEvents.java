package com.dolthhaven.dolt_mod_how.core.other.events;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.other.util.ThunderdomeUtil;
import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.dolthhaven.dolt_mod_how.integration.DMHBCCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHSpeciesCompat;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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
    public static void mimeUseTotem(LivingUseTotemEvent event) {
        if (DMHUtils.cavernsChasmsLoaded()) {
            if (DMHCCCompat.isMime(event.getEntity()) && event.getSource().getEntity() instanceof ServerPlayer player) {
                DMHCriteriaTriggers.TRIGGER_MIME_TOTEM.trigger(player);
            }
        }
    }

    @SubscribeEvent
    public static void stickbugBugLightningDeath(LivingDeathEvent event) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.SPAWN)) return;

        Entity deadGuy = event.getEntity();
        if (DMHUtils.entityId(deadGuy.getType()).equals(DMHUtils.Constants.STICKBUG) && event.getSource().is(DamageTypes.LIGHTNING_BOLT) && !deadGuy.level().isClientSide) {
            getAllNearbyPlayers(deadGuy.level(), deadGuy.position()).forEach(DMHCriteriaTriggers.KILL_BUG_WITH_LIGHTNING::trigger);
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
    public static void watchMobKillTrigger(LivingDeathEvent event) {
        Entity victim = event.getEntity();
        Entity killer = event.getSource().getEntity();
        if (killer == null) return;

        if (!DMHUtils.unreadableCode(killer, victim, event.getSource())) {
            return;
        }

        if (killer.level().getNearestPlayer(killer, 50) instanceof ServerPlayer player) {
            DMHCriteriaTriggers.WATCH_MOB_KILL.trigger(player, killer, victim);
        }

        if (killer instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() instanceof ServerPlayer player) {
            DMHCriteriaTriggers.WATCH_MOB_KILL.trigger(player, killer, victim);
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

    private static List<ServerPlayer> getAllNearbyPlayers(EntityGetter entityGetter, Vec3 position) {
        return entityGetter.getEntitiesOfClass(ServerPlayer.class, new AABB(position, position.add(1, 1, 1)).inflate(40));
    }

    @SubscribeEvent
    public static void serverStart(ServerStartedEvent event) {
//        MinecraftServer server = event.getServer();
//        Advancement adv = server.getAdvancements().getAdvancement(new ResourceLocation("husbandry/balanced_diet"));
//        System.out.println("hi");

    }
}
