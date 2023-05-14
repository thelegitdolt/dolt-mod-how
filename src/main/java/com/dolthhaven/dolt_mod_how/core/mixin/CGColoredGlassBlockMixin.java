package com.dolthhaven.dolt_mod_how.core.mixin;

import com.supermartijn642.connectedglass.CGColoredGlassBlock;
import com.supermartijn642.connectedglass.CGGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CGColoredGlassBlock.class)
public class CGColoredGlassBlockMixin extends CGGlassBlock {
    @Shadow @Final private DyeColor color;

    public CGColoredGlassBlockMixin(String texture, boolean connected) {
        super(texture, connected);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("minecraft", color.getName() + "_glass")
        ));
    }
}
