package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record ExplodingEffect(LevelBasedValue damagePercentage) implements EntityEffectBase {
    public static final MapCodec<ExplodingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("damage_percentage")
            .xmap(ExplodingEffect::new, ExplodingEffect::damagePercentage);

    @Override
    public void applyIfKilled(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {
        createCustomExplosion(serverLevel, enchantedItemInUse.owner(), victim.position(), 2,
                victim.getMaxHealth() * damagePercentage.calculate(i));
    }

    @Override
    public MapCodec<ExplodingEffect> codec() {
        return CODEC;
    }

    public void createCustomExplosion(ServerLevel level, LivingEntity owner, Vec3 pos, float radius,
            float customDamage) {
        // 1. Play explosion sounds and spawn particles across surrounding clients
        level.playSound(
                null,
                pos.x, pos.y, pos.z,
                SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.BLOCKS,
                4.0F,
                (1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F);

        level.sendParticles(
                ParticleTypes.EXPLOSION_EMITTER,
                pos.x, pos.y, pos.z,
                1, 0.0, 0.0, 0.0, 0.0);

        // 2. Define search bounding box around the explosion center
        AABB searchBox = new AABB(
                pos.x - radius, pos.y - radius, pos.z - radius,
                pos.x + radius, pos.y + radius, pos.z + radius);

        // 3. Find and affect nearby living entities
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, searchBox)) {
            // --- OWNER IMMUNITY CHECK ---
            if (owner != null && target.is(owner)) {
                continue; // Skip the owner completely
            }

            double distanceSq = target.distanceToSqr(pos);
            if (distanceSq <= radius * radius) {

                // Apply custom damage using explosion damage source
                target.hurtServer(level, Explosion.getDefaultDamageSource(level, owner), Math.max(1.0F, customDamage));

                // Optional: Calculate and apply custom knockback vector
                Vec3 knockbackDir = target.getEyePosition().subtract(pos).normalize();
                target.setDeltaMovement(target.getDeltaMovement().add(knockbackDir.scale(1.2)));
            }
        }
    }
}
