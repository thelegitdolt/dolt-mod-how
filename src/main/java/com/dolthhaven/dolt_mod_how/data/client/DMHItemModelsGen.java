package com.dolthhaven.dolt_mod_how.data.client;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class DMHItemModelsGen extends BlueprintItemModelProvider {
    public DMHItemModelsGen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), DoltModHow.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        generatedItem(DMHItems.WARDENZOLA_WEDGE);
    }
}
