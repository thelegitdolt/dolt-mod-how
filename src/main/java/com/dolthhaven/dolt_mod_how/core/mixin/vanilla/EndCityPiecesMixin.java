package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EndCityPieces.EndCityPiece.class)
public class EndCityPiecesMixin {
    @ModifyArg(method = "handleDataMarker", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"))
    private ItemLike sex(ItemLike item) {
        String newItem = DMHConfig.COMMON.replaceEndShipItem.get();
        if (newItem.equals("minecraft:elytra")) return item;
        try {
            Item newNewItem = DMHUtils.getPotentialItem(ResourceLocation.tryParse(newItem));
            if (newNewItem == null || newNewItem == Items.AIR) {
                return item;
            } return newNewItem;
        }
        catch (ResourceLocationException | NullPointerException e) {
            return item;
        }
    }

}
