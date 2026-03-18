package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.WhatSpyglassClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardObjective;
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

    @Inject(method = "renderPlayerList*", at = @At("HEAD"), cancellable = true)
    void stopPlayerListRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderScoreboardSidebar*", at = @At("HEAD"), cancellable = true)
    void stopScoreboardSidebarRender(DrawContext context, ScoreboardObjective objective, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }
    @Inject(method = "renderScoreboardSidebar*", at = @At("HEAD"), cancellable = true)
    void stopScoreboardSidebarRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    void stopStatusEffectOverlayRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderTitleAndSubtitle", at = @At("HEAD"), cancellable = true)
    void stopTitleAndSubtitleRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    void stopStatusBarsRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderChat", at = @At("HEAD"), cancellable = true)
    void stopChatRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    void stopHeldItemTooltipRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderMainHud", at = @At("HEAD"), cancellable = true)
    private void stopMainHudRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass) {
            info.cancel();
        }
    }

    @Inject(method = "renderMountHealth", at = @At("HEAD"), cancellable = true)
    void stopMountHealthRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    void stopHealthBarRender(DrawContext context, PlayerEntity player,
         int x, int y, int lines, int regeneratingHeartIndex,
         float maxHealth, int lastHealth, int health, int absorption,
         boolean blinking, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    void stopHotbarRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    void stopCrosshairRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @ModifyArg(method = "renderMiscOverlays", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    float stopOverlayAnimation(float delta)
    {
        return 1;
    }

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"), cancellable = true)
    void checkIfStoppedUsingSpyglass(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info)
    {
        if (client.player != null) {
            if (!client.player.isUsingSpyglass() && WhatSpyglassClient.inSpyglass) {
                ActionResult result = SpyglassStopCallback.EVENT.invoker().interact(client.player);

                if (result == ActionResult.FAIL)
                    info.cancel();
            }
        }
    }
}
