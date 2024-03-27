package com.dolthhaven.dolt_mod_how.core.data;

import com.uraneptus.sullysmod.core.registry.SMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHRecipes extends RecipeProvider {
    public static final ModLoadedCondition FISH_IN_PLANKS_LOADED = new ModLoadedCondition("fish_in_planks");


    public DMHRecipes(DataGenerator data) {
        super(data);
    }



    @Override
    public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        conditionalRecipe(consumer, FISH_IN_PLANKS_LOADED, ShapedRecipeBuilder.shaped(LANTERNFISH_BARREL.get()).define('1', SMItems.LANTERNFISH.get()).pattern("111").pattern("111").pattern("111")
                .unlockedBy("has_lanternfish", has(SMItems.LANTERNFISH.get())), new ResourceLocation("dolt_mod_how", "lanternfish_barrel"));

        conditionalRecipe(consumer, FISH_IN_PLANKS_LOADED, ShapelessRecipeBuilder.shapeless(SMItems.LANTERNFISH.get(), 9).requires(LANTERNFISH_BARREL.get())
                .unlockedBy("has_lanternfish_barrel", has(LANTERNFISH_BARREL.get())), new ResourceLocation("dolt_mod_how", "lanternfish_barrel_unpack"));

        ShapedRecipeBuilder.shaped(STURDY_DEEPSLATE.get()).define('1', Items.COBBLED_DEEPSLATE).pattern("111").pattern("111").pattern("111").unlockedBy("has_cobble_deepslate", has(Items.COBBLED_DEEPSLATE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.COBBLED_DEEPSLATE, 9).requires(STURDY_DEEPSLATE.get()).unlockedBy("has_sturdy_dep", has(STURDY_DEEPSLATE.get())).save(consumer, new ResourceLocation("dolt_mod_how", "cobbled_deepslate_from_unpacking_sturdy_deepslate"));
    }


    public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe, ResourceLocation id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(consumer, id);
    }

}
