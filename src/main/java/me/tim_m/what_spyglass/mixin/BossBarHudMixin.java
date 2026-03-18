package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.WhatSpyglassClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin
{
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    void stopBossBarRender(DrawContext context, CallbackInfo info) {
        if (WhatSpyglassClient.inSpyglass) {
            info.cancel();
        }
    }
}
