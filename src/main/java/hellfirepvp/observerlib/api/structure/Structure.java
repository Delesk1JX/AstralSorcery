package hellfirepvp.observerlib.api.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level().LevelAccessor;

/**
 * Stub interface for Structure from observerlib.
 */
public interface Structure {
    BlockPos getAnchor();
    LevelAccessor getWorld();
}
