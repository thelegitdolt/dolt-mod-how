package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.item.ChorusSodaItem;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.compat.DMHCCAndACCompat;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHItems {
    public static final ItemSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> GLOWSHROOM_COLONY = HELPER.createItem("glowshroom_colony", () -> new MushroomColonyItem(DMHBlocks.GLOWSHROOM_COLONY.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHORUS_SODA = HELPER.createItem("chorus_soda",
            () -> new ChorusSodaItem(new Item.Properties().food(Food.CHORUS_SODA)));
    public static final RegistryObject<Item> LEATHER_SCRAPS = HELPER.createItem("leather_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ALPHACENE_SALAD = HELPER.createItem("alphacene_salad",
            () -> new ConsumableItem(new Item.Properties().food(Food.ALPHACENE_SALAD).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static final RegistryObject<Item> GOLDEN_ACID_BUCKET = HELPER.createItem("golden_acid_bucket",
            BlockSubRegistryHelper.areModsLoaded(Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.ALEXS_CAVES) ? DMHCCAndACCompat.GOLDEN_ACID_BUCKET : () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_PURPLE_SODA_BUCKET = HELPER.createItem("golden_purple_soda_bucket",
            BlockSubRegistryHelper.areModsLoaded(Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.ALEXS_CAVES) ? DMHCCAndACCompat.GOLDEN_PURPLE_SODA_BUCKET : () -> new Item(new Item.Properties()));

//    public static final RegistryObject<Item> GOLDEN_MOLTEN_LEAD_BUCKET = HELPER.createItem("golden_molten_lead_bucket",
//            getGoldenBucket(DMHOptionalItems.GOLDEN_MOLTEN_LEAD_BUCKET));

    public static void setUpTabEditors() {
        CreativeModeTabContentsPopulator.mod(DoltModHow.MOD_ID)
                .tab(CreativeModeTabs.BUILDING_BLOCKS)
                .addItemsAfter(ofID(Util.Constants.STURDY_STONE), STURDY_DEEPSLATE)
                .tab(CreativeModeTabs.FOOD_AND_DRINKS)
                .addItemsAfter(of(Items.HONEY_BOTTLE), CHORUS_SODA)
                .addItemsAfter(of(Items.MUSHROOM_STEW), ALPHACENE_SALAD)

                .tab(CreativeModeTabs.INGREDIENTS)
                .addItemsAfter(of(Items.LEATHER), LEATHER_SCRAPS)

                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                .addItemsAfter(modLoaded(Blocks.HAY_BLOCK, Util.Constants.ALEXS_CAVES), PINE_NUTS_CRATE)
                .addItemsAfter(of(Items.DIRT_PATH), ALPHACENE_PATH)

                .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .addItemsAfter(ofID(Util.Constants.GOLDEN_LAVA_BUCKET, Util.Constants.CAVERNS_AND_CHASMS, Util.Constants.ALEXS_CAVES),
                        GOLDEN_ACID_BUCKET, GOLDEN_PURPLE_SODA_BUCKET)

                .predicate(DMHItems::fdGroupPredicate)
                .addItemsAfter(ofID(ModItems.RED_MUSHROOM_COLONY.getId()), GLOWSHROOM_COLONY);
    }

    private static boolean areModsLoaded(String... ids) {
        return Arrays.stream(ids).allMatch(ModList.get()::isLoaded);
    }

    public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
        return stack -> BlockSubRegistryHelper.areModsLoaded(modids) && of(item).test(stack);
    }

    public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(ForgeRegistries.ITEMS.getValue(location)).test(stack));
    }

    public static boolean fdGroupPredicate(BuildCreativeModeTabContentsEvent event) {
        // !ADConfig.COMMON.replaceFDItemGroup.get() &&
        return event.getTabKey() == ModCreativeTabs.TAB_FARMERS_DELIGHT.getKey();
    }

    public static boolean modPredicate(BuildCreativeModeTabContentsEvent event, ResourceKey<CreativeModeTab> tab) {
        // ADConfig.COMMON.replaceFDItemGroup.get() &&
        return event.getTabKey() == tab;
    }

    public static class Food {
        public static final FoodProperties CHORUS_SODA = (new FoodProperties.Builder()).alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 50, 0), 1.0F).build();

        public static final FoodProperties ALPHACENE_SALAD = (new FoodProperties.Builder()).alwaysEat().nutrition(6).saturationMod(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();

    }
}
