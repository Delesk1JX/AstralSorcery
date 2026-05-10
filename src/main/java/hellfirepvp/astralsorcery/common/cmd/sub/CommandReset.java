/*******************************************************************************
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
import hellfirepvp.astralsorcery.common.data.research.ResearchHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.world.entity.player.ServerPlayer;
import static net.minecraft.network.chat.Component.literal;
import net.minecraft.ChatFormatting;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CommandReset
 * Created by HellFirePvP
 * Date: 21.07.2019 / 20:19
 */
public class CommandReset implements Command<CommandSource> {

    private static final CommandReset CMD = new CommandReset();

    private CommandReset() {}

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("reset")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(CMD));
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayer player = (ServerPlayer) context.getArgument("player", EntitySelector.class).selectOne(context.getSource());
        ResearchHelper.wipeKnowledge(player);

        String name = player.getGameProfile().getName();
        context.getSource().sendFeedback(new Component.literal("Wiped " + name + "'s data!").withStyle(ChatFormatting.GREEN), true);
        return 0;
    }
}
