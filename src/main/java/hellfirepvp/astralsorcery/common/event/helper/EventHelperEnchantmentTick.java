/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.helper;

import hellfirepvp.astralsorcery.common.enchantment.EnchantmentPlayerTick;
import hellfirepvp.observerlib.common.util.tick.ITickHandler;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ClientTickEvent;
import net.neoforged.fml.LogicalSide;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EventHelperEnchantmentTick
 * Created by HellFirePvP
 * Date: 02.05.2020 / 12:56
 */
public class EventHelperEnchantmentTick implements ITickHandler {

    public static final EventHelperEnchantmentTick INSTANCE = new EventHelperEnchantmentTick();

    private Collection<EnchantmentPlayerTick> tickableEnchantments = null;

    private EventHelperEnchantmentTick() {}

    @Override
    public void tick(net.neoforged.neoforge.event.tick.ClientTickEvent type, Object... context) {
        Player player = (Player) context[0];
        LogicalSide side = (LogicalSide) context[1];

        if (tickableEnchantments == null) {
            tickableEnchantments = BuiltInRegistries.ENCHANTMENT.gets().stream()
                    .filter(enchantment -> enchantment instanceof EnchantmentPlayerTick)
                    .map(enchantment -> (EnchantmentPlayerTick) enchantment)
                    .collect(Collectors.toList());
        }

        for (EnchantmentPlayerTick ench : this.tickableEnchantments) {
            int totalLevel = EnchantmentHelper.getMaxEnchantmentLevel(ench, player);
            if (totalLevel > 0) {
                ench.tick(player, side, totalLevel);
            }
        }
    }

    @Override
    public EnumSet<net.neoforged.neoforge.event.tick.ClientTickEvent> getHandledTypes() {
        return EnumSet.of(net.neoforged.neoforge.event.tick.ClientTickEvent.PLAYER);
    }

    @Override
    public boolean canFire(net.neoforged.neoforge.event.tick.ClientTickEvent.Phase phase) {
        return phase == net.neoforged.neoforge.event.tick.ClientTickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "TickEnchantment Helper";
    }
}
