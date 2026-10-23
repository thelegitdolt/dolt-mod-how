package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.addons.oddities.module.PipesModule;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.WAX_BLOCK;

public class DMHRecipes extends BlueprintRecipeProvider {
    public DMHRecipes(PackOutput output) {
        super(DoltModHow.MOD_ID, output);
    }


    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.storageRecipes(consumer, RecipeCategory.MISC, Items.HONEYCOMB, RecipeCategory.BUILDING_BLOCKS, WAX_BLOCK.get());
    }



    public void stonecutterRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike output, ItemLike input, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count).unlockedBy(getHasName(input), has(input)).save(consumer, this.getModConversionRecipeName(output, input) + "_stonecutting");
    }
}
