package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.STURDY_DEEPSLATE;

public class DMHRecipes extends BlueprintRecipeProvider {
    public DMHRecipes(PackOutput output) {
        super(DoltModHow.MOD_ID, output);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, STURDY_DEEPSLATE.get())
                .define('1', Items.COBBLED_DEEPSLATE)
                .pattern("111").pattern("111")
                .pattern("111")
                .unlockedBy("has_cobble_deepslate", has(Items.COBBLED_DEEPSLATE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DMHItems.CHORUS_SODA.get(), 2)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.SUGAR)
                .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE, 9)
                .requires(STURDY_DEEPSLATE.get())
                .unlockedBy("has_sturdy_dep", has(STURDY_DEEPSLATE.get()))
                .save(consumer, new ResourceLocation("dolt_mod_how", "cobbled_deepslate_from_unpacking_sturdy_deepslate"));
    }
}
