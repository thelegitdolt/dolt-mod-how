package com.dolthhaven.dolt_mod_how.integration;

import com.teamabnormals.savage_and_ravage.common.entity.projectile.SporeCloud;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import net.minecraft.world.entity.Entity;

public class DMHSnSCompat {
    public static void makeSporeCloud(Entity entity, int baseSize, int randomSize) {
        SporeCloud sporeCloud = SREntityTypes.SPORE_CLOUD.get().create(entity.level());
        if (sporeCloud != null) {
            sporeCloud.setCloudSize(baseSize + entity.level().getRandom().nextInt(randomSize));
            sporeCloud.setSpawnCloudInstantly(true);
            sporeCloud.creepiesAttackPlayersOnly(true);
            sporeCloud.copyPosition(entity);
            entity.level().addFreshEntity(sporeCloud);
        }
    }
}
