package com.endchants.enchantment.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public interface EntityEffectBase extends EnchantmentEntityEffect {
    default public void applyToLiving(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {
        spawnEntityApply(serverLevel, i, enchantedItemInUse.owner().position(), enchantedItemInUse.owner(), victim);
        affectVictim(i, enchantedItemInUse.owner().position(), victim);
        if (victim.isDeadOrDying())
            applyIfKilled(serverLevel, i, enchantedItemInUse, victim, vec3);
    };

    default public void applyIfKilled(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {
    };

    default public void spawnEntityApply(ServerLevel serverLevel, int i, Vec3 ownerPosition) {
    };

    default public void spawnEntityApply(ServerLevel serverLevel, int i, Vec3 ownerPosition, LivingEntity owner) {
        spawnEntityApply(serverLevel, i, ownerPosition);
    };

    default public void spawnEntityApply(ServerLevel serverLevel, int i, Vec3 ownerPosition, LivingEntity owner,
            LivingEntity victim) {
        spawnEntityApply(serverLevel, i, ownerPosition, owner);
    };

    default public void affectOwner(int i, LivingEntity owner) {
    };

    default public void affectOwnerOnKill(int i, LivingEntity owner) {
    };

    default public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
    };

    @Override
    default public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity,
            Vec3 vec3) {
        affectOwner(i, enchantedItemInUse.owner());
        if (entity instanceof LivingEntity livingEntity) {
            applyToLiving(serverLevel, i, enchantedItemInUse, livingEntity, vec3);

            if (livingEntity.isDeadOrDying()) {
                affectOwnerOnKill(i, livingEntity);
            }
        }
    }
}
