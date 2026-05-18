package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.WhatSpyglassClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.InteractionResult;
import me.tim_m.what_spyglass.SpyglassStopCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class SpyglassRenderMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "extractTabList", at = @At("HEAD"), cancellable = true)
    void stopPlayerListRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info) {
        if (WhatSpyglassClient.inSpyglass) {
            info.cancel();
        }
    }

    @Inject(method = "extractScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    void stopScoreboardSidebarRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "extractEffects", at = @At("HEAD"), cancellable = true)
    void stopStatusEffectOverlayRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "extractTitle", at = @At("HEAD"), cancellable = true)
    void stopTitleAndSubtitleRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "extractChat", at = @At("HEAD"), cancellable = true)
    void stopChatRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    void stopHeldItemTooltipRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    private void stopMainHudRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
        {
            info.cancel();
        }
    }

    @Inject(method = "extractBossOverlay", at = @At("HEAD"), cancellable = true)
    private void stopBossBarRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (WhatSpyglassClient.inSpyglass) {
            ci.cancel();
        }
    }

    @Inject(method = "extractSubtitleOverlay", at = @At("HEAD"), cancellable = true)
    private void stopSubtitleRender(GuiGraphicsExtractor graphics, boolean deferRendering, CallbackInfo ci) {
        if (WhatSpyglassClient.inSpyglass) {
            ci.cancel();
        }
    }

    @Inject(method = "extractDeferredSubtitles", at = @At("HEAD"), cancellable = true)
    private void stopDeferredSubtitleRender(CallbackInfo ci) {
        if (WhatSpyglassClient.inSpyglass) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "extractCameraOverlays", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"))
    private float stopOverlayAnimation(float delta)
    {
        return 1.0F;
    }

    @Inject(method = "extractCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"), cancellable = true)
    private void checkIfStoppedUsingSpyglass(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci)
    {
        if (minecraft.player != null && !minecraft.player.isScoping() && WhatSpyglassClient.inSpyglass)
        {
            InteractionResult result = SpyglassStopCallback.EVENT.invoker().interact(minecraft.player);
            if (result == InteractionResult.FAIL)
            {
                ci.cancel();
            }
        }
    }
}
