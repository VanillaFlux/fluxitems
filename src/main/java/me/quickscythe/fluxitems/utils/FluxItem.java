package me.quickscythe.fluxitems.utils;

import org.json.JSONObject;
import me.quickscythe.fluxcore.api.data.AccountManager;
import me.quickscythe.fluxcore.api.data.StorageManager;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class FluxItem {
    String name;
    String id;
    int qid;
    JSONObject components;

    public FluxItem(String name, String id, int qid, JSONObject components) {
        this.name = name;
        this.id = id;
        this.qid = qid;
        this.components = components;
    }

    public int getQuickId() {
        return qid;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public JSONObject getComponents() {
        return components;
    }

    public boolean canConvert(ServerPlayerEntity player) {
        AccountManager accountManager = (AccountManager) StorageManager.getDataManager("playerdata");
        if ((accountManager.getQuickID(player.getUuid()) == getQuickId() || getQuickId() == 0) || player.hasPermissionLevel(4)) {
            try {
                Class<? extends Items> clazz = Items.class;
                Item i = (Item) clazz.getField(getId().toUpperCase()).get(null);
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
