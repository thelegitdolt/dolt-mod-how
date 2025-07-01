package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlockFamilies;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.THORNWOOD_LADDER;

public class DMHRecipes extends BlueprintRecipeProvider {
    public DMHRecipes(PackOutput output) {
        super(DoltModHow.MOD_ID, output);
    }
    public static final ModLoadedCondition ALEXSCAVES_LOADED = new ModLoadedCondition(DMHUtils.Constants.ALEXS_CAVES);
    public static final ModLoadedCondition CREATE_LOADED = new ModLoadedCondition(DMHUtils.Constants.CREATE);
    public static final ModLoadedCondition CAVERNS_CHASMS_LOADED = new ModLoadedCondition(DMHUtils.Constants.CAVERNS_AND_CHASMS);
    public static final AndCondition CCC_LOADED = new AndCondition(CREATE_LOADED, CAVERNS_CHASMS_LOADED);

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, STURDY_DEEPSLATE.get())
                .define('1', Items.COBBLED_DEEPSLATE)
                .pattern("111").pattern("111")
                .pattern("111")
                .unlockedBy("has_cobble_deepslate", has(Items.COBBLED_DEEPSLATE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DMHItems.CHORUS_SODA.get(), 2)
                .requires(Items.GLASS_BOTTLE).requires(Items.DRAGON_BREATH).requires(Items.CHORUS_FRUIT).requires(Items.SUGAR)
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

        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_SLAB.get(), ZINC_BRICKS.get(), 2);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_STAIRS.get(), ZINC_BRICKS.get(), 1);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_ZINC_BRICKS.get(), ZINC_BRICKS.get(), 1);
        stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZINC_BRICK_WALL.get(), ZINC_BRICKS.get(), 1);

        generateRecipes(consumer, DMHBlockFamilies.ZINC_BRICKS_FAMILY);
    }

    private void cabinet(Consumer<FinishedRecipe> consumer, RegistryObject<Block> cabinet, RegistryObject<Block> slab, RegistryObject<Block> trapdoor) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cabinet.get())
                .define('1', slab.get()).define('2', trapdoor.get())
                .pattern("111").pattern("2 2").pattern("111")
                .unlockedBy("has_pewen_slab", has(slab.get())).save(consumer);
    }

    public void stonecutterRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike output, ItemLike input, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count).unlockedBy(getHasName(input), has(input)).save(consumer, this.getModConversionRecipeName(output, input) + "_stonecutting");
    }
}
