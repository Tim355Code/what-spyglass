package me.tim_m.what_spyglass;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;

// Client-only
public class WhatSpyglassClient implements ClientModInitializer {
	public static boolean inSpyglass = false;

	@Override
	public void onInitializeClient() {
		SpyglassUseCallback.EVENT.register((world, player, hand) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if (player != client.player) return ActionResult.PASS;

			if (!inSpyglass) {
				inSpyglass = true;
				client.getMusicTracker().stop();
				client.getSoundManager().stopSounds(WhatSpyglass.MUSIC_ID, SoundCategory.PLAYERS);
				world.playSound(player, player.getBlockPos(), WhatSpyglass.MUSIC_EVENT,
						SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
			return ActionResult.SUCCESS;
		});

		SpyglassStopCallback.EVENT.register(user -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if (user != client.player) return ActionResult.PASS;

			if (inSpyglass) {
				inSpyglass = false;
				client.getSoundManager().stopSounds(WhatSpyglass.MUSIC_ID, SoundCategory.PLAYERS);
			}
			return ActionResult.SUCCESS;
		});
	}
}