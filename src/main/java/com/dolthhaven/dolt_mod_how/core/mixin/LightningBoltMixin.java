package com.dolthhaven.dolt_mod_how.core.mixin;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.other.DoltModHowTrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin extends Entity {

    @Shadow protected abstract BlockPos getStrikePosition();


    public LightningBoltMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "tick()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;clearCopperOnLightningStrike(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
    private void DoltModHow$StruckJukeboxPlaysEpilogue(CallbackInfo ci) {
//        if (ModList.get().isLoaded("anchor"))
            doJukeboxCheck(this.level, this.getStrikePosition());
    }


    private static void doJukeboxCheck(Level level, BlockPos struckPos)  {
        if (struckPos.getY() > 0)
            return;

        BlockPos pos;
        BlockState struckState = level.getBlockState(struckPos);
        if (struckState.is(Blocks.LIGHTNING_ROD))
            pos = struckPos.relative(struckState.getValue(LightningRodBlock.FACING).getOpposite());
        else
            pos = struckPos;

        IDataManager manager = (IDataManager) level;

        long lastDiscTime = manager.getValue(DoltModHowTrackedData.LAST_EPILOGUE_DISC);
        DoltModHow.LOGGER.info(lastDiscTime + " SHUT UP HI HERE NUMBER YAY");

        if (level.getGameTime() - lastDiscTime < Level.TICKS_PER_DAY)
            return;

        if (level.getBlockState(pos).getBlock() instanceof JukeboxBlock juke && !(level.getBlockState(pos).getValue(JukeboxBlock.HAS_RECORD))) {
//            Item epilogue = ForgeRegistries.ITEMS.getValue(new ResourceLocation("anchor", "music_disc_epilogue"));
            Item epilogue = Items.MUSIC_DISC_MALL;
            juke.setRecord(null, level, pos, level.getBlockState(pos), new ItemStack(epilogue));
            level.levelEvent(null, 1010, pos, Item.getId(epilogue));

            manager.setValue(DoltModHowTrackedData.LAST_EPILOGUE_DISC, level.getGameTime());
        }
    }
}
