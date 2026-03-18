package me.tim_m.what_spyglass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class WhatSpyglass implements ModInitializer {
    public static final Identifier MUSIC_ID = Identifier.of("what-spyglass", "what");
    public static final SoundEvent MUSIC_EVENT = SoundEvent.of(MUSIC_ID);

    @Override
    public void onInitialize() {
        Registry.register(Registries.SOUND_EVENT, MUSIC_ID, MUSIC_EVENT);
    }
}