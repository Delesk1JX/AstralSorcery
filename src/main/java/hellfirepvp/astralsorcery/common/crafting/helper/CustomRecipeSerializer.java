/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crafting.helper;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CustomRecipeSerializer
 * Created by HellFirePvP
 * Date: 06.07.2019 / 20:38
 */
public abstract class CustomRecipeSerializer<T extends CustomMatcherRecipe> extends DeferredHolder<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    public CustomRecipeSerializer(ResourceLocation name) {
        this.setRegistryName(name);
    }

    public abstract void write(JsonObject object, T recipe);
}
