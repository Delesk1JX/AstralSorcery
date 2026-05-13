/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import hellfirepvp.astralsorcery.client.util.RenderingUtils;
import hellfirepvp.astralsorcery.common.tile.base.TileFakedState;
import net.minecraft.world.level().block.AirBlock;
import net.minecraft.world.level().block.state.BlockState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import java.awt.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RenderTileFakedState
 * Created by HellFirePvP
 * Date: 28.11.2019 / 19:52
 */
public class RenderTileFakedState extends CustomTileEntityRenderer<TileFakedState> {

    public RenderTileFakedState(TileEntityRendererDispatcher tileRenderer) {
        super(tileRenderer);
    }

    @Override
    public void render(TileFakedState tile, float pTicks, PoseStack renderStack, MultiBufferSource renderTypeBuffer, int combinedLight, int combinedOverlay) {
        BlockState fakedState = tile.getFakedState();
        if (fakedState.getBlock() instanceof AirBlock) {
            return;
        }
        Color blendColor = tile.getOverlayColor();
        int[] color = new int[] { blendColor.getRed(), blendColor.getGreen(), blendColor.getBlue(), 128 };

        // TODO: Fix RenderTypeDecorator and BufferDecoratorBuilder for 1.21+
        RenderType type = RenderType.translucent();
        VertexConsumer buf = renderTypeBuffer.getBuffer(type);
        RenderingUtils.renderSimpleBlockModel(fakedState, renderStack, buf, tile.getPos(), tile, true);
    }
}
