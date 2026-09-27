package com.endchants.datagen;

import java.util.concurrent.CompletableFuture;

import com.endchants.ModDamageTypes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeProvider extends FabricDynamicRegistryProvider {
    public ModDamageTypeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public String getName() {
        return "Damage types";
    }

    @Override
    protected void configure(Provider registries, Entries entries) {
        entries.add(ModDamageTypes.SHOCKWAVE_DAMAGE, new DamageType("shockwave_damage", 5f));
    }
}
