package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.WhatSpyglassClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.ActionResult;
import me.tim_m.what_spyglass.SpyglassStopCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class SpyglassRenderMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    void onRenderHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    void onRenderCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @ModifyArg(method = "renderMiscOverlays", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    float modifyScale(float delta)
    {
        return 1;
    }

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"), cancellable = true)
    void check(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (client.player != null) {
            if (!client.player.isUsingSpyglass() && WhatSpyglassClient.inSpyglass) {
                ActionResult result = SpyglassStopCallback.EVENT.invoker().interact(client.player);

                if (result == ActionResult.FAIL)
                    info.cancel();
            }
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    void onRenderItemTooltip(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }
}
