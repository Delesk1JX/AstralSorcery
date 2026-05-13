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
import hellfirepvp.astralsorcery.common.block.base.BlockDynamicColor;
import hellfirepvp.astralsorcery.common.block.base.CustomItemBlock;
import hellfirepvp.astralsorcery.common.block.base.template.BlockSlabTemplate;
import hellfirepvp.astralsorcery.common.block.base.template.BlockStairsTemplate;
import hellfirepvp.astralsorcery.common.block.blackmarble.*;
import hellfirepvp.astralsorcery.common.block.foliage.BlockGlowFlower;
import hellfirepvp.astralsorcery.common.block.infusedwood.*;
import hellfirepvp.astralsorcery.common.block.marble.*;
import hellfirepvp.astralsorcery.common.block.ore.BlockAquamarineSandOre;
import hellfirepvp.astralsorcery.common.block.ore.BlockRockCrystalOre;
import hellfirepvp.astralsorcery.common.block.ore.BlockStarmetal;
import hellfirepvp.astralsorcery.common.block.ore.BlockStarmetalOre;
import hellfirepvp.astralsorcery.common.block.tile.*;
import hellfirepvp.astralsorcery.common.block.tile.altar.BlockAltarAttunement;
import hellfirepvp.astralsorcery.common.block.tile.altar.BlockAltarConstellation;
import hellfirepvp.astralsorcery.common.block.tile.altar.BlockAltarDiscovery;
import hellfirepvp.astralsorcery.common.block.tile.altar.BlockAltarRadiance;
import hellfirepvp.astralsorcery.common.block.tile.crystal.BlockCelestialCollectorCrystal;
import hellfirepvp.astralsorcery.common.block.tile.crystal.BlockRockCollectorCrystal;
import hellfirepvp.astralsorcery.common.block.tile.fountain.BlockFountainPrimeLiquid;
import hellfirepvp.astralsorcery.common.block.tile.fountain.BlockFountainPrimeOre;
import hellfirepvp.astralsorcery.common.block.tile.fountain.BlockFountainPrimeVortex;
import hellfirepvp.astralsorcery.common.util.NameUtil;
import net.minecraft.world.level().block.Block;
import net.minecraft.world.level().block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ColorHandlerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.LinkedList;
import java.util.List;

import static hellfirepvp.astralsorcery.common.lib.BlocksAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryBlocks
 * Created by HellFirePvP
 * Date: 31.05.2019 / 21:44
 */
public class RegistryBlocks {

    private static final List<BlockDynamicColor> COLOR_BLOCKS = Lists.newArrayList();
    static final List<CustomItemBlock> ITEM_BLOCKS = new LinkedList<>();

    // DeferredHolder references for blocks
    public static DeferredHolder<Block, BlockMarbleArch> MARBLE_ARCH;
    public static DeferredHolder<Block, BlockMarbleBricks> MARBLE_BRICKS;
    public static DeferredHolder<Block, BlockMarbleChiseled> MARBLE_CHISELED;
    public static DeferredHolder<Block, BlockMarbleEngraved> MARBLE_ENGRAVED;
    public static DeferredHolder<Block, BlockMarblePillar> MARBLE_PILLAR;
    public static DeferredHolder<Block, BlockMarbleRaw> MARBLE_RAW;
    public static DeferredHolder<Block, BlockMarbleRuned> MARBLE_RUNED;
    public static DeferredHolder<Block, BlockStairsTemplate> MARBLE_STAIRS;
    public static DeferredHolder<Block, BlockSlabTemplate> MARBLE_SLAB;
    public static DeferredHolder<Block, BlockBlackMarbleArch> BLACK_MARBLE_ARCH;
    public static DeferredHolder<Block, BlockBlackMarbleBricks> BLACK_MARBLE_BRICKS;
    public static DeferredHolder<Block, BlockBlackMarbleChiseled> BLACK_MARBLE_CHISELED;
    public static DeferredHolder<Block, BlockBlackMarbleEngraved> BLACK_MARBLE_ENGRAVED;
    public static DeferredHolder<Block, BlockBlackMarblePillar> BLACK_MARBLE_PILLAR;
    public static DeferredHolder<Block, BlockBlackMarbleRaw> BLACK_MARBLE_RAW;
    public static DeferredHolder<Block, BlockBlackMarbleRuned> BLACK_MARBLE_RUNED;
    public static DeferredHolder<Block, BlockStairsTemplate> BLACK_MARBLE_STAIRS;
    public static DeferredHolder<Block, BlockSlabTemplate> BLACK_MARBLE_SLAB;
    public static DeferredHolder<Block, BlockInfusedWood> INFUSED_WOOD;
    public static DeferredHolder<Block, BlockInfusedWoodArch> INFUSED_WOOD_ARCH;
    public static DeferredHolder<Block, BlockInfusedWoodColumn> INFUSED_WOOD_COLUMN;
    public static DeferredHolder<Block, BlockInfusedWoodEngraved> INFUSED_WOOD_ENGRAVED;
    public static DeferredHolder<Block, BlockInfusedWoodEnriched> INFUSED_WOOD_ENRICHED;
    public static DeferredHolder<Block, BlockInfusedWoodInfused> INFUSED_WOOD_INFUSED;
    public static DeferredHolder<Block, BlockInfusedWoodPlanks> INFUSED_WOOD_PLANKS;
    public static DeferredHolder<Block, BlockStairsTemplate> INFUSED_WOOD_STAIRS;
    public static DeferredHolder<Block, BlockSlabTemplate> INFUSED_WOOD_SLAB;
    public static DeferredHolder<Block, BlockAquamarineSandOre> AQUAMARINE_SAND_ORE;
    public static DeferredHolder<Block, BlockRockCrystalOre> ROCK_CRYSTAL_ORE;
    public static DeferredHolder<Block, BlockStarmetalOre> STARMETAL_ORE;
    public static DeferredHolder<Block, BlockStarmetal> STARMETAL;
    public static DeferredHolder<Block, BlockGlowFlower> GLOW_FLOWER;
    public static DeferredHolder<Block, BlockSpectralRelay> SPECTRAL_RELAY;
    public static DeferredHolder<Block, BlockAltarDiscovery> ALTAR_DISCOVERY;
    public static DeferredHolder<Block, BlockAltarAttunement> ALTAR_ATTUNEMENT;
    public static DeferredHolder<Block, BlockAltarConstellation> ALTAR_CONSTELLATION;
    public static DeferredHolder<Block, BlockAltarRadiance> ALTAR_RADIANCE;
    public static DeferredHolder<Block, BlockAttunementAltar> ATTUNEMENT_ALTAR;
    public static DeferredHolder<Block, BlockCelestialCrystalCluster> CELESTIAL_CRYSTAL_CLUSTER;
    public static DeferredHolder<Block, BlockGemCrystalCluster> GEM_CRYSTAL_CLUSTER;
    public static DeferredHolder<Block, BlockRockCollectorCrystal> ROCK_COLLECTOR_CRYSTAL;
    public static DeferredHolder<Block, BlockCelestialCollectorCrystal> CELESTIAL_COLLECTOR_CRYSTAL;
    public static DeferredHolder<Block, BlockLens> LENS;
    public static DeferredHolder<Block, BlockPrism> PRISM;
    public static DeferredHolder<Block, BlockRitualLink> RITUAL_LINK;
    public static DeferredHolder<Block, BlockRitualPedestal> RITUAL_PEDESTAL;
    public static DeferredHolder<Block, BlockInfuser> INFUSER;
    public static DeferredHolder<Block, BlockChalice> CHALICE;
    public static DeferredHolder<Block, BlockWell> WELL;
    public static DeferredHolder<Block, BlockIlluminator> ILLUMINATOR;
    public static DeferredHolder<Block, BlockTelescope> TELESCOPE;
    public static DeferredHolder<Block, BlockObservatory> OBSERVATORY;
    public static DeferredHolder<Block, BlockRefractionTable> REFRACTION_TABLE;
    public static DeferredHolder<Block, BlockTreeBeacon> TREE_BEACON;
    public static DeferredHolder<Block, BlockTreeBeaconComponent> TREE_BEACON_COMPONENT;
    public static DeferredHolder<Block, BlockCelestialGateway> GATEWAY;
    public static DeferredHolder<Block, BlockFountain> FOUNTAIN;
    public static DeferredHolder<Block, BlockFountainPrimeLiquid> FOUNTAIN_PRIME_LIQUID;
    public static DeferredHolder<Block, BlockFountainPrimeVortex> FOUNTAIN_PRIME_VORTEX;
    public static DeferredHolder<Block, BlockFountainPrimeOre> FOUNTAIN_PRIME_ORE;
    public static DeferredHolder<Block, BlockFlareLight> FLARE_LIGHT;
    public static DeferredHolder<Block, BlockTranslucentBlock> TRANSLUCENT_BLOCK;
    public static DeferredHolder<Block, BlockVanishing> VANISHING;
    public static DeferredHolder<Block, BlockStructural> STRUCTURAL;

    private RegistryBlocks() {}

    public static void registerBlocks() {
        MARBLE_ARCH = ASRegistries.BLOCKS.register("marble_arch", () -> new BlockMarbleArch());
        MARBLE_BRICKS = ASRegistries.BLOCKS.register("marble_bricks", () -> new BlockMarbleBricks());
        MARBLE_CHISELED = ASRegistries.BLOCKS.register("marble_chiseled", () -> new BlockMarbleChiseled());
        MARBLE_ENGRAVED = ASRegistries.BLOCKS.register("marble_engraved", () -> new BlockMarbleEngraved());
        MARBLE_PILLAR = ASRegistries.BLOCKS.register("marble_pillar", () -> new BlockMarblePillar());
        MARBLE_RAW = ASRegistries.BLOCKS.register("marble_raw", () -> new BlockMarbleRaw());
        MARBLE_RUNED = ASRegistries.BLOCKS.register("marble_runed", () -> new BlockMarbleRuned());
        MARBLE_STAIRS = ASRegistries.BLOCKS.register("marble_stairs", () -> makeStairs(MARBLE_BRICKS.get().defaultBlockState(), "marble_stairs"));
        MARBLE_SLAB = ASRegistries.BLOCKS.register("marble_slab", () -> makeSlab(MARBLE_BRICKS.get().defaultBlockState(), "marble_slab"));
        BLACK_MARBLE_ARCH = ASRegistries.BLOCKS.register("black_marble_arch", () -> new BlockBlackMarbleArch());
        BLACK_MARBLE_BRICKS = ASRegistries.BLOCKS.register("black_marble_bricks", () -> new BlockBlackMarbleBricks());
        BLACK_MARBLE_CHISELED = ASRegistries.BLOCKS.register("black_marble_chiseled", () -> new BlockBlackMarbleChiseled());
        BLACK_MARBLE_ENGRAVED = ASRegistries.BLOCKS.register("black_marble_engraved", () -> new BlockBlackMarbleEngraved());
        BLACK_MARBLE_PILLAR = ASRegistries.BLOCKS.register("black_marble_pillar", () -> new BlockBlackMarblePillar());
        BLACK_MARBLE_RAW = ASRegistries.BLOCKS.register("black_marble_raw", () -> new BlockBlackMarbleRaw());
        BLACK_MARBLE_RUNED = ASRegistries.BLOCKS.register("black_marble_runed", () -> new BlockBlackMarbleRuned());
        BLACK_MARBLE_STAIRS = ASRegistries.BLOCKS.register("black_marble_stairs", () -> makeStairs(BLACK_MARBLE_BRICKS.get().defaultBlockState(), "black_marble_stairs"));
        BLACK_MARBLE_SLAB = ASRegistries.BLOCKS.register("black_marble_slab", () -> makeSlab(BLACK_MARBLE_BRICKS.get().defaultBlockState(), "black_marble_slab"));
        INFUSED_WOOD = ASRegistries.BLOCKS.register("infused_wood", () -> new BlockInfusedWood());
        INFUSED_WOOD_ARCH = ASRegistries.BLOCKS.register("infused_wood_arch", () -> new BlockInfusedWoodArch());
        INFUSED_WOOD_COLUMN = ASRegistries.BLOCKS.register("infused_wood_column", () -> new BlockInfusedWoodColumn());
        INFUSED_WOOD_ENGRAVED = ASRegistries.BLOCKS.register("infused_wood_engraved", () -> new BlockInfusedWoodEngraved());
        INFUSED_WOOD_ENRICHED = ASRegistries.BLOCKS.register("infused_wood_enriched", () -> new BlockInfusedWoodEnriched());
        INFUSED_WOOD_INFUSED = ASRegistries.BLOCKS.register("infused_wood_infused", () -> new BlockInfusedWoodInfused());
        INFUSED_WOOD_PLANKS = ASRegistries.BLOCKS.register("infused_wood_planks", () -> new BlockInfusedWoodPlanks());
        INFUSED_WOOD_STAIRS = ASRegistries.BLOCKS.register("infused_wood_stairs", () -> makeStairs(INFUSED_WOOD_PLANKS.get().defaultBlockState(), "infused_wood_stairs"));
        INFUSED_WOOD_SLAB = ASRegistries.BLOCKS.register("infused_wood_slab", () -> makeSlab(INFUSED_WOOD_PLANKS.get().defaultBlockState(), "infused_wood_slab"));

        AQUAMARINE_SAND_ORE = ASRegistries.BLOCKS.register("aquamarine_sand_ore", () -> new BlockAquamarineSandOre());
        ROCK_CRYSTAL_ORE = ASRegistries.BLOCKS.register("rock_crystal_ore", () -> new BlockRockCrystalOre());
        STARMETAL_ORE = ASRegistries.BLOCKS.register("starmetal_ore", () -> new BlockStarmetalOre());
        STARMETAL = ASRegistries.BLOCKS.register("starmetal", () -> new BlockStarmetal());
        GLOW_FLOWER = ASRegistries.BLOCKS.register("glow_flower", () -> new BlockGlowFlower());

        SPECTRAL_RELAY = ASRegistries.BLOCKS.register("spectral_relay", () -> new BlockSpectralRelay());
        ALTAR_DISCOVERY = ASRegistries.BLOCKS.register("altar_discovery", () -> new BlockAltarDiscovery());
        ALTAR_ATTUNEMENT = ASRegistries.BLOCKS.register("altar_attunement", () -> new BlockAltarAttunement());
        ALTAR_CONSTELLATION = ASRegistries.BLOCKS.register("altar_constellation", () -> new BlockAltarConstellation());
        ALTAR_RADIANCE = ASRegistries.BLOCKS.register("altar_radiance", () -> new BlockAltarRadiance());
        ATTUNEMENT_ALTAR = ASRegistries.BLOCKS.register("attunement_altar", () -> new BlockAttunementAltar());
        CELESTIAL_CRYSTAL_CLUSTER = ASRegistries.BLOCKS.register("celestial_crystal_cluster", () -> new BlockCelestialCrystalCluster());
        GEM_CRYSTAL_CLUSTER = ASRegistries.BLOCKS.register("gem_crystal_cluster", () -> new BlockGemCrystalCluster());
        ROCK_COLLECTOR_CRYSTAL = ASRegistries.BLOCKS.register("rock_collector_crystal", () -> new BlockRockCollectorCrystal());
        CELESTIAL_COLLECTOR_CRYSTAL = ASRegistries.BLOCKS.register("celestial_collector_crystal", () -> new BlockCelestialCollectorCrystal());
        LENS = ASRegistries.BLOCKS.register("lens", () -> new BlockLens());
        PRISM = ASRegistries.BLOCKS.register("prism", () -> new BlockPrism());
        RITUAL_LINK = ASRegistries.BLOCKS.register("ritual_link", () -> new BlockRitualLink());
        RITUAL_PEDESTAL = ASRegistries.BLOCKS.register("ritual_pedestal", () -> new BlockRitualPedestal());
        INFUSER = ASRegistries.BLOCKS.register("infuser", () -> new BlockInfuser());
        CHALICE = ASRegistries.BLOCKS.register("chalice", () -> new BlockChalice());
        WELL = ASRegistries.BLOCKS.register("well", () -> new BlockWell());
        ILLUMINATOR = ASRegistries.BLOCKS.register("illuminator", () -> new BlockIlluminator());
        TELESCOPE = ASRegistries.BLOCKS.register("telescope", () -> new BlockTelescope());
        OBSERVATORY = ASRegistries.BLOCKS.register("observatory", () -> new BlockObservatory());
        REFRACTION_TABLE = ASRegistries.BLOCKS.register("refraction_table", () -> new BlockRefractionTable());
        TREE_BEACON = ASRegistries.BLOCKS.register("tree_beacon", () -> new BlockTreeBeacon());
        TREE_BEACON_COMPONENT = ASRegistries.BLOCKS.register("tree_beacon_component", () -> new BlockTreeBeaconComponent());
        GATEWAY = ASRegistries.BLOCKS.register("gateway", () -> new BlockCelestialGateway());
        FOUNTAIN = ASRegistries.BLOCKS.register("fountain", () -> new BlockFountain());
        FOUNTAIN_PRIME_LIQUID = ASRegistries.BLOCKS.register("fountain_prime_liquid", () -> new BlockFountainPrimeLiquid());
        FOUNTAIN_PRIME_VORTEX = ASRegistries.BLOCKS.register("fountain_prime_vortex", () -> new BlockFountainPrimeVortex());
        FOUNTAIN_PRIME_ORE = ASRegistries.BLOCKS.register("fountain_prime_ore", () -> new BlockFountainPrimeOre());

        FLARE_LIGHT = ASRegistries.BLOCKS.register("flare_light", () -> new BlockFlareLight());
        TRANSLUCENT_BLOCK = ASRegistries.BLOCKS.register("translucent_block", () -> new BlockTranslucentBlock());
        VANISHING = ASRegistries.BLOCKS.register("vanishing", () -> new BlockVanishing());
        STRUCTURAL = ASRegistries.BLOCKS.register("structural", () -> new BlockStructural());
    }

    public static void registerFluidBlocks() {
        // Fluid blocks are now registered via DeferredRegister in RegistryFluids
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerColors(ColorHandlerEvent.Block blockColorEvent) {
        COLOR_BLOCKS.forEach(block -> blockColorEvent.getBlockColors().register(block::getColor, (Block) block));
    }

    private static BlockSlabTemplate makeSlab(BlockState base, String name) {
        return new BlockSlabTemplate(base, Block.Properties.ofFullCopy(base.getBlock()));
    }

    private static BlockStairsTemplate makeStairs(BlockState base, String name) {
        return new BlockStairsTemplate(base, Block.Properties.ofFullCopy(base.getBlock()));
    }
}
