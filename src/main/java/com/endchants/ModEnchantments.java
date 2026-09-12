package com.endchants;

import com.endchants.enchantment.effect.FleshEaterEffect;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public class ModEnchantments {

        public static ResourceKey<Enchantment> register(BootstrapContext<Enchantment> ctx,
                        ResourceKey<Enchantment> enchant,
                        Enchantment.Builder builder) {
                ctx.register(ResourceKey.create(Registries.ENCHANTMENT, enchant.identifier()),
                                builder.build(enchant.identifier()));
                return enchant;
        }

        public static ResourceKey<Enchantment> FLESH_EATER = ResourceKey.create(Registries.ENCHANTMENT,
                        IdGen.id("flesh_eater"));

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, ModEnchantments.FLESH_EATER,
                                Enchantment.enchantment(
                                                Enchantment.definition(
                                                                ctx.lookup(Registries.ITEM)
                                                                                .getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                                10,
                                                                3,
                                                                Enchantment.dynamicCost(6, 8),
                                                                Enchantment.dynamicCost(8, 8),
                                                                5,
                                                                EquipmentSlotGroup.HAND))
                                                .withEffect(
                                                                EnchantmentEffectComponents.POST_ATTACK,
                                                                EnchantmentTarget.ATTACKER,
                                                                EnchantmentTarget.ATTACKER,
                                                                new FleshEaterEffect(LevelBasedValue.perLevel(1, 2))));
        }
}
