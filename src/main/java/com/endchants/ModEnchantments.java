package com.endchants;

import java.util.List;

import com.endchants.enchantment.effect.BuffOnKillEffect;
import com.endchants.enchantment.effect.FleshEaterEffect;
import com.endchants.enchantment.effect.FreezingEffect;
import com.endchants.enchantment.effect.GravityEffect;
import com.endchants.enchantment.effect.RadianceEffect;
import com.endchants.enchantment.effect.RepellingEffect;
import com.endchants.enchantment.subpredicate.IsHurtPredicate;
import com.endchants.enchantment.subpredicate.IsIllagerPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class ModEnchantments {

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

        public static Enchantment.Builder weaponEnchant(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost, EnchantmentEntityEffect effect) {
                return weaponEnchant(ctx, weight, levels, min, max, anvilCost)
                                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM, effect);
        }

        public static Enchantment.Builder weaponEnchant(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.HAND));
        }

        public static Enchantment.Builder armorEnchant(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.ARMOR));
        }

        public static <T extends EnchantmentEntityEffect> Enchantment.Builder armorEnchant(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<TargetedConditionalEffect<T>>> when,
                        EnchantmentTarget affected, T effect) {
                return armorEnchant(ctx, weight, levels, min, max, anvilCost).withEffect(when, EnchantmentTarget.VICTIM,
                                affected, effect);
        }

        public static ResourceKey<Enchantment> FLESH_EATER = genKey("flesh_eater");
        public static ResourceKey<Enchantment> REPELLING = genKey("repelling");
        public static ResourceKey<Enchantment> RADIANCE = genKey("radiance");
        public static ResourceKey<Enchantment> COMMITTED = genKey("committed");
        public static ResourceKey<Enchantment> GUARDING_STRIKE = genKey("guarding_strike");
        public static ResourceKey<Enchantment> RAMPAGING = genKey("rampaging");
        public static ResourceKey<Enchantment> FREEZING = genKey("freezing");
        public static ResourceKey<Enchantment> ILLAGERS_BANE = genKey("illagers_bane");
        public static ResourceKey<Enchantment> GRAVITY = genKey("gravity");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, FLESH_EATER,
                                weaponEnchant(ctx, 10, 3, Enchantment.dynamicCost(6, 8), Enchantment.dynamicCost(8, 8),
                                                5, new FleshEaterEffect(LevelBasedValue.perLevel(1, 2))));
                register(ctx, REPELLING,
                                armorEnchant(ctx, 10, 2, Enchantment.dynamicCost(6, 8), Enchantment.dynamicCost(8, 8),
                                                8, EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                                new RepellingEffect(.2f)));
                register(ctx, RADIANCE,
                                weaponEnchant(ctx, 6, 2, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28,
                                                8), 8, new RadianceEffect(1)));
                register(ctx, COMMITTED, weaponEnchant(ctx, 6, 2, Enchantment.dynamicCost(8, 8),
                                Enchantment.dynamicCost(28, 8), 8)
                                .withEffect(
                                                EnchantmentEffectComponents.DAMAGE,
                                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                                LootItemEntityPropertyCondition.hasProperties(
                                                                LootContext.EntityTarget.THIS,
                                                                EntityPredicate.Builder.entity()

                                                                                .subPredicate(new IsHurtPredicate()))));
                register(ctx, GUARDING_STRIKE,
                                weaponEnchant(ctx, 6, 3, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28, 8),
                                                8, new BuffOnKillEffect(LevelBasedValue.perLevel(2, 0),
                                                                LevelBasedValue.perLevel(2),
                                                                BuiltInRegistries.MOB_EFFECT.get(Identifier
                                                                                .withDefaultNamespace("resistance"))
                                                                                .get(),
                                                                .2f)));
                register(ctx, RAMPAGING,
                                weaponEnchant(ctx, 6, 3, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28, 8),
                                                8, new BuffOnKillEffect(LevelBasedValue.perLevel(0, 0),
                                                                LevelBasedValue.perLevel(5),
                                                                ModEffects.RAMPAGING,
                                                                .1f)));
                register(ctx, FREEZING,
                                weaponEnchant(ctx, 4, 3, Enchantment.dynamicCost(10, 8),
                                                Enchantment.dynamicCost(15, 10), 5,
                                                new FreezingEffect(LevelBasedValue.perLevel(150, 50))));
                register(ctx, ILLAGERS_BANE, weaponEnchant(ctx, 12, 5, Enchantment.dynamicCost(5, 5),
                                Enchantment.dynamicCost(10, 5), 3).withEffect(EnchantmentEffectComponents.DAMAGE,
                                                new AddValue(LevelBasedValue.perLevel(3, 2)),
                                                LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
                                                                EntityPredicate.Builder.entity().subPredicate(
                                                                                new IsIllagerPredicate()))));
                register(ctx, GRAVITY, weaponEnchant(ctx, 6, 1, Enchantment.dynamicCost(10, 6),
                                Enchantment.dynamicCost(15, 6), 5, new GravityEffect(0.4f)));

        }
}
