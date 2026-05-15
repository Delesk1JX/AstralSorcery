/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.tick;

import com.google.common.collect.Lists;
import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

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
        EnumSet<hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase> types = handler.getHandledTypes();
        if (types.contains(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.START)) {
            serverTickHandlers.add(handler);
        }
        if (types.contains(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.END)) {
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
    
    @net.neoforged.bus.api.SubscribeEvent
    public void onServerTick(ServerTickEvent.Pre event) {
        fireTicks(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.START, serverTickHandlers, hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.START);
    }
    
    @net.neoforged.bus.api.SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.END, serverTickHandlers, hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.END);
    }
    
    @net.neoforged.bus.api.SubscribeEvent
    public void onClientTick(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientTickEvent event) {
        fireTicks(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.START, clientTickHandlers, hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.START);
    }
    
    @net.neoforged.bus.api.SubscribeEvent
    public void onClientTickPost(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientTickEvent event) {
        fireTicks(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.END, clientTickHandlers, hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase.END);
    }
    
    private void fireTicks(hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase phase, List<ITickHandler> handlers, hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientClientTickEvent.Phase type) {
        for (ITickHandler handler : handlers) {
            if (handler.canFire(phase)) {
                try {
                    handler.tick(new hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientTickEvent(type));
                } catch (Exception e) {
                    hellfirepvp.astralsorcery.common.CommonProxy.LOGGER.error("Error during tick in handler: " + handler.getName(), e);
                }
            }
        }
    }
}
