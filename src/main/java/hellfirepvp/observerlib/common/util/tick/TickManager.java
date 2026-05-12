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
import net.neoforged.bus.api.IEventBus;

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
    public void onServerTick(ServerTickEvent.Pre event) {
        fireTicks(TickEvent.Phase.START, serverTickHandlers, TickEvent.Type.SERVER, event.getServer());
    }
    
    @SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(TickEvent.Phase.END, serverTickHandlers, TickEvent.Type.SERVER, event.getServer());
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        fireTicks(TickEvent.Phase.START, clientTickHandlers, TickEvent.Type.CLIENT, null);
    }
    
    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent.Post event) {
        fireTicks(TickEvent.Phase.END, clientTickHandlers, TickEvent.Type.CLIENT, null);
    }
    
    private void fireTicks(TickEvent.Phase phase, List<ITickHandler> handlers, TickEvent.Type type, Object context) {
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
