package me.quickscythe.fluxitems.utils;

import org.json.JSONObject;
import me.quickscythe.fluxcore.api.data.AccountManager;
import me.quickscythe.fluxcore.api.data.StorageManager;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public record FluxItem(String id, String material, int qid, JSONObject components) {

    public boolean canConvert(ServerPlayerEntity player) {
        AccountManager accountManager = (AccountManager) StorageManager.getDataManager("playerdata");
        if ((accountManager.getQuickID(player.getUuid()) == qid() || qid() == 0) || player.hasPermissionLevel(4)) {
            try {
                Item i = (Item) Items.class.getField(material().toUpperCase()).get(null);
                if (i == player.getInventory().getMainHandStack().getItem()) {
                    return true;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
