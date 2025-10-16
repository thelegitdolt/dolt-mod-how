package com.dolthhaven.dolt_mod_how.data.client;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.INDIGO_ENCASED_PIPE;

public class DMHItemModelsGen extends BlueprintItemModelProvider {
    public DMHItemModelsGen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), DoltModHow.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        generatedItem(DMHItems.WARDENZOLA_WEDGE, DMHItems.TEQUILA, ANCIENT_BRAZIER);
        pipes();
    }

    private void pipes() {
        for (RegistryObject<?> blocks : new RegistryObject<?>[]{
                WHITE_ENCASED_PIPE, BROWN_ENCASED_PIPE, GRAY_ENCASED_PIPE, LIGHT_GRAY_ENCASED_PIPE, RED_ENCASED_PIPE, ORANGE_ENCASED_PIPE,
                YELLOW_ENCASED_PIPE, LIME_ENCASED_PIPE, GREEN_ENCASED_PIPE, BLUE_ENCASED_PIPE, LIGHT_BLUE_ENCASED_PIPE, CYAN_ENCASED_PIPE, PURPLE_ENCASED_PIPE,
                MAGENTA_ENCASED_PIPE, PINK_ENCASED_PIPE, BLACK_ENCASED_PIPE, ROSE_ENCASED_PIPE, MAROON_ENCASED_PIPE, GINGER_ENCASED_PIPE, TAN_ENCASED_PIPE,
                BEIGE_ENCASED_PIPE, CORAL_ENCASED_PIPE, OLIVE_ENCASED_PIPE, FOREST_ENCASED_PIPE, VERDANT_ENCASED_PIPE, AMBER_ENCASED_PIPE,
                TEAL_ENCASED_PIPE, MINT_ENCASED_PIPE, AQUA_ENCASED_PIPE, SLATE_ENCASED_PIPE, NAVY_ENCASED_PIPE, INDIGO_ENCASED_PIPE
        }) {
            String color = blocks.getId().getPath().replace("_encased_pipe", "");
            boolean dyeDepot = DyeDepotCompat.getDyeDepotDye(color) != null;
            ResourceLocation glass = new ResourceLocation(dyeDepot ? DMHUtils.Constants.DYE_DEPOT : ResourceLocation.DEFAULT_NAMESPACE,
                    color + "_stained_glass");
            this.withExistingParent(blocks.getId().getPath(), "quark:item/encased_pipe")
                    .texture("glass", glass.withPath(a -> "block/" + a));
        }
    }
}
