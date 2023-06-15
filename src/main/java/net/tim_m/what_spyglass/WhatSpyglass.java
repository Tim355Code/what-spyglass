package net.tim_m.what_spyglass;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class WhatSpyglass implements ModInitializer
{
	private static final Identifier MUSIC_ID = new Identifier("what-spyglass:what");
	private static final SoundEvent MUSIC_EVENT = SoundEvent.of(MUSIC_ID);
	public static final MinecraftClient client = MinecraftClient.getInstance();
	public static boolean inSpyglass = false;

	@Override
	public void onInitialize() {
		Registry.register(Registries.SOUND_EVENT, MUSIC_ID, MUSIC_EVENT);

		SpyglassUseCallback.EVENT.register(((world, player, hand) -> {
			if (inSpyglass)
			{
				client.getSoundManager().stopSounds(MUSIC_ID, SoundCategory.PLAYERS);
			}

			inSpyglass = true;
			client.getSoundManager().stopSounds(SoundEvents.ITEM_SPYGLASS_USE.getId(), SoundCategory.PLAYERS);
			player.playSound(MUSIC_EVENT, SoundCategory.PLAYERS, 1f, 1f);

			return ActionResult.SUCCESS;
		}));
		SpyglassStopCallback.EVENT.register(((user) -> {
			if (inSpyglass)
			{
				inSpyglass = false;
				client.getSoundManager().stopSounds(MUSIC_ID, SoundCategory.PLAYERS);
			}

			return ActionResult.SUCCESS;
		}));
	}
}