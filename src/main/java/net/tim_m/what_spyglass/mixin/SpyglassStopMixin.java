package net.tim_m.what_spyglass.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tim_m.what_spyglass.SpyglassStopCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpyglassItem.class)
public class SpyglassStopMixin
{
    @Inject(method="onStoppedUsing", at = @At("HEAD"), cancellable = true)
    private void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info)
    {
        ActionResult result = SpyglassStopCallback.EVENT.invoker().interact(user);

        if (result == ActionResult.FAIL)
            info.cancel();
    }
}
