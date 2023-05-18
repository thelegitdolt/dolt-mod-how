package com.dolthhaven.dolt_mod_how.core.data.specialrecipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OriginalChestRecipe extends CustomRecipe {
    public static final SimpleRecipeSerializer<?> SERIALIZER = new SimpleRecipeSerializer<>(OriginalChestRecipe::new);

    public OriginalChestRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        return checkForOtherRecipes(container, level) && checkForThing(container, 0, 1, 2, 3, 5, 6, 7, 8);
    }

    private static boolean checkForThing(CraftingContainer container, int... num) {
        if (!container.getItem(4).is(Items.AIR)) return false;
        for (int stack : num) {
            if (!container.getItem(stack).is(ItemTags.PLANKS)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container) {
        return new ItemStack(Items.CHEST);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static synchronized boolean checkForOtherRecipes(CraftingContainer container, Level level) {
        MinecraftServer server = level.getServer();
        if(server != null) {
            Optional<CraftingRecipe> optional = server.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level);
            return optional.isEmpty();
        }
        return false;
    }

}
