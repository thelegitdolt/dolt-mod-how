package com.dolthhaven.dolt_mod_how.core.mixin.anchor;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
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
        if (!DMHConfig.COMMON.discToSpawnAfterJukeboxStruckWithLightning.get().equals("no"))
            tryDoJukebox(this.level(), this.getStrikePosition());
    }

    @Unique
    private static void logError() {
        DoltModHow.LOGGER.error("INVALID DISC DETECTED. IN THE DOLT MOD HOW CONFIG vanilla/disc/Lightning Disc FIELD.");
        DoltModHow.LOGGER.error("THAT'S EITHER NOT A DISC ITEM, OR AN INCORRECT RESOURCE LOCATION. YOU SUCK. LEARN TO TYPE IDIOT.");
        DoltModHow.LOGGER.error("Or, if it is a disc item, report to " + DoltModHow.GIT_URL + " and I'm sorry I called you an idiot.");
    }

    @Unique
    private static void tryDoJukebox(Level level, BlockPos struckPos)  {
        ResourceLocation discLoc = ResourceLocation.tryParse(
                DMHConfig.COMMON.discToSpawnAfterJukeboxStruckWithLightning.get());

        if (discLoc == null) {
            logError();
            return;
        }

        Item item = ForgeRegistries.ITEMS.getValue(discLoc);
        if (!(item instanceof RecordItem)) {
            logError();
            return;
        }

        if (struckPos.getY() > 0)
            return;


        BlockPos pos = (level.getBlockState(struckPos).getBlock() instanceof LightningRodBlock) ?
                struckPos.relative(level.getBlockState(struckPos).getValue(LightningRodBlock.FACING).getOpposite()) :
                struckPos;

        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof JukeboxBlock && level.getBlockEntity(pos) instanceof JukeboxBlockEntity jukeEntity) {
            ItemStack stack = jukeEntity.getItem(0);
            if (!state.getValue(JukeboxBlock.HAS_RECORD) || stack.is(item)) {
                return;
            }

            jukeEntity.setItem(0, new ItemStack(item));
        }
    }
}