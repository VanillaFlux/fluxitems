package me.quickscythe.fluxitems.utils;

import json2.JSONObject;
import me.quickscythe.fluxcore.utils.CoreUtils;
import me.quickscythe.fluxcore.utils.data.StorageManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.core.Core;

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
            CoreUtils.getLoggerUtils().log("Creating item folder: " + itemsFolder.mkdirs());
        }
        CoreUtils.getLoggerUtils().log("Loading items...");
        for(File file : itemsFolder.listFiles()){
            if(file.getName().endsWith(".json")){
                CoreUtils.getLoggerUtils().log("Loading item: " + file.getName());
                JSONObject json = (JSONObject) StorageManager.getStorage().load(file);
                createItem(json);
            }
        }
    }

    public static FluxItem createItem(String name, String id, int qid, JSONObject components){
        FluxItem item = new FluxItem(name, id, qid, components);
        items.put(name, item);
        CoreUtils.getLoggerUtils().log("Created item: " + name + " (" + qid + ")");
        return item;
    }

    public static FluxItem createItem(JSONObject json){
        String name = json.getString("name");
        String id = json.getString("base_item");
        int qid = json.getInt("qid");
        JSONObject components = json.getJSONObject("components");
        return createItem(name, id, qid, components);
    }

    public static FluxItem getItem(String name){
        return items.get(name);
    }

    public static Collection<FluxItem> getItems(){
        return items.values();
    }

}
