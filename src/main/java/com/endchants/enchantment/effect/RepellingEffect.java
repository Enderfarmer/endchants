package com.endchants.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RepellingEffect(float repellingStrength) implements EnchantmentEntityEffect {
    public static final MapCodec<RepellingEffect> CODEC = Codec.FLOAT.fieldOf("repelling_strength")
            .xmap(RepellingEffect::new, RepellingEffect::repellingStrength);

    @Override
    public void apply(ServerLevel serverLevel, int level, EnchantedItemInUse enchantedItemInUse, Entity entity,
            Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity && !livingEntity.isInvulnerable() && Math.random() < 0.5) {
            Vec3 direction = enchantedItemInUse.owner().position().subtract(vec3).normalize().reverse();
            double strength = repellingStrength * level;
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(direction.scale(strength)));
            enchantedItemInUse.itemStack()
                    .setDamageValue(enchantedItemInUse.itemStack().getDamageValue() + Math.ceilDiv(level, 2));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
