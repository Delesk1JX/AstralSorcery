/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry.internal;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import net.minecraft.util.ResourceLocation;
import net.neoforged.neoforge.registries.Registry;
import net.neoforged.neoforge.registries.IRegistryObject;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: InternalRegistryPrimer
 * Created by HellFirePvP
 * Date: 26.06.2017 / 14:49
 */
public class InternalRegistryPrimer {

    private final Map<Class<?>, List<IRegistryObject<?>>> primed = new HashMap<>();

    public <V extends IRegistryObject<V>> V register(V entry) {
        Class<V> type = entry.getRegistryType();
        List<IRegistryObject<?>> entries = primed.computeIfAbsent(type, k -> Lists.newLinkedList());
        entries.add(entry);
        return entry;
    }

    <T extends IRegistryObject<T>> List<?> getEntries(Class<T> type) {
        return primed.getOrDefault(type, Collections.emptyList());
    }

    @Nullable
    public <V extends IRegistryObject<V>> V getCached(Registry<V> registry, ResourceLocation key) {
        return (V) MiscUtils.iterativeSearch(this.primed.getOrDefault(registry.getRegistrySuperType(), Collections.emptyList()),
                entry -> entry.getRegistryName().equals(key));
    }

}
