package com.endchants.enchantment.effect.armor;

import java.util.List;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record BurningEffect(LevelBasedValue radius) implements EntityEffectBase {
    public static final MapCodec<BurningEffect> CODEC = LevelBasedValue.CODEC.fieldOf("radius").xmap(BurningEffect::new,
            BurningEffect::radius);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        int rad = (int) radius.calculate(i);
        List<LivingEntity> entities = serverLevel.getNearbyEntities(LivingEntity.class,
                TargetingConditions.forNonCombat(), enchantedItemInUse.owner(),
                new AABB(vec3.x - rad, vec3.y, vec3.z - rad, vec3.x + rad, vec3.y + 1, vec3.z + rad));
        entities.forEach(enity -> {
            enity.setRemainingFireTicks(5);
            enity.addEffect(new MobEffectInstance(MobEffects.WITHER, 3, i));
        });

        for (int j = 0; j < 36; j++) {
            if (Math.random() < .03) {
                serverLevel.sendParticles(ParticleTypes.FLAME,
                        vec3.x + Math.cos(Math.toRadians(j * 10 + Math.random() * 5)) * rad,
                        vec3.y + 0.5,
                        vec3.z + Math.sin(Math.toRadians(j * 10 + Math.random() * 5)) * rad, 0, 0, 0, 0, 2);
            }
        }
    }

    public MapCodec<BurningEffect> codec() {
        return CODEC;
    }
}
