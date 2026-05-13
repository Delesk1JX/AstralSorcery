/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.util.camera;

import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import hellfirepvp.observerlib.common.util.tick.TickEvent.ClientTickEvent;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.TreeSet;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ClientCameraManager
 * Created by HellFirePvP
 * Date: 02.12.2019 / 19:50
 */
public class ClientCameraManager implements ITickHandler {

    public static final ClientCameraManager INSTANCE = new ClientCameraManager();

    private final TreeSet<ICameraTransformer> transformers = new TreeSet<>(Comparator.comparingInt(ICameraTransformer::getPriority));
    private ICameraTransformer lastTransformer = null;

    @Override
    public void tick(TickEvent.ClientTickEvent type, Object... context) {
        if (type == TickEvent.ClientTickEvent.RENDER) {
            //Render Tick
            float pTicks = (float) context[0];
            if (this.hasActiveTransformer()) {
                ICameraTransformer prio = this.getActiveTransformer();
                if (!prio.equals(lastTransformer)) {
                    if (lastTransformer != null) {
                        lastTransformer.onStopTransforming(pTicks);
                    }
                    prio.onStartTransforming(pTicks);
                    lastTransformer = prio;
                }
                prio.transformRenderView(Minecraft.getInstance().isGamePaused() ? 0F : pTicks);
                if (prio.getPersistencyFunction().isExpired()) {
                    prio.onStopTransforming(pTicks);
                    transformers.remove(prio);
                }
            } else {
                //Clean up remaining transformer
                if (lastTransformer != null) {
                    lastTransformer.onStopTransforming(pTicks);
                    lastTransformer = null;
                }
            }
        } else if (!Minecraft.getInstance().isGamePaused()) {
            //Client Tick
            if (this.hasActiveTransformer()) {
                this.getActiveTransformer().onClientTick();
            }
        }
    }

    public void removeAllAndCleanup() {
        if (this.hasActiveTransformer()) {
            transformers.last().onStopTransforming(0);
        }
        this.transformers.clear();
    }

    public void addTransformer(ICameraTransformer transformer) {
        this.transformers.add(transformer);
    }

    public void removeTransformer(ICameraTransformer transformer) {
        this.transformers.remove(transformer);
    }

    @Nullable
    public ICameraTransformer getActiveTransformer() {
        if (this.hasActiveTransformer()) {
            return this.transformers.last();
        }
        return null;
    }

    public boolean hasActiveTransformer() {
        return !this.transformers.isEmpty();
    }

    @Override
    public EnumSet<TickEvent.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(TickEvent.ClientTickEvent.RENDER, TickEvent.ClientTickEvent.CLIENT);
    }

    @Override
    public boolean canFire(TickEvent.ClientTickEvent.Phase phase) {
        return phase == TickEvent.ClientTickEvent.Phase.START;
    }

    @Override
    public String getName() {
        return "Client Camera Manager";
    }
}
