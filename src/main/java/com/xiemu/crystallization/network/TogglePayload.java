package com.xiemu.crystallization.network;

import com.xiemu.crystallization.Crystallization;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TogglePayload(boolean enabled) implements CustomPacketPayload {
	public static final Type<TogglePayload> TYPE = new Type<>(
			Identifier.fromNamespaceAndPath(Crystallization.MOD_ID, "toggle")
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, TogglePayload> CODEC =
			StreamCodec.composite(
					ByteBufCodecs.BOOL,
					TogglePayload::enabled,
					TogglePayload::new
			);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
