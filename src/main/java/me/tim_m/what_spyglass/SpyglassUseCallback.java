package me.tim_m.what_spyglass;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface SpyglassUseCallback {
    Event<SpyglassUseCallback> EVENT = EventFactory.createArrayBacked(
            SpyglassUseCallback.class,
            listeners -> (world, level, hand) -> {
                for (SpyglassUseCallback listener : listeners) {
                    InteractionResult result = listener.interact(world, level, hand);
                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }
                return InteractionResult.PASS;
            }
    );

    InteractionResult interact(Level level, Player player, InteractionHand hand);
}