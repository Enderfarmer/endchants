package com.endchants.client;

import com.endchants.ModEntities;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EndchantsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(ModEntities.SHOCKWAVE,
				context -> new GeoEntityRenderer<>(context, ModEntities.SHOCKWAVE));
	}
}