/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk.node.key;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hellfirepvp.astralsorcery.common.data.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.data.research.ResearchHelper;
import hellfirepvp.astralsorcery.common.enchantment.dynamic.DynamicEnchantment;
import hellfirepvp.astralsorcery.common.enchantment.dynamic.Dynamicnet.minecraft.world.item.enchantment.Enchantment;
import hellfirepvp.astralsorcery.common.event.DynamicEnchantmentEvent;
import hellfirepvp.astralsorcery.common.perk.node.KeyPerk;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.JSONUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.LogicalSide;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: KeyAddEnchantment
 * Created by HellFirePvP
 * Date: 25.08.2019 / 19:08
 */
public class KeyAddEnchantment extends KeyPerk {

    private final List<DynamicEnchantment> enchantments = Lists.newArrayList();

    public KeyAddEnchantment(ResourceLocation name, float x, float y) {
        super(name, x, y);
    }

    @Override
    public void attachListeners(LogicalSide side, IEventBus bus) {
        super.attachListeners(side, bus);
        if (side.isServer()) {
            bus.addListener(this::onEnchantmentAddServer);
        } else {
            bus.addListener(this::onEnchantmentAddClient);
        }
    }

    public KeyAddEnchantment addEnchantment(Enchantment ench, int level) {
        return addEnchantment(Dynamicnet.minecraft.world.item.enchantment.Enchantment.ADD_TO_SPECIFIC, ench, level);
    }

    public KeyAddEnchantment addEnchantment(Dynamicnet.minecraft.world.item.enchantment.Enchantment type, Enchantment ench, int level) {
        this.enchantments.add(new DynamicEnchantment(type, ench, level));
        return this;
    }

    public KeyAddEnchantment addAllEnchantmentIncrease(int level) {
        this.enchantments.add(new DynamicEnchantment(Dynamicnet.minecraft.world.item.enchantment.Enchantment.ADD_TO_EXISTING_ALL, level));
        return this;
    }
    private void onEnchantmentAddClient(DynamicEnchantmentEvent.Add event) {
        Player player = event.getResolvedPlayer();
        LogicalSide side = this.getSide(player);
        if (side.isClient()) {
            addEnchantments(player, side, event);
        }
    }

    private void onEnchantmentAddServer(DynamicEnchantmentEvent.Add event) {
        Player player = event.getResolvedPlayer();
        LogicalSide side = this.getSide(player);
        if (side.isServer()) {
            addEnchantments(player, side, event);
        }
    }

    private void addEnchantments(Player player, LogicalSide side, DynamicEnchantmentEvent.Add event) {
        PlayerProgress prog = ResearchHelper.getProgress(player, side);
        if (prog.getPerkData().hasPerkEffect(this)) {
            List<DynamicEnchantment> listedEnchantments = event.getEnchantmentsToApply();
            for (DynamicEnchantment ench : this.enchantments) {
                DynamicEnchantment added = MiscUtils.iterativeSearch(listedEnchantments, e ->
                        (e.getEnchantment() == null ? ench.getEnchantment() == null : e.getEnchantment().equals(ench.getEnchantment())) &&
                                e.getType().equals(ench.getType()));
                if (added != null) {
                    added.setLevelAddition(added.getLevelAddition() + ench.getLevelAddition());
                } else {
                    listedEnchantments.add(ench.copy());
                }
            }
        }
    }

    @Override
    public void deserializeData(JsonObject perkData) {
        super.deserializeData(perkData);

        this.enchantments.clear();

        if (perkData.has("enchantments")) {
            JsonArray array = JSONUtils.getJsonArray(perkData, "enchantments");
            for (int i = 0; i < array.size(); i++) {
                JsonObject serializedEnchantment = JSONUtils.getJsonObject(array.get(i), "enchantments[%s]");

                String typeKey = JSONUtils.getString(serializedEnchantment, "type");
                Dynamicnet.minecraft.world.item.enchantment.Enchantment type;
                try {
                    type = Dynamicnet.minecraft.world.item.enchantment.Enchantment.valueOf(typeKey);
                } catch (Exception exc) {
                    throw new IllegalArgumentException("Unknown dynamic enchantment type: " + typeKey);
                }
                int level = JSONUtils.getInt(serializedEnchantment, "level");

                if (type.isEnchantmentSpecific()) {
                    String enchantmentKey = JSONUtils.getString(serializedEnchantment, "enchantment");
                    Enchantment ench = BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(enchantmentKey));
                    if (ench == null) {
                        throw new IllegalArgumentException("Unknown Enchantment: " + enchantmentKey);
                    }
                    this.addEnchantment(type, ench, level);
                } else {
                    this.addAllEnchantmentIncrease(level);
                }
            }
        }
    }

    @Override
    public void serializeData(JsonObject perkData) {
        super.serializeData(perkData);

        if (!this.enchantments.isEmpty()) {
            JsonArray array = new JsonArray();

            for (DynamicEnchantment enchantment : this.enchantments) {
                JsonObject serializedEnchantment = new JsonObject();

                serializedEnchantment.addProperty("type", enchantment.getType().name());
                if (enchantment.getEnchantment() != null) {
                    serializedEnchantment.addProperty("enchantment", enchantment.getEnchantment().getRegistryName().toString());
                }
                serializedEnchantment.addProperty("level", enchantment.getLevelAddition());

                array.add(serializedEnchantment);
            }

            perkData.add("enchantments", array);
        }
    }
}
