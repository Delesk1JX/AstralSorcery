/*******************************************************************************
 * ObserverLib - Utility Library
 *
 * All rights reserved.
 ******************************************************************************/

package hellfirepvp.observerlib.common.util.tick;

import net.minecraft.server.MinecraftServer;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.Event;

import java.util.EnumSet;

/**
 * Tick event wrapper for observerlib library.
 * This is a stub for the observerlib library.
 */
public class TickEvent extends Event {
    
    public enum Type {
        SERVER,
        CLIENT
    }
    
    public enum Phase {
        START,
        END
    }
    
    private final Type type;
    private final Phase phase;
    private final Object context;
    
    public TickEvent(Type type, Phase phase, Object context) {
        this.type = type;
        this.phase = phase;
        this.context = context;
    }
    
    public Type getType() {
        return type;
    }
    
    public Phase getPhase() {
        return phase;
    }
    
    public Object getContext() {
        return context;
    }
    
    /**
     * Server tick event.
     */
    public static class ServerTickEvent extends TickEvent {
        private final MinecraftServer server;
        
        public ServerTickEvent(Phase phase, MinecraftServer server) {
            super(Type.SERVER, phase, server);
            this.server = server;
        }
        
        public MinecraftServer getServer() {
            return server;
        }
        
        public Phase getPhase() {
            return super.getPhase();
        }
    }
    
    /**
     * Client tick event.
     */
    public static class ClientTickEvent extends TickEvent {
        private final Minecraft minecraft;
        
        public ClientTickEvent(Phase phase, Minecraft minecraft) {
            super(Type.CLIENT, phase, minecraft);
            this.minecraft = minecraft;
        }
        
        public Minecraft getMinecraft() {
            return minecraft;
        }
        
        public Phase getPhase() {
            return super.getPhase();
        }
    }
}
