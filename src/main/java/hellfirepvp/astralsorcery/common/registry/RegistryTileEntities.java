/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.render.tile.*;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.tile.*;
import hellfirepvp.astralsorcery.common.tile.altar.TileAltar;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static hellfirepvp.astralsorcery.common.lib.BlockEntityTypesAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryTileEntities
 * Created by HellFirePvP
 * Date: 01.06.2019 / 13:35
 */
public class RegistryTileEntities {

    private RegistryTileEntities() {}

    public static void registerTiles() {
        SPECTRAL_RELAY = ASRegistries.BLOCK_ENTITY_TYPES.register("spectral_relay", 
                () -> BlockEntityType.Builder.of(TileSpectralRelay::new, BlocksAS.SPECTRAL_RELAY.get()).build(null));
        ALTAR = ASRegistries.BLOCK_ENTITY_TYPES.register("altar", 
                () -> BlockEntityType.Builder.of(TileAltar::new, 
                        BlocksAS.ALTAR_DISCOVERY.get(), BlocksAS.ALTAR_ATTUNEMENT.get(), 
                        BlocksAS.ALTAR_CONSTELLATION.get(), BlocksAS.ALTAR_RADIANCE.get()).build(null));
        ATTUNEMENT_ALTAR = ASRegistries.BLOCK_ENTITY_TYPES.register("attunement_altar", 
                () -> BlockEntityType.Builder.of(TileAttunementAltar::new, BlocksAS.ATTUNEMENT_ALTAR.get()).build(null));
        CELESTIAL_CRYSTAL_CLUSTER = ASRegistries.BLOCK_ENTITY_TYPES.register("celestial_crystal_cluster", 
                () -> BlockEntityType.Builder.of(TileCelestialCrystals::new, BlocksAS.CELESTIAL_CRYSTAL_CLUSTER.get()).build(null));
        GATEWAY = ASRegistries.BLOCK_ENTITY_TYPES.register("gateway", 
                () -> BlockEntityType.Builder.of(TileCelestialGateway::new, BlocksAS.GATEWAY.get()).build(null));
        CHALICE = ASRegistries.BLOCK_ENTITY_TYPES.register("chalice", 
                () -> BlockEntityType.Builder.of(TileChalice::new, BlocksAS.CHALICE.get()).build(null));
        COLLECTOR_CRYSTAL = ASRegistries.BLOCK_ENTITY_TYPES.register("collector_crystal", 
                () -> BlockEntityType.Builder.of(TileCollectorCrystal::new, 
                        BlocksAS.ROCK_COLLECTOR_CRYSTAL.get(), BlocksAS.CELESTIAL_COLLECTOR_CRYSTAL.get()).build(null));
        FOUNTAIN = ASRegistries.BLOCK_ENTITY_TYPES.register("fountain", 
                () -> BlockEntityType.Builder.of(TileFountain::new, BlocksAS.FOUNTAIN.get()).build(null));
        GEM_CRYSTAL_CLUSTER = ASRegistries.BLOCK_ENTITY_TYPES.register("gem_crystal_cluster", 
                () -> BlockEntityType.Builder.of(TileGemCrystals::new, BlocksAS.GEM_CRYSTAL_CLUSTER.get()).build(null));
        ILLUMINATOR = ASRegistries.BLOCK_ENTITY_TYPES.register("illuminator", 
                () -> BlockEntityType.Builder.of(TileIlluminator::new, BlocksAS.ILLUMINATOR.get()).build(null));
        INFUSER = ASRegistries.BLOCK_ENTITY_TYPES.register("infuser", 
                () -> BlockEntityType.Builder.of(TileInfuser::new, BlocksAS.INFUSER.get()).build(null));
        LENS = ASRegistries.BLOCK_ENTITY_TYPES.register("lens", 
                () -> BlockEntityType.Builder.of(TileLens::new, BlocksAS.LENS.get()).build(null));
        OBSERVATORY = ASRegistries.BLOCK_ENTITY_TYPES.register("observatory", 
                () -> BlockEntityType.Builder.of(TileObservatory::new, BlocksAS.OBSERVATORY.get()).build(null));
        PRISM = ASRegistries.BLOCK_ENTITY_TYPES.register("prism", 
                () -> BlockEntityType.Builder.of(TilePrism::new, BlocksAS.PRISM.get()).build(null));
        REFRACTION_TABLE = ASRegistries.BLOCK_ENTITY_TYPES.register("refraction_table", 
                () -> BlockEntityType.Builder.of(TileRefractionTable::new, BlocksAS.REFRACTION_TABLE.get()).build(null));
        RITUAL_LINK = ASRegistries.BLOCK_ENTITY_TYPES.register("ritual_link", 
                () -> BlockEntityType.Builder.of(TileRitualLink::new, BlocksAS.RITUAL_LINK.get()).build(null));
        RITUAL_PEDESTAL = ASRegistries.BLOCK_ENTITY_TYPES.register("ritual_pedestal", 
                () -> BlockEntityType.Builder.of(TileRitualPedestal::new, BlocksAS.RITUAL_PEDESTAL.get()).build(null));
        TELESCOPE = ASRegistries.BLOCK_ENTITY_TYPES.register("telescope", 
                () -> BlockEntityType.Builder.of(TileTelescope::new, BlocksAS.TELESCOPE.get()).build(null));
        TRANSLUCENT_BLOCK = ASRegistries.BLOCK_ENTITY_TYPES.register("translucent_block", 
                () -> BlockEntityType.Builder.of(TileTranslucentBlock::new, BlocksAS.TRANSLUCENT_BLOCK.get()).build(null));
        TREE_BEACON = ASRegistries.BLOCK_ENTITY_TYPES.register("tree_beacon", 
                () -> BlockEntityType.Builder.of(TileTreeBeacon::new, BlocksAS.TREE_BEACON.get()).build(null));
        TREE_BEACON_COMPONENT = ASRegistries.BLOCK_ENTITY_TYPES.register("tree_beacon_component", 
                () -> BlockEntityType.Builder.of(TileTreeBeaconComponent::new, BlocksAS.TREE_BEACON_COMPONENT.get()).build(null));
        VANISHING = ASRegistries.BLOCK_ENTITY_TYPES.register("vanishing", 
                () -> BlockEntityType.Builder.of(TileVanishing::new, BlocksAS.VANISHING.get()).build(null));
        WELL = ASRegistries.BLOCK_ENTITY_TYPES.register("well", 
                () -> BlockEntityType.Builder.of(TileWell::new, BlocksAS.WELL.get()).build(null));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initClient() {
        // Block entity rendering registration moved to client setup event
    }
}
