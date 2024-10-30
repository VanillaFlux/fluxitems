package me.quickscythe.fluxitems.utils;

import me.quickscythe.fluxcore.api.data.StorageManager;
import me.quickscythe.fluxcore.api.logger.LoggerUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FluxItemManager {

    static Map<String, FluxItem> items = new HashMap<>();
    static File itemsFolder;

    public static void init(){
        itemsFolder = new File(StorageManager.getConfigFolder(), "items");
        if(!itemsFolder.exists()){
            LoggerUtils.getLogger().info("Creating item folder: {}", itemsFolder.mkdirs());
        }
        LoggerUtils.getLogger().info("Loading items...");
        load();
    }

    public static void reload(){
        LoggerUtils.getLogger().info("Reloading items...");
        items.clear();
        load();
    }

    private static void load() {
        for(File file : itemsFolder.listFiles()){
            if(file.getName().endsWith(".json")){
                LoggerUtils.getLogger().info("Loading item: {}", file.getName());
                JSONObject json = (JSONObject) StorageManager.getStorage().load(file);
                json.put("id", file.getName().replace(".json", ""));
                createItem(json);
            }
        }
    }

    public static FluxItem createItem(String id, String material, int qid, JSONObject components){
        FluxItem item = new FluxItem(id, material, qid, components);
        items.put(id, item);
        LoggerUtils.getLogger().info("Created item: {}", id);
        return item;
    }

    public static FluxItem createItem(JSONObject json){
        String id = json.getString("id");
        String material = json.getString("base_item");
        int qid = json.getInt("qid");
        JSONObject components = json.getJSONObject("components");
        return createItem(id, material, qid, components);
    }

    public static FluxItem getItem(String name){
        return items.get(name);
    }

    public static Collection<FluxItem> getItems(){
        return items.values();
    }

}
