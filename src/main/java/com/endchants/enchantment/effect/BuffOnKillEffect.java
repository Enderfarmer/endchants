package com.endchants.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record BuffOnKillEffect(LevelBasedValue amplifier, LevelBasedValue duration, Holder<MobEffect> effect,
                float chance)
                implements EnchantmentEntityEffect {
        public static final MapCodec<BuffOnKillEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        LevelBasedValue.CODEC.fieldOf("amplifier").forGetter(BuffOnKillEffect::amplifier),
                        LevelBasedValue.CODEC.fieldOf("duration").forGetter(BuffOnKillEffect::duration),
                        BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect")
                                        .forGetter(BuffOnKillEffect::effect),
                        Codec.FLOAT.fieldOf("chance").forGetter(
                                        BuffOnKillEffect::chance))

                        .apply(instance, BuffOnKillEffect::new));

        @Override
        public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity victim,
                        Vec3 vec3) {
                if (!victim.isAlive() && Math.random() < chance) {
                        enchantedItemInUse.owner()
                                        .addEffect(new MobEffectInstance(effect,
                                                        Math.round(duration.calculate(i) * 20), Math.round(
                                                                        amplifier.calculate(i))));
                        System.out.println("Amplifier: " + amplifier.calculate(i) + ", Duration: "
                                        + duration.calculate(i));
                }

        }

        public MapCodec<BuffOnKillEffect> codec() {
                return CODEC;
        }

}