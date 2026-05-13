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
import net.neoforged.neoforge.event.tick.TickEvent;
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
        EnumSet<TickEvent.ClientTickEvent.Phase> types = handler.getHandledTypes();
        if (types.contains(TickEvent.ClientTickEvent.Phase.START)) {
            serverTickHandlers.add(handler);
        }
        if (types.contains(TickEvent.ClientTickEvent.Phase.END)) {
            clientTickHandlers.add(handler);
        }
    }
    
    /**
     * Attaches the tick listeners to the event bus.
     * 
     * @param eventBus the event bus to attach to
     */
    public void attachListeners(EventBus eventBus) {
        eventBus.register(this);
    }
    
    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Pre event) {
        fireTicks(TickEvent.ClientTickEvent.Phase.START, serverTickHandlers, TickEvent.ClientTickEvent.Phase.START);
    }
    
    @SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(TickEvent.ClientTickEvent.Phase.END, serverTickHandlers, TickEvent.ClientTickEvent.Phase.END);
    }
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        fireTicks(TickEvent.ClientTickEvent.Phase.START, clientTickHandlers, TickEvent.ClientTickEvent.Phase.START);
    }
    
    @SubscribeEvent
    public void onClientTickPost(TickEvent.ClientTickEvent event) {
        fireTicks(TickEvent.ClientTickEvent.Phase.END, clientTickHandlers, TickEvent.ClientTickEvent.Phase.END);
    }
    
    private void fireTicks(TickEvent.ClientTickEvent.Phase phase, List<ITickHandler> handlers, TickEvent.ClientTickEvent.Phase type) {
        for (ITickHandler handler : handlers) {
            if (handler.canFire(phase)) {
                try {
                    handler.tick(new TickEvent.ClientTickEvent(type));
                } catch (Exception e) {
                    CommonProxy.LOGGER.error("Error during tick in handler: " + handler.getName(), e);
                }
            }
        }
    }
}
