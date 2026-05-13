/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.event.handler;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.GuiType;
import hellfirepvp.astralsorcery.common.constellation.SkyHandler;
import hellfirepvp.astralsorcery.common.constellation.world.WorldContext;
import hellfirepvp.astralsorcery.common.effect.EffectDropModifier;
import hellfirepvp.astralsorcery.common.item.ItemTome;
import hellfirepvp.astralsorcery.common.item.crystal.ItemCrystalBase;
import hellfirepvp.astralsorcery.common.lib.CapabilitiesAS;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import net.minecraft.world.entity.AreaEffectCloudEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.LecternTileEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.neoforge.event.entity.EntityJoinWorldEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent;
import net.neoforged.neoforge.event.world.ChunkEvent;
import net.neoforged.bus.api.EventBus;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EventHandlerMisc
 * Created by HellFirePvP
 * Date: 23.02.2020 / 19:30
 */
//Event handler to fix/prevent lots of random stuff
public class EventHandlerMisc {

    public static void attachListeners(IEventBus bus) {
        bus.addListener(EventHandlerMisc::onSpawnEffectCloud);
        bus.addListener(EventHandlerMisc::onPlayerSleepEclipse);
        bus.addListener(EventHandlerMisc::onChunkLoad);
        bus.addListener(EventHandlerMisc::onLecternOpen);
        bus.addListener(EventHandlerMisc::onCrystalToss);
    }

    private static void onCrystalToss(ItemTossEvent event) {
        if (!event.getPlayer().level.isRemote()) {
            ItemStack thrown = event.getEntityItem().getItem();
            if (thrown.getItem() instanceof ItemCrystalBase) {
                event.getEntityItem().setThrowerId(event.getPlayer().getUniqueID());
            }
        }
    }

    private static void onLecternOpen(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isRemote()) {
            return;
        }
        LecternTileEntity lectern = MiscUtils.getTileAt(event.getWorld(), event.getPos(), LecternTileEntity.class, false);
        if (lectern != null) {
            ItemStack contained = lectern.getBook();
            if (contained.getItem() instanceof ItemTome) {
                event.setCanceled(true);
                AstralSorcery.getProxy().openGui(event.getPlayer(), GuiType.TOME);
            }
        }
    }

    private static void onChunkLoad(ChunkEvent.Load event) {
        IChunk ch = event.getChunk();
        if (ch instanceof Chunk && !event.getWorld().isRemote()) {
            ((Chunk) ch).getCapability(CapabilitiesAS.CHUNK_FLUID).ifPresent(entry -> {
                if (!entry.isInitialized()) {
                    IWorld w = event.getWorld();
                    if (w instanceof ServerLevel) {
                        long seed = ((ServerLevel) w).getSeed();
                        long chX = event.getChunk().getPos().x;
                        long chZ = event.getChunk().getPos().z;
                        seed ^= chX << 32;
                        seed ^= chZ;
                        entry.generate(seed);
                        ((Chunk) ch).markDirty();
                    }
                }
            });
        }
    }

    private static void onPlayerSleepEclipse(PlayerSleepInBedEvent event) {
        WorldContext ctx = SkyHandler.getContext(event.getEntityLiving().level);
        if (ctx != null && ctx.getCelestialEventHandler().getSolarEclipse().isActiveNow()) {
            if (event.getResultStatus() == null) {
                event.setResult(Player.SleepResult.NOT_POSSIBLE_NOW);
            }
        }
    }

    private static void onSpawnEffectCloud(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AreaEffectCloudEntity &&
                MiscUtils.contains(((AreaEffectCloudEntity) event.getEntity()).effects, effect -> effect.getPotion() instanceof EffectDropModifier)) {
            event.setCanceled(true);
        }
    }
}
