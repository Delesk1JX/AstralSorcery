/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.mixin;

import hellfirepvp.astralsorcery.common.enchantment.dynamic.DynamicEnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: MixinEnchantmentHelper
 * Created by HellFirePvP
 * Date: 01.01.2022 / 09:52
 */
@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {

    @Inject(method = "getEnchantmentLevel", at = @At("RETURN"), cancellable = true)
    private static void getEnhancedEnchantmentLevel(Enchantment enchID, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(DynamicEnchantmentHelper.getNewEnchantmentLevel(cir.getReturnValue(), enchID, stack, null));
    }

    @Redirect(
            method = "applyEnchantmentModifier",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getEnchantmentTagList()Lnet/minecraft/nbt/ListTag;")
    )
    private static ListTag applyEnhancedEnchantmentsTag(ItemStack stack) {
        return DynamicEnchantmentHelper.modifyEnchantmentTags(stack.getEnchantmentTagList(), stack);
    }

    @Inject(method = "getEnchantments", at = @At("RETURN"))
    private static void applyDeserializedEnhancedEnchantments(ItemStack stack, CallbackInfoReturnable<Map<Enchantment, Integer>> cir) {
        DynamicEnchantmentHelper.addNewLevels(cir.getReturnValue(), stack);
    }
}
