package com.dolthhaven.dolt_mod_how.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHRecipes extends RecipeProvider {
    public DMHRecipes(DataGenerator data) {
        super(data);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(MUD_LANTERN.get()).define('1', Items.PACKED_MUD).define('2', Items.REDSTONE_TORCH).pattern("1").pattern("2").pattern("1").unlockedBy("has_packed_mud", has(Items.PACKED_MUD)).save(consumer);
    }

}
