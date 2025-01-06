package com.dolthhaven.dolt_mod_how.client.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
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
import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, value = Dist.CLIENT)
public class DMHClientCompat {
    public static void doCompat() {
        registerItemProperties();
    }

    private static void registerItemProperties() {
        registerCrossbowIfModLoaded("large_arrow", () -> Util.getPotentialItem(Util.Constants.CAVERNS_AND_CHASMS, "large_arrow"), Util.Constants.CAVERNS_AND_CHASMS);
        registerCrossbowIfModLoaded("seeking_arrow", () -> Util.getPotentialItem(Util.Constants.ALEXS_CAVES, "seeking_arrow"), Util.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("burrowing_arrow", () -> Util.getPotentialItem(Util.Constants.ALEXS_CAVES, "burrowing_arrow"), Util.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("phasmo_arrow", () -> Util.getPotentialItem(Util.Constants.JNE, "phasmo_arrow"), Util.Constants.JNE);


        registerCrossbowPredicate("torch_arrow", () -> TorchArrowModule.torch_arrow);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_ACID_BUCKET, Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.ALEXS_CAVES);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_PURPLE_SODA_BUCKET, Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.ALEXS_CAVES);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET, Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.OREGANIZED);
    }

    private static void registerGoldenBucketIfModLoaded(Supplier<Item> item, String... modids) {
        if (item.get() == null) {
            DoltModHow.LOGGER.info("Failed to register item " + item + " this is bad report to Dolt Mod How");
            return;
        }
        if (Arrays.stream(modids).allMatch(ModList.get()::isLoaded)) {
            registerGoldenBucket(item);
        }
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

    private static void registerGoldenBucket(Supplier<Item> bucket) {
        ItemProperties.register(bucket.get(), DoltModHow.rl("level"), (stack, level, entity, hash) ->
                stack.getOrCreateTag().getInt("FluidLevel"));
    }

    private static ItemPropertyFunction createCrossbowProjectilePredicate(Supplier<Item> projectile) {
        return (stack, world, entity, i) ->
                entity != null &&
                CrossbowItem.isCharged(stack) &&
                CrossbowItem.containsChargedProjectile(stack, projectile.get()) ? 1.0F : 0.0F;
    }
}
