package com.endchants;

import com.endchants.enchantment.effect.BackstabbingEffect;
import com.endchants.enchantment.effect.BuffOnKillEffect;
import com.endchants.enchantment.effect.CloudEffect;
import com.endchants.enchantment.effect.CriticalHitEffect;
import com.endchants.enchantment.effect.EchoEffect;
import com.endchants.enchantment.effect.FleshEaterEffect;
import com.endchants.enchantment.effect.FreezingEffect;
import com.endchants.enchantment.effect.GravityEffect;
import com.endchants.enchantment.effect.LeechingEffect;
import com.endchants.enchantment.effect.RadianceEffect;
import com.endchants.enchantment.effect.RepellingEffect;
import com.endchants.enchantment.effect.ShockwaveEffect;
import com.endchants.enchantment.effect.StunningEffect;
import com.endchants.enchantment.effect.VoidedEffect;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public class ModEnchantmentEffects {
        public static MapCodec<FleshEaterEffect> FLESH_EATER_EFFECT = register("flesh_eater_effect",
                        FleshEaterEffect.CODEC);
        public static MapCodec<RepellingEffect> REPELLING_EFFECT = register("repelling_effect", RepellingEffect.CODEC);

        public static MapCodec<RadianceEffect> RADIANCE_EFFECT = register("radiance_effect", RadianceEffect.CODEC);

        public static MapCodec<BuffOnKillEffect> BUFF_ON_KILL_EFFECT = register("buff_on_kill_effect",
                        BuffOnKillEffect.CODEC);
        public static MapCodec<FreezingEffect> FREEZING_EFFECT = register("freezing_effect", FreezingEffect.CODEC);

        public static MapCodec<GravityEffect> GRAVITY_EFFECT = register("gravity_effect", GravityEffect.CODEC);

        public static MapCodec<BackstabbingEffect> BACKSTABBING_EFFECT = registerValEff("backstabbing_effect",
                        BackstabbingEffect.CODEC);
        public static MapCodec<CriticalHitEffect> CRITICAL_HIT_EFFECT = registerValEff("critical_hit_effect",
                        CriticalHitEffect.CODEC);
        public static MapCodec<EchoEffect> ECHO_EFFECT = register("echo_effect", EchoEffect.CODEC);
        public static MapCodec<VoidedEffect> VOIDED_EFFECT = register("void_strike_effect", VoidedEffect.CODEC);
        public static MapCodec<ShockwaveEffect> SHOCKWAVE_EFFECT = register("shockwave_effect", ShockwaveEffect.CODEC);
        public static MapCodec<CloudEffect> CLOUD_EFFECT = register("cloud_effect", CloudEffect.CODEC);
        public static MapCodec<StunningEffect> STUNNING_EFFECT = register("stunning_effect", StunningEffect.CODEC);
        public static MapCodec<LeechingEffect> LEECHING_EFFECT = register("leeching_effect", LeechingEffect.CODEC);

        private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
                return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                                Endchants.id(id), codec);
        }

        private static <T extends EnchantmentValueEffect> MapCodec<T> registerValEff(String id, MapCodec<T> codec) {
                return Registry.register(BuiltInRegistries.ENCHANTMENT_VALUE_EFFECT_TYPE, Endchants.id(id), codec);
        }

        public static void registerModEnchantmentEffects() {

        }
}