package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.item.PoulpoItem;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHItems {
    public static final ItemSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> POULPO = HELPER
            .createItem("poulpo", () -> new PoulpoItem(new Item.Properties()));


    public static final RegistryObject<Item> LEATHER_SCRAPS = HELPER.createItem("leather_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ALPHACENE_SALAD = HELPER.createItem("alphacene_salad",
            () -> new ConsumableItem(new Item.Properties().food(Food.ALPHACENE_SALAD).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static void setUpTabEditors() {
        var thing = CreativeModeTabContentsPopulator.mod(DoltModHow.MOD_ID)
                .tab(CreativeModeTabs.BUILDING_BLOCKS)

                .addItemsAfter(of(Items.MUSHROOM_STEW), ALPHACENE_SALAD)

                .tab(CreativeModeTabs.INGREDIENTS)
                .addItemsAfter(of(Items.LEATHER), LEATHER_SCRAPS)

                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                .addItemsAfter(of(Blocks.HAY_BLOCK), WAX_BLOCK)
                .addItemsAfter(of(Items.DIRT_PATH), ALPHACENE_PATH);
    }

    public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
        return stack -> BlockSubRegistryHelper.areModsLoaded(modids) && of(item).test(stack);
    }

    public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(ForgeRegistries.ITEMS.getValue(location)).test(stack));
    }

    public static boolean fdPredicate(BuildCreativeModeTabContentsEvent event) {
        return event.getTabKey() == ModCreativeTabs.TAB_FARMERS_DELIGHT.getKey();
    }

    public static Predicate<ItemStack> ofLoaded(Supplier<Item> item, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(item.get()).test(stack));
    }

    public static class Food {
        public static final FoodProperties ALPHACENE_SALAD = (new FoodProperties.Builder()).alwaysEat().nutrition(6).saturationMod(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();

        public static final FoodProperties WARDENZOLA = new FoodProperties.Builder()
                .nutrition(3).saturationMod(0.6f).build();
    }
}
