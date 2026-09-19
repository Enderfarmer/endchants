package com.endchants;

import com.endchants.enchantment.effect.BuffOnKillEffect;
import com.endchants.enchantment.effect.FleshEaterEffect;
import com.endchants.enchantment.effect.FreezingEffect;
import com.endchants.enchantment.effect.RadianceEffect;
import com.endchants.enchantment.effect.RepellingEffect;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class ModEnchantmentEffects {
        public static MapCodec<FleshEaterEffect> FLESH_EATER_EFFECT = register("flesh_eater_effect",
                        FleshEaterEffect.CODEC);
        public static MapCodec<RepellingEffect> REPELLING_EFFECT = register("repelling_effect", RepellingEffect.CODEC);

        public static MapCodec<RadianceEffect> RADIANCE_EFFECT = register("radiance_effect", RadianceEffect.CODEC);

        public static MapCodec<BuffOnKillEffect> BUFF_ON_KILL_EFFECT = register("buff_on_kill_effect",
                        BuffOnKillEffect.CODEC);
        public static MapCodec<FreezingEffect> FREEZING_EFFECT = register("freezing_effect", FreezingEffect.CODEC);

        private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
                return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                                Endchants.id(id), codec);
        }

        public static void registerModEnchantmentEffects() {

        }
}