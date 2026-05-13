package hellfirepvp.observerlib.api.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/**
 * Stub interface for MatchableStructure from observerlib.
 */
public interface MatchableStructure extends Structure {
    boolean matches(LevelAccessor world, BlockPos anchor);
}
