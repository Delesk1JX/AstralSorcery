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
import net.neoforged.bus.api.IEventBus;

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
    public void onServerTick(ServerTickEvent.Pre event) {
        fireTicks(TickEvent.Phase.START, serverTickHandlers, TickEvent.Type.SERVER);
    }
    
    @SubscribeEvent
    public void onServerTickPost(ServerTickEvent.Post event) {
        fireTicks(TickEvent.Phase.END, serverTickHandlers, TickEvent.Type.SERVER);
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        fireTicks(TickEvent.Phase.START, clientTickHandlers, TickEvent.Type.CLIENT);
    }
    
    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent.Post event) {
        fireTicks(TickEvent.Phase.END, clientTickHandlers, TickEvent.Type.CLIENT);
    }
    
    private void fireTicks(TickEvent.Phase phase, List<ITickHandler> handlers, TickEvent.Type type) {
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
