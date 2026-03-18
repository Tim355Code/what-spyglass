package me.tim_m.what_spyglass;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface SpyglassStopCallback {
    Event<SpyglassStopCallback> EVENT = EventFactory.createArrayBacked(SpyglassStopCallback.class ,
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
