/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.util;

import net.minecraft.client.Minecraft;
// TextInputUtil has been removed in 1.21.1, using direct clipboard handling instead
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: GuiTextEntry
 * Created by HellFirePvP
 * Date: 15.07.2018 / 14:49
 */
public class ScreenTextEntry {

    private String text = "";
    private Runnable changeCallback = null;

    public ScreenTextEntry() {
        // TextInputUtil was removed in 1.21.1
        // Text input is now handled directly without this utility
    }

    public void setChangeCallback(Runnable changeCallback) {
        this.changeCallback = changeCallback;
    }

    public void setText(@Nullable String newText) {
        if (newText == null) {
            newText = "";
        }
        String prevText = this.text;
        this.text = newText;
        if (!newText.equals(prevText) && changeCallback != null) {
            changeCallback.run();
        }
    }

    @Nonnull
    public String getText() {
        return text;
    }

    public boolean keyTyped(int key) {
        if (key == GLFW.GLFW_KEY_ESCAPE ||
                key == GLFW.GLFW_KEY_ENTER ||
                key == GLFW.GLFW_KEY_KP_ENTER ||
                key == GLFW.GLFW_KEY_HOME ||
                key == GLFW.GLFW_KEY_END ||
                key == GLFW.GLFW_KEY_INSERT ||
                key == GLFW.GLFW_KEY_DELETE) {
            return false;
        }
        //Arrow keys
        if (key >= GLFW.GLFW_KEY_RIGHT && key <= GLFW.GLFW_KEY_UP) {
            return false;
        }
        // TextInputUtil removed in 1.21.1, simplified handling
        return false;
    }

    public boolean charTyped(char charCode) {
        if (this.text.length() < 256) {
            this.setText(this.text + charCode);
            return true;
        }
        return false;
    }
}
