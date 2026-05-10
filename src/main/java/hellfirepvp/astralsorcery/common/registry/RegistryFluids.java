/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.fluid.BlockLiquidStarlight;
import hellfirepvp.astralsorcery.common.fluid.FluidLiquidStarlight;
import hellfirepvp.astralsorcery.common.fluid.ItemLiquidStarlightBucket;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static hellfirepvp.astralsorcery.common.lib.FluidsAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryFluids
 * Created by HellFirePvP
 * Date: 20.09.2019 / 21:53
 */
public class RegistryFluids {

    static final List<DeferredHolder<?, ?>> FLUID_BLOCKS = new LinkedList<>();
    static final List<DeferredHolder<?, ?>> FLUID_HOLDER_ITEMS = new LinkedList<>();

    private RegistryFluids() {}

    public static void registerFluids() {
        makeProperties();

        LIQUID_STARLIGHT_SOURCE = ASRegistries.FLUIDS.register("liquid_starlight", 
                () -> new FluidLiquidStarlight.Source(LIQUID_STARLIGHT_PROPERTIES));
        LIQUID_STARLIGHT_FLOWING = ASRegistries.FLUIDS.register("flowing_liquid_starlight", 
                () -> new FluidLiquidStarlight.Flowing(LIQUID_STARLIGHT_PROPERTIES));

        FLUID_BLOCKS.add(BlocksAS.FLUID_LIQUID_STARLIGHT = ASRegistries.BLOCKS.register("liquid_starlight", 
                () -> new BlockLiquidStarlight(() -> LIQUID_STARLIGHT_SOURCE.get())));
        FLUID_HOLDER_ITEMS.add(ItemsAS.BUCKET_LIQUID_STARLIGHT = ASRegistries.ITEMS.register("bucket_liquid_starlight", 
                () -> new ItemLiquidStarlightBucket(() -> LIQUID_STARLIGHT_SOURCE.get())));
    }

    private static void makeProperties() {
        LIQUID_STARLIGHT_PROPERTIES = makeProperties(FluidLiquidStarlight.class, 
                () -> LIQUID_STARLIGHT_SOURCE, () -> LIQUID_STARLIGHT_FLOWING)
                .block(() -> BlocksAS.FLUID_LIQUID_STARLIGHT.get())
                .bucket(() -> ItemsAS.BUCKET_LIQUID_STARLIGHT.get());
    }

    private static BaseFlowingFluid.Properties makeProperties(Class<? extends BaseFlowingFluid> fluidClass,
                                                               Supplier<BaseFlowingFluid> stillFluidSupplier,
                                                               Supplier<BaseFlowingFluid> flowingFluidSupplier) {
        return new BaseFlowingFluid.Properties(stillFluidSupplier, flowingFluidSupplier);
    }
}
