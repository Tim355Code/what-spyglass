package me.tim_m.what_spyglass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WhatSpyglass implements ModInitializer {
    public static final Identifier MUSIC_ID = Identifier.of("what-spyglass", "what");
    public static final SoundEvent MUSIC_EVENT = new SoundEvent(MUSIC_ID);

    @Override
    public void onInitialize() {
        Registry.register(Registry.SOUND_EVENT, MUSIC_ID, MUSIC_EVENT);
    }
}