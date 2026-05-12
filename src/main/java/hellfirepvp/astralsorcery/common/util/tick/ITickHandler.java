/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.tick;

import java.util.EnumSet;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ITickHandler
 * Created by HellFirePvP
 * Date: 30.03.2017 / 22:23
 * 
 * Interface for tick handlers that need to be called every tick.
 */
public interface ITickHandler {
    
    /**
     * Called every tick for the types specified by {@link #getHandledTypes()}.
     * 
     * @param type the type of tick (SERVER or CLIENT)
     * @param context additional context information (e.g., ServerLevel or ClientLevel)
     */
    void tick(net.neoforged.neoforge.event.tick.ClientTickEvent type, Object... context);
    
    /**
     * Returns the set of tick types this handler wants to receive.
     * 
     * @return EnumSet of net.neoforged.neoforge.event.tick.ClientTickEvent that this handler handles
     */
    EnumSet<net.neoforged.neoforge.event.tick.ClientTickEvent> getHandledTypes();
    
    /**
     * Checks if this handler should fire for the given phase.
     * 
     * @param phase the phase of the tick (START or END)
     * @return true if this handler should be called for this phase
     */
    boolean canFire(net.neoforged.neoforge.event.tick.Clientnet.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase);
    
    /**
     * Returns a name for this tick handler, useful for debugging.
     * 
     * @return the name of this handler
     */
    String getName();
}
