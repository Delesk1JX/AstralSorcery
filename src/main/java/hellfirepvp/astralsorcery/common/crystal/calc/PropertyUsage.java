/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crystal.calc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Component;
import net.minecraft.util.text.Component.translatable;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PropertyUsage
 * Created by HellFirePvP
 * Date: 30.01.2019 / 08:14
 */
public class PropertyUsage extends DeferredHolder<PropertyUsage> {

    public PropertyUsage(ResourceLocation registryName) {
        setRegistryName(registryName);
    }

    public IFormattableTextComponent getName() {
        return new Component.translatable(String.format("crystal.usage.%s.%s.name",
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
