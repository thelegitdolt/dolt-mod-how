package com.dolthhaven.dolt_mod_how.core.mixin.jne;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.jadenxgamer.netherexp.registry.block.custom.BoneRodBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoneRodBlock.class)
public class BoneRodsMixin {
    @Definition(id = "asItem", method = "Lnet/jadenxgamer/netherexp/registry/block/custom/BoneRodBlock;asItem()Lnet/minecraft/world/item/Item;")
    @Expression("this.asItem()")
    @ModifyExpressionValue(method = "canBeReplaced", at = @At("MIXINEXTRAS:EXPRESSION"))
    private Item hi(Item original){
        return DMHConfig.COMMON.shouldPlaceBonePilesWithNormalBones.get() ? Items.BONE : original;
    }
}
