package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class DMHMobEffectTags extends IntrinsicHolderTagsProvider<MobEffect> {
    public DMHMobEffectTags(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), Registries.MOB_EFFECT, event.getLookupProvider(),
                (effect) -> ForgeRegistries.MOB_EFFECTS.getResourceKey(effect).get(),
                DoltModHow.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(DMHTags.GENEROSITY_CANNOT_SHARE).add(MobEffects.HERO_OF_THE_VILLAGE, MobEffects.BAD_OMEN);
        tag(DMHTags.MIMING_CANNOT_COPY).add(MobEffects.HERO_OF_THE_VILLAGE, MobEffects.BAD_OMEN);
    }
}
