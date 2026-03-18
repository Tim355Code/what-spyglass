package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.WhatSpyglassClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import me.tim_m.what_spyglass.SpyglassStopCallback;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class SpyglassRenderMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Nullable private Text title;

    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;title:Lnet/minecraft/text/Text;", opcode = Opcodes.GETFIELD))
    Text hideTitleWhileScoped(InGameHud hud) {
        if (WhatSpyglassClient.inSpyglass) {
            return null;
        }
        return title;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
    void stopPlayerListRender(
            net.minecraft.client.gui.hud.PlayerListHud playerListHud,
            DrawContext context,
            int scaledWidth,
            Scoreboard scoreboard,
            ScoreboardObjective objective)
    {
        if (!WhatSpyglassClient.inSpyglass) {
            playerListHud.render(context, scaledWidth, scoreboard, objective);
        }
    }
    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    void stopScoreboardSidebarRender(DrawContext context, ScoreboardObjective objective, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    void stopStatusEffectOverlayRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;render(Lnet/minecraft/client/gui/DrawContext;)V"))
    void stopBossBarRender(net.minecraft.client.gui.hud.BossBarHud bossBarHud, DrawContext context)
    {
        if (!WhatSpyglassClient.inSpyglass) {
            bossBarHud.render(context);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud;render(Lnet/minecraft/client/gui/DrawContext;)V"))
    void stopSubtitlesRender(SubtitlesHud subtitlesHud, DrawContext context)
    {
        if (!WhatSpyglassClient.inSpyglass) {
            subtitlesHud.render(context);
        }
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    void stopStatusBarsRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/DrawContext;III)V"))
    void stopChatRender(ChatHud chatHud, DrawContext context, int currentTick, int mouseX, int mouseY)
    {
        if (!WhatSpyglassClient.inSpyglass) {
            chatHud.render(context, currentTick, mouseX, mouseY);
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    void stopHeldItemTooltipRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    void stopExperienceBarRender(DrawContext context, int x, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
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
    void stopHotbarRender(float tickDelta, DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    void stopCrosshairRender(DrawContext context, CallbackInfo info)
    {
        if (WhatSpyglassClient.inSpyglass)
            info.cancel();
    }

    @ModifyArg(method = "render", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    float stopOverlayAnimation(float delta)
    {
        return 1;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"), cancellable = true)
    void checkIfStoppedUsingSpyglass(DrawContext context, float tickDelta, CallbackInfo info)
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
