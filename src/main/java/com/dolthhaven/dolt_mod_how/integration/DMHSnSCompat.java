package com.dolthhaven.dolt_mod_how.integration;

import com.teamabnormals.savage_and_ravage.common.entity.projectile.SporeCloud;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class DMHSnSCompat {
    public static void makeSporeCloud(Vec3 pos, Vec2 rotation, Level level, int baseSize, int randomSize) {
        SporeCloud sporeCloud = SREntityTypes.SPORE_CLOUD.get().create(level);
        if (sporeCloud != null) {
            sporeCloud.setCloudSize(baseSize + level.getRandom().nextInt(randomSize));
            sporeCloud.setSpawnCloudInstantly(true);
            sporeCloud.absMoveTo(pos.x, pos.y + 0.0625F, pos.z, rotation.x, rotation.y);
        }
    }
}
