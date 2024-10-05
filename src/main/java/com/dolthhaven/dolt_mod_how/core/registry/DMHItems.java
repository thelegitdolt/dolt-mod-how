package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.item.ChorusSodaItem;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;

import java.util.function.Predicate;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.STURDY_DEEPSLATE;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHItems {
    public static final ItemSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> GLOWSHROOM_COLONY = HELPER.createItem("glowshroom_colony", () -> new MushroomColonyItem(DMHBlocks.GLOWSHROOM_COLONY.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHORUS_SODA = HELPER.createItem("chorus_soda",
            () -> new ChorusSodaItem(new Item.Properties().food(Food.CHORUS_SODA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));


    public static void setUpTabEditors() {
        CreativeModeTabContentsPopulator.mod(DoltModHow.MOD_ID)
                .tab(CreativeModeTabs.FOOD_AND_DRINKS)
                .addItemsAfter(of(Items.HONEY_BOTTLE), CHORUS_SODA);

//        CreativeModeTabContentsPopulator.mod(DoltModHow.MOD_ID)
//                .tab(CreativeModeTabs.NATURAL_BLOCKS)
//                .addItemsBefore(Ingredient.of(ModItems.RED_MUSHROOM_COLONY.get()), GLOWSHROOM_COLONY);

        CreativeModeTabContentsPopulator.mod("quark_" + DoltModHow.MOD_ID)
                .tab(CreativeModeTabs.BUILDING_BLOCKS)
                .addItemsAfter(ofID(Util.Constants.STURDY_STONE), STURDY_DEEPSLATE);

    }

    public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
        return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
    }

    public static Predicate<ItemStack> ofID(ResourceLocation location, ItemLike fallback, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) ? of(ForgeRegistries.ITEMS.getValue(location)) : of(fallback)).test(stack);
    }

    public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(ForgeRegistries.ITEMS.getValue(location)).test(stack));
    }

    public static class Food {
        public static final FoodProperties CHORUS_SODA = (new FoodProperties.Builder()).alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 50, 0), 1.0F).build();
    }
}
