package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;

public class DMHTrackedData {
    public static final TrackedData<Byte> LEVEL_IMPENDING_STAB =
            TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_DOUBLE_STAB =
            TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_SATED_BLADE =
            TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Boolean> IS_DESOLATE_DAGGER =
            TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).build();


    public static void registerTrackedData() {
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("desolate"), IS_DESOLATE_DAGGER);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_double_stab"), LEVEL_DOUBLE_STAB);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_sated_blade"), LEVEL_SATED_BLADE);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_impending_stab"), LEVEL_IMPENDING_STAB);
    }
}
