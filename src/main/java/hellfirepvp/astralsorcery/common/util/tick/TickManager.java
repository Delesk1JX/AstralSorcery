/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.tick;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.CommonProxy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.eventbus.api.IEventBus;

import java.util.EnumSet;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: TickManager
 * Created by HellFirePvP
 * Date: 30.03.2017 / 22:23
 * 
 * Manager class that handles registration and execution of tick handlers.
 */
public class TickManager {
    
    private final List<ITickHandler> serverTickHandlers = Lists.newArrayList();
    private final List<ITickHandler> clientTickHandlers = Lists.newArrayList();
    
    /**
     * Registers a tick handler with this manager.
     * 
     * @param handler the handler to register
     */
    public void register(ITickHandler handler) {
        EnumSet<TickEvent.Type> types = handler.getHandledTypes();
        if (types.contains(TickEvent.Type.SERVER)) {
            serverTickHandlers.add(handler);
        }
        if (types.contains(TickEvent.Type.CLIENT)) {
            clientTickHandlers.add(handler);
        }
    }
    
    /**
     * Attaches the tick listeners to the event bus.
     * 
     * @param eventBus the event bus to attach to
     */
    public void attachListeners(IEventBus eventBus) {
        eventBus.register(this);
    }
    
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (!canFireForPhase(event.getPhase())) {
            return;
        }
        for (ITickHandler handler : serverTickHandlers) {
            if (handler.canFire(event.getPhase())) {
                try {
                    handler.tick(TickEvent.Type.SERVER, event.getServer());
                } catch (Exception e) {
                    CommonProxy.LOGGER.error("Error during tick in handler: " + handler.getName(), e);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!canFireForPhase(event.getPhase())) {
            return;
        }
        for (ITickHandler handler : clientTickHandlers) {
            if (handler.canFire(event.getPhase())) {
                try {
                    handler.tick(TickEvent.Type.CLIENT, event.getMinecraft());
                } catch (Exception e) {
                    CommonProxy.LOGGER.error("Error during tick in handler: " + handler.getName(), e);
                }
            }
        }
    }
    
    private boolean canFireForPhase(TickEvent.Phase phase) {
        return phase == TickEvent.Phase.END;
    }
}
