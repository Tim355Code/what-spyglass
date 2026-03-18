package me.tim_m.what_spyglass;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

// Client-only
public class WhatSpyglassClient implements ClientModInitializer
{
	public static boolean inSpyglass = false;
	private static final MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient()
	{
		SpyglassUseCallback.EVENT.register((world, player, hand) -> {
			if (!inSpyglass) {
				inSpyglass = true;
				client.getSoundManager().stopSounds(WhatSpyglass.MUSIC_ID, SoundCategory.PLAYERS);
				world.playSoundClient(WhatSpyglass.MUSIC_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
			return ActionResult.SUCCESS;
		});

		SpyglassStopCallback.EVENT.register(user -> {
			if (inSpyglass) {
				inSpyglass = false;
				client.getSoundManager().stopSounds(WhatSpyglass.MUSIC_ID, SoundCategory.PLAYERS);
			}
			return ActionResult.SUCCESS;
		});
	}
}
