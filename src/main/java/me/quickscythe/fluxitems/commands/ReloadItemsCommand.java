package me.quickscythe.fluxitems.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.quickscythe.fluxitems.utils.FluxItem;
import me.quickscythe.fluxitems.utils.FluxItemManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ReloadItemsCommand implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("reload-items").executes(context -> {
            ServerCommandSource source = context.getSource();
            if(source.hasPermissionLevel(4)){
                FluxItemManager.reload();
                source.sendFeedback(() -> Text.literal("§6Reloaded items!"), false);
                return Command.SINGLE_SUCCESS;
            }
            source.sendError(Text.literal("Invalid permissions"));
            return 0;
        }).then(argument("item", StringArgumentType.word())
                .executes(context -> {

                    ServerCommandSource source = context.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    FluxItem item = FluxItemManager.getItem(StringArgumentType.getString(context, "item"));
                    if (item.canConvert(player)) {
                        source.sendMessage(Text.literal("§6Converting item..."));
                        String cmd = "/item modify entity " + player.getName().getString() + " weapon {function:\"minecraft:set_components\", components: " + item.components() + "}";
                        System.out.println(cmd);
                        source.getServer().getCommandManager().executeWithPrefix(source.getServer().getCommandSource(), cmd);
                        return Command.SINGLE_SUCCESS;
                    }
                    source.sendError(Text.literal("Invalid arguments. Usage: /convert <item>"));

                    return 0;
                }).suggests((context, builder) -> {
                    ServerCommandSource source = context.getSource();
                    List<String> items = new ArrayList<>();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    for (FluxItem item : FluxItemManager.getItems()) {
                        if (item.canConvert(player)) {
                            items.add(item.id());
                        }
                    }

                    return CommandSource.suggestMatching(items, builder);
                })));

    }
}
