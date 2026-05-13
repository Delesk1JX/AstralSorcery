#!/bin/bash

# Миграция Astral Sorcery на MC 1.21.1 / NeoForge

SRC_DIR="/workspace/src/main/java"

echo "Начало миграции..."

# 1. Замена World -> Level, ServerLevel, ClientLevel
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.World;/import net.minecraft.world.level.Level;/g' \
  -e 's/\bWorld\b(?=\s+world)/Level/g' \
  -e 's/\bWorld\b(?=\s+world)/Level/g' \
  {} \;

# 2. Замена AxisAlignedBB -> AABB
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.util\.math\.AxisAlignedBB;/import net.minecraft.world.phys.AABB;/g' \
  -e 's/\bAxisAlignedBB\b/AABB/g' \
  {} \;

# 3. Замена ITickableTileEntity
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.level\.block\.entity\.ITickableTileEntity;//g' \
  -e 's/, ITickableTileEntity//g' \
  -e 's/implements ITickableTileEntity//g' \
  {} \;

# 4. Замена INBT -> Tag
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.nbt\.INBT;/import net.minecraft.nbt.Tag;/g' \
  -e 's/\bINBT\b/Tag/g' \
  {} \;

# 5. Замена IWorld -> LevelAccessor
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.IWorldReader;/import net.minecraft.world.level.LevelAccessor;/g' \
  -e 's/import net\.minecraft\.world\.IWorld;/import net.minecraft.world.level.LevelAccessor;/g' \
  -e 's/\bIWorldReader\b/LevelAccessor/g' \
  -e 's/\bIWorld\b/LevelAccessor/g' \
  {} \;

# 6. Замена NetworkManager -> Connection
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.network\.NetworkManager;/import net.minecraft.network.Connection;/g' \
  -e 's/\bNetworkManager\b/Connection/g' \
  {} \;

# 7. Замена SUpdateTileEntityPacket -> BlockEntityDataPacket
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.network\.play\.server\.SUpdateTileEntityPacket;/import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;/g' \
  -e 's/\bSUpdateTileEntityPacket\b/ClientboundBlockEntityDataPacket/g' \
  {} \;

# 8. Замена TickEvent.Type и TickEvent.Phase
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/TickEvent\.Type/ClientTickEvent.Type/g' \
  -e 's/TickEvent\.Phase/ClientTickEvent.Phase/g' \
  {} \;

# 9. Замена Vector3i и Vector3d (если есть в проекте)
# Проверка наличия собственных классов

# 10. Замена SoundEvents и Util
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.util\.SoundEvents;/import net.minecraft.sounds.SoundEvents;/g' \
  -e 's/import net\.minecraft\.util\.Util;/import net.minecraft.Util;/g' \
  {} \;

# 11. Замена RegistryKey и DimensionType
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.util\.RegistryKey;/import net.minecraft.resources.ResourceKey;/g' \
  -e 's/\bRegistryKey\b/ResourceKey/g' \
  -e 's/import net\.minecraft\.world\.DimensionType;/import net.minecraft.world.level.dimension.DimensionType;/g' \
  {} \;

# 12. Замена Constants
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.common\.util\.Constants;/import net.neoforged.neoforge.common.Tags;/g' \
  {} \;

# 13. Замена LazyOptional
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.common\.util\.LazyOptional;/import net.neoforged.neoforge.capabilities.LazyOptional;/g' \
  {} \;

# 14. Замена CapabilityItemHandler
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.items\.CapabilityItemHandler;/import net.neoforged.neoforge.capabilities.CapabilityItemStack;/g' \
  {} \;

# 15. Замена ForgeEventFactory
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.event\.ForgeEventFactory;/import net.neoforged.neoforge.event.EventHooks;/g' \
  -e 's/ForgeEventFactory/EventHooks/g' \
  {} \;

# 16. Замена EffectiveSide
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.fml\.common\.thread\.EffectiveSide;/import net.neoforged.fml.LogicalSide;/g' \
  -e 's/EffectiveSide/LogicalSide/g' \
  {} \;

# 17. Замена MendingEnchantment
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.item\.enchantment\.MendingEnchantment;//g' \
  {} \;

# 18. Замена EnchantmentType
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.item\.enchantment\.EnchantmentType;//g' \
  {} \;

# 19. Замена IDispenseItemBehavior
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.core\.dispenser\.IDispenseItemBehavior;/import net.minecraft.core.dispenser.DispenseItemBehavior;/g' \
  -e 's/\bIDispenseItemBehavior\b/DispenseItemBehavior/g' \
  {} \;

# 20. Замена BlockSource
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.core\.BlockSource;/import net.minecraft.core.BlockSource;/g' \
  {} \;

# 21. Замена ISeedReader
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.ISeedReader;/import net.minecraft.world.level.WorldGenLevel;/g' \
  -e 's/\bISeedReader\b/WorldGenLevel/g' \
  {} \;

# 22. Замена TileEntityType (проверка)
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.minecraft\.world\.level\.block\.entity\.TileEntityType;/import net.minecraft.world.level.block.entity.BlockEntityType;/g' \
  -e 's/\bTileEntityType\b/BlockEntityType/g' \
  -e 's/\bTileEntity\b/BlockEntity/g' \
  {} \;

# 23. Замена ICapabilityProvider
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.common\.capabilities\.ICapabilityProvider;/import net.neoforged.neoforge.capabilities.ICapabilityProvider;/g' \
  {} \;

# 24. Замена Capability
find "$SRC_DIR" -name "*.java" -type f -exec sed -i \
  -e 's/import net\.neoforged\.neoforge\.common\.capabilities\.Capability;/import net.neoforged.neoforge.capabilities.CapabilityTokens;/g' \
  {} \;

echo "Миграция завершена!"
