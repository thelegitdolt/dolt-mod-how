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

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, value = Dist.CLIENT)
public class DMHClientCompat {
    public static void doCompat() {
        registerItemProperties();
        registerRendersMaybe();
    }

    private static void registerItemProperties() {
        registerCrossbowIfModLoaded("large_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.CAVERNS_AND_CHASMS, "large_arrow"), DMHUtils.Constants.CAVERNS_AND_CHASMS);
        registerCrossbowIfModLoaded("seeking_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.ALEXS_CAVES, "seeking_arrow"), DMHUtils.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("burrowing_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.ALEXS_CAVES, "burrowing_arrow"), DMHUtils.Constants.ALEXS_CAVES);
        registerCrossbowIfModLoaded("phasmo_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.JNE, "phasmo_arrow"), DMHUtils.Constants.JNE);
        registerCrossbowIfModLoaded("ricochet_arrow", () -> DMHUtils.getPotentialItem(DMHUtils.Constants.CAVERNS_AND_CHASMS, "ricochet_arrow"), DMHUtils.Constants.CAVERNS_AND_CHASMS);


        registerCrossbowPredicate("torch_arrow", () -> TorchArrowModule.torch_arrow);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_ACID_BUCKET, DMHUtils.Constants.CAVERNS_AND_CHASMS, DMHUtils.Constants.ALEXS_CAVES);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_PURPLE_SODA_BUCKET, DMHUtils.Constants.CAVERNS_AND_CHASMS, DMHUtils.Constants.ALEXS_CAVES);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET, DMHUtils.Constants.CAVERNS_AND_CHASMS, DMHUtils.Constants.OREGANIZED);
        registerGoldenBucketIfModLoaded(DMHItems.GOLDEN_ECTOPLASM_BUCKET, DMHUtils.Constants.CAVERNS_AND_CHASMS, DMHUtils.Constants.JNE);
    }

    private static void registerRendersMaybe() {
        ItemBlockRenderTypes.setRenderLayer(ANCIENT_BRAZIER.get(), RenderType.cutout());


        ItemBlockRenderTypes.setRenderLayer(WHITE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BROWN_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(GRAY_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LIGHT_GRAY_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RED_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ORANGE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(YELLOW_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LIME_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(GREEN_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BLUE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(LIGHT_BLUE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(CYAN_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(PURPLE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MAGENTA_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(PINK_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BLACK_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ROSE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MAROON_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(GINGER_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TAN_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BEIGE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(CORAL_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(OLIVE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FOREST_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(VERDANT_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBER_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TEAL_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MINT_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AQUA_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SLATE_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(NAVY_ENCASED_PIPE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(INDIGO_ENCASED_PIPE.get(), RenderType.translucent());
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
