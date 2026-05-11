/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.mixin;

import hellfirepvp.astralsorcery.common.event.CooldownSetEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: MixinCooldownTracker
 * Created by HellFirePvP
 * Date: 01.01.2022 / 09:52
 */
@Mixin(Player.class)
public class MixinCooldownTracker {

    @ModifyArg(method = "addCooldown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"), index = 1)
    public int fireCooldownEvent(int cooldownTicks) {
        Player player = (Player)(Object) this;
        CooldownSetEvent event = new CooldownSetEvent(player, cooldownTicks);
        NeoForge.EVENT_BUS.post(event);
        return event.getResultCooldown();
    }

}
