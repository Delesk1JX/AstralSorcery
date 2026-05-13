/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.block.tile;

import hellfirepvp.astralsorcery.common.tile.TileVanishing;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ContainerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.util.shapes.VoxelShape;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BlockVanishing
 * Created by HellFirePvP
 * Date: 11.03.2020 / 21:15
 */
public class BlockVanishing extends ContainerBlock {

    public BlockVanishing() {
        super(Properties.create(Material.BARRIER, MaterialColor.AIR)
                .hardnessAndResistance(-1F, 3600000.0F)
                .sound(SoundType.METAL));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        if (ctx.getEntity() instanceof Player) {
            return VoxelShapes.fullCube();
        }
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileVanishing();
    }
}
