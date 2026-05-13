package hellfirepvp.observerlib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level().LevelAccessor;
import net.minecraft.world.level().block.state.BlockState;

/**
 * Stub interface for BlockArray from observerlib.
 */
public interface BlockArray {
    BlockState getStateAt(BlockPos pos);
    boolean matches(LevelAccessor world, BlockPos anchor);
}
