package com.dolthhaven.dolt_mod_how.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHRecipes extends RecipeProvider {
    public DMHRecipes(DataGenerator data) {
        super(data);
    }

    @Override
    public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(MUD_LANTERN.get()).define('1', Items.PACKED_MUD).define('2', Items.REDSTONE_TORCH).pattern("1").pattern("2").pattern("1").unlockedBy("has_packed_mud", has(Items.PACKED_MUD)).save(consumer);

        ShapedRecipeBuilder.shaped(STURDY_DEEPSLATE.get()).define('1', Items.COBBLED_DEEPSLATE).pattern("111").pattern("111").pattern("111").unlockedBy("has_cobble_deepslate", has(Items.COBBLED_DEEPSLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.COBBLED_DEEPSLATE, 9).requires(STURDY_DEEPSLATE.get()).unlockedBy("has_sturdy_dep", has(STURDY_DEEPSLATE.get())).save(consumer, new ResourceLocation("dolt_mod_how", "cobbled_deepslate_from_unpacking_sturdy_deepslate"));

    }

}
