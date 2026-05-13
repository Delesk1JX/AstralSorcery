/*******************************************************************************
import net.minecraft.network.chat.Component;
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.cmd.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import hellfirepvp.astralsorcery.common.util.block.BlockStateHelper;
import hellfirepvp.astralsorcery.common.util.data.JsonHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.MutableComponent;
import static net.minecraft.network.chat.Component.literal;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CommandSerialize
 * Created by HellFirePvP
 * Date: 07.03.2021 / 15:38
 */
public class CommandSerialize {

    private CommandSerialize() {}

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("serialize")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.literal("hand")
                        .executes(CommandSerialize::serializeHand))
                .then(Commands.literal("look")
                        .executes(CommandSerialize::serializeLook));
    }

    private static int serializeHand(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Player player = context.getSource().asPlayer();
        ItemStack held = player.getHeldItemMainhand();
        String serialized = JsonHelper.serializeItemStack(held).toString();

        net.minecraft.network.chat.Component msg = Component.literal(serialized);
        Style s = Style.EMPTY.setFormatting(ChatFormatting.GREEN)
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Copy")))
                .setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, serialized));
        msg.setStyle(s);

        context.getSource().sendFeedback(msg, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int serializeLook(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Player player = context.getSource().asPlayer();
        BlockHitResult result = MiscUtils.rayTraceLookBlock(player);
        BlockState state = result == null ? Blocks.AIR.getDefaultState() : player.level().getBlockState(result.getPos());
        String serialized = BlockStateHelper.serialize(state);

        net.minecraft.network.chat.Component msg = Component.literal(serialized);
        Style s = Style.EMPTY.setFormatting(ChatFormatting.GREEN)
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Copy")))
                .setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, serialized));
        msg.setStyle(s);

        context.getSource().sendFeedback(msg, true);
        return Command.SINGLE_SUCCESS;
    }
}
