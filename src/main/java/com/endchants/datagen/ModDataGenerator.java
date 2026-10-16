package com.endchants.datagen;

import com.endchants.ModArmorEnchantments;
import com.endchants.ModEnchantmentEffects;
import com.endchants.ModMeleeEnchantments;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ModEnchantmentEffects.registerModEnchantmentEffects();
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModEnchantmentGenerator::new);
        pack.addProvider(ModEnchantmentTagProvider::new);
        pack.addProvider(ModDamageTypeProvider::new);
        // pack.addProvider(ModItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.ENCHANTMENT, ModMeleeEnchantments::init);
        registryBuilder.add(Registries.ENCHANTMENT, ModArmorEnchantments::init);
    }
}
