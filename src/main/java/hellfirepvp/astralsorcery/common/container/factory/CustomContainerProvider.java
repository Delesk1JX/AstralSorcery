/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.container.factory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import static net.minecraft.network.chat.Component.translatable;
import net.neoforged.neoforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CustomContainerProvider
 * Created by HellFirePvP
 * Date: 10.08.2019 / 09:11
 */
public abstract class CustomContainerProvider<C extends Container> implements INamedContainerProvider {

    private final ContainerType<C> type;

    public CustomContainerProvider(ContainerType<C> type) {
        this.type = type;
    }

    @Override
    public Component getDisplayName() {
        ResourceLocation key = this.type.getRegistryName();
        return Component.translatable("screen.%s.%s", key.getNamespace(), key.getPath());
    }

    @Nonnull
    @Override
    public abstract C createMenu(int id, IInventory plInventory, Player player);

    protected abstract void writeExtraData(FriendlyByteBuf buf);

    public void openFor(ServerPlayer player) {
        NetworkHooks.openGui(player, this, this::writeExtraData);
    }
}
