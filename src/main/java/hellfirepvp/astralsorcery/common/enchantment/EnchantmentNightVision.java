/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.enchantment;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.potion.Effects;
import net.neoforged.fml.LogicalSide;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EnchantmentNightVision
 * Created by HellFirePvP
 * Date: 02.05.2020 / 12:38
 */
public class EnchantmentNightVision extends EnchantmentPlayerTick {

    public EnchantmentNightVision() {
        super(Rarity.VERY_RARE, net.minecraft.world.item.enchantment.Enchantment.ARMOR_HEAD, new EquipmentSlot[] { EquipmentSlot.HEAD });
    }

    @Override
    public void tick(Player player, LogicalSide side, int level) {
        if (side.isServer()) {
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, level - 1, true, false));
        }
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return this.type.canEnchantItem(stack.getItem());
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
