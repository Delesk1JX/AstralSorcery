/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hellfirepvp.astralsorcery.common.perk.source.ModifierManager;
import hellfirepvp.astralsorcery.common.perk.source.ModifierSource;
import hellfirepvp.astralsorcery.common.perk.source.ModifierSourceProvider;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ByteBufUtils
 * Created by HellFirePvP
 * Date: 07.05.2016 / 01:13
 */
public class ByteBufUtils {

    @Nullable
    public static <T> T readOptional(FriendlyByteBuf buf, Function<FriendlyByteBuf, T> readFct) {
        if (buf.readBoolean()) {
            return readFct.apply(buf);
        }
        return null;
    }

    public static <T> void writeOptional(FriendlyByteBuf buf, @Nullable T object, BiConsumer<FriendlyByteBuf, T> applyFct) {
        writeOptional(buf, object, Function.identity(), applyFct);
    }

    public static <T, R> void writeOptional(FriendlyByteBuf buf, @Nullable T object, Function<T, R> converter, BiConsumer<FriendlyByteBuf, R> applyFct) {
        buf.writeBoolean(object != null);
        if (object != null) {
            applyFct.accept(buf, converter.apply(object));
        }
    }

    public static void writeUUID(FriendlyByteBuf buf, UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    public static UUID readUUID(FriendlyByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static <T> void writeCollection(FriendlyByteBuf buf, @Nullable Collection<T> list, BiConsumer<FriendlyByteBuf, T> iterationFct) {
        if (list != null) {
            buf.writeInt(list.size());
            list.forEach(e -> iterationFct.accept(buf, e));
        } else {
            buf.writeInt(-1);
        }
    }

    @Nullable
    public static <T> List<T> readList(FriendlyByteBuf buf, Function<FriendlyByteBuf, T> readFct) {
        return readCollection(buf, ArrayList::new, List::add, readFct);
    }

    @Nullable
    public static <T> Set<T> readSet(FriendlyByteBuf buf, Function<FriendlyByteBuf, T> readFct) {
        return readCollection(buf, HashSet::new, Set::add, readFct);
    }

    @Nullable
    public static <T, C extends Collection<T>> C readCollection(FriendlyByteBuf buf, Supplier<C> newCollection, BiConsumer<C, T> addFn, Function<FriendlyByteBuf, T> readFct) {
        int size = buf.readInt();
        if (size == -1) {
            return null;
        }
        C collection = newCollection.get();
        for (int i = 0; i < size; i++) {
            addFn.accept(collection, readFct.apply(buf));
        }
        return collection;
    }

    public static <K, V> void writeMap(FriendlyByteBuf buf,
                                       @Nullable Map<K, V> map,
                                       BiConsumer<FriendlyByteBuf, K> keySerializer,
                                       BiConsumer<FriendlyByteBuf, V> valueSerializer) {
        if (map != null) {
            buf.writeInt(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                keySerializer.accept(buf, entry.getKey());
                valueSerializer.accept(buf, entry.getValue());
            }
        } else {
            buf.writeInt(-1);
        }
    }

    @Nullable
    public static <K, V> Map<K, V> readMap(FriendlyByteBuf buf,
                                           Function<FriendlyByteBuf, K> readKey,
                                           Function<FriendlyByteBuf, V> readValue) {
        int size = buf.readInt();
        if (size == -1) {
            return null;
        }
        Map<K, V> map = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            map.put(readKey.apply(buf), readValue.apply(buf));
        }
        return map;
    }

    public static void writeTextComponent(FriendlyByteBuf buf, Component cmp) {
        writeString(buf, IFormattableTextComponent.Serializer.toJson(cmp));
    }

    public static IFormattableTextComponent readTextComponent(FriendlyByteBuf buf) {
        return IFormattableTextComponent.Serializer.getComponentFromJson(readString(buf));
    }

    public static void writeString(FriendlyByteBuf buf, String toWrite) {
        byte[] str = toWrite.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(str.length);
        buf.writeBytes(str);
    }

    public static String readString(FriendlyByteBuf buf) {
        int length = buf.readInt();
        byte[] strBytes = new byte[length];
        buf.readBytes(strBytes, 0, length);
        return new String(strBytes, StandardCharsets.UTF_8);
    }

    public static <T> void writeRegistryEntry(FriendlyByteBuf buf, T entry) {
        ResourceLocation registryName = BuiltInRegistries.getRegistry(entry.getClass()).getKey();
        if (registryName == null) {
            registryName = BuiltInRegistries.REGISTRY_REGISTRY.getKey(BuiltInRegistries.getRegistry(entry.getClass()));
        }
        writeResourceLocation(buf, BuiltInRegistries.getRegistry(entry.getClass()).getKey());
        writeResourceLocation(buf, registryName);
    }

    public static <T> T readRegistryEntry(FriendlyByteBuf buf) {
        ResourceLocation entryName = readResourceLocation(buf);
        ResourceLocation registryName = readResourceLocation(buf);
        return (T) BuiltInRegistries.getRegistry(registryName).get(entryName);
    }

    public static void writeVanillaRegistryEntry(FriendlyByteBuf buf, ResourceKey<?> key) {
        writeResourceLocation(buf, key.getRegistryName());
        writeResourceLocation(buf, key.getLocation());
    }

    public static <T> ResourceKey<T> readVanillaRegistryEntry(FriendlyByteBuf buf) {
        ResourceLocation registryName = readResourceLocation(buf);
        return ResourceKey.getOrCreateKey(ResourceKey.getOrCreateRootKey(registryName), readResourceLocation(buf));
    }

    public static void writeResourceLocation(FriendlyByteBuf buf, ResourceLocation key) {
        writeString(buf, key.toString());
    }

    public static ResourceLocation readResourceLocation(FriendlyByteBuf buf) {
        return new ResourceLocation(readString(buf));
    }

    public static <T extends Enum<T>> void writeEnumValue(FriendlyByteBuf buf, T value) {
        buf.writeInt(value.ordinal());
    }

    public static <T extends Enum<T>> T readEnumValue(FriendlyByteBuf buf, Class<T> enumClazz) {
        if (!enumClazz.isEnum()) {
            throw new IllegalArgumentException("Passed class is not an enum!");
        }
        return enumClazz.getEnumnet.neoforged.neoforge.common.util.FakePlayerFactory()[buf.readInt()];
    }

    public static void writeJsonObject(FriendlyByteBuf buf, JsonObject object) {
        writeString(buf, object.toString());
    }

    public static JsonObject readJsonObject(FriendlyByteBuf buf) {
        return new JsonParser().parse(readString(buf)).getAsJsonObject();
    }

    public static void writeModifierSource(FriendlyByteBuf buf, ModifierSource source) {
        ResourceLocation providerName = source.getProviderName();
        ByteBufUtils.writeResourceLocation(buf, providerName);

        ModifierSourceProvider provider = ModifierManager.getProvider(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerName);
        }
        provider.serialize(source, buf);
    }

    public static ModifierSource readModifierSource(FriendlyByteBuf buf) {
        ResourceLocation providerName = ByteBufUtils.readResourceLocation(buf);
        ModifierSourceProvider<?> provider = ModifierManager.getProvider(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerName);
        }
        return provider.deserialize(buf);
    }

    public static void writePos(FriendlyByteBuf buf, BlockPos pos) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static BlockPos readPos(FriendlyByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        return new BlockPos(x, y, z);
    }

    public static void writeVector(FriendlyByteBuf buf, Vector3 vec) {
        buf.writeDouble(vec.getX());
        buf.writeDouble(vec.getY());
        buf.writeDouble(vec.getZ());
    }

    public static Vector3 readVector(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new Vector3(x, y, z);
    }

    public static void writeItemStack(FriendlyByteBuf byteBuf, @Nonnull ItemStack stack) {
        boolean defined = !stack.isEmpty();
        byteBuf.writeBoolean(defined);
        if (defined) {
            CompoundTag tag = new CompoundTag();
            stack.write(tag);
            writeNBTTag(byteBuf, tag);
        }
    }

    @Nonnull
    public static ItemStack readItemStack(FriendlyByteBuf byteBuf) {
        boolean defined = byteBuf.readBoolean();
        if (defined) {
            return ItemStack.read(readNBTTag(byteBuf));
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void writeBlockState(FriendlyByteBuf byteBuf, @Nonnull BlockState state) {
        ByteBufUtils.writeRegistryEntry(byteBuf, state.getBlock());

        Collection<Property<?>> properties = state.getProperties();
        byteBuf.writeInt(properties.size());
        for (Property prop : properties) {
            ByteBufUtils.writeString(byteBuf, prop.getName());
            ByteBufUtils.writeString(byteBuf, prop.getName(state.get(prop)));
        }
    }

    public static <T extends Comparable<T>> BlockState readBlockState(FriendlyByteBuf byteBuf) {
        Block block = ByteBufUtils.readRegistryEntry(byteBuf);
        BlockState state = block.getDefaultState();

        int properties = byteBuf.readInt();
        for (int i = 0; i < properties; i++) {
            String propName = ByteBufUtils.readString(byteBuf);
            String valueStr = ByteBufUtils.readString(byteBuf);
            Property<T> property = (Property<T>) MiscUtils.iterativeSearch(state.getProperties(), prop -> prop.getName().equalsIgnoreCase(propName));
            if (property != null) {
                Optional<T> value = property.parseValue(valueStr);
                if (value.isPresent()) {
                    state = state.with(property, value.get());
                }
            }
        }
        return state;
    }

    public static void writeFluidStack(FriendlyByteBuf byteBuf, @Nonnull FluidStack stack) {
        stack.writeToPacket(byteBuf);
    }

    @Nonnull
    public static FluidStack readFluidStack(FriendlyByteBuf byteBuf) {
        return FluidStack.readFromPacket(byteBuf);
    }

    public static void writeNBTTag(FriendlyByteBuf byteBuf, @Nonnull CompoundTag tag) {
        try (DataOutputStream dos = new DataOutputStream(new ByteBufOutputStream(byteBuf))) {
            CompressedStreamTools.write(tag, dos);
        } catch (Exception exc) {}
    }

    @Nonnull
    public static CompoundTag readNBTTag(FriendlyByteBuf byteBuf) {
        try (DataInputStream dis = new DataInputStream(new ByteBufInputStream(byteBuf))) {
            return CompressedStreamTools.read(dis);
        } catch (Exception exc) {}
        throw new IllegalStateException("Could not load NBT Tag from incoming byte buffer!");
    }

}
