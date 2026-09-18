package com.endchants;

import com.endchants.enchantment.effect.BuffOnKillEffect;
import com.endchants.enchantment.effect.FleshEaterEffect;
import com.endchants.enchantment.effect.RadianceEffect;
import com.endchants.enchantment.effect.RepellingEffect;
import com.endchants.enchantment.subpredicate.IsHurtPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
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
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.LootContext;
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

        public static ResourceKey<Enchantment> FLESH_EATER = genKey("flesh_eater");
        public static ResourceKey<Enchantment> REPELLING = genKey("repelling");
        public static ResourceKey<Enchantment> RADIANCE = genKey("radiance");
        public static ResourceKey<Enchantment> COMMITTED = genKey("committed");
        public static ResourceKey<Enchantment> GUARDING_STRIKE = genKey("guarding_strike");
        public static ResourceKey<Enchantment> RAMPAGING = genKey("rampaging");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, FLESH_EATER,
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
                                                                EnchantmentTarget.VICTIM,
                                                                new FleshEaterEffect(LevelBasedValue.perLevel(1, 2))));
                register(ctx, REPELLING,
                                Enchantment.enchantment(
                                                Enchantment.definition(
                                                                ctx.lookup(Registries.ITEM)
                                                                                .getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                                                10,
                                                                2,
                                                                Enchantment.dynamicCost(6, 8),
                                                                Enchantment.dynamicCost(8, 8),
                                                                8,
                                                                EquipmentSlotGroup.ARMOR))
                                                .withEffect(
                                                                EnchantmentEffectComponents.POST_ATTACK,
                                                                EnchantmentTarget.VICTIM,
                                                                EnchantmentTarget.ATTACKER,
                                                                new RepellingEffect(0.2f)));
                register(ctx, RADIANCE, Enchantment.enchantment(
                                Enchantment.definition(
                                                ctx.lookup(Registries.ITEM)
                                                                .getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                6,
                                                2,
                                                Enchantment.dynamicCost(8, 8),
                                                Enchantment.dynamicCost(28,
                                                                8),
                                                8,
                                                EquipmentSlotGroup.HAND))
                                .withEffect(
                                                EnchantmentEffectComponents.POST_ATTACK,
                                                EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM,
                                                new RadianceEffect(1)));
                register(ctx, COMMITTED, Enchantment.enchantment(
                                Enchantment.definition(
                                                ctx.lookup(Registries.ITEM)
                                                                .getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                6,
                                                2,
                                                Enchantment.dynamicCost(8, 8),
                                                Enchantment.dynamicCost(28,
                                                                8),
                                                8,
                                                EquipmentSlotGroup.HAND))
                                .withEffect(
                                                EnchantmentEffectComponents.DAMAGE,
                                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                                LootItemEntityPropertyCondition.hasProperties(
                                                                LootContext.EntityTarget.THIS, // "THIS" refers to the
                                                                                               // target/victim in
                                                                                               // DAMAGE context
                                                                EntityPredicate.Builder.entity()
                                                                                // Check that health is less than max
                                                                                // health
                                                                                .subPredicate(new IsHurtPredicate()) // or
                                                                                                                     // custom
                                                                                                                     // predicate
                                                )));
                register(ctx, GUARDING_STRIKE, Enchantment.enchantment(
                                Enchantment.definition(
                                                ctx.lookup(Registries.ITEM)
                                                                .getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                6,
                                                3,
                                                Enchantment.dynamicCost(8, 8),
                                                Enchantment.dynamicCost(28,
                                                                8),
                                                8,
                                                EquipmentSlotGroup.HAND))
                                .withEffect(
                                                EnchantmentEffectComponents.POST_ATTACK,
                                                EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM,
                                                new BuffOnKillEffect(LevelBasedValue.perLevel(2, 0),
                                                                LevelBasedValue.perLevel(2),
                                                                BuiltInRegistries.MOB_EFFECT.get(Identifier
                                                                                .withDefaultNamespace("resistance"))
                                                                                .get(),
                                                                .2f)));
                register(ctx, RAMPAGING, Enchantment.enchantment(
                                Enchantment.definition(
                                                ctx.lookup(Registries.ITEM)
                                                                .getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                                6,
                                                3,
                                                Enchantment.dynamicCost(8, 8),
                                                Enchantment.dynamicCost(28,
                                                                8),
                                                8,
                                                EquipmentSlotGroup.HAND))
                                .withEffect(
                                                EnchantmentEffectComponents.POST_ATTACK,
                                                EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM,
                                                new BuffOnKillEffect(LevelBasedValue.perLevel(0, 0),
                                                                LevelBasedValue.perLevel(5),
                                                                ModEffects.RAMPAGING,
                                                                .1f)));

        }
}
