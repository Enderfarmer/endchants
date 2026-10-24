package com.endchants.enchantment.effect.armor;

import com.endchants.ModEffects;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record SnowballEffect(LevelBasedValue cooldown) implements EntityEffectBase {
    public static final MapCodec<SnowballEffect> CODEC = LevelBasedValue.CODEC.fieldOf("cooldown")
            .xmap(SnowballEffect::new, SnowballEffect::cooldown);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (enchantedItemInUse.owner().tickCount % cooldown.calculate(i) == 0) {
            LivingEntity nearestEntity = serverLevel.getNearestEntity(LivingEntity.class,
                    TargetingConditions.forNonCombat(), enchantedItemInUse.owner(), vec3.x, vec3.y, vec3.z,
                    new AABB(vec3.x - 5, vec3.y - 5, vec3.z - 5, vec3.x + 5, vec3.y + 5, vec3.z + 5));
            if (nearestEntity != null) {
                nearestEntity.addEffect(new MobEffectInstance(ModEffects.STUNNED, 22, 0));
                Snowball snowball = EntityType.SNOWBALL.create(serverLevel, EntitySpawnReason.TRIGGERED);
                snowball.setPos(vec3.add(0, enchantedItemInUse.owner().getEyeHeight(), 0));
                Vec3 dir = nearestEntity.position().subtract(enchantedItemInUse.owner().position()).normalize()
                        .scale(1.5);
                snowball.shoot(dir.x, dir.y, dir.z, 1.5f, 0);
                serverLevel.addFreshEntity(snowball);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
