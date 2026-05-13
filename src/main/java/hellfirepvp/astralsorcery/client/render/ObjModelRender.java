/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import hellfirepvp.astralsorcery.client.lib.RenderTypesAS;
import hellfirepvp.astralsorcery.client.resource.AssetLoader;
import hellfirepvp.astralsorcery.client.util.obj.WavefrontObject;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ObjModelRender
 * Created by HellFirePvP
 * Date: 05.04.2020 / 10:59
 */
public class ObjModelRender {

    private static WavefrontObject crystalModel;
    //private static VertexBuffer vboCrystal;

    private static WavefrontObject celestialWingsModel;
    private static VertexBuffer vboCelestialWings;

    private static WavefrontObject wraithWingsModel;
    private static VertexBuffer wraithWingsBones, wraithWingsWing;

    public static void renderCrystal(PoseStack renderStack, VertexConsumer buf, Runnable drawFn) {
        if (crystalModel == null) {
            crystalModel = AssetLoader.loadObjModel(AssetLoader.ModelLocation.OBJ, "crystal");
        }
        //if (vboCrystal == null) {
        //    int[] transparent = new int[] { 255, 255, 255, 65 };
        //    VertexConsumer (buffer) -> { int r = 0, g = 0, b = 0, a = 255; return new int[]{r, g, b, a}; } -> transparent)
        //            .decorate(buffer,
        //                    (VertexConsumer decorated) -> vboCrystal = crystalModel.batch(decorated));
        //}

        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(renderStack.getLast().getMatrix());
        crystalModel.render(buf);
        drawFn.run();
        RenderSystem.popMatrix();

        //vboCrystal.bindBuffer();
        //DefaultVertexFormat.POSITION_COLOR_TEX.setupBufferState(0L);
        //vboCrystal.draw(renderStack.getLast().getMatrix(), crystalModel.getGLDrawingMode());
        //DefaultVertexFormat.POSITION_COLOR_TEX.clearBufferState();
        //VertexBuffer.unbindBuffer();
    }

    public static void renderCelestialWings(PoseStack renderStack) {
        if (celestialWingsModel == null) {
            celestialWingsModel = AssetLoader.loadObjModel(AssetLoader.ModelLocation.OBJ, "celestial_wings");
        }
        if (vboCelestialWings == null) {
            // TODO: Fix VertexFormat decorator for 1.21+
            int[] lightGray = new int[] { 178, 178, 178, 255 };
            vboCelestialWings = celestialWingsModel.batch(buffer);
        }
        vboCelestialWings.bindBuffer();
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.setupBufferState(0L);
        vboCelestialWings.draw(renderStack.getLast().getMatrix(), celestialWingsModel.getGLDrawingMode());
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.clearBufferState();
        VertexBuffer.unbindBuffer();
    }

    public static void renderWraithWings(PoseStack renderStack) {
        if (wraithWingsModel == null) {
            wraithWingsModel = AssetLoader.loadObjModel(AssetLoader.ModelLocation.OBJ, "wraith_wings");
        }

        if (wraithWingsBones == null) {
            // TODO: Fix VertexFormat decorator for 1.21+
            int[] gray = new int[] { 77, 77, 77, 255 };
            wraithWingsBones = wraithWingsModel.batchOnly(buffer, "Bones");
        }
        if (wraithWingsWing == null) {
            // TODO: Fix VertexFormat decorator for 1.21+
            int[] black = new int[] { 0, 0, 0, 255 };
            wraithWingsWing = wraithWingsModel.batchOnly(buffer, "Wing");
        }

        wraithWingsBones.bindBuffer();
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.setupBufferState(0L);
        wraithWingsBones.draw(renderStack.getLast().getMatrix(), wraithWingsModel.getGLDrawingMode());
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.clearBufferState();
        VertexBuffer.unbindBuffer();

        wraithWingsWing.bindBuffer();
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.setupBufferState(0L);
        wraithWingsWing.draw(renderStack.getLast().getMatrix(), wraithWingsModel.getGLDrawingMode());
        RenderTypesAS.POSITION_COLOR_TEX_NORMAL.clearBufferState();
        VertexBuffer.unbindBuffer();
    }
}
