package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.data.specialrecipe.OriginalChestRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DMHRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DoltModHow.MOD_ID);

    public static final RegistryObject<SimpleRecipeSerializer<?>> CHEST_FROM_VARIATED_PLANKS =
            RECIPE_SERIALIZERS.register("chest_from_variated_planks", () -> new SimpleRecipeSerializer<>(OriginalChestRecipe::new));
}
