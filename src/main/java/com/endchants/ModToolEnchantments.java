package com.endchants;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModToolEnchantments {
        public static ResourceKey<Enchantment> register(BootstrapContext<Enchantment> ctx,
                        ResourceKey<Enchantment> enchant,
                        Enchantment.Builder builder) {
                ctx.register(enchant,
                                builder.build(enchant.identifier()));
                return enchant;
        }

        public static Enchantment.Builder builderForPick(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.PICKAXES),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.MAINHAND));
        }

        public static Enchantment.Builder builderForShovel(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.SHOVELS),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.MAINHAND));
        }

        public static Enchantment.Builder builderForHoe(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.HOES),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.MAINHAND));
        }

        public static ResourceKey<Enchantment> genKey(String name) {
                return ResourceKey.create(Registries.ENCHANTMENT, Endchants.id(name));
        }

        public static final ResourceKey<Enchantment> GRAVEL_LIBERATOR = genKey("gravel_liberator");
        public static final ResourceKey<Enchantment> HARVEST_WAVE = genKey("harvest_wave");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, GRAVEL_LIBERATOR,
                                builderForShovel(ctx, 6, 3, Enchantment.dynamicCost(5, 3),
                                                Enchantment.dynamicCost(8, 4),
                                                1));
                register(ctx, HARVEST_WAVE,
                                builderForHoe(ctx, 9, 1, Enchantment.dynamicCost(5, 3),
                                                Enchantment.dynamicCost(8, 4),
                                                3));

        }
}
