package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.rosemods.heart_crystals.core.registry.HCBlocks;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import com.teamabnormals.upgrade_aquatic.core.registry.UAItems;
import galena.doom_and_gloom.index.OItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class DMHAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    public static ForgeAdvancementProvider create(GatherDataEvent event) {
        return new ForgeAdvancementProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper(), List.of(new DMHAdvancements()));
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        adv(new ResourceLocation("adventure/totem_of_undying"), CCItems.SPINEL.get(), "adventure", "trigger_mime_totem", FrameType.TASK, true, 0)
                .addCriterion("mimers", DMHCriteriaTriggers.TRIGGER_MIME_TOTEM.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":adventure/trigger_mime_totem");
        adv(new ResourceLocation("nether/root"), UAItems.THRASHER_TOOTH.get(), "nether", "nether_thrasher", FrameType.CHALLENGE, false, 100)
                .addCriterion("thrash", DMHCriteriaTriggers.NETHER_THRASHER.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":nether/nether_thrasher");
        adv(new ResourceLocation("adventure/play_jukebox_in_meadows"), OItems.MUSIC_DISC_AFTERLIFE.get(), "adventure", "witness_holler_possess_jukebox", FrameType.TASK, false, 0)
                .addCriterion("jukers", DMHCriteriaTriggers.WITNESS_HOLLER_POSSESS_JUKEBOX.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":adventure/witness_holler_possess_jukebox");
        adv(new ResourceLocation("spawn", "husbandry/discover_stickbug"), Items.STICK, "adventure", "kill_stickbug_with_lightning", FrameType.TASK, false, 0)
                .addCriterion("oopsies", DMHCriteriaTriggers.KILL_BUG_WITH_LIGHTNING.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":adventure/kill_stickbug_with_lightning");
        adv(new ResourceLocation("adventure/kill_a_mob"), HCBlocks.HEART_CRYSTAL.get().asItem(), "adventure", "use_heart_crystal_on_low_health", FrameType.TASK, false, 0)
                .addCriterion("oopsies", DMHCriteriaTriggers.USE_HEART_CRYSTAL_ON_LOW_HEALTH.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":adventure/use_heart_crystal_on_low_health");
    }

    public Advancement.Builder adv(ResourceLocation parent, Item item, String category, String name, FrameType frame, boolean hidden, int rewards) {
        return Advancement.Builder.advancement()
                .parent(Advancement.Builder.advancement()
                        .build(parent)).rewards(new AdvancementRewards(rewards, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.NONE)).display(item,
                        Component.translatable("advancements.dolt_mod_how." +
                                category + "." + name + ".title"),
                        Component.translatable("advancements.dolt_mod_how." + category + "." + name + ".description"),
                        null, frame, true, true, hidden);
    }
}
