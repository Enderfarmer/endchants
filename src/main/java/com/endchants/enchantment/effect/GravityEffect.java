package com.endchants.enchantment.effect;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record GravityEffect(double pullingStrength) implements EnchantmentEntityEffect {
    public static MapCodec<GravityEffect> CODEC = Codec.DOUBLE.fieldOf("pullingStrength").xmap(GravityEffect::new,
            GravityEffect::pullingStrength);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        List<LivingEntity> nearbyEntities = (List<LivingEntity>) (Object) serverLevel
                .getEntities(entity, new AABB(vec3.subtract(4), vec3.add(4))).stream()
                .filter(enity -> enity instanceof LivingEntity && enity != enchantedItemInUse.owner()).toList();
        nearbyEntities.forEach(
                livingEntity -> livingEntity.setDeltaMovement(entity.position().subtract(livingEntity.position())
                        .normalize().multiply(pullingStrength, pullingStrength, pullingStrength)));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
