/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.constellation.effect;

import hellfirepvp.astralsorcery.common.constellation.IWeakConstellation;
import hellfirepvp.astralsorcery.common.util.block.ILocatable;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.Holder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ConstellationEffectProvider
 * Created by HellFirePvP
 * Date: 11.06.2019 / 19:34
 */
public abstract class ConstellationEffectProvider extends DeferredHolder<ConstellationEffectProvider> implements IRegistryObject<ConstellationEffectProvider> {

    private final IWeakConstellation cst;

    protected ConstellationEffectProvider(IWeakConstellation cst) {
        this.cst = cst;
        this.setRegistryName(cst.getRegistryName());
    }

    @Nonnull
    public IWeakConstellation getConstellation() {
        return cst;
    }

    public abstract ConstellationEffect createEffect(@Nullable ILocatable origin);

}
