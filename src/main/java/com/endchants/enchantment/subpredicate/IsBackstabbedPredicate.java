package com.endchants.enchantment.subpredicate;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IsBackstabbedPredicate implements EntitySubPredicate {
    public static final MapCodec<IsBackstabbedPredicate> CODEC = MapCodec.unit(IsBackstabbedPredicate::new);

    @Override
    public boolean matches(Entity arg0, ServerLevel arg1, @Nullable Vec3 arg2) {
        if (arg0 instanceof LivingEntity entity) {
            Vec3 pos0 = arg0.position();
            LivingEntity attacker = entity.getLastAttacker();
            Vec3 pos1 = null;
            if (attacker != null) {
                pos1 = attacker.position();
            } else {
                attacker = entity.getLastHurtByMob();
                if (attacker != null)
                    pos1 = attacker.position();
                else
                    return false;
            }
            double dx = pos1.x - pos0.x;
            double dz = pos1.z - pos0.z;

            double yaw = Math.toDegrees(Math.atan2(dz, dx)) - 90.0;
            yaw = Mth.wrapDegrees(yaw);
            int yawDiff = (int) entity.getYHeadRot() - (int) yaw;
            if (yawDiff > 40 || yawDiff < -40) {

                return true;
            }

        }
        return false;

    }

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return CODEC;
    }
}
