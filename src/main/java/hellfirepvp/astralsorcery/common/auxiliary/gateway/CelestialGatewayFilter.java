/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.auxiliary.gateway;

import hellfirepvp.astralsorcery.AstralSorcery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CelestialGatewayFilter
 * Created by HellFirePvP
 * Date: 23.08.2019 / 22:15
 */
public class CelestialGatewayFilter {

    private final File gatewayFilter;
    private Set<ResourceKey<Level>> cache = new HashSet<>();

    CelestialGatewayFilter() {
        this.gatewayFilter = this.loadFilter();
        this.loadCache();
    }

    private File loadFilter() {
        File dataDir = AstralSorcery.getProxy().getASServerDataDirectory();
        File gatewayFilter = new File(dataDir, "gateway_filter.dat");
        if (!gatewayFilter.exists()) {
            try {
                gatewayFilter.createNewFile();
            } catch (IOException exc) {
                throw new IllegalStateException("Couldn't create plain world filter file! Are we missing file permissions?", exc);
            }
        }
        return gatewayFilter;
    }

    public boolean hasGateways(ResourceLocation worldKey) {
        return this.cache.contains(worldKey);
    }

    void addDim(ResourceKey<Level> worldKey) {
        if (cache.add(worldKey)) {
            this.saveCache();
        }
    }

    void removeDim(ResourceKey<Level> worldKey) {
        if (cache.remove(worldKey)) {
            this.saveCache();
        }
    }

    private void loadCache() {
        try {
            CompoundTag tag = CompressedStreamTools.read(this.gatewayFilter);
            ListTag list = tag.getList("list", net.neoforged.neoforge.common.util.FakePlayerFactory.NBT.TAG_STRING);
            this.cache = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                ResourceLocation location = new ResourceLocation(list.getString(i));
                this.cache.add(ResourceKey.getOrCreateKey(Registry.WORLD_KEY, location));
            }
        } catch (IOException ignored) {
            this.cache = new HashSet<>();
        }
    }

    private void saveCache() {
        try {
            ListTag list = new ListTag();
            for (ResourceKey<Level> dimType : cache) {
                list.add(StringTag.valueOf(dimType.getLocation().toString()));
            }
            CompoundTag cmp = new CompoundTag();
            cmp.put("list", list);
            CompressedStreamTools.write(cmp, this.gatewayFilter);
        } catch (IOException ignored) {}
    }
}
