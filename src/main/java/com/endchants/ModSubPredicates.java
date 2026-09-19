package com.endchants;

import com.endchants.enchantment.subpredicate.IsHurtPredicate;
import com.endchants.enchantment.subpredicate.IsIllagerPredicate;
import com.mojang.serialization.MapCodec;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModSubPredicates {
    private static void register(String name, MapCodec<? extends EntitySubPredicate> codec) {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, Endchants.id(name), codec);
    }

    public static void registerSubPredicates() {
        register("is_hurt", IsHurtPredicate.CODEC);
        register("is_illager", IsIllagerPredicate.CODEC);
    }
}
