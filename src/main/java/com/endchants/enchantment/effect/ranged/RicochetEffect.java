package com.endchants.enchantment.effect.ranged;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RicochetEffect(LevelBasedValue chance) implements EntityEffectBase {
    public static final MapCodec<RicochetEffect> CODEC = LevelBasedValue.CODEC.fieldOf("chance")
            .xmap(RicochetEffect::new, RicochetEffect::chance);

    @Override
    public void applyToLiving(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {
        if (Math.random() < chance.calculate(i))
            SendArrowToEntity.sendArrowToEntity(victim.getEyePosition(), victim,
                    enchantedItemInUse.owner(),
                    serverLevel);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
