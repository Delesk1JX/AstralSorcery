/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.starlight.network;

import hellfirepvp.astralsorcery.common.starlight.transmission.IPrismTransmissionNode;
import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ClientTickEvent;

import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: StarlightUpdateHandler
 * Created by HellFirePvP
 * Date: 01.10.2016 / 01:41
 */
public class StarlightUpdateHandler implements ITickHandler {

    private static final StarlightUpdateHandler instance = new StarlightUpdateHandler();
    private static final Map<net.minecraft.resources.ResourceKey<Level>, List<IPrismTransmissionNode>> updateRequired = new HashMap<>();
    private static final Object accessLock = new Object();

    private StarlightUpdateHandler() {}

    public static StarlightUpdateHandler getInstance() {
        return instance;
    }

    @Override
    public void tick(net.neoforged.neoforge.event.tick.ClientTickEvent type, Object... context) {
        Level world = (Level) context[0];
        if (world.isRemote()) {
            return;
        }

        List<IPrismTransmissionNode> nodes = getNodes(world);
        synchronized (accessLock) {
            for (IPrismTransmissionNode node : nodes) {
                node.update(world);
            }
        }
    }

    private List<IPrismTransmissionNode> getNodes(Level world) {
        return updateRequired.computeIfAbsent(world.getDimensionKey(), k -> new LinkedList<>());
    }

    public void removeNode(Level world, IPrismTransmissionNode node) {
        synchronized (accessLock) {
            getNodes(world).remove(node);
        }
    }

    public void addNode(Level world, IPrismTransmissionNode node) {
        synchronized (accessLock) {
            getNodes(world).add(node);
        }
    }

    public void informWorldLoad(Level world) {
        synchronized (accessLock) {
            updateRequired.remove(world.getDimensionKey());
        }
    }

    public void clearServer() {
        synchronized (accessLock) {
            updateRequired.clear();
        }
    }

    @Override
    public EnumSet<net.neoforged.neoforge.event.tick.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(net.neoforged.neoforge.event.tick.ClientTickEvent.WORLD);
    }

    @Override
    public boolean canFire(net.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase) {
        return phase == net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "Starlight Update Handler";
    }

}
