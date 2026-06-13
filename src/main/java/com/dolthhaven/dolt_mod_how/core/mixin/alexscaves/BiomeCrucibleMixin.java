package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ConversionCrucibleBlockEntity;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ConversionCrucibleBlockEntity.class)
public class BiomeCrucibleMixin {
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z")
    @Definition(id = "UNMOVEABLE", field = "Lcom/github/alexmodguy/alexscaves/server/misc/ACTagRegistry;UNMOVEABLE:Lnet/minecraft/tags/TagKey;")
    @Expression("?.is(UNMOVEABLE)")
    @WrapOperation(method = "recursivelySpreadBiomeBlocks", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean alsoCheckForNaturalBlock(BlockState instance, TagKey<Block> tagKey, Operation<Boolean> original) {
        boolean isUnmovable = original.call(instance, tagKey);
        boolean cannotReplaceBlock = !instance.is(DMHTags.BIOME_CRUCIBLE_CAN_CONVERT);
        return isUnmovable || cannotReplaceBlock;
    }
}
