/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.enchantment.EnchantmentNightVision;
import hellfirepvp.astralsorcery.common.enchantment.EnchantmentScorchingHeat;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;

import static hellfirepvp.astralsorcery.common.lib.EnchantmentsAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryEnchantments
 * Created by HellFirePvP
 * Date: 02.05.2020 / 12:43
 */
public class RegistryEnchantments {

    private RegistryEnchantments() {}

    /**
     * @see hellfirepvp.astralsorcery.common.loot.global.LootModifierScorchingHeat
     */
    public static void init() {
        NIGHT_VISION = ASRegistries.ENCHANTMENTS.register("night_vision", EnchantmentNightVision::new);
        SCORCHING_HEAT = ASRegistries.ENCHANTMENTS.register("scorching_heat", EnchantmentScorchingHeat::new);
    }
}
