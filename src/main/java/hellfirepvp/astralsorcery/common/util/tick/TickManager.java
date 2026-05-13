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
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ClientTickEvent;
import net.neoforged.bus.api.EventBus;

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
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.START, serverTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.SERVER);
    }
    
    @SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END, serverTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.SERVER);
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.START, clientTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT);
    }
    
    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent event) {
        fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END, clientTickHandlers, net.neoforged.neoforge.event.tick.ClientTickEvent.CLIENT);
    }
    
    private void fireTicks(net.neoforged.neoforge.event.tick.net.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase, List<ITickHandler> handlers, net.neoforged.neoforge.event.tick.ClientTickEvent type) {
        for (ITickHandler handler : handlers) {
            if (handler.canFire(phase)) {
                try {
                    handler.tick(type);
                } catch (Exception e) {
                    CommonProxy.LOGGER.error("Error during tick in handler: " + handler.getName(), e);
                }
            }
        }
    }
}
