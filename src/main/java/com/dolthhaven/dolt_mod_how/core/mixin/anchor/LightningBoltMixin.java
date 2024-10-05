package com.dolthhaven.dolt_mod_how.core.mixin.anchor;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixins lightning bolts so that when they strike a jukebox filled with a disc, the disc is set to epilogue.
 */
@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin extends Entity {
    @Shadow protected abstract BlockPos getStrikePosition();

    public LightningBoltMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "tick()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;clearCopperOnLightningStrike(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
    private void DoltModHow$StruckJukeboxPlaysEpilogue(CallbackInfo ci) {
        if (ModList.get().isLoaded("anchor") && DoltModHowConfig.COMMON.doLightningEpilogueDisc.get())
            tryDoJukebox(this.level(), this.getStrikePosition());
    }


    @Unique
    private static void tryDoJukebox(Level level, BlockPos struckPos)  {
        if (struckPos.getY() > 0)
            return;

        BlockPos pos = (level.getBlockState(struckPos).getBlock() instanceof LightningRodBlock) ?
                struckPos.relative(level.getBlockState(struckPos).getValue(LightningRodBlock.FACING).getOpposite()) :
                struckPos;

//        Item epilogue = ForgeRegistries.ITEMS.getValue(new ResourceLocation("anchor", "music_disc_epilogue"));
        Item ward = Items.MUSIC_DISC_WARD;
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof JukeboxBlock juke && level.getBlockEntity(pos) instanceof JukeboxBlockEntity jukeEntity) {
            if (!state.getValue(JukeboxBlock.HAS_RECORD)) {
                return;
            }
            ItemStack stack = jukeEntity.getItem(0);
            if (stack.is(Items.MUSIC_DISC_WARD)) {
                return;
            }

            jukeEntity.setItem(0, new ItemStack(ward));
        }
    }
}