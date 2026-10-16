package com.endchants.datagen;

import java.util.concurrent.CompletableFuture;

import com.endchants.ModItemTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.ItemTags;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(Provider wrapperLookup) {
        getOrCreateRawBuilder(ModItemTags.RANGED_WEAPON)
                .addOptionalTag(ItemTags.BOW_ENCHANTABLE.location())
                .addOptionalTag(ItemTags.CROSSBOW_ENCHANTABLE.location())
                .addOptionalTag(ItemTags.TRIDENT_ENCHANTABLE.location());
        getOrCreateRawBuilder(ModItemTags.ARROW_FIRING)
                .addOptionalTag(ItemTags.CROSSBOW_ENCHANTABLE.location())
                .addOptionalTag(ItemTags.BOW_ENCHANTABLE.location());

    }
}
