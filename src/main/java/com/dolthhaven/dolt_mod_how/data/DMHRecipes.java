package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import com.rosemods.heart_crystals.core.registry.HCBlocks;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.addons.oddities.module.PipesModule;

import java.util.function.Consumer;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHRecipes extends BlueprintRecipeProvider {
    public DMHRecipes(PackOutput output) {
        super(DoltModHow.MOD_ID, output);
    }
    public static final ModLoadedCondition HEART_CRYSTALS_LOADED = new ModLoadedCondition(DMHUtils.Constants.HEART_CRYSTALS);


    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.storageRecipes(consumer, RecipeCategory.MISC, Items.HONEYCOMB, RecipeCategory.BUILDING_BLOCKS, WAX_BLOCK.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DMHItems.CHORUS_SODA.get())
                .requires(Items.GLASS_BOTTLE).requires(Items.CHORUS_FRUIT)
                .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
                .save(consumer);

        pipes(consumer);
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
