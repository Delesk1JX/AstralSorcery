/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.CommonProxy;
import hellfirepvp.astralsorcery.common.block.base.CustomItemBlock;
import hellfirepvp.astralsorcery.common.block.base.CustomItemBlockProperties;
import hellfirepvp.astralsorcery.common.block.tile.BlockCelestialCrystalCluster;
import hellfirepvp.astralsorcery.common.block.tile.BlockGemCrystalCluster;
import hellfirepvp.astralsorcery.common.item.*;
import hellfirepvp.astralsorcery.common.item.armor.ItemMantle;
import hellfirepvp.astralsorcery.common.item.base.client.ItemDynamicColor;
import hellfirepvp.astralsorcery.common.item.crystal.ItemAttunedCelestialCrystal;
import hellfirepvp.astralsorcery.common.item.crystal.ItemAttunedRockCrystal;
import hellfirepvp.astralsorcery.common.item.crystal.ItemCelestialCrystal;
import hellfirepvp.astralsorcery.common.item.crystal.ItemRockCrystal;
import hellfirepvp.astralsorcery.common.item.dust.ItemIlluminationPowder;
import hellfirepvp.astralsorcery.common.item.dust.ItemNocturnalPowder;
import hellfirepvp.astralsorcery.common.item.gem.ItemPerkGemDay;
import hellfirepvp.astralsorcery.common.item.gem.ItemPerkGemNight;
import hellfirepvp.astralsorcery.common.item.gem.ItemPerkGemSky;
import hellfirepvp.astralsorcery.common.item.lens.*;
import hellfirepvp.astralsorcery.common.item.tool.*;
import hellfirepvp.astralsorcery.common.item.useables.*;
import hellfirepvp.astralsorcery.common.item.wand.*;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.util.NameUtil;
import hellfirepvp.astralsorcery.common.util.dispenser.FluidContainerDispenseBehavior;
import net.minecraft.world.level().block.Block;
import net.minecraft.world.level().block.DispenserBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ColorHandlerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

import static hellfirepvp.astralsorcery.common.lib.ItemsAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryItems
 * Created by HellFirePvP
 * Date: 01.06.2019 / 13:57
 */
public class RegistryItems {

    private static final List<ItemDynamicColor> colorItems = Lists.newArrayList();

    // DeferredHolder references for items
    public static DeferredHolder<Item, ItemAquamarine> AQUAMARINE;
    public static DeferredHolder<Item, ItemResonatingGem> RESONATING_GEM;
    public static DeferredHolder<Item, ItemGlassLens> GLASS_LENS;
    public static DeferredHolder<Item, ItemParchment> PARCHMENT;
    public static DeferredHolder<Item, ItemStarmetalIngot> STARMETAL_INGOT;
    public static DeferredHolder<Item, ItemStardust> STARDUST;
    public static DeferredHolder<Item, ItemPerkGemSky> PERK_GEM_SKY;
    public static DeferredHolder<Item, ItemPerkGemDay> PERK_GEM_DAY;
    public static DeferredHolder<Item, ItemPerkGemNight> PERK_GEM_NIGHT;
    public static DeferredHolder<Item, ItemCrystalAxe> CRYSTAL_AXE;
    public static DeferredHolder<Item, ItemCrystalPickaxe> CRYSTAL_PICKAXE;
    public static DeferredHolder<Item, ItemCrystalShovel> CRYSTAL_SHOVEL;
    public static DeferredHolder<Item, ItemCrystalSword> CRYSTAL_SWORD;
    public static DeferredHolder<Item, ItemInfusedCrystalAxe> INFUSED_CRYSTAL_AXE;
    public static DeferredHolder<Item, ItemInfusedCrystalPickaxe> INFUSED_CRYSTAL_PICKAXE;
    public static DeferredHolder<Item, ItemInfusedCrystalShovel> INFUSED_CRYSTAL_SHOVEL;
    public static DeferredHolder<Item, ItemInfusedCrystalSword> INFUSED_CRYSTAL_SWORD;
    public static DeferredHolder<Item, ItemTome> TOME;
    public static DeferredHolder<Item, ItemConstellationPaper> CONSTELLATION_PAPER;
    public static DeferredHolder<Item, ItemEnchantmentAmulet> ENCHANTMENT_AMULET;
    public static DeferredHolder<Item, ItemKnowledgeShare> KNOWLEDGE_SHARE;
    public static DeferredHolder<Item, ItemWand> WAND;
    public static DeferredHolder<Item, ItemChisel> CHISEL;
    public static DeferredHolder<Item, ItemResonator> RESONATOR;
    public static DeferredHolder<Item, ItemLinkingTool> LINKING_TOOL;
    public static DeferredHolder<Item, ItemIlluminationWand> ILLUMINATION_WAND;
    public static DeferredHolder<Item, ItemArchitectWand> ARCHITECT_WAND;
    public static DeferredHolder<Item, ItemExchangeWand> EXCHANGE_WAND;
    public static DeferredHolder<Item, ItemGrappleWand> GRAPPLE_WAND;
    public static DeferredHolder<Item, ItemBlinkWand> BLINK_WAND;
    public static DeferredHolder<Item, ItemHandTelescope> HAND_TELESCOPE;
    public static DeferredHolder<Item, ItemInfusedGlass> INFUSED_GLASS;
    public static DeferredHolder<Item, ItemMantle> MANTLE;
    public static DeferredHolder<Item, ItemPerkSeal> PERK_SEAL;
    public static DeferredHolder<Item, ItemNocturnalPowder> NOCTURNAL_POWDER;
    public static DeferredHolder<Item, ItemIlluminationPowder> ILLUMINATION_POWDER;
    public static DeferredHolder<Item, ItemShiftingStar> SHIFTING_STAR;
    public static DeferredHolder<Item, ItemShiftingStarAevitas> SHIFTING_STAR_AEVITAS;
    public static DeferredHolder<Item, ItemShiftingStarArmara> SHIFTING_STAR_ARMARA;
    public static DeferredHolder<Item, ItemShiftingStarDiscidia> SHIFTING_STAR_DISCIDIA;
    public static DeferredHolder<Item, ItemShiftingStarEvorsio> SHIFTING_STAR_EVORSIO;
    public static DeferredHolder<Item, ItemShiftingStarVicio> SHIFTING_STAR_VICIO;
    public static DeferredHolder<Item, ItemColoredLensFire> COLORED_LENS_FIRE;
    public static DeferredHolder<Item, ItemColoredLensBreak> COLORED_LENS_BREAK;
    public static DeferredHolder<Item, ItemColoredLensGrowth> COLORED_LENS_GROWTH;
    public static DeferredHolder<Item, ItemColoredLensDamage> COLORED_LENS_DAMAGE;
    public static DeferredHolder<Item, ItemColoredLensRegeneration> COLORED_LENS_REGENERATION;
    public static DeferredHolder<Item, ItemColoredLensPush> COLORED_LENS_PUSH;
    public static DeferredHolder<Item, ItemColoredLensSpectral> COLORED_LENS_SPECTRAL;
    public static DeferredHolder<Item, ItemRockCrystal> ROCK_CRYSTAL;
    public static DeferredHolder<Item, ItemAttunedRockCrystal> ATTUNED_ROCK_CRYSTAL;
    public static DeferredHolder<Item, ItemCelestialCrystal> CELESTIAL_CRYSTAL;
    public static DeferredHolder<Item, ItemAttunedCelestialCrystal> ATTUNED_CELESTIAL_CRYSTAL;

    private RegistryItems() {}

    public static void registerItems() {
        AQUAMARINE = ASRegistries.ITEMS.register("aquamarine", () -> new ItemAquamarine());
        RESONATING_GEM = ASRegistries.ITEMS.register("resonating_gem", () -> new ItemResonatingGem());
        GLASS_LENS = ASRegistries.ITEMS.register("glass_lens", () -> new ItemGlassLens());
        PARCHMENT = ASRegistries.ITEMS.register("parchment", () -> new ItemParchment());
        STARMETAL_INGOT = ASRegistries.ITEMS.register("starmetal_ingot", () -> new ItemStarmetalIngot());
        STARDUST = ASRegistries.ITEMS.register("stardust", () -> new ItemStardust());

        PERK_GEM_SKY = ASRegistries.ITEMS.register("perk_gem_sky", () -> new ItemPerkGemSky());
        PERK_GEM_DAY = ASRegistries.ITEMS.register("perk_gem_day", () -> new ItemPerkGemDay());
        PERK_GEM_NIGHT = ASRegistries.ITEMS.register("perk_gem_night", () -> new ItemPerkGemNight());
        CRYSTAL_AXE = ASRegistries.ITEMS.register("crystal_axe", () -> new ItemCrystalAxe());
        CRYSTAL_PICKAXE = ASRegistries.ITEMS.register("crystal_pickaxe", () -> new ItemCrystalPickaxe());
        CRYSTAL_SHOVEL = ASRegistries.ITEMS.register("crystal_shovel", () -> new ItemCrystalShovel());
        CRYSTAL_SWORD = ASRegistries.ITEMS.register("crystal_sword", () -> new ItemCrystalSword());
        INFUSED_CRYSTAL_AXE = ASRegistries.ITEMS.register("infused_crystal_axe", () -> new ItemInfusedCrystalAxe());
        INFUSED_CRYSTAL_PICKAXE = ASRegistries.ITEMS.register("infused_crystal_pickaxe", () -> new ItemInfusedCrystalPickaxe());
        INFUSED_CRYSTAL_SHOVEL = ASRegistries.ITEMS.register("infused_crystal_shovel", () -> new ItemInfusedCrystalShovel());
        INFUSED_CRYSTAL_SWORD = ASRegistries.ITEMS.register("infused_crystal_sword", () -> new ItemInfusedCrystalSword());

        TOME = ASRegistries.ITEMS.register("tome", () -> new ItemTome());
        CONSTELLATION_PAPER = ASRegistries.ITEMS.register("constellation_paper", () -> new ItemConstellationPaper());
        ENCHANTMENT_AMULET = ASRegistries.ITEMS.register("enchantment_amulet", () -> new ItemEnchantmentAmulet());
        KNOWLEDGE_SHARE = ASRegistries.ITEMS.register("knowledge_share", () -> new ItemKnowledgeShare());
        WAND = ASRegistries.ITEMS.register("wand", () -> new ItemWand());
        CHISEL = ASRegistries.ITEMS.register("chisel", () -> new ItemChisel());
        RESONATOR = ASRegistries.ITEMS.register("resonator", () -> new ItemResonator());
        LINKING_TOOL = ASRegistries.ITEMS.register("linking_tool", () -> new ItemLinkingTool());
        ILLUMINATION_WAND = ASRegistries.ITEMS.register("illumination_wand", () -> new ItemIlluminationWand());
        ARCHITECT_WAND = ASRegistries.ITEMS.register("architect_wand", () -> new ItemArchitectWand());
        EXCHANGE_WAND = ASRegistries.ITEMS.register("exchange_wand", () -> new ItemExchangeWand());
        GRAPPLE_WAND = ASRegistries.ITEMS.register("grapple_wand", () -> new ItemGrappleWand());
        BLINK_WAND = ASRegistries.ITEMS.register("blink_wand", () -> new ItemBlinkWand());
        HAND_TELESCOPE = ASRegistries.ITEMS.register("hand_telescope", () -> new ItemHandTelescope());
        INFUSED_GLASS = ASRegistries.ITEMS.register("infused_glass", () -> new ItemInfusedGlass());

        MANTLE = ASRegistries.ITEMS.register("mantle", () -> new ItemMantle());

        PERK_SEAL = ASRegistries.ITEMS.register("perk_seal", () -> new ItemPerkSeal());
        NOCTURNAL_POWDER = ASRegistries.ITEMS.register("nocturnal_powder", () -> new ItemNocturnalPowder());
        ILLUMINATION_POWDER = ASRegistries.ITEMS.register("illumination_powder", () -> new ItemIlluminationPowder());
        SHIFTING_STAR = ASRegistries.ITEMS.register("shifting_star", () -> new ItemShiftingStar());
        SHIFTING_STAR_AEVITAS = ASRegistries.ITEMS.register("shifting_star_aevitas", () -> new ItemShiftingStarAevitas());
        SHIFTING_STAR_ARMARA = ASRegistries.ITEMS.register("shifting_star_armara", () -> new ItemShiftingStarArmara());
        SHIFTING_STAR_DISCIDIA = ASRegistries.ITEMS.register("shifting_star_discidia", () -> new ItemShiftingStarDiscidia());
        SHIFTING_STAR_EVORSIO = ASRegistries.ITEMS.register("shifting_star_evorsio", () -> new ItemShiftingStarEvorsio());
        SHIFTING_STAR_VICIO = ASRegistries.ITEMS.register("shifting_star_vicio", () -> new ItemShiftingStarVicio());

        COLORED_LENS_FIRE = ASRegistries.ITEMS.register("colored_lens_fire", () -> new ItemColoredLensFire());
        COLORED_LENS_BREAK = ASRegistries.ITEMS.register("colored_lens_break", () -> new ItemColoredLensBreak());
        COLORED_LENS_GROWTH = ASRegistries.ITEMS.register("colored_lens_growth", () -> new ItemColoredLensGrowth());
        COLORED_LENS_DAMAGE = ASRegistries.ITEMS.register("colored_lens_damage", () -> new ItemColoredLensDamage());
        COLORED_LENS_REGENERATION = ASRegistries.ITEMS.register("colored_lens_regeneration", () -> new ItemColoredLensRegeneration());
        COLORED_LENS_PUSH = ASRegistries.ITEMS.register("colored_lens_push", () -> new ItemColoredLensPush());
        COLORED_LENS_SPECTRAL = ASRegistries.ITEMS.register("colored_lens_spectral", () -> new ItemColoredLensSpectral());

        ROCK_CRYSTAL = ASRegistries.ITEMS.register("rock_crystal", () -> new ItemRockCrystal());
        ATTUNED_ROCK_CRYSTAL = ASRegistries.ITEMS.register("attuned_rock_crystal", () -> new ItemAttunedRockCrystal());
        CELESTIAL_CRYSTAL = ASRegistries.ITEMS.register("celestial_crystal", () -> new ItemCelestialCrystal());
        ATTUNED_CELESTIAL_CRYSTAL = ASRegistries.ITEMS.register("attuned_celestial_crystal", () -> new ItemAttunedCelestialCrystal());
    }

    public static void registerItemBlocks() {
        // Item blocks are now registered automatically via DeferredRegister in RegistryBlocks
    }

    public static void registerFluidContainerItems() {
        // Fluid container items are now registered via DeferredRegister in RegistryFluids
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerColors(ColorHandlerEvent.Item itemColorEvent) {
        colorItems.forEach(item -> itemColorEvent.getItemColors().register(item::getColor, (Item) item));
    }

    public static void registerDispenseBehaviors() {
        DispenserBlock.registerBehavior(BUCKET_LIQUID_STARLIGHT.get(), FluidContainerDispenseBehavior.getInstance());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerItemProperties() {
        // Item properties are now registered via ClientRegistry.registerItemProperty
        // This method is kept for compatibility but properties should be registered elsewhere
    }

    private static void registerItemBlock(CustomItemBlock block) {
        // Item blocks are now automatically registered via DeferredRegister
        // This method is kept for compatibility
    }

    private static Item.Properties buildItemBlockProperties(Block block) {
        Item.Properties props = new Item.Properties();
        props.stacksTo(64);
        if (block instanceof CustomItemBlockProperties) {
            if (!((CustomItemBlockProperties) block).canItemBeRepaired()) {
                props.setNoRepair();
            }

            props.rarity(((CustomItemBlockProperties) block).getItemRarity());
            props.durability(((CustomItemBlockProperties) block).getItemMaxDamage());
            props.craftRemainder(((CustomItemBlockProperties) block).getContainerItem());
        }
        return props;
    }

}
