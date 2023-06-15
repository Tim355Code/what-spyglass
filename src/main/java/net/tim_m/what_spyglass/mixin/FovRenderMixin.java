package net.tim_m.what_spyglass.mixin;

import net.minecraft.client.render.GameRenderer;
import net.tim_m.what_spyglass.WhatSpyglass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class FovRenderMixin {
    @Shadow private float fovMultiplier;

    @Inject(method = "updateFovMultiplier", at = @At("TAIL"))
    void FovOverwrite(CallbackInfo ci)
    {
        if (WhatSpyglass.inSpyglass)
            fovMultiplier = 0.1f;
    }
}
