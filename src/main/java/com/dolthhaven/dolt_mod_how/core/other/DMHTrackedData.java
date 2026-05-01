package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.world.entity.LivingEntity;

public class DMHTrackedData {
    public static final TrackedData<Byte> LEVEL_IMPENDING_STAB = TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_DOUBLE_STAB = TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_SATED_BLADE = TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Boolean> IS_DESOLATE_DAGGER = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).build();

    public static final TrackedData<Boolean> VALLUMRAPTOR_CAN_OPEN_CHESTS = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> true).build();

    public static final TrackedData<Integer> THUNDERDOME_CHALLENGE_DATA = TrackedData.Builder.create(DataProcessors.INT, () -> 0).build();

    public static void registerTrackedData() {
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("desolate"), IS_DESOLATE_DAGGER);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_double_stab"), LEVEL_DOUBLE_STAB);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_sated_blade"), LEVEL_SATED_BLADE);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("level_impending_stab"), LEVEL_IMPENDING_STAB);
        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("vr_chest"), VALLUMRAPTOR_CAN_OPEN_CHESTS);

//        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("desolate_cleaver_info"), DESOLATE_DAGGER_CLEAVER_INFO);

        TrackedDataManager.INSTANCE.registerData(DoltModHow.rl("thunderdome_time"), THUNDERDOME_CHALLENGE_DATA);
    }

    public static boolean getVallumraptorChestData(LivingEntity entity) {
        return ((IDataManager) entity).getValue(VALLUMRAPTOR_CAN_OPEN_CHESTS);
    }

    public static void setVallumraptorChestData(LivingEntity entity, boolean set) {
        ((IDataManager) entity).setValue(VALLUMRAPTOR_CAN_OPEN_CHESTS, set);
    }
}
