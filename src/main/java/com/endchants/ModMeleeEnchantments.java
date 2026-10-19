package com.endchants;

import java.util.List;

import com.endchants.enchantment.effect.melee.BackstabbingEffect;
import com.endchants.enchantment.effect.melee.BuffOnKillEffect;
import com.endchants.enchantment.effect.melee.CloudEffect;
import com.endchants.enchantment.effect.melee.CriticalHitEffect;
import com.endchants.enchantment.effect.melee.EchoEffect;
import com.endchants.enchantment.effect.melee.ExplodingEffect;
import com.endchants.enchantment.effect.melee.FleshEaterEffect;
import com.endchants.enchantment.effect.melee.FreezingEffect;
import com.endchants.enchantment.effect.melee.GravityEffect;
import com.endchants.enchantment.effect.melee.LeechingEffect;
import com.endchants.enchantment.effect.melee.RadianceEffect;
import com.endchants.enchantment.effect.melee.ShockwaveEffect;
import com.endchants.enchantment.effect.melee.StunningEffect;
import com.endchants.enchantment.effect.melee.SwirlingEffect;
import com.endchants.enchantment.effect.melee.VoidedEffect;
import com.endchants.enchantment.subpredicate.IsBackstabbedPredicate;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class ModMeleeEnchantments {
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
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost, EnchantmentEntityEffect effect) {
                return builder(ctx, weight, levels, min, max, anvilCost)
                                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM, effect);
        }

        public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<ConditionalEffect<T>>> when, T effect,
                        LootItemCondition.Builder predicate) {
                return builder(ctx, weight, levels, min, max, anvilCost)
                                .withEffect(when, effect, predicate);
        }

        public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<ConditionalEffect<T>>> when, T effect) {
                return builder(ctx, weight, levels, min, max, anvilCost)
                                .withEffect(when, effect);
        }

        public static Enchantment.Builder builder(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost) {
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM)
                                                                .getOrThrow(ItemTags.MELEE_WEAPON_ENCHANTABLE),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.HAND));
        }

        public static ResourceKey<Enchantment> FLESH_EATER = genKey("flesh_eater");
        public static ResourceKey<Enchantment> RADIANCE = genKey("radiance");
        public static ResourceKey<Enchantment> COMMITTED = genKey("committed");
        public static ResourceKey<Enchantment> GUARDING_STRIKE = genKey("guarding_strike");
        public static ResourceKey<Enchantment> RAMPAGING = genKey("rampaging");
        public static ResourceKey<Enchantment> FREEZING = genKey("freezing");
        public static ResourceKey<Enchantment> ILLAGERS_BANE = genKey("illagers_bane");
        public static ResourceKey<Enchantment> GRAVITY = genKey("gravity");
        public static ResourceKey<Enchantment> BACKSTABBING = genKey("backstabbing");
        public static ResourceKey<Enchantment> CRITICAL_HIT = genKey("critical_hit");
        public static ResourceKey<Enchantment> ECHO = genKey("echo");
        public static ResourceKey<Enchantment> VOID_STRIKE = genKey("void_strike");
        public static ResourceKey<Enchantment> SHOCKWAVE = genKey("shockwave");
        public static ResourceKey<Enchantment> POISON_CLOUD = genKey("poison_cloud");
        public static ResourceKey<Enchantment> WEAKENING = genKey("weakening");
        public static ResourceKey<Enchantment> STUNNING = genKey("stunning");
        public static ResourceKey<Enchantment> LEECHING = genKey("leeching");
        public static ResourceKey<Enchantment> SWIRLING = genKey("swirling");
        public static ResourceKey<Enchantment> EXPLODING = genKey("exploding");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, FLESH_EATER,
                                builder(ctx, 10, 3, Enchantment.dynamicCost(6, 8), Enchantment.dynamicCost(8, 8),
                                                5, new FleshEaterEffect(LevelBasedValue.perLevel(1, 2))));
                register(ctx, RADIANCE,
                                builder(ctx, 6, 2, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28,
                                                8), 8, new RadianceEffect(1)));
                register(ctx, COMMITTED, builder(ctx, 6, 2, Enchantment.dynamicCost(8, 8),
                                Enchantment.dynamicCost(28, 8), 8)
                                .withEffect(
                                                EnchantmentEffectComponents.DAMAGE,
                                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                                LootItemEntityPropertyCondition.hasProperties(
                                                                LootContext.EntityTarget.THIS,
                                                                EntityPredicate.Builder.entity()

                                                                                .subPredicate(new IsHurtPredicate()))));
                register(ctx, GUARDING_STRIKE,
                                builder(ctx, 6, 3, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28, 8),
                                                8, new BuffOnKillEffect(LevelBasedValue.perLevel(2, 0),
                                                                LevelBasedValue.perLevel(2),
                                                                BuiltInRegistries.MOB_EFFECT.get(Identifier
                                                                                .withDefaultNamespace("resistance"))
                                                                                .get(),
                                                                .2f)));
                register(ctx, RAMPAGING,
                                builder(ctx, 6, 3, Enchantment.dynamicCost(8, 8), Enchantment.dynamicCost(28, 8),
                                                8, new BuffOnKillEffect(LevelBasedValue.perLevel(0, 0),
                                                                LevelBasedValue.perLevel(5),
                                                                ModEffects.RAMPAGING,
                                                                .1f)));
                register(ctx, FREEZING,
                                builder(ctx, 4, 3, Enchantment.dynamicCost(10, 8),
                                                Enchantment.dynamicCost(15, 10), 5,
                                                new FreezingEffect(LevelBasedValue.perLevel(150, 50))));
                register(ctx, ILLAGERS_BANE, builder(ctx, 12, 5, Enchantment.dynamicCost(5, 5),
                                Enchantment.dynamicCost(10, 5), 3).withEffect(EnchantmentEffectComponents.DAMAGE,
                                                new AddValue(LevelBasedValue.perLevel(3, 2)),
                                                LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
                                                                EntityPredicate.Builder.entity().subPredicate(
                                                                                new IsIllagerPredicate()))));
                register(ctx, GRAVITY, builder(ctx, 6, 1, Enchantment.dynamicCost(10, 6),
                                Enchantment.dynamicCost(15, 6), 5, new GravityEffect(0.4f)));
                register(ctx, BACKSTABBING,
                                builder(ctx, 9, 3, Enchantment.dynamicCost(6, 5), Enchantment.dynamicCost(10, 5),
                                                3, EnchantmentEffectComponents.DAMAGE,
                                                new BackstabbingEffect(LevelBasedValue.perLevel(1.2f, .2f)),
                                                LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
                                                                EntityPredicate.Builder.entity().subPredicate(
                                                                                new IsBackstabbedPredicate()))));
                register(ctx, CRITICAL_HIT,
                                builder(ctx, 5, 3, Enchantment.dynamicCost(8, 6), Enchantment.dynamicCost(10, 6),
                                                4, EnchantmentEffectComponents.DAMAGE, new CriticalHitEffect()));
                register(ctx, ECHO, builder(ctx, 4, 3, Enchantment.dynamicCost(10, 8),
                                Enchantment.dynamicCost(13, 8), 5, new EchoEffect()));
                register(ctx, VOID_STRIKE, builder(ctx, 4, 3, Enchantment.dynamicCost(10, 8),
                                Enchantment.dynamicCost(13, 8), 5,
                                new VoidedEffect(LevelBasedValue.perLevel(2.0f, 2.0f))));
                register(ctx, SHOCKWAVE,
                                builder(ctx, 5, 3, Enchantment.dynamicCost(8, 9), Enchantment.dynamicCost(11, 9),
                                                5, new ShockwaveEffect(LevelBasedValue.perLevel(3, 2))));
                register(ctx, POISON_CLOUD, builder(ctx, 8, 3, Enchantment.dynamicCost(4, 3),
                                Enchantment.dynamicCost(7, 4), 2,
                                new CloudEffect(MobEffects.POISON, 3, 0.3f,
                                                LevelBasedValue.perLevel(70, 30), LevelBasedValue.perLevel(2))));
                register(ctx, WEAKENING, builder(ctx, 7, 3, Enchantment.dynamicCost(5, 4),
                                Enchantment.dynamicCost(8, 4), 4,
                                new CloudEffect(MobEffects.WEAKNESS, 4, 1,
                                                LevelBasedValue.perLevel(100, 10), LevelBasedValue.perLevel(0, 1))));
                register(ctx, STUNNING,
                                builder(ctx, 5, 3, Enchantment.dynamicCost(7, 4), Enchantment.dynamicCost(10, 5),
                                                5, new StunningEffect(LevelBasedValue.perLevel(0.05f))));
                register(ctx, LEECHING, builder(ctx, 7, 3, Enchantment.dynamicCost(10, 8),
                                Enchantment.dynamicCost(13, 8), 5,
                                new LeechingEffect(LevelBasedValue.perLevel(.05f, .02f))));
                register(ctx, SWIRLING, builder(ctx, 6, 3, Enchantment.dynamicCost(11, 7),
                                Enchantment.dynamicCost(14, 7), 3, new SwirlingEffect(LevelBasedValue.perLevel(3))));
                register(ctx, EXPLODING, builder(ctx, 3, 3, Enchantment.dynamicCost(14, 8),
                                Enchantment.dynamicCost(16, 8), 5, new ExplodingEffect(LevelBasedValue.perLevel(.2f))));

        }

}
