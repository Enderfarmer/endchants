package com.endchants;

import net.minecraft.resources.Identifier;

public class IdGen {
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Endchants.MOD_ID, path);
    }
}
