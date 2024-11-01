package com.dolthhaven.dolt_mod_how.core.serialization.biome_modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.Set;

public record ModLoadedRemoveBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step, String modLoaded) implements BiomeModifier {
    public static Codec<ModLoadedRemoveBiomeModifier> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModLoadedRemoveBiomeModifier::biomes),
                             PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(ModLoadedRemoveBiomeModifier::features),
                             GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(ModLoadedRemoveBiomeModifier::step),
                             ListCodec.STRING.fieldOf("is_loaded").forGetter(ModLoadedRemoveBiomeModifier::modLoaded)).apply(builder, ModLoadedRemoveBiomeModifier::new);
    });

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (ModList.get().isLoaded(this.modLoaded)) {
            if (phase == Phase.ADD && this.biomes.contains(biome)) {
                BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
                this.features.forEach((holder) -> generationSettings.addFeature(this.step, holder));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
