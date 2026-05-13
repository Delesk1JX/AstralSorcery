/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.astralsorcery.common.constellation.effect.ConstellationEffectProvider;
import hellfirepvp.astralsorcery.common.constellation.engraving.EngravingEffect;
import hellfirepvp.astralsorcery.common.constellation.mantle.MantleEffect;
import hellfirepvp.astralsorcery.common.crafting.recipe.altar.effect.AltarRecipeEffect;
import hellfirepvp.astralsorcery.common.crystal.CrystalProperty;
import hellfirepvp.astralsorcery.common.crystal.calc.PropertyUsage;
import hellfirepvp.astralsorcery.common.perk.PerkConverter;
import hellfirepvp.astralsorcery.common.perk.modifier.PerkAttributeModifier;
import hellfirepvp.astralsorcery.common.perk.reader.PerkAttributeReader;
import hellfirepvp.astralsorcery.common.perk.type.PerkAttributeType;
import hellfirepvp.astralsorcery.common.structure.types.StructureType;
import hellfirepvp.observerlib.api.structure.MatchableStructure;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level().block.entity.BlockEntityType;
import net.minecraft.world.level().material.Fluid;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central registry class for all Astral Sorcery registries using DeferredRegister
 */
public class ASRegistries {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AstralSorcery.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AstralSorcery.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(AstralSorcery.MODID, "fluids");
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AstralSorcery.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AstralSorcery.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AstralSorcery.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT, AstralSorcery.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AstralSorcery.MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, AstralSorcery.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, AstralSorcery.MODID);
    
    // Custom registries
    public static final DeferredRegister<IConstellation> CONSTELLATIONS = createCustomRegistry("constellations");
    public static final DeferredRegister<ConstellationEffectProvider> CONSTELLATION_EFFECTS = createCustomRegistry("constellation_effects");
    public static final DeferredRegister<MantleEffect> MANTLE_EFFECTS = createCustomRegistry("mantle_effects");
    public static final DeferredRegister<EngravingEffect> ENGRAVING_EFFECTS = createCustomRegistry("engraving_effects");
    public static final DeferredRegister<PerkAttributeType> PERK_ATTRIBUTE_TYPES = createCustomRegistry("perk_attribute_types");
    public static final DeferredRegister<PerkConverter> PERK_CONVERTERS = createCustomRegistry("perk_converters");
    public static final DeferredRegister<PerkAttributeModifier> PERK_ATTRIBUTE_MODIFIERS = createCustomRegistry("perk_attribute_modifiers");
    public static final DeferredRegister<PerkAttributeReader> PERK_ATTRIBUTE_READERS = createCustomRegistry("perk_attribute_readers");
    public static final DeferredRegister<CrystalProperty> CRYSTAL_PROPERTIES = createCustomRegistry("crystal_properties");
    public static final DeferredRegister<PropertyUsage> CRYSTAL_PROPERTY_USAGES = createCustomRegistry("crystal_property_usages");
    public static final DeferredRegister<AltarRecipeEffect> ALTAR_RECIPE_EFFECTS = createCustomRegistry("altar_recipe_effects");
    public static final DeferredRegister<StructureType> STRUCTURE_TYPES = createCustomRegistry("structure_types");
    public static final DeferredRegister<MatchableStructure> MATCHABLE_STRUCTURES = createCustomRegistry("matchable_structures");
    
    private static <T> DeferredRegister<T> createCustomRegistry(String name) {
        return DeferredRegister.create(AstralSorcery.MODID, name);
    }
    
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        EFFECTS.register(modEventBus);
        ENCHANTMENTS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        MENU_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        
        CONSTELLATIONS.register(modEventBus);
        CONSTELLATION_EFFECTS.register(modEventBus);
        MANTLE_EFFECTS.register(modEventBus);
        ENGRAVING_EFFECTS.register(modEventBus);
        PERK_ATTRIBUTE_TYPES.register(modEventBus);
        PERK_CONVERTERS.register(modEventBus);
        PERK_ATTRIBUTE_MODIFIERS.register(modEventBus);
        PERK_ATTRIBUTE_READERS.register(modEventBus);
        CRYSTAL_PROPERTIES.register(modEventBus);
        CRYSTAL_PROPERTY_USAGES.register(modEventBus);
        ALTAR_RECIPE_EFFECTS.register(modEventBus);
        STRUCTURE_TYPES.register(modEventBus);
        MATCHABLE_STRUCTURES.register(modEventBus);
    }
}
