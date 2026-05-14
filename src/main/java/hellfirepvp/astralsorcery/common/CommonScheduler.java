/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common;

import hellfirepvp.astralsorcery.common.util.Counter;
import hellfirepvp.astralsorcery.common.util.tick.ITickHandler;
import hellfirepvp.astralsorcery.common.util.tick.TickEvent;
import net.minecraft.util.Tuple;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CommonScheduler
 * Created by HellFirePvP
 * Date: 30.03.2017 / 22:23
 */
public class CommonScheduler implements ITickHandler {

    private static final Object lock = new Object();

    private boolean inTick = false;
    private final LinkedList<Tuple<Runnable, Counter>> queue = new LinkedList<>();
    private final LinkedList<Tuple<Runnable, Integer>> waiting = new LinkedList<>();

    @Override
    public void tick(TickEvent.ClientTickEvent type, Object... context) {
        inTick = true;
        synchronized (lock) {
            Iterator<Tuple<Runnable, Counter>> iterator = queue.iterator();
            while (iterator.hasNext()) {
                Tuple<Runnable, Counter> r = iterator.next();
                r.getB().decrement();
                if (r.getB().getValue() <= 0) {
                    r.getA().run();
                    iterator.remove();
                }
            }
            inTick = false;
            for (Tuple<Runnable, Integer> wait : waiting) {
                queue.addLast(new Tuple<>(wait.getA(), new Counter(wait.getB())));
            }
        }
        waiting.clear();
    }

    @Override
    public EnumSet<TickEvent.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(TickEvent.ClientTickEvent.CLIENT);
    }

    @Override
    public boolean canFire(TickEvent.Phase phase) {
        return phase == TickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "Common Scheduler";
    }

    public void addRunnable(Runnable r, int tickDelay) {
        synchronized (lock) {
            if (inTick) {
                waiting.addLast(new Tuple<>(r, tickDelay));
            } else {
                queue.addLast(new Tuple<>(r, new Counter(tickDelay)));
            }
        }
    }

}
