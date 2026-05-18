package me.tim_m.what_spyglass.mixin;


import me.tim_m.what_spyglass.SpyglassStopCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpyglassItem.class)
public class SpyglassStopMixin
{
    @Inject(method="stopUsing", at = @At("HEAD"))
    private void onStoppedUsing(LivingEntity entity, CallbackInfo info)
    {
        SpyglassStopCallback.EVENT.invoker().interact(entity);
    }
}
