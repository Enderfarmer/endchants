package com.endchants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endchants.enchantment.effect.tool.DrillingEffect;
import com.endchants.enchantment.effect.tool.GravelLiberatorEffect;
import com.endchants.enchantment.effect.tool.HarvestWaveEffect;
import com.endchants.enchantment.effect.tool.LuminaryEffect;

public class Endchants implements ModInitializer {
	public static final String MOD_ID = "endchants";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModEnchantmentEffects.registerModEnchantmentEffects();
		ModEffects.registerEffects();
		ModSubPredicates.registerSubPredicates();
		ModEntities.init();
		FlightTracker.register();
		ModSoundEvents.init();
		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
				ItemStack tool = player.getMainHandItem();

				int gravelLiberatorLevel = getLevel(ModToolEnchantments.GRAVEL_LIBERATOR, tool, level);
				int harvestWaveLevel = getLevel(ModToolEnchantments.HARVEST_WAVE, tool, level);
				int luminaryLevel = getLevel(ModToolEnchantments.LUMINARY, tool, level);
				int drillingLevel = getLevel(ModToolEnchantments.DRILLING, tool, level);

				if (gravelLiberatorLevel > 0) {
					if (state.is(Blocks.GRAVEL)) {
						GravelLiberatorEffect.INSTANCE.apply(serverLevel, gravelLiberatorLevel, pos);
					}
				}
				if (harvestWaveLevel > 0) {
					if (state.is(BlockTags.CROPS)) {
						HarvestWaveEffect.apply(serverLevel, pos, player.getLookAngle());
					}
				}
				if (luminaryLevel > 0) {
					LuminaryEffect.apply(serverLevel, pos, player);
				}
				if (drillingLevel > 0 && player.isCrouching()) {
					player.getMainHandItem().hurtAndBreak(drillingLevel * 50, player, InteractionHand.MAIN_HAND);
					Vec3 posVec3 = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					DrillingEffect.apply(serverLevel, posVec3, drillingLevel);
				}
			}
		});

	}

	public static int getLevel(ResourceKey<Enchantment> enchant, ItemStack itemStack, Level level) {
		return EnchantmentHelper.getItemEnchantmentLevel(
				level.holderLookup(Registries.ENCHANTMENT).getOrThrow(enchant),
				itemStack);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
