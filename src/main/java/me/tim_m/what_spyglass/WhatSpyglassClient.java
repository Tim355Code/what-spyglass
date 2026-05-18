package me.tim_m.what_spyglass;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;

public class WhatSpyglassClient implements ClientModInitializer {
	public static boolean inSpyglass = false;

	@Override
	public void onInitializeClient() {
		SpyglassUseCallback.EVENT.register((level, player, _) -> {
			Minecraft client = Minecraft.getInstance();

			if (player != client.player) {
				return InteractionResult.PASS;
			}

			if (!inSpyglass) {
				inSpyglass = true;

				client.getMusicManager().stopPlaying();
				client.getSoundManager().stop(WhatSpyglass.MUSIC_ID, SoundSource.PLAYERS);

				level.playSound(player, player.blockPosition(), WhatSpyglass.MUSIC_EVENT, SoundSource.PLAYERS, 1.0F, 1.0F);
			}

			return InteractionResult.SUCCESS;
		});

		SpyglassStopCallback.EVENT.register(player -> {
			Minecraft client = Minecraft.getInstance();

			if (player != client.player) {
				return InteractionResult.PASS;
			}

			if (inSpyglass) {
				inSpyglass = false;
				client.getSoundManager().stop(WhatSpyglass.MUSIC_ID, SoundSource.PLAYERS);
			}

			return InteractionResult.SUCCESS;
		});
	}
}