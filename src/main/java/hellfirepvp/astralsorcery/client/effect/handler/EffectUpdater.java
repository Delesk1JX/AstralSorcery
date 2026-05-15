/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.effect.handler;

import hellfirepvp.astralsorcery.common.util.tick.ITickHandler;
import hellfirepvp.astralsorcery.common.util.tick.TickEvent.ClientTickEvent;
import hellfirepvp.observerlib.common.util.tick.TickEvent;

import java.io.IOException;
import java.util.EnumSet;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EffectUpdater
 * Created by HellFirePvP
 * Date: 30.05.2019 / 13:46
 */
public class EffectUpdater implements ITickHandler {

    private static final EffectUpdater INSTANCE = new EffectUpdater();

    public static EffectUpdater getInstance() {
        return INSTANCE;
    }

    @Override
    public void tick(TickEvent.ClientTickEvent type, Object... context) {
        try {
            EffectHandler.getInstance().tick();
        } catch (IOException ignored) {}
    }

    @Override
    public EnumSet<ClientTickEvent> getHandledTypes() {
        return EnumSet.of(ClientTickEvent.CLIENT);
    }

    @Override
    public boolean canFire(ClientTickEvent.Phase phase) {
        return phase == ClientTickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "EffectUpdater";
    }
}
