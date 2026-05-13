package hellfirepvp.observerlib.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Stub class for SimpleMatchableBlock from observerlib.
 */
public class SimpleMatchableBlock implements MatchableState {
    private final Block block;
    
    public SimpleMatchableBlock(Block block) {
        this.block = block;
    }
    
    @Override
    public boolean matches(BlockState state) {
        return state.getBlock() == block;
    }
}
