package net.tim_m.what_spyglass;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tim_m.what_spyglass.mixin.SpyglassStopMixin;

public interface SpyglassStopCallback {
    public static final Event<SpyglassStopCallback> EVENT = EventFactory.createArrayBacked(SpyglassStopCallback.class ,
            listeners -> (LivingEntity user) -> {
                for (SpyglassStopCallback event : listeners)
                {
                    ActionResult result = event.interact(user);
                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity user);
}
