/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.tick;

import net.minecraft.server.MinecraftServer;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.EnumSet;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: TickEvent
 * Created by HellFirePvP
 * Date: 30.03.2017 / 22:23
 * 
 * Custom tick event wrapper for compatibility with new NeoForge API.
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
     * Server tick event for NeoForge compatibility.
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
     * Client tick event for NeoForge compatibility.
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
