/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.util.draw;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BufferContext
 * Created by HellFirePvP
 * Date: 08.07.2019 / 20:39
 */
public class BufferContext extends VertexConsumer {

    private boolean inDrawing = false;

    BufferContext(int size) {
        super(size);
    }

    @Override
    public void begin(int mode, VertexFormat format) {
        super.begin(mode, format);

        this.inDrawing = true;
    }

    @Override
    public void sortVertexData(float x, float y, float z) {
        if (this.inDrawing) {
            super.sortVertexData(x, y, z);
        }
    }

    public void draw() {
        if (this.inDrawing) {
            RenderingUtils.finishDrawing(this);
            this.inDrawing = false;
        }
    }

}
