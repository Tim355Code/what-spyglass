package me.tim_m.what_spyglass.mixin;

import me.tim_m.what_spyglass.SpyglassUseCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpyglassItem.class)
public class SpyglassUseMixin
{
    @Inject(method="use", at = @At("HEAD"), cancellable = true)
    private void use(Level level, Player player, InteractionHand hand,
        CallbackInfoReturnable<InteractionResult> info)
    {
        InteractionResult result = SpyglassUseCallback.EVENT.invoker().interact(level, player, hand);
        if (result == InteractionResult.FAIL) {
            info.setReturnValue(InteractionResult.FAIL);
        }
    }
}