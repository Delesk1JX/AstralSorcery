/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.enchantment.amulet;

import hellfirepvp.astralsorcery.common.enchantment.dynamic.DynamicEnchantment;
import hellfirepvp.astralsorcery.common.enchantment.dynamic.DynamicEnchantmentType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.client.resources.language.LanguageManager;
import static net.minecraft.network.chat.Component.translatable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AmuletEnchantment
 * Created by HellFirePvP
 * Date: 11.08.2019 / 20:12
 */
public class AmuletEnchantment extends DynamicEnchantment {

    public AmuletEnchantment(Dynamicnet.minecraft.world.item.enchantment.Enchantment type, @Nonnull Enchantment enchantment, int levelAddition) {
        super(type, enchantment, levelAddition);
    }

    public AmuletEnchantment(Dynamicnet.minecraft.world.item.enchantment.Enchantment type, int levelAddition) {
        super(type, levelAddition);
    }

    //TODO nested translation components..?
    @OnlyIn(Dist.CLIENT)
    public net.minecraft.network.chat.Component getDisplay() {
        String typeStr = this.getType().getDisplayName();
        String levelsStr = I18n.format(String.format("astralsorcery.amulet.enchantment.level.%s", this.levelAddition > 1 ? "more" : "one"));

        if (this.getType().isEnchantmentSpecific()) {
            return new Component.translatable(typeStr,
                    String.valueOf(this.getLevelAddition()), levelsStr, LanguageMap.getInstance().func_230503_a_(this.getEnchantment().getName()));
        } else {
            return new Component.translatable(typeStr, String.valueOf(this.getLevelAddition()), levelsStr);
        }
    }

    public boolean canMerge(AmuletEnchantment other) {
        return this.type.equals(other.type) && (!this.type.isEnchantmentSpecific() || this.enchantment.equals(other.enchantment));
    }

    public void merge(AmuletEnchantment src) {
        if (canMerge(src)) {
            this.levelAddition += src.levelAddition;
        }
    }

    public CompoundTag serialize() {
        CompoundTag cmp = new CompoundTag();
        cmp.putInt("type", this.type.ordinal());
        cmp.putInt("level", this.levelAddition);
        if (this.type.isEnchantmentSpecific()) { //Enchantment must not be null here anyway as the type requires a ench to begin with
            cmp.putString("ench", this.enchantment.getRegistryName().toString());
        }
        return cmp;
    }

    @Nullable
    public static AmuletEnchantment deserialize(CompoundTag cmp) {
        int typeId = cmp.getInt("type");
        Dynamicnet.minecraft.world.item.enchantment.Enchantment type = Dynamicnet.minecraft.world.item.enchantment.Enchantment.values()[typeId];
        int level = Math.max(0, cmp.getInt("level"));
        if (type.isEnchantmentSpecific()) {
            ResourceLocation res = new ResourceLocation(cmp.getString("ench"));
            Enchantment e = BuiltInRegistries.ENCHANTMENT.get(res);
            if (e != null) {
                return new AmuletEnchantment(type, e, level);
            }
        } else {
            return new AmuletEnchantment(type, level);
        }
        return null;
    }

}
