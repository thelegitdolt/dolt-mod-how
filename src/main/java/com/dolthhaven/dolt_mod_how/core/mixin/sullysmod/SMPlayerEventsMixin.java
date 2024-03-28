package com.dolthhaven.dolt_mod_how.core.mixin.sullysmod;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.uraneptus.sullysmod.common.recipes.GrindstonePolishingRecipe;
import com.uraneptus.sullysmod.core.events.SMPlayerEvents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(SMPlayerEvents.class)
public class SMPlayerEventsMixin {
    @Redirect(method = "onItemTooltip", at=@At(value = "INVOKE", target = "Lcom/uraneptus/sullysmod/common/recipes/GrindstonePolishingRecipe;getRecipes(Lnet/minecraft/world/level/Level;)Ljava/util/List;"))
    private static List<GrindstonePolishingRecipe> DoltModHow$GetGrindstoneRecipes(Level level) {
        if (!DoltModHowConfig.CLIENT.removeSullyGrindstoneTooltip.get()) {
            return GrindstonePolishingRecipe.getRecipes(level);
        }
        else {
            return new ArrayList<>();
        }
    }
}
