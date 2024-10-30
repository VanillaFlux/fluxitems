package me.quickscythe.fluxitems;

import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxitems.commands.ConvertCommand;
import me.quickscythe.fluxitems.commands.ReloadItemsCommand;
import me.quickscythe.fluxitems.utils.FluxItemManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Initializer extends FluxEntrypoint {

    @Override
    public void onInitialize() {

        CommandRegistrationCallback.EVENT.register(new ConvertCommand());
        CommandRegistrationCallback.EVENT.register(new ReloadItemsCommand());

        FluxItemManager.init();



    }
}
