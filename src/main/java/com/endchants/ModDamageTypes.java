package com.endchants;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static ResourceKey<DamageType> SHOCKWAVE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            Endchants.id("shockwave_damage"));

    public static void init() {
    }
}
