/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery;

import hellfirepvp.astralsorcery.client.ClientProxy;
import hellfirepvp.astralsorcery.common.CommonProxy;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModList;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AstralSorcery
 * Created by HellFirePvP
 * Date: 19.04.2019 / 18:14
 */
@Mod(AstralSorcery.MODID)
public class AstralSorcery {

    public static final String MODID = "astralsorcery";
    public static final String NAME = "Astral Sorcery";

    public static Logger log = LogManager.getLogger(NAME);

    private static AstralSorcery instance;
    private static ModContainer modContainer;
    private final CommonProxy proxy;

    public AstralSorcery(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        AstralSorcery.modContainer = modContainer;

        this.proxy = Thread.currentThread().getThreadGroup() == SidedThreadGroups.CLIENT_GROUP ? new ClientProxy() : new CommonProxy();
        this.proxy.initialize();
        this.proxy.attachLifecycle(modEventBus);
        this.proxy.attachEventHandlers(NeoForge.EVENT_BUS);
    }

    public static AstralSorcery getInstance() {
        return instance;
    }

    public static ModContainer getModContainer() {
        return modContainer;
    }

    public static CommonProxy getProxy() {
        return getInstance().proxy;
    }

    public static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(AstralSorcery.MODID, path);
    }

    public static boolean isDoingDataGeneration() {
        return false; // Datagen в 1.21 работает по-другому
    }
}
