/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.block.tile;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.util.RenderingUtils;
import hellfirepvp.astralsorcery.common.GuiType;
import hellfirepvp.astralsorcery.common.block.base.CustomItemBlock;
import hellfirepvp.astralsorcery.common.block.properties.PropertiesWood;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.tile.TileTelescope;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ContainerBlock;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.util.shapes.VoxelShape;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BlockTelescope
 * Created by HellFirePvP
 * Date: 15.01.2020 / 15:36
 */
public class BlockTelescope extends ContainerBlock implements CustomItemBlock {

    private static final VoxelShape TELESCOPE = VoxelShapes.create(1D / 16D, 0D / 16D, 1D / 16D, 15D / 16D, 32D / 16D, 15D / 16D);

    public BlockTelescope() {
        super(PropertiesWood.defaultInfusedWood());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        RenderingUtils.playBlockBreakParticles(pos.up(), BlocksAS.TELESCOPE.getDefaultState(), BlocksAS.TELESCOPE.getDefaultState());
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return TELESCOPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, Player player, Hand hand, BlockHitResult rayTraceResult) {
        if (world.isRemote()) {
            AstralSorcery.getProxy().openGui(player, GuiType.TELESCOPE, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        world.setBlockState(pos.up(), BlocksAS.STRUCTURAL.getDefaultState().with(BlockStructural.BLOCK_TYPE, BlockStructural.BlockType.TELESCOPE));
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.isAirBlock(pos.up())) {
            world.removeBlock(pos, isMoving);
        }
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileTelescope();
    }
}
