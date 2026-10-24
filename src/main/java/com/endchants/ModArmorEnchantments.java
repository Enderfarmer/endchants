package com.endchants;

import java.util.List;

import com.endchants.enchantment.effect.RepellingEffect;
import com.endchants.enchantment.effect.armor.GravityPulseEffect;
import com.endchants.enchantment.effect.armor.RushEffect;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class ModArmorEnchantments {
        public static ResourceKey<Enchantment> register(BootstrapContext<Enchantment> ctx,
                        ResourceKey<Enchantment> enchant,
                        Enchantment.Builder builder) {
                ctx.register(ResourceKey.create(Registries.ENCHANTMENT, enchant.identifier()),
                                builder.build(enchant.identifier()));
                return enchant;
        }

        public static ResourceKey<Enchantment> genKey(String name) {
                return ResourceKey.create(Registries.ENCHANTMENT, Endchants.id(name));
        }

        public static Enchantment.Builder builder(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.ARMOR));
        }

        public static <T extends EnchantmentEntityEffect> Enchantment.Builder builder(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<TargetedConditionalEffect<T>>> when,
                        EnchantmentTarget affected, T effect) {
                return builder(ctx, weight, levels, min, max, anvilCost).withEffect(when, EnchantmentTarget.VICTIM,
                                affected, effect);
        }

        public static ResourceKey<Enchantment> REPELLING = genKey("repelling");
        public static ResourceKey<Enchantment> GRAVITY_PULSE = genKey("gravity_pulse");
        public static ResourceKey<Enchantment> RUSH = genKey("rush");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, REPELLING,
                                builder(ctx, 10, 2, Enchantment.dynamicCost(6, 8), Enchantment.dynamicCost(8, 8),
                                                8, EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                                new RepellingEffect(.2f)));
                register(ctx, GRAVITY_PULSE,
                                builder(ctx, 4, 1, Enchantment.dynamicCost(7, 2), Enchantment.dynamicCost(9, 3), 2)
                                                .withEffect(EnchantmentEffectComponents.TICK,
                                                                new GravityPulseEffect(0.005)));
                register(ctx, RUSH,
                                builder(ctx, 6, 3, Enchantment.dynamicCost(10, 2), Enchantment.dynamicCost(12, 3), 3,
                                                EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.VICTIM,
                                                new RushEffect()));

        }
}
