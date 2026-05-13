/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.resource;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BindableResource
 * Created by HellFirePvP
 * Date: 07.05.2016 / 00:50
 */
@OnlyIn(Dist.CLIENT)
public class BindableResource extends AbstractRenderableTexture.Full implements ReloadableResource {

    private Integer textureId = null;
    private String path = null;

    protected BindableResource(ResourceLocation key) {
        super(key);
    }

    BindableResource(String path) {
        this(AstralSorcery.key(path.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")));
        this.path = path;
        allocateGlId();
    }

    public String getPath() {
        return path;
    }

    public SpriteSheetResource asSpriteSheet(int rows, int columns) {
        return new SpriteSheetResource(this, rows, columns);
    }

    public void invalidateAndReload() {
        Minecraft.getInstance().getTextureManager().deleteTexture(this.getKey());
        this.textureId = null;
    }

    protected Integer allocateGlId() {
        if (AssetLibrary.isReloading()) {
            return null;
        }
        TextureManager mgr = Minecraft.getInstance().getTextureManager();
        SimpleTexture texture = new SimpleTexture(new ResourceLocation(this.getPath()));
        mgr.loadTexture(this.getKey(), texture);
        // Get the texture ID from the loaded texture
        return texture.getId();
    }

    @Override
    public void bindTexture() {
        if (AssetLibrary.isReloading()) {
            return; //we do nothing but wait.
        }
        if (this.textureId == null) {
            this.textureId = allocateGlId();
        }
        if (this.textureId == null) {
            return;
        }
        RenderSystem.bindTexture(this.textureId);
    }

    @Override
    public RenderType.CompositeState asState() {
        return new RenderType.CompositeState(this.getKey(), false, false) {
            @Override
            public void setupRenderState() {
                RenderSystem.enableTexture();
                BindableResource.this.bindTexture();
            }
        };
    }
}
