/*******************************************************************************
 * ObserverLib - Utility Library
 *
 * All rights reserved.
 ******************************************************************************/

package hellfirepvp.observerlib.common.util.tick;

import com.google.common.collect.Lists;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ClientTickEvent;
import net.neoforged.bus.api.EventBus;

import java.util.EnumSet;
import java.util.List;

/**
 * Manager class that handles registration and execution of tick handlers.
 * This is a stub for the observerlib library.
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
        EnumSet<net.neoforged.neoforge.event.tick.ClientTickEvent> types = handler.getHandledTypes();
        if (types.contains(net.neoforged.neoforge.event.tick.ClientTickEvent.SERVER)) {
            serverTickHandlers.add(handler);
        }
        if (types.contains(net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT)) {
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
    public void onServerTick(ServerTickEvent.Pre event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.START, serverTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.SERVER, event.getServer());
    }
    
    @SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END, serverTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.SERVER, event.getServer());
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.START, clientTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT, null);
    }
    
    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END, clientTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT, null);
    }
    
    private void fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase, List<ITickHandler> handlers, net.neoforged.neoforge.event.tick.ClientTickEvent type, Object context) {
        for (ITickHandler handler : handlers) {
            if (handler.canFire(phase)) {
                try {
                    handler.tick(type, context);
                } catch (Exception e) {
                    System.err.println("Error during tick in handler: " + handler.getName());
                    e.printStackTrace();
                }
            }
        }
    }
}
