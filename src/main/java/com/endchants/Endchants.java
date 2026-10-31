package com.endchants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endchants.enchantment.effect.tool.GravelLiberatorEffect;

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
		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
				ItemStack tool = player.getMainHandItem();

				int levelInt = EnchantmentHelper.getItemEnchantmentLevel(
						level.holderLookup(Registries.ENCHANTMENT).getOrThrow(ModToolEnchantments.GRAVEL_LIBERATOR),
						tool);
				if (levelInt > 0) {

					if (state.is(Blocks.GRAVEL)) {
						GravelLiberatorEffect.INSTANCE.apply(serverLevel, levelInt, pos);
					}
				}
			} else {
				System.out.println(level.isClientSide());
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
