package me.quickscythe.fluxitems;

import me.quickscythe.fluxitems.commands.ConvertCommand;
import me.quickscythe.fluxitems.utils.FluxItemManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;

public class Initializer implements ModInitializer {

    @Override
    public void onInitialize() {

        CommandRegistrationCallback.EVENT.register(new ConvertCommand());

        FluxItemManager.init();

    }
}
