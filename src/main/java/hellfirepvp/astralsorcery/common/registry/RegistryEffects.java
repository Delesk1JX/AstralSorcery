/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.effect.*;
import net.neoforged.neoforge.registries.DeferredHolder;

import static hellfirepvp.astralsorcery.common.lib.EffectsAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryEffects
 * Created by HellFirePvP
 * Date: 26.08.2019 / 19:12
 */
public class RegistryEffects {

    private RegistryEffects() {}

    public static void init() {
        EFFECT_BLEED = ASRegistries.EFFECTS.register("bleed", EffectBleed::new);
        EFFECT_CHEAT_DEATH = ASRegistries.EFFECTS.register("cheat_death", EffectCheatDeath::new);
        EFFECT_DROP_MODIFIER = ASRegistries.EFFECTS.register("drop_modifier", EffectDropModifier::new);
    }
}
