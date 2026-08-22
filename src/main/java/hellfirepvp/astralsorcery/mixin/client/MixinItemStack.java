/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.mixin.client;

import hellfirepvp.astralsorcery.common.enchantment.dynamic.DynamicEnchantmentHelper;
import hellfirepvp.astralsorcery.common.perk.DynamicModifierHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import static net.minecraft.network.chat.Component.translatable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: MixinItemStack
 * Created by HellFirePvP
 * Date: 01.01.2022 / 09:52
 */
@Mixin(ItemStack.class)
public class MixinItemStack {

    @Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasTag()Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void addMissingEnchantmentTooltip(Player player, TooltipFlag advanced, CallbackInfoReturnable<List<Component>> cir, List<Component> tooltip) {
        ItemStack stack = (ItemStack)(Object) this;

        List<Component> addition = new ArrayList<>();
        try {
            //Add any dynamic modifiers this item has.
            DynamicModifierHelper.addModifierTooltip(stack, addition);

            //Add prism enchantments
            Map<Enchantment, Integer> enchantments;
            if (!stack.hasTag() && !(enchantments = EnchantmentHelper.getEnchantments(stack)).isEmpty()) {
                for (Enchantment e : enchantments.keySet()) {
                    addition.add(e.getDisplayName(enchantments.get(e)));
                }
            }
        } catch (Exception exc) {
            addition.clear();
            tooltip.add(Component.translatable("astralsorcery.misc.tooltipError").withStyle(ChatFormatting.GRAY));
        }
        tooltip.addAll(addition);
    }

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getEnchantmentList()Lnet/minecraft/nbt/ListTag;"))
    public ListTag enhanceEnchantmentTooltip(ItemStack stack) {
        return DynamicEnchantmentHelper.modifyEnchantmentTags(stack.getEnchantmentList(), stack);
    }
}
