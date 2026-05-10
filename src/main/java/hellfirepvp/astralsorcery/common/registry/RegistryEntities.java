/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntityEmpty;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntityGrapplingHook;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntityItemHighlighted;
import hellfirepvp.astralsorcery.client.render.entity.RenderEntitySpectralTool;
import hellfirepvp.astralsorcery.common.entity.EntityFlare;
import hellfirepvp.astralsorcery.common.entity.EntityIlluminationSpark;
import hellfirepvp.astralsorcery.common.entity.EntityNocturnalSpark;
import hellfirepvp.astralsorcery.common.entity.EntitySpectralTool;
import hellfirepvp.astralsorcery.common.entity.item.EntityCrystal;
import hellfirepvp.astralsorcery.common.entity.item.EntityItemExplosionResistant;
import hellfirepvp.astralsorcery.common.entity.item.EntityItemHighlighted;
import hellfirepvp.astralsorcery.common.entity.item.EntityStarmetal;
import hellfirepvp.astralsorcery.common.entity.technical.EntityGrapplingHook;
import hellfirepvp.astralsorcery.common.entity.technical.EntityObservatoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static hellfirepvp.astralsorcery.common.lib.EntityTypesAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryEntities
 * Created by HellFirePvP
 * Date: 17.08.2019 / 08:47
 */
@Mod.EventBusSubscriber(modid = AstralSorcery.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEntities {

    private RegistryEntities() {}

    public static void init() {
        NOCTURNAL_SPARK = ASRegistries.ENTITY_TYPES.register("nocturnal_spark", 
                () -> EntityType.Builder.of(EntityNocturnalSpark::new, MobCategory.MISC)
                        .sized(0.1F, 0.1F)
                        .clientTrackingRange(32)
                        .updateInterval(1)
                        .build(AstralSorcery.key("nocturnal_spark").toString()));
        ILLUMINATION_SPARK = ASRegistries.ENTITY_TYPES.register("illumination_spark", 
                () -> EntityType.Builder.of(EntityIlluminationSpark::new, MobCategory.MISC)
                        .sized(0.1F, 0.1F)
                        .clientTrackingRange(32)
                        .updateInterval(1)
                        .build(AstralSorcery.key("illumination_spark").toString()));
        FLARE = ASRegistries.ENTITY_TYPES.register("flare", 
                () -> EntityType.Builder.of(EntityFlare::new, MobCategory.MISC)
                        .sized(0.4F, 0.4F)
                        .fireImmune()
                        .clientTrackingRange(64)
                        .updateInterval(1)
                        .build(AstralSorcery.key("flare").toString()));
        SPECTRAL_TOOL = ASRegistries.ENTITY_TYPES.register("spectral_tool", 
                () -> EntityType.Builder.of(EntitySpectralTool::new, MobCategory.MISC)
                        .sized(0.6F, 0.8F)
                        .clientTrackingRange(32)
                        .updateInterval(1)
                        .build(AstralSorcery.key("spectral_tool").toString()));

        ITEM_HIGHLIGHT = ASRegistries.ENTITY_TYPES.register("item_highlighted", 
                () -> EntityType.Builder.of(EntityItemHighlighted::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F)
                        .clientTrackingRange(16)
                        .updateInterval(1)
                        .build(AstralSorcery.key("item_highlighted").toString()));
        ITEM_EXPLOSION_RESISTANT = ASRegistries.ENTITY_TYPES.register("item_explosion_resistant", 
                () -> EntityType.Builder.of(EntityItemExplosionResistant::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F)
                        .clientTrackingRange(16)
                        .updateInterval(1)
                        .build(AstralSorcery.key("item_explosion_resistant").toString()));
        ITEM_CRYSTAL = ASRegistries.ENTITY_TYPES.register("item_crystal", 
                () -> EntityType.Builder.of(EntityCrystal::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(16)
                        .updateInterval(1)
                        .build(AstralSorcery.key("item_crystal").toString()));
        ITEM_STARMETAL_INGOT = ASRegistries.ENTITY_TYPES.register("item_starmetal", 
                () -> EntityType.Builder.of(EntityStarmetal::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(16)
                        .updateInterval(1)
                        .build(AstralSorcery.key("item_starmetal").toString()));
        OBSERVATORY_HELPER = ASRegistries.ENTITY_TYPES.register("observatory_helper", 
                () -> EntityType.Builder.of(EntityObservatoryHelper::new, MobCategory.MISC)
                        .sized(0, 0)
                        .fireImmune()
                        .clientTrackingRange(64)
                        .updateInterval(1)
                        .build(AstralSorcery.key("observatory_helper").toString()));
        GRAPPLING_HOOK = ASRegistries.ENTITY_TYPES.register("grappling_hook", 
                () -> EntityType.Builder.of(EntityGrapplingHook::new, MobCategory.MISC)
                        .sized(0.1F, 0.1F)
                        .fireImmune()
                        .clientTrackingRange(64)
                        .updateInterval(1)
                        .build(AstralSorcery.key("grappling_hook").toString()));
    }

    @SubscribeEvent
    public static void initAttributes(EntityAttributeCreationEvent event) {
        event.put(FLARE.get(), EntityFlare.createAttributes().build());
        event.put(SPECTRAL_TOOL.get(), EntitySpectralTool.createAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    public static void initClient() {
        // Entity rendering registration moved to client setup event
    }
}
