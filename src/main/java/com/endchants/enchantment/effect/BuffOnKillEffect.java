package com.endchants.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record BuffOnKillEffect(LevelBasedValue amplifier, LevelBasedValue duration, Holder<MobEffect> effect,
                float chance)
                implements EntityEffectBase {
        public static final MapCodec<BuffOnKillEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        LevelBasedValue.CODEC.fieldOf("amplifier").forGetter(BuffOnKillEffect::amplifier),
                        LevelBasedValue.CODEC.fieldOf("duration").forGetter(BuffOnKillEffect::duration),
                        BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect")
                                        .forGetter(BuffOnKillEffect::effect),
                        Codec.FLOAT.fieldOf("chance").forGetter(
                                        BuffOnKillEffect::chance))

                        .apply(instance, BuffOnKillEffect::new));

        @Override
        public void affectOwnerOnKill(int i, LivingEntity owner) {
                if (Math.random() < chance) {
                        owner
                                        .addEffect(new MobEffectInstance(effect,
                                                        Math.round(duration.calculate(i) * 20), Math.round(
                                                                        amplifier.calculate(i))));
                }
        }

        public MapCodec<BuffOnKillEffect> codec() {
                return CODEC;
        }

}