/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.base.patreon.manager;

import hellfirepvp.astralsorcery.client.data.config.entry.RenderingConfig;
import hellfirepvp.astralsorcery.common.base.patreon.PatreonEffect;
import hellfirepvp.astralsorcery.common.base.patreon.PatreonEffectHelper;
import hellfirepvp.astralsorcery.common.base.patreon.entity.PatreonPartialEntity;
import hellfirepvp.astralsorcery.common.data.sync.SyncDataHolder;
import hellfirepvp.astralsorcery.common.data.sync.client.ClientPatreonFlares;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ClientTickEvent;
import net.neoforged.fml.LogicalSide;

import java.util.Collection;
import java.util.EnumSet;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PatreonManagerClient
 * Created by HellFirePvP
 * Date: 31.08.2019 / 01:42
 */
public class PatreonManagerClient implements ITickHandler {

    public static PatreonManagerClient INSTANCE = new PatreonManagerClient();

    private PatreonManagerClient() {}

    @Override
    public void tick(net.neoforged.neoforge.event.tick.ClientTickEvent type, Object... context) {
        Level clWorld = Minecraft.getInstance().world;
        Player thisPlayer = Minecraft.getInstance().player;
        if (clWorld == null || thisPlayer == null) {
            return;
        }
        net.minecraft.resources.ResourceKey<Level> clientWorld = clWorld.getDimensionKey();
        Vector3 thisPlayerPos = Vector3.atEntityCenter(thisPlayer);

        SyncDataHolder.executeClient(SyncDataHolder.DATA_PATREON_FLARES, ClientPatreonFlares.class, data -> {
            for (Collection<PatreonPartialEntity> playerEntities : data.getEntities()) {
                for (PatreonPartialEntity entity : playerEntities) {
                    if (entity.getLastTickedDimension() == null || !entity.getLastTickedDimension().equals(clientWorld)) {
                        continue;
                    }
                    if (entity.getPos().distanceSquared(thisPlayerPos) <= RenderingConfig.CONFIG.getMaxEffectRenderDistanceSq()) {
                        entity.tickClient();
                    }
                    entity.tick(clWorld);
                }
            }
        });

        SyncDataHolder.executeClient(SyncDataHolder.DATA_PATREON_FLARES, ClientPatreonFlares.class, data -> {
            for (Player player : clWorld.getPlayers()) {
                for (PatreonEffect effect : PatreonEffectHelper.getPatreonEffects(LogicalSide.CLIENT, player.getUniqueID())) {
                    effect.doClientEffect(player);
                }
            }
        });
    }

    @Override
    public EnumSet<net.neoforged.neoforge.event.tick.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT);
    }

    @Override
    public boolean canFire(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase) {
        return phase == net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "Patreon Flare Manager (Client)";
    }
}
