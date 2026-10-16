package com.endchants;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private static TagKey<Item> createKey(String name) {
        return TagKey.create(Registries.ITEM, Endchants.id(name));
    }

    public static final TagKey<Item> RANGED_WEAPON = createKey("ranged_weapon");
    public static final TagKey<Item> ARROW_FIRING = createKey("arrow_firing");
}
