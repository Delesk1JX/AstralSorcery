/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.screen.ScreenObservatory;
import hellfirepvp.astralsorcery.client.screen.container.*;
import hellfirepvp.astralsorcery.common.container.ContainerObservatory;
import hellfirepvp.astralsorcery.common.container.factory.*;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;

import static hellfirepvp.astralsorcery.common.lib.ContainerTypesAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryContainerTypes
 * Created by HellFirePvP
 * Date: 09.08.2019 / 21:15
 */
public class RegistryContainerTypes {

    private RegistryContainerTypes() {}

    public static void init() {
        TOME = ASRegistries.MENU_TYPES.register("tome", 
                () -> new MenuType<>(ContainerTomeProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));
        OBSERVATORY = ASRegistries.MENU_TYPES.register("observatory", 
                () -> new MenuType<>(ContainerObservatoryProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));

        ALTAR_DISCOVERY = ASRegistries.MENU_TYPES.register("altar_discovery", 
                () -> new MenuType<>(ContainerAltarDiscoveryProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));
        ALTAR_ATTUNEMENT = ASRegistries.MENU_TYPES.register("altar_attunement", 
                () -> new MenuType<>(ContainerAltarAttunementProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));
        ALTAR_CONSTELLATION = ASRegistries.MENU_TYPES.register("altar_constellation", 
                () -> new MenuType<>(ContainerAltarConstellationProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));
        ALTAR_RADIANCE = ASRegistries.MENU_TYPES.register("altar_radiance", 
                () -> new MenuType<>(ContainerAltarRadianceProvider.Factory::create, FeatureFlags.DEFAULT_FLAGS));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initClient() {
        // Screen registration moved to client setup event
    }
}
