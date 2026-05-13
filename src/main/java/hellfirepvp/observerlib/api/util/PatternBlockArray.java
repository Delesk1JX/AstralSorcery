package hellfirepvp.observerlib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level().LevelAccessor;
import net.minecraft.world.level().block.state.BlockState;

/**
 * Stub class for PatternBlockArray from observerlib.
 */
public class PatternBlockArray implements BlockArray {
    
    @Override
    public BlockState getStateAt(BlockPos pos) {
        return null; // Stub implementation
    }
    
    @Override
    public boolean matches(LevelAccessor world, BlockPos anchor) {
        return true; // Stub implementation
    }
}
