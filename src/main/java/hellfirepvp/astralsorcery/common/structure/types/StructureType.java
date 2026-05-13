/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.structure.types;

import hellfirepvp.observerlib.api.ChangeSubscriber;
import hellfirepvp.observerlib.api.ObserverHelper;
import hellfirepvp.observerlib.api.util.BlockArray;
import hellfirepvp.observerlib.common.change.ChangeObserverStructure;
import hellfirepvp.observerlib.common.change.ObserverProviderStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import static net.minecraft.network.chat.Component.translatable;
import net.minecraft.world.level().Level;
import net.minecraft.core.Holder;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: StructureType
 * Created by HellFirePvP
 * Date: 30.05.2019 / 15:07
 */
public class StructureType {

    private final ResourceLocation name;
    private final Supplier<BlockArray> structureSupplier;

    public StructureType(ResourceLocation name, Supplier<BlockArray> structureSupplier) {
        this.name = name;
        this.structureSupplier = structureSupplier;
    }

    public BlockArray getStructure() {
        return this.structureSupplier.get();
    }

    public Component getDisplayName() {
        return Component.translatable(String.format("structure.%s.%s.name", name.getNamespace(), name.getPath()));
    }

    public ChangeSubscriber observe(Level world, BlockPos pos) {
        return ObserverHelper.getHelper().observeArea(world, pos, new ObserverProviderStructure(getRegistryName()));
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return this.name;
    }
}
