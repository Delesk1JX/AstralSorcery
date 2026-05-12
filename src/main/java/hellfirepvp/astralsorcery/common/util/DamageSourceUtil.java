/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.common.extensions.IDamageSourceExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: DamageSourceUtil
 * Created by HellFirePvP
 * Date: 17.11.2018 / 08:29
 */
public class DamageSourceUtil {

    public static DamageSource newType(String damageType) {
        return new DamageSource(damageType);
    }

    public static DamageSource withEntityDirect(String damageType, Entity source) {
        return source.createDamageSource(damageType);
    }

    public static DamageSource withEntityIndirect(String damageType, Entity actualSource, Entity indirectSource) {
        return indirectSource.createDamageSource(damageType);
    }

    public static DamageSource setToBypassArmor(DamageSource src) {
        return ((IDamageSourceExtension) src).setBypassArmor();
    }

    public static DamageSource setToFireDamage(DamageSource src) {
        return ((IDamageSourceExtension) src).setIsFire();
    }

    public static DamageSource changeAttribute(DamageSource src, Consumer<DamageSource> update) {
        update.accept(src);
        return src;
    }
}
