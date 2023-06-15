package net.tim_m.what_spyglass.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.ActionResult;
import net.tim_m.what_spyglass.SpyglassStopCallback;
import net.tim_m.what_spyglass.WhatSpyglass;
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
    void onRenderHud(float tickDelta, DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglass.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    void onRenderCrosshair(DrawContext context, CallbackInfo ci)
    {
        if (WhatSpyglass.inSpyglass)
            ci.cancel();
    }

    @ModifyArg(method = "render", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderSpyglassOverlay(Lnet/minecraft/client/gui/DrawContext;F)V"))
    float modifyScale(float delta)
    {
        return 1;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"), cancellable = true)
    void check(DrawContext context, float tickDelta, CallbackInfo info)
    {
        if (client.player != null) {
            if (!client.player.isUsingSpyglass() && WhatSpyglass.inSpyglass) {
                ActionResult result = SpyglassStopCallback.EVENT.invoker().interact(client.player);

                if (result == ActionResult.FAIL)
                    info.cancel();
            }
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    void onRenderItemTooltip(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglass.inSpyglass)
            info.cancel();
    }
}
