package com.endchants;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    private static final SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT,
                ResourceKey.create(Registries.SOUND_EVENT, Endchants.id(name)),
                SoundEvent.createVariableRangeEvent(Endchants.id(name)));
    }

    public static final SoundEvent BACKSTABBING = register("backstabbing");
    public static final SoundEvent CRITICAL_HIT = register("critical_hit");
    public static final SoundEvent FREEZING = register("freezing");
    public static final SoundEvent GUARDING_STRIKE_1 = register("guarding_strike_1");
    public static final SoundEvent GUARDING_STRIKE_2 = register("guarding_strike_2");
    public static final SoundEvent GUARDING_STRIKE_3 = register("guarding_strike_3");
    public static final SoundEvent STUNNING = register("stunning");
    public static final SoundEvent LEECHING = register("leeching");
    public static final SoundEvent POISON_CLOUD = register("poison_cloud");
    public static final SoundEvent VOIDED = register("voided");
    public static final SoundEvent LEVISHOT = register("levishot");
    public static final SoundEvent RUSH_1 = register("rush_1");
    public static final SoundEvent RUSH_2 = register("rush_2");
    public static final SoundEvent RUSH_3 = register("rush_3");
    public static final SoundEvent REPELLING = register("repelling");
    public static final SoundEvent RAMPAGING = register("rampaging");

    public static void init() {
        // This method is intentionally left empty. It is used to trigger the static
        // initializers of this class.
        System.out.println("Initializing ModSoundEvents...");
    }
}
