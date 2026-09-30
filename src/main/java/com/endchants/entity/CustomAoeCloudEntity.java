package com.endchants.entity;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class CustomAoeCloudEntity extends AreaEffectCloud {
    public CustomAoeCloudEntity(EntityType<? extends CustomAoeCloudEntity> type, Level level) {
        super(type, level);
    }

    // @Override
    // public void tick() {
    // setCustomParticle(ColorParticleOption.create(ParticleTypes.SMOKE,
    // BASE_SAFE_FALL_DISTANCE));
    // }

}
