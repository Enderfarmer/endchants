package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LeechingEffect(LevelBasedValue partOfMaxHealth) implements EntityEffectBase {
    public static final MapCodec<LeechingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("part_of_max_health")
            .xmap(LeechingEffect::new, LeechingEffect::partOfMaxHealth);

    @Override
    public void applyIfKilled(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {
        float maxHealth = victim.getMaxHealth();
        float healAmount = maxHealth * partOfMaxHealth.calculate(i);
        enchantedItemInUse.owner().heal(healAmount);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
