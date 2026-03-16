package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.common.WatchMobKillTrigger;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
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
        adv(new ResourceLocation("adventure/totem_of_undying"), CCItems.SPINEL.get(), "adventure", "totes", FrameType.TASK, true)
                .addCriterion("mimers", DMHCriteriaTriggers.TRIGGER_MIME_TOTEM.createInstance())
                .save(saver, DoltModHow.MOD_ID + ":adventure/trigger_mime_totem");
    }

    public Advancement.Builder adv(ResourceLocation parent, Item item, String category, String name, FrameType frame, boolean hidden) {
        return Advancement.Builder.advancement()
                .parent(Advancement.Builder.advancement()
                        .build(parent)).display(item,
                        Component.translatable("advancements.dolt_mod_how." +
                                category + "." + name + ".title"),
                        Component.translatable("advancements.dolt_mod_how." + category + "." + name + ".description"),
                        null, frame, true, true, hidden);
    }
}
