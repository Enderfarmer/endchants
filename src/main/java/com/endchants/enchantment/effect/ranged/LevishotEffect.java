package com.endchants.enchantment.effect.ranged;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LevishotEffect(LevelBasedValue chance, LevelBasedValue duration) implements EntityEffectBase {
    public static final MapCodec<LevishotEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(LevelBasedValue.CODEC.fieldOf("chance").forGetter(LevishotEffect::chance),
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(LevishotEffect::duration))
            .apply(instance, LevishotEffect::new));

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (Math.random() < chance.calculate(i) && entity instanceof Arrow arrow)
            arrow.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) duration.calculate(i) * 20, 2));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
