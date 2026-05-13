package hellfirepvp.observerlib.api;

import net.minecraft.world.level().block.state.BlockState;

/**
 * Stub interface for ChangeSubscriber from observerlib.
 */
public interface ChangeSubscriber {
    void onBlockChange(BlockState oldState, BlockState newState);
}
