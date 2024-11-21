package com.dolthhaven.dolt_mod_how.client.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.violetmoon.quark.content.tools.module.TorchArrowModule;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, value = Dist.CLIENT)
public class DMHClientCompat {
    public static void doCompat() {
        registerItemProperties();
    }

    private static void registerItemProperties() {
        registerCrossbowPredicate("large_arrow", CCItems.LARGE_ARROW);
        registerCrossbowPredicate("seeking_arrow", ACItemRegistry.SEEKING_ARROW);
        registerCrossbowPredicate("burrowing_arrow", ACItemRegistry.BURROWING_ARROW);
        registerCrossbowPredicate("torch_arrow", () -> TorchArrowModule.torch_arrow);

        registerGoldenBucket(DMHItems.GOLDEN_ACID_BUCKET);
        registerGoldenBucket(DMHItems.GOLDEN_PURPLE_SODA_BUCKET);
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
