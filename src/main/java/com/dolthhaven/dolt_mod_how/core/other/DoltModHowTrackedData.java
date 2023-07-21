package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.resources.ResourceLocation;

public class DoltModHowTrackedData {
    public static final TrackedData<Long> LAST_EPILOGUE_DISC = TrackedData.Builder.create(DataProcessors.LONG, () -> -24000L).enableSaving().build();

    public static void registerTrackedData() {
        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(DoltModHow.MOD_ID, "last_epilogue_disc"), LAST_EPILOGUE_DISC);
    }
}
