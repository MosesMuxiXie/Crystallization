package com.xiemu.crystallization;

import com.xiemu.crystallization.network.TogglePayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class Crystallization implements ModInitializer {
	public static final String MOD_ID = "crystallization";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Set<UUID> ENABLED_PLAYERS = ConcurrentHashMap.newKeySet();
	private static volatile boolean clientEnabled;

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(TogglePayload.TYPE, TogglePayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(TogglePayload.TYPE, (payload, context) ->
				setPlayerEnabled(context.player(), payload.enabled())
		);
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
				ENABLED_PLAYERS.remove(handler.player.getUUID())
		);

		UseBlockCallback.EVENT.register(CrystallizationInteraction::useIce);
		LOGGER.info("Crystallization initialized");
	}

	public static boolean isPlayerEnabled(ServerPlayer player) {
		return ENABLED_PLAYERS.contains(player.getUUID());
	}

	public static void setPlayerEnabled(ServerPlayer player, boolean enabled) {
		if (enabled) {
			ENABLED_PLAYERS.add(player.getUUID());
		} else {
			ENABLED_PLAYERS.remove(player.getUUID());
		}
	}

	public static boolean isClientEnabled() {
		return clientEnabled;
	}

	public static void setClientEnabled(boolean enabled) {
		clientEnabled = enabled;
	}
}
