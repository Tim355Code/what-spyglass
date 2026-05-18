package me.tim_m.what_spyglass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class WhatSpyglass implements ModInitializer {
    public static final String MOD_ID = "what-spyglass";

    public static final Identifier MUSIC_ID = Identifier.fromNamespaceAndPath(MOD_ID, "what");
    public static final SoundEvent MUSIC_EVENT = SoundEvent.createVariableRangeEvent(MUSIC_ID);

    @Override
    public void onInitialize()
    {
        Registry.register(BuiltInRegistries.SOUND_EVENT, MUSIC_ID, MUSIC_EVENT);
    }
}