package com.endchants.enchantment.subpredicate;

import com.mojang.serialization.MapCodec;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IsHurtPredicate implements EntitySubPredicate {
    public static final MapCodec<IsHurtPredicate> CODEC = MapCodec.unit(IsHurtPredicate::new);

    @Override
    public boolean matches(
            Entity entity,
            ServerLevel serverLevel,
            Vec3 position) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity
                    .getHealth() < livingEntity
                            .getMaxHealth();
        }
        return false;
    }

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return CODEC;
    }

}
