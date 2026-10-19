package com.endchants;

import java.util.List;

import com.endchants.enchantment.RangedItemType;
import com.endchants.enchantment.effect.melee.CloudEffect;
import com.endchants.enchantment.effect.melee.CriticalHitEffect;
import com.endchants.enchantment.effect.melee.ExplodingEffect;
import com.endchants.enchantment.effect.melee.GravityEffect;
import com.endchants.enchantment.effect.melee.VoidedEffect;
import com.endchants.enchantment.effect.ranged.TempoTheftEffect;
import com.endchants.enchantment.ranged.ChainReactionEffect;
import com.endchants.enchantment.subpredicate.IsHurtPredicate;

import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class ModRangedEnchantments {
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
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost, EnchantmentEntityEffect effect,
                        RangedItemType itemType) {
                return builder(ctx, weight, levels, min, max, anvilCost, itemType)
                                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                                EnchantmentTarget.VICTIM, effect, DamageSourceCondition
                                                                .hasDamageSource(DamageSourcePredicate.Builder
                                                                                .damageType().isDirect(false)));
        }

        public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<ConditionalEffect<T>>> when, T effect,
                        LootItemCondition.Builder predicate, RangedItemType itemType) {
                return builder(ctx, weight, levels, min, max, anvilCost, itemType)
                                .withEffect(when, effect, AllOfCondition.allOf(predicate, DamageSourceCondition
                                                .hasDamageSource(DamageSourcePredicate.Builder.damageType()
                                                                .isDirect(false))));
        }

        public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
                        BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
                        DataComponentType<List<ConditionalEffect<T>>> when, T effect, RangedItemType itemType) {
                return builder(ctx, weight, levels, min, max, anvilCost, itemType)
                                .withEffect(when, effect, DamageSourceCondition
                                                .hasDamageSource(DamageSourcePredicate.Builder.damageType()
                                                                .isDirect(false)));
        }

        public static Enchantment.Builder builder(BootstrapContext<Enchantment> ctx, int weight, int levels,
                        Enchantment.Cost min, Enchantment.Cost max, int anvilCost, RangedItemType itemType) {
                TagKey<Item> itemTagKey = ModItemTags.ARROW_FIRING;
                switch (itemType) {
                        case ALL:
                                itemTagKey = ModItemTags.RANGED_WEAPON;
                                break;
                        case THROWABLE:
                                itemTagKey = ItemTags.TRIDENT_ENCHANTABLE;
                                break;
                        case BOW:
                                itemTagKey = ItemTags.BOW_ENCHANTABLE;
                                break;
                        case CROSSBOW:
                                itemTagKey = ItemTags.CROSSBOW_ENCHANTABLE;
                                break;
                        default:
                }
                return Enchantment
                                .enchantment(Enchantment.definition(
                                                ctx.lookup(Registries.ITEM).getOrThrow(itemTagKey),
                                                weight, levels, min, max, anvilCost, EquipmentSlotGroup.HAND));
        }

        public static final ResourceKey<Enchantment> CHAIN_REACTION = genKey("chain_reaction");
        public static final ResourceKey<Enchantment> COMMITTED_RANGED = genKey("committed_ranged");
        public static final ResourceKey<Enchantment> EXPLODING_RANGED = genKey("exploding_ranged");
        public static final ResourceKey<Enchantment> VOID_SHOT = genKey("void_shot");
        public static final ResourceKey<Enchantment> CRITICAL_SHOT = genKey("critical_shot");
        public static final ResourceKey<Enchantment> POISON_CLOUD_RANGED = genKey("poison_cloud_ranged");
        public static final ResourceKey<Enchantment> GRAVITY_RANGED = genKey("gravity_ranged");
        public static final ResourceKey<Enchantment> WEAKENING_RANGED = genKey("weakening_ranged");
        public static final ResourceKey<Enchantment> TEMPO_THEFT = genKey("tempo_theft");

        public static void init(BootstrapContext<Enchantment> ctx) {
                register(ctx, CHAIN_REACTION,
                                builder(ctx, 4, 3, Enchantment.dynamicCost(7, 4), Enchantment.dynamicCost(9, 5),
                                                2, new ChainReactionEffect(LevelBasedValue.perLevel(0.1f, 0.1f)),
                                                RangedItemType.ARROW_FIRING));
                register(ctx, COMMITTED_RANGED, builder(ctx, 6, 2, Enchantment.dynamicCost(8, 8),
                                Enchantment.dynamicCost(28, 8), 8,
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()

                                                                .subPredicate(new IsHurtPredicate())),
                                RangedItemType.ARROW_FIRING));
                register(ctx, EXPLODING_RANGED,
                                builder(ctx, 4, 3, Enchantment.dynamicCost(7, 4), Enchantment.dynamicCost(9, 5),
                                                3,
                                                new ExplodingEffect(LevelBasedValue.perLevel(0.1f)),
                                                RangedItemType.ALL));
                register(ctx, VOID_SHOT, builder(ctx, 4, 3, Enchantment.dynamicCost(10, 8),
                                Enchantment.dynamicCost(13, 8), 5,
                                new VoidedEffect(LevelBasedValue.perLevel(2.0f, 2.0f)), RangedItemType.ALL));
                register(ctx, CRITICAL_SHOT,
                                builder(ctx, 5, 3, Enchantment.dynamicCost(8, 6), Enchantment.dynamicCost(10, 6),
                                                4, EnchantmentEffectComponents.DAMAGE, new CriticalHitEffect(),
                                                RangedItemType.ALL));
                register(ctx, POISON_CLOUD_RANGED, builder(ctx, 8, 3, Enchantment.dynamicCost(4, 3),
                                Enchantment.dynamicCost(7, 4), 2,
                                new CloudEffect(MobEffects.POISON, 3, 0.3f,
                                                LevelBasedValue.perLevel(70, 30), LevelBasedValue.perLevel(2)),
                                RangedItemType.ARROW_FIRING));
                register(ctx, GRAVITY_RANGED, builder(ctx, 6, 1, Enchantment.dynamicCost(10, 6),
                                Enchantment.dynamicCost(15, 6), 5, new GravityEffect(0.4f), RangedItemType.ALL));
                register(ctx, WEAKENING_RANGED, builder(ctx, 7, 3, Enchantment.dynamicCost(5, 4),
                                Enchantment.dynamicCost(8, 4), 4,
                                new CloudEffect(MobEffects.WEAKNESS, 4, 1,
                                                LevelBasedValue.perLevel(100, 10), LevelBasedValue.perLevel(0, 1)),
                                RangedItemType.ARROW_FIRING));
                register(ctx, TEMPO_THEFT,
                                builder(ctx, 4, 2, Enchantment.dynamicCost(15, 4), Enchantment.dynamicCost(20, 5), 3,
                                                new TempoTheftEffect(), RangedItemType.ARROW_FIRING));
        }
}
