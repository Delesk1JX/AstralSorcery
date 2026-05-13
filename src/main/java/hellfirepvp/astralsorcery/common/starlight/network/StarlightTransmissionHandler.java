/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.starlight.network;

import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level().Level;
import net.minecraft.server.level().ServerLevel;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import hellfirepvp.observerlib.common.util.tick.TickEvent.ClientTickEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: StarlightTransmissionHandler
 * Created by HellFirePvP
 * Date: 04.08.2016 / 23:24
 */
public class StarlightTransmissionHandler implements ITickHandler {

    private static final StarlightTransmissionHandler instance = new StarlightTransmissionHandler();
    private final Map<net.minecraft.resources.ResourceKey<Level>, TransmissionWorldHandler> worldHandlers = new HashMap<>();

    private StarlightTransmissionHandler() {}

    public static StarlightTransmissionHandler getInstance() {
        return instance;
    }

    @Override
    public void tick(TickEvent.ClientTickEvent type, Object... context) {
        Level world = (Level) context[0];
        if (world.isRemote() || !(world instanceof ServerLevel)) {
            return;
        }

        worldHandlers.computeIfAbsent(world.getDimensionKey(), TransmissionWorldHandler::new).tick((ServerLevel) world);
    }

    public void clearServer() {
        worldHandlers.values().forEach(TransmissionWorldHandler::clear);
        worldHandlers.clear();
    }

    public void informWorldUnload(Level world) {
        net.minecraft.resources.ResourceKey<Level> dimKey = world.getDimensionKey();
        TransmissionWorldHandler handle = worldHandlers.get(dimKey);
        if (handle != null) {
            handle.clear();
        }
        this.worldHandlers.remove(dimKey);
    }

    @Nullable
    public TransmissionWorldHandler getWorldHandler(Level world) {
        if (world == null) {
            return null;
        }
        return worldHandlers.get(world.getDimensionKey());
    }

    @Override
    public EnumSet<TickEvent.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(TickEvent.ClientTickEvent.WORLD);
    }

    @Override
    public boolean canFire(TickEvent.ClientTickEvent.Phase phase) {
        return phase == TickEvent.ClientTickEvent.Phase.START;
    }

    @Override
    public String getName() {
        return "Starlight Transmission Handler";
    }

}
