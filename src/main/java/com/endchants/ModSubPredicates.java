package com.endchants;

import com.endchants.enchantment.subpredicate.IsHurtPredicate;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModSubPredicates {
    public static void register() {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, Endchants.id("is_hurt"), IsHurtPredicate.CODEC);
    }
}
