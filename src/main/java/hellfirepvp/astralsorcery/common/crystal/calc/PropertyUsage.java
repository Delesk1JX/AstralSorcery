/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crystal.calc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import static net.minecraft.network.chat.Component.translatable;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.item.Item;

import java.util.Objects;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PropertyUsage
 * Created by HellFirePvP
 * Date: 30.01.2019 / 08:14
 */
public class PropertyUsage extends DeferredHolder<Item, PropertyUsage> {

    public PropertyUsage(ResourceLocation registryName) {
        super(registryName);
    }

    public net.minecraft.network.chat.Component getName() {
        return Component.translatable(String.format("crystal.usage.%s.%s.name",
                getRegistryName().getNamespace(), getRegistryName().getPath()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyUsage that = (PropertyUsage) o;
        return Objects.equals(this.getRegistryName(), that.getRegistryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getRegistryName());
    }
}
