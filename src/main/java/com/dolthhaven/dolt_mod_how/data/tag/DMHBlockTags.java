package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCBlockTags;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.level.block.Blocks.*;

@SuppressWarnings("removal")
public class DMHBlockTags extends BlockTagsProvider {
    public DMHBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper efh) {
        super(output, lookupProvider, DoltModHow.MOD_ID, efh);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ALPHACENE_PATH.get());


        this.tag(DMHTags.NO_XP_CROPS)
                .addOptional(new ResourceLocation(DMHUtils.Constants.FARMERS_DELIGHT, "tomatoes"));

        this.tag(ModTags.MINEABLE_WITH_KNIFE)
                .addOptional(JNEBlocks.SORROWSQUASH.getId()).addOptional(JNEBlocks.CARVED_SORROWSQUASH.getId())
                .addOptional(DMHUtils.Constants.DINOSAUR_CHOP)
                .addOptional(DMHUtils.Constants.COOKED_DINOSAUR_CHOPS);
        this.tag(DMHTags.MINEABLE_SHEARS).add(HONEYCOMB_BLOCK, WAX_BLOCK.get());

        this.tag(DMHTags.RARE_ORES).addTags(BlockTags.GOLD_ORES)
                .addOptionalTag(new ResourceLocation("forge", "ores/silver"))
                .addOptionalTag(new ResourceLocation("forge", "ores/tin"))
                .addOptionalTag(new ResourceLocation("forge", "ores/palladium"));
        this.tag(DMHTags.COMMON_ORES).add(AMETHYST_CLUSTER).addTags(BlockTags.IRON_ORES, BlockTags.COPPER_ORES)
                .addOptional(new ResourceLocation("pigsteel", "porkslag"))
                .addOptionalTag(new ResourceLocation("forge", "ores/lead"))
                .addOptionalTag(new ResourceLocation("forge", "ores/zinc"));

        this.tag(DMHTags.CHANNELS_LIGHTNING).add(JUKEBOX);
    }

}
