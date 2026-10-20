package com.endchants;

import com.endchants.enchantment.effect.RepellingEffect;
import com.endchants.enchantment.effect.melee.BackstabbingEffect;
import com.endchants.enchantment.effect.melee.BuffOnKillEffect;
import com.endchants.enchantment.effect.melee.CloudEffect;
import com.endchants.enchantment.effect.melee.CriticalHitEffect;
import com.endchants.enchantment.effect.melee.EchoEffect;
import com.endchants.enchantment.effect.melee.ExplodingEffect;
import com.endchants.enchantment.effect.melee.FleshEaterEffect;
import com.endchants.enchantment.effect.melee.FreezingEffect;
import com.endchants.enchantment.effect.melee.GravityEffect;
import com.endchants.enchantment.effect.melee.LeechingEffect;
import com.endchants.enchantment.effect.melee.RadianceEffect;
import com.endchants.enchantment.effect.melee.ShockwaveEffect;
import com.endchants.enchantment.effect.melee.StunningEffect;
import com.endchants.enchantment.effect.melee.SwirlingEffect;
import com.endchants.enchantment.effect.melee.VoidedEffect;
import com.endchants.enchantment.effect.ranged.BonusShotEffect;
import com.endchants.enchantment.effect.ranged.ChainReactionEffect;
import com.endchants.enchantment.effect.ranged.FuseShotEffect;
import com.endchants.enchantment.effect.ranged.LevishotEffect;
import com.endchants.enchantment.effect.ranged.RicochetEffect;
import com.endchants.enchantment.effect.ranged.TempoTheftEffect;
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
        public static MapCodec<SwirlingEffect> SWIRLING_EFFECT = register("swirling_effect", SwirlingEffect.CODEC);
        public static MapCodec<ExplodingEffect> EXPLODING_EFFECT = register("exploding_effect", ExplodingEffect.CODEC);
        public static MapCodec<ChainReactionEffect> CHAIN_REACTION_EFFECT = register("chain_reaction_effect",
                        ChainReactionEffect.CODEC);
        public static MapCodec<TempoTheftEffect> TEMPO_THEFT_EFFECT = register("tempo_theft", TempoTheftEffect.CODEC);
        public static MapCodec<FuseShotEffect> FUSE_SHOT_EFFECT = register("fuse_shot_effect", FuseShotEffect.CODEC);
        public static MapCodec<LevishotEffect> LEVISHOT_EFFECT = register("levishot_effect", LevishotEffect.CODEC);
        public static MapCodec<RicochetEffect> RICOCHET_EFFFECT = register("ricochet_effect", RicochetEffect.CODEC);
        public static MapCodec<BonusShotEffect> BONUS_SHOT_EFFECT = register("bonus_shot_effect",
                        BonusShotEffect.CODEC);

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