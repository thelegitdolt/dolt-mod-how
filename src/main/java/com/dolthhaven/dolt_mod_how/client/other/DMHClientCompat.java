package com.dolthhaven.dolt_mod_how.client.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.violetmoon.quark.content.tools.module.TorchArrowModule;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHClientCompat {
    public static void doCompat() {
        registerItemProperties();
    }

    private static void registerItemProperties() {
        registerCrossbowIfModLoaded("large_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.CAVERNS_AND_CHASMS, "large_arrow"), DMHUtils.Constants.CAVERNS_AND_CHASMS);
        registerCrossbowIfModLoaded("seeking_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.ALEXS_CAVES, "seeking_arrow"), DMHUtils.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("burrowing_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.ALEXS_CAVES, "burrowing_arrow"), DMHUtils.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("phasmo_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.JNE, "phasmo_arrow"), DMHUtils.Constants.JNE);
        registerCrossbowIfModLoaded("ricochet_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.CAVERNS_AND_CHASMS, "ricochet_arrow"), DMHUtils.Constants.CAVERNS_AND_CHASMS);

        registerCrossbowPredicate("torch_arrow", () -> TorchArrowModule.torch_arrow);
    }

    private static void registerCrossbowIfModLoaded(String name, Supplier<Item> projectile, String... modids) {
        if (projectile.get() == null) {
            DoltModHow.LOGGER.info("Failed to register crossbow item property " + name + " with item " + projectile.get() + " this is bad please report to dolt mod how");
        }
        if (Arrays.stream(modids).allMatch(ModList.get()::isLoaded)) {
            registerCrossbowPredicate(name, projectile);
        }
    }

    private static void registerCrossbowPredicate(String name, Supplier<Item> projectile) {
        ItemProperties.register(Items.CROSSBOW, DoltModHow.rl(name), createCrossbowProjectilePredicate(projectile));
    }

    private static ItemPropertyFunction createCrossbowProjectilePredicate(Supplier<Item> projectile) {
        return (stack, world, entity, i) ->
                entity != null &&
                CrossbowItem.isCharged(stack) &&
                CrossbowItem.containsChargedProjectile(stack, projectile.get()) ? 1.0F : 0.0F;
    }
}
