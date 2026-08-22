/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.entity;

import com.google.common.base.Predicate;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnSettings;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ForgeHooks;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.eventbus.api.Event;
import net.neoforged.fml.LogicalSide;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: EntityUtils
 * Created by HellFirePvP
 * Date: 27.05.2019 / 22:26
 */
public class EntityUtils {

    private static final Random rand = new Random();

    @Nullable
    public static Player getPlayer(UUID playerUUID, LogicalSide side) {
        return side.isClient() ? getPlayerClient(playerUUID) : getPlayerServer(playerUUID);
    }

    @Nullable
    public static Player getPlayerServer(UUID playerUUID) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        if (server == null) {
            return null;
        }
        return server.getPlayerList().getPlayerByUUID(playerUUID);
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static Player getPlayerClient(UUID playerUUID) {
        ClientWorld clWorld = Minecraft.getInstance().level;
        if (clWorld == null) {
            return null;
        }
        return clWorld.getPlayerByUuid(playerUUID);
    }

    public static void applyPotionEffectAtHalf(LivingEntity entity, MobEffectInstance effect) {
        MobEffectInstance activeEffect = entity.getEffect(effect.getEffect());
        if (activeEffect != null) {
            if (activeEffect.getDuration() <= effect.getDuration() / 2) {
                entity.addEffect(effect);
            }
        } else {
            entity.addEffect(effect);
        }
    }

    public static void applyVortexMotion(Supplier<Vector3> positionSupplier, Consumer<Vector3> addMotion, Vector3 to, double vortexRange, double multiplier) {
        Vector3 pos = positionSupplier.get();
        double diffX = (to.getX() - pos.getX()) / vortexRange;
        double diffY = (to.getY() - pos.getY()) / vortexRange;
        double diffZ = (to.getZ() - pos.getZ()) / vortexRange;
        double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
        if (1.0D - dist > 0.0D) {
            double dstFactorSq = (1.0D - dist) * (1.0D - dist);
            Vector3 toAdd = new Vector3();
            toAdd.setX(diffX / dist * dstFactorSq * 0.15D * multiplier);
            toAdd.setY(diffY / dist * dstFactorSq * 0.15D * multiplier);
            toAdd.setZ(diffZ / dist * dstFactorSq * 0.15D * multiplier);
            addMotion.accept(toAdd);
        }
    }

    @Nullable
    public static LivingEntity performWorldSpawningAt(ServerLevel world, BlockPos pos, MobCategory category, SpawnReason reason, boolean ignoreWeighting, int ignoreSpawnCheckFlags) {
        Biome b = world.getBiome(pos).value();
        List<MobSpawnSettings.SpawnerData> spawnList = new LinkedList<>(b.getSpawners(category));
        spawnList = EventHooks.getPotentialSpawns(world, category, pos, spawnList);
        spawnList.removeIf(s -> !s.type.isSummonable());
        MobSpawnSettings.SpawnerData entry;
        if (ignoreWeighting) {
            entry = MiscUtils.getRandomEntry(spawnList, rand);
        } else {
            entry = MiscUtils.getWeightedRandomEntry(spawnList, rand, ee -> ee.weight);
        }

        if (entry != null) {
            float x = pos.getX() + 0.5F;
            float y = pos.getY();
            float z = pos.getZ() + 0.5F;

            BlockState state = world.getBlockState(pos);
            if (!state.isNormalCube() && canEntitySpawnHere(world, pos, entry.type, reason, ignoreSpawnCheckFlags, null)) {
                MobEntity entity;
                try {
                    entity = (MobEntity) entry.type.create(world);
                } catch (Exception exception) {
                    return null;
                }
                if (entity == null) {
                    return null;
                }

                entity.setPos(x, y, z);
                entity.yRot = rand.nextFloat() * 360F;
                entity.xRot = 0F;
                int result = ForgeHooks.canEntitySpawn(entity, world, x, y, z, null, reason); //We already did the default test before.
                if (result == -1) {
                    return null;
                }

                if (!EventHooks.doSpecialSpawn(entity, world, x, y, z, null, reason)) {
                    entity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), reason, null, null);
                }

                world.addFreshEntity(entity);
                return entity;
            }
        }
        return null;
    }

    public static boolean canEntitySpawnHere(ServerLevel world, BlockPos at, EntityType<? extends Entity> type, SpawnReason spawnReason, int ignoreCheckFlags, @Nullable Consumer<Entity> preCheckEntity) {
        if (type.getClassification() == MobCategory.MISC || !type.isSummonable() || !world.getWorldBorder().contains(at)) {
            return false;
        }
        if (!SpawnConditionFlags.isSet(ignoreCheckFlags, SpawnConditionFlags.IGNORE_PLACEMENT_RULES)) {
            SpawnPlacements.Type placementType = SpawnPlacements.getPlacementType(type);
            if (!SpawnPlacements.checkSpawnRules(type, world, spawnReason, at, world.getRandom())) {
                return false;
            }
        }
        if (!SpawnConditionFlags.isSet(ignoreCheckFlags, SpawnConditionFlags.IGNORE_BLOCK_COLLISION)) {
            if (!world.noCollision(type.getBoundingBoxWithSizeApplied(at.getX() + 0.5, at.getY(), at.getZ() + 0.5))) {
                return false;
            }
        }

        Entity entity = type.create(world);
        if (entity == null) {
            return false;
        }
        entity.setPos(at.getX() + 0.5, at.getY() + 0.5, at.getZ() + 0.5);
        entity.yRot = world.getRandom().nextFloat() * 360.0F;
        entity.xRot = 0.0F;
        if (preCheckEntity != null) {
            preCheckEntity.accept(entity);
        }

        if (entity instanceof LivingEntity) {
            if (entity instanceof MobEntity) {
                MobEntity mobEntity = (MobEntity) entity;
                Event.Result canSpawn = EventHooks.canEntitySpawn(mobEntity, world, entity.getX(), entity.getY(), entity.getZ(), null, spawnReason);
                if (canSpawn == Event.Result.DENY) {
                    return false;
                } else if (canSpawn == Event.Result.DEFAULT) {
                    if (!SpawnConditionFlags.isSet(ignoreCheckFlags, SpawnConditionFlags.IGNORE_ENTITY_SPAWN_CONDITIONS)) {
                        if (!mobEntity.checkSpawnRules(world, spawnReason)) {
                            return false;
                        }
                    }
                    if (!SpawnConditionFlags.isSet(ignoreCheckFlags, SpawnConditionFlags.IGNORE_ENTITY_COLLISION)) {
                        if (!mobEntity.checkSpawnObstruction(world)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Nonnull
    public static List<ItemStack> generateLoot(LivingEntity entity, Random rand, DamageSource srcDeath, @Nullable LivingEntity lastAttacker) {
        MinecraftServer srv = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        ServerLevel sw = (ServerLevel) entity.level;

        if (!sw.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            return Collections.emptyList();
        }

        ResourceLocation lootTableKey = entity.getLootTableResourceLocation();
        LootTable table = srv.getLootTableManager().getLootTableFromLocation(lootTableKey);
        LootContext.Builder builder = new LootContext.Builder(sw)
                .withRandom(rand)
                .withParameter(LootParameters.THIS_ENTITY, entity)
                .withParameter(LootParameters.field_237457_g_, entity.getPositionVec())
                .withParameter(LootParameters.DAMAGE_SOURCE, srcDeath)
                .withNullableParameter(LootParameters.KILLER_ENTITY, srcDeath.getTrueSource())
                .withNullableParameter(LootParameters.DIRECT_KILLER_ENTITY, srcDeath.getImmediateSource());
        if (lastAttacker != null) {
            if (lastAttacker instanceof Player) {
                builder.withParameter(LootParameters.LAST_DAMAGE_PLAYER, (Player) lastAttacker)
                        .withLuck(((Player) lastAttacker).getLuck());
            }
        }

        return table.generate(builder.build(LootParameterSets.ENTITY));
    }

    @Nullable
    public static <T extends Entity> T getClosestEntity(LevelAccessor world, Class<T> type, AABB box, Vector3 closestTo) {
        List<T> entities = world.getEntitiesWithinAABB(type, box, Entity::isAlive);
        return selectClosest(entities, closestTo::distanceSquared);
    }

    public static Predicate<? super Entity> selectEntities(Class<? extends Entity>... entities) {
        return (Predicate<Entity>) entity -> {
            if (entity == null || !entity.isAlive()) return false;
            Class<? extends Entity> clazz = entity.getClass();
            for (Class<? extends Entity> test : entities) {
                if (test.isAssignableFrom(clazz)) return true;
            }
            return false;
        };
    }

    public static Predicate<? super Entity> selectItemClassInstanceof(Class<?> itemClass) {
        return (Predicate<Entity>) entity -> {
            if (entity == null || !entity.isAlive()) return false;
            if (!(entity instanceof ItemEntity)) return false;
            ItemStack i = ((ItemEntity) entity).getItem();
            if (i.isEmpty()) return false;
            return itemClass.isAssignableFrom(i.getItem().getClass());
        };
    }

    public static Predicate<? super Entity> selectItem(Item item) {
        return (Predicate<Entity>) entity -> {
            if (entity == null || !entity.isAlive()) return false;
            if (!(entity instanceof ItemEntity)) return false;
            ItemStack i = ((ItemEntity) entity).getItem();
            if (i.isEmpty()) return false;
            return i.getItem().equals(item);
        };
    }

    public static Predicate<? super Entity> selectItemStack(Function<ItemStack, Boolean> acceptor) {
        return entity -> {
            if (entity == null || !entity.isAlive()) return false;
            if (!(entity instanceof ItemEntity)) return false;
            ItemStack i = ((ItemEntity) entity).getItem();
            if (i.isEmpty()) return false;
            return acceptor.apply(i);
        };
    }

    @Nullable
    public static <T> T selectClosest(Collection<T> elements, Function<T, Double> dstFunc) {
        if (elements.isEmpty()) return null;

        double dstClosest = Double.MAX_VALUE;
        T closestElement = null;
        for (T element : elements) {
            double dst = dstFunc.apply(element);
            if (dst < dstClosest) {
                closestElement = element;
                dstClosest = dst;
            }
        }
        return closestElement;
    }

    public static class SpawnConditionFlags {

        public static final int IGNORE_PLACEMENT_RULES         = 0b0001;
        public static final int IGNORE_ENTITY_COLLISION        = 0b0010;
        public static final int IGNORE_BLOCK_COLLISION         = 0b0100;
        public static final int IGNORE_ENTITY_SPAWN_CONDITIONS = 0b1000;

        public static final int IGNORE_COLLISIONS = IGNORE_BLOCK_COLLISION | IGNORE_ENTITY_COLLISION;
        public static final int IGNORE_SPAWN_CONDITIONS = IGNORE_PLACEMENT_RULES | IGNORE_ENTITY_SPAWN_CONDITIONS;
        public static final int IGNORE_ALL = IGNORE_COLLISIONS | IGNORE_SPAWN_CONDITIONS; //Why would you actually use this? Consider not calling the method..

        public static boolean isSet(int flags, int flag) {
            return (flags & flag) != 0;
        }

    }
}
