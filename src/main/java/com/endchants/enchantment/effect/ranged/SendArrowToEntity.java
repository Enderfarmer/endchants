package com.endchants.enchantment.effect.ranged;

import java.util.List;

import com.endchants.ModEntities;
import com.endchants.entity.ShortArrow;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SendArrowToEntity {
    private static Vec3 calculateMotion(Vec3 origin, Vec3 target) {
        Vec3 delta = target.subtract(origin).normalize();
        return delta.scale(1.5);
    }

    public static void sendArrowToEntity(Vec3 origin, LivingEntity entity, LivingEntity attacker,
            ServerLevel serverLevel) {
        List<LivingEntity> entities = serverLevel.getNearbyEntities(LivingEntity.class,
                TargetingConditions.forCombat().range(16), attacker,
                new AABB(origin.x - 16, origin.y - 5, origin.z - 16, origin.x + 16, origin.y + 5, origin.z + 16));
        LivingEntity targetEntity = null;
        for (LivingEntity livingEntity : entities) {
            if (!livingEntity.equals(entity)) {
                targetEntity = livingEntity;
                break;
            }
        }
        if (targetEntity != null) {
            Vec3 vel = calculateMotion(origin, targetEntity.position());
            ShortArrow arrow = ModEntities.SHORT_ARROW.create(serverLevel, EntitySpawnReason.TRIGGERED);
            arrow.setDeltaMovement(vel);
            arrow.setPos(origin);
            arrow.setYRot((float) Math.toDegrees(Math.atan2(vel.z, vel.x)));

            serverLevel.addFreshEntity(arrow);
        }

    }
}
