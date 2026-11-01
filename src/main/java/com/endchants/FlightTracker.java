package com.endchants;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.UUID;

public class FlightTracker {
    // Keep track of who was flying on the previous tick
    private static final HashSet<UUID> FLYING_PLAYERS = new HashSet<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                UUID uuid = player.getUUID();

                // Check for Elytra flying (fall flying)
                // Use player.getAbilities().flying if you want creative flight instead
                boolean isCurrentlyFlying = player.isFallFlying();
                boolean wasFlying = FLYING_PLAYERS.contains(uuid);

                if (isCurrentlyFlying && !wasFlying) {
                    // --- PLAYER JUST ENTERED FLIGHT MODE! ---
                    onStartFlying(player);
                } else if (!isCurrentlyFlying && wasFlying) {
                    // --- PLAYER STOPPED FLYING ---
                    onStopFlying(player);
                }

                // Update cache
                if (isCurrentlyFlying) {
                    FLYING_PLAYERS.add(uuid);
                } else {
                    FLYING_PLAYERS.remove(uuid);
                }
            }
        });
    }

    private static void onStartFlying(ServerPlayer player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (Endchants.getLevel(ModArmorEnchantments.LAUNCH_BOOST, chestplate, player.level()) > 0) {
            boostPlayer(player, 2.5);
            chestplate.setDamageValue(chestplate.getDamageValue() + 3);
        }
    }

    private static void onStopFlying(ServerPlayer player) {
        // Cleanup logic when they land or stop gliding
    }

    public static void boostPlayer(ServerPlayer player, double boostStrength) {
        // 1. Ensure the player is actually fall-flying (gliding)
        if (!player.isFallFlying())
            return;

        // 2. Get the direction the player's camera is facing
        Vec3 lookVector = player.getLookAngle();

        // 3. Get their current velocity
        Vec3 currentVelocity = player.getDeltaMovement();

        // 4. Combine current velocity with a forward push along their look direction
        // (You can choose to override completely or add to it. Adding keeps momentum
        // smooth)
        Vec3 boostedVelocity = currentVelocity.add(
                lookVector.x * boostStrength,
                lookVector.y * boostStrength,
                lookVector.z * boostStrength);

        // 5. Apply the new velocity to the player
        player.setDeltaMovement(boostedVelocity);

        // 6. CRITICAL: Tell the server/client engine that this entity's velocity
        // was artificially modified so it syncs instantly and doesn't get rubberbanded
        player.hurtMarked = true;

        // Optional: Play a sound or trigger game events (e.g., firework sound)
        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                net.minecraft.sounds.SoundEvents.WIND_CHARGE_BURST,
                net.minecraft.sounds.SoundSource.PLAYERS,
                1.0F, 1.0F);
    }
}