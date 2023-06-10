package com.dolthhaven.dolt_mod_how.core.mixin;

import lilypuree.mapatlases.recipe.MapAtlasCreateRecipe;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collection;
import java.util.List;

@Mixin(MapAtlasCreateRecipe.class)
public class MapAtlasCreateRecipeMixin {
    @ModifyArg(method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z",
    at = @At(value = "INVOKE", target = "Ljava/util/List;containsAll(Ljava/util/Collection;)Z"), remap = false)
    private Collection<?> DoltModHow$GoodAtlasRecipe(Collection<?> c) {
        return List.of(Items.WRITABLE_BOOK, Items.COMPASS, Items.FILLED_MAP);
    }
}
