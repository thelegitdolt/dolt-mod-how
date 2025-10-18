package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlockFamilies;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHFluids;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCItemTags;
import com.teamabnormals.caverns_and_chasms.core.registry.CCBlocks;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.addons.oddities.module.PipesModule;
import umpaz.brewinandchewin.client.recipebook.FermentingRecipeBookTab;
import umpaz.brewinandchewin.common.registry.BnCItems;
import umpaz.brewinandchewin.data.builder.KegFermentingRecipeBuilder;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHRecipes extends BlueprintRecipeProvider {
    public DMHRecipes(PackOutput output) {
        super(DoltModHow.MOD_ID, output);
    }
    public static final ModLoadedCondition ALEXSCAVES_LOADED = new ModLoadedCondition(DMHUtils.Constants.ALEXS_CAVES);
    public static final ModLoadedCondition CREATE_LOADED = new ModLoadedCondition(DMHUtils.Constants.CREATE);
    public static final ModLoadedCondition BNC_LOADED = new ModLoadedCondition(DMHUtils.Constants.BREWING_AND_CHEWING);
    public static final ModLoadedCondition ATMOSPHERIC_LOADED = new ModLoadedCondition(DMHUtils.Constants.ATMOSPHERIC);
    public static final AndCondition BNC_ATMO_LOADED = new AndCondition(BNC_LOADED, ATMOSPHERIC_LOADED);
    public static final ModLoadedCondition JNE_LOADED = new ModLoadedCondition(DMHUtils.Constants.JNE);

    public static final ModLoadedCondition CAVERNS_CHASMS_LOADED = new ModLoadedCondition(DMHUtils.Constants.CAVERNS_AND_CHASMS);
    public static final AndCondition CCC_LOADED = new AndCondition(CREATE_LOADED, CAVERNS_CHASMS_LOADED);
    public static final AndCondition CCJNE_LOADED = new AndCondition(CAVERNS_CHASMS_LOADED, JNE_LOADED);


    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, STURDY_DEEPSLATE.get())
                .define('1', Items.COBBLED_DEEPSLATE)
                .pattern("111").pattern("111")
                .pattern("111")
                .unlockedBy("has_cobble_deepslate", has(Items.COBBLED_DEEPSLATE)).save(consumer);
        this.storageRecipes(consumer, RecipeCategory.MISC, Items.HONEYCOMB, RecipeCategory.BUILDING_BLOCKS, WAX_BLOCK.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DMHItems.CHORUS_SODA.get())
                .requires(Items.GLASS_BOTTLE).requires(Items.CHORUS_FRUIT)
                .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE, 9)
                .requires(STURDY_DEEPSLATE.get())
                .unlockedBy("has_sturdy_dep", has(STURDY_DEEPSLATE.get()))
                .save(consumer, new ResourceLocation("dolt_mod_how", "cobbled_deepslate_from_unpacking_sturdy_deepslate"));

        WoodworksRecipeProvider.baseRecipes(consumer, ACBlockRegistry.PEWEN_PLANKS.get(), ACBlockRegistry.PEWEN_PLANKS_SLAB.get(), PEWEN_BOARDS.get(), PEWEN_BOOKSHELF.get(),
                CHISELED_PEWEN_BOOKSHELF.get(), PEWEN_LADDER.get(), PEWEN_BEEHIVE.get(), PEWEN_CHEST.get(),
                TRAPPED_PEWEN_CHEST.get(), DMHUtils.Constants.ALEXS_CAVES);
        WoodworksRecipeProvider.baseRecipes(consumer, ACBlockRegistry.THORNWOOD_PLANKS.get(), ACBlockRegistry.THORNWOOD_PLANKS_SLAB.get(), THORNWOOD_BOARDS.get(), THORNWOOD_BOOKSHELF.get(),
                CHISELED_THORNWOOD_BOOKSHELF.get(), THORNWOOD_LADDER.get(), THORNWOOD_BEEHIVE.get(), THORNWOOD_CHEST.get(),
                TRAPPED_THORNWOOD_CHEST.get(), DMHUtils.Constants.ALEXS_CAVES);

        WoodworksRecipeProvider.leafPileRecipes(consumer, ACBlockRegistry.ANCIENT_LEAVES.get(), ANCIENT_LEAF_PILE.get());
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.PEWEN_LOG.get(), PEWEN_LADDER.get(), 4);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.PEWEN_PLANKS.get(), PEWEN_LADDER.get(), 1);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.PEWEN_PLANKS.get(), PEWEN_BOARDS.get(), 1);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.PEWEN_LOG.get(), PEWEN_BOARDS.get(), 4);

        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.THORNWOOD_LOG.get(), THORNWOOD_LADDER.get(), 4);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.THORNWOOD_PLANKS.get(), THORNWOOD_LADDER.get(), 1);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.THORNWOOD_LOG.get(), THORNWOOD_BOARDS.get(), 4);
        WoodworksRecipeProvider.sawmillRecipe(consumer, ALEXSCAVES_LOADED, RecipeCategory.BUILDING_BLOCKS, ACBlockRegistry.THORNWOOD_PLANKS.get(), THORNWOOD_BOARDS.get(), 1);

        cabinet(consumer, PEWEN_CABINET, ACBlockRegistry.PEWEN_PLANKS_SLAB, ACBlockRegistry.PEWEN_TRAPDOOR);
        cabinet(consumer, THORNWOOD_CABINET, ACBlockRegistry.THORNWOOD_PLANKS_SLAB, ACBlockRegistry.THORNWOOD_PLANKS_SLAB);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ANCIENT_BRAZIER.get())
                .define('#', JNEItems.ANCIENT_WAX.get())
                .define('S', CCItemTags.INGOTS_SILVER)
                .pattern("S#S").pattern(" S ")
                .unlockedBy("has_silver_ingot", has(CCItemTags.INGOTS_SILVER))
                .unlockedBy("has_waxers", has(JNEItems.ANCIENT_WAX.get())).save(consumer);

        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_SLAB.get(), ZINC_BRICKS.get(), 2);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_STAIRS.get(), ZINC_BRICKS.get(), 1);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_ZINC_BRICKS.get(), ZINC_BRICKS.get(), 1);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_WALL.get(), ZINC_BRICKS.get(), 1);

        generateRecipes(consumer, DMHBlockFamilies.ZINC_BRICKS_FAMILY);

        KegFermentingRecipeBuilder.kegFermentingRecipe(DMHFluids.TEQUILA.get(), 1000, 9600, 1.0F, 1)
                .addFluidIngredient(Fluids.WATER, 1000)
                .addIngredient(AtmosphericBlocks.AGAVE.get(), 2)
                .addIngredient(AtmosphericItems.YUCCA_FRUIT.get())
                .addIngredient(Items.SUGAR).unlockedByItems("has_tankard", BnCItems.TANKARD.get()).setRecipeBookTab(FermentingRecipeBookTab.DRINKS).build(consumer);

        pipes(consumer);
    }

    private void cabinet(Consumer<FinishedRecipe> consumer, RegistryObject<Block> cabinet, RegistryObject<Block> slab, RegistryObject<Block> trapdoor) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cabinet.get())
                .define('1', slab.get()).define('2', trapdoor.get())
                .pattern("111").pattern("2 2").pattern("111")
                .unlockedBy("has_pewen_slab", has(slab.get())).save(consumer);
    }

    private void pipes(Consumer<FinishedRecipe> consumer) {
        for (RegistryObject<?> blocks : new RegistryObject<?>[]{
                WHITE_ENCASED_PIPE, BROWN_ENCASED_PIPE, GRAY_ENCASED_PIPE, LIGHT_GRAY_ENCASED_PIPE, RED_ENCASED_PIPE, ORANGE_ENCASED_PIPE,
                YELLOW_ENCASED_PIPE, LIME_ENCASED_PIPE, GREEN_ENCASED_PIPE, BLUE_ENCASED_PIPE, LIGHT_BLUE_ENCASED_PIPE, CYAN_ENCASED_PIPE, PURPLE_ENCASED_PIPE,
                MAGENTA_ENCASED_PIPE, PINK_ENCASED_PIPE, BLACK_ENCASED_PIPE, ROSE_ENCASED_PIPE, MAROON_ENCASED_PIPE, GINGER_ENCASED_PIPE, TAN_ENCASED_PIPE,
                BEIGE_ENCASED_PIPE, CORAL_ENCASED_PIPE, OLIVE_ENCASED_PIPE, FOREST_ENCASED_PIPE, VERDANT_ENCASED_PIPE, AMBER_ENCASED_PIPE,
                TEAL_ENCASED_PIPE, MINT_ENCASED_PIPE, AQUA_ENCASED_PIPE, SLATE_ENCASED_PIPE, NAVY_ENCASED_PIPE, INDIGO_ENCASED_PIPE
        }) {
            if (blocks.get() instanceof Block block) {
                String color = blocks.getId().getPath().replace("_encased_pipe", "");
                boolean dyeDepot = DyeDepotCompat.getDyeDepotDye(color) != null;
                Item glass = ForgeRegistries.ITEMS.getValue(new ResourceLocation(dyeDepot ? DMHUtils.Constants.DYE_DEPOT : ResourceLocation.DEFAULT_NAMESPACE,
                        color + "_stained_glass"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, block)
                        .requires(PipesModule.pipe).requires(glass)
                        .unlockedBy("has_pipe", has(PipesModule.pipe))
                        .save(consumer);
            }
        }
    }

    public void stonecutterRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike output, ItemLike input, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count).unlockedBy(getHasName(input), has(input)).save(consumer, this.getModConversionRecipeName(output, input) + "_stonecutting");
    }
}
