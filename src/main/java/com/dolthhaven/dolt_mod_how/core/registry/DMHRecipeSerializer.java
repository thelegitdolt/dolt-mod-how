package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.DyeDepotShulkerColoring;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShulkerBoxColoring;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DMHRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DoltModHow.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> DYE_DEPOT_SHULKER_COLORING = RECIPE_SERIALIZERS
            .register("dye_depot_shulker_coloring", () ->new SimpleCraftingRecipeSerializer<>(DyeDepotShulkerColoring::new));
}
