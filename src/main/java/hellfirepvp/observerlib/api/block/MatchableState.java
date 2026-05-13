package hellfirepvp.observerlib.api.block;

import net.minecraft.world.level.block.state.BlockState;

/**
 * Stub interface for MatchableState from observerlib.
 */
public interface MatchableState {
    boolean matches(BlockState state);
}
