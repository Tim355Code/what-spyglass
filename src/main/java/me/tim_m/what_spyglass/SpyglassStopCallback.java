package me.tim_m.what_spyglass;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;

public interface SpyglassStopCallback {
    Event<SpyglassStopCallback> EVENT = EventFactory.createArrayBacked(SpyglassStopCallback.class ,
            listeners -> (LivingEntity player) -> {
                for (SpyglassStopCallback event : listeners)
                {
                    InteractionResult result = event.interact(player);
                    if (result != InteractionResult.PASS)
                        return result;
                }

                return InteractionResult.PASS;
            });
    InteractionResult interact(LivingEntity player);
}
