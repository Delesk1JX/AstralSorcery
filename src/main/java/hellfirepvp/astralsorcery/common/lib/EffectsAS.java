/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.lib;

import hellfirepvp.astralsorcery.common.effect.EffectBleed;
import hellfirepvp.astralsorcery.common.effect.EffectCheatDeath;
import hellfirepvp.astralsorcery.common.effect.EffectDropModifier;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EffectsAS
 * Created by HellFirePvP
 * Date: 26.08.2019 / 19:13
 */
public class EffectsAS {

    private EffectsAS() {}

    public static DeferredHolder<?, EffectBleed>        EFFECT_BLEED;
    public static DeferredHolder<?, EffectCheatDeath>   EFFECT_CHEAT_DEATH;
    public static DeferredHolder<?, EffectDropModifier> EFFECT_DROP_MODIFIER;

}
