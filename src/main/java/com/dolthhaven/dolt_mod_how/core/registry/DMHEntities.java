package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.entity.ThrowableTankardEntity;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DMHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DoltModHow.MOD_ID);

    public static final RegistryObject<EntityType<ThrowableTankardEntity>> TANKARD = ENTITIES.register("rotten_tomato", () -> (
            EntityType.Builder.<ThrowableTankardEntity>of(ThrowableTankardEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("rotten_tomato")));
}
