package com.endchants.enchantment.effect.tool;

import com.endchants.ModEntities;
import com.endchants.entity.HarvestWaveEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.phys.Vec3;

public class HarvestWaveEffect {
    public static void apply(ServerLevel serverLevel, BlockPos blockPos, Vec3 lookingDirection) {
        HarvestWaveEntity wave = ModEntities.HARVEST_WAVE.create(serverLevel, EntitySpawnReason.TRIGGERED);
        wave.setPos(new Vec3(blockPos));
        wave.shoot(lookingDirection.x, 0, lookingDirection.z, 1.2f, 0);
        wave.setYBodyRot((float) (lookingDirection.z / lookingDirection.x));
        serverLevel.addFreshEntity(wave);

    }
}
