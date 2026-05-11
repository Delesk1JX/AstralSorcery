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
import net.minecraft.world.item.ItemCooldowns;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: MixinCooldownTracker
 * Created by HellFirePvP
 * Date: 01.01.2022 / 09:52
 */
@Mixin(ItemCooldowns.class)
public class MixinCooldownTracker {

    // TODO: Rewrite for Minecraft 1.21.1 ItemCooldowns system
    // The cooldown system has changed significantly in 1.21.1
    // This mixin needs to be applied to ServerPlayer instead
    /*
    @ModifyVariable(method = "setCooldown", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int fireCooldownEvent(int cooldownTicks) {
        ItemCooldowns cooldowns = (ItemCooldowns)(Object) this;
        // TODO: Find a way to get player reference and fire event
        return cooldownTicks;
    }
    */

}
