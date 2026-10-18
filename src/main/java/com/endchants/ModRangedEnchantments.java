package com.endchants;

import java.util.List;

import com.endchants.enchantment.RangedItemType;
import com.endchants.enchantment.ranged.ChainReactionEffect;

import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

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
                                .hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(false)));
    }

    public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
            BootstrapContext<Enchantment> ctx, int weight, int levels,
            Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
            DataComponentType<List<ConditionalEffect<T>>> when, T effect,
            LootItemCondition.Builder predicate, RangedItemType itemType) {
        return builder(ctx, weight, levels, min, max, anvilCost, itemType)
                .withEffect(when, effect, AllOfCondition.allOf(predicate, DamageSourceCondition
                        .hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(false))));
    }

    public static <T extends EnchantmentValueEffect> Enchantment.Builder builder(
            BootstrapContext<Enchantment> ctx, int weight, int levels,
            Enchantment.Cost min, Enchantment.Cost max, int anvilCost,
            DataComponentType<List<ConditionalEffect<T>>> when, T effect, RangedItemType itemType) {
        return builder(ctx, weight, levels, min, max, anvilCost, itemType)
                .withEffect(when, effect, DamageSourceCondition
                        .hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(false)));
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

    public static void init(BootstrapContext<Enchantment> ctx) {
        register(ctx, CHAIN_REACTION, builder(ctx, 4, 3, Enchantment.dynamicCost(7, 4), Enchantment.dynamicCost(9, 5),
                2, new ChainReactionEffect(LevelBasedValue.perLevel(.2f, .1f)), RangedItemType.ARROW_FIRING));
    }
}
