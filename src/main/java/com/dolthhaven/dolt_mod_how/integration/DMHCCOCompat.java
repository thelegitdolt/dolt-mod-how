package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.teamabnormals.blueprint.core.api.BlueprintCauldronInteraction;
import com.teamabnormals.caverns_and_chasms.core.other.CCCauldronInteractions;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import galena.oreganized.content.block.MoltenLeadCauldronBlock;
import galena.oreganized.index.OBlocks;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DMHCCOCompat {
    public static final CauldronInteraction FILL_CAULDRON_WITH_LEAD = (state, level, pos, player, hand, stack) ->
            CCCauldronInteractions.emptyBucket(level, pos, player, hand, stack,
                    OBlocks.MOLTEN_LEAD_CAULDRON.get().defaultBlockState().setValue(MoltenLeadCauldronBlock.AGE, 3),
                    (blockState) -> blockState.getValue(MoltenLeadCauldronBlock.AGE) == 3, SoundEvents.BUCKET_EMPTY_LAVA, SoundEvents.BUCKET_FILL_LAVA);
    public static final CauldronInteraction EMPTY_CAULDRON_OF_LEAD = (state, level, pos, player, hand, stack) ->
            CCCauldronInteractions.fillBucket(state, level, pos, player, hand, stack, new ItemStack(DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET.get()),
                    (blockState) -> blockState.getValue(MoltenLeadCauldronBlock.AGE) == 3, SoundEvents.BUCKET_FILL_LAVA);

    public static void registerCauldrons() {
        BlueprintCauldronInteraction.addMoreDefaultInteractions(DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET.get(), FILL_CAULDRON_WITH_LEAD);

        put(CCItems.GOLDEN_BUCKET.get(), EMPTY_CAULDRON_OF_LEAD);
    }

    public static void put(Item item, CauldronInteraction interaction) {
        MoltenLeadCauldronBlock.INTERACTION_MAP.put(item, interaction);
    }
}
