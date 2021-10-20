package ru.DmN.tech.common.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import ru.DmN.tech.common.material.DFM;
import ru.DmN.tech.common.material.DTMM;
import ru.DmN.tech.common.material.IMaterial;

import java.util.ArrayList;

public class MaterialRegistry {
    public static final ArrayList<IMaterial> materials = new ArrayList<>();

    public static void register(IMaterial material) {
        materials.add(material);
    }

    public static IMaterial get(Identifier id) {
        for (IMaterial material : materials)
            if (material.getId().equals(id))
                return material;
        return null;
    }

    public static IMaterial ofItem(Identifier id) {
        switch (id.toString()) {
            case "minecraft:coal":
                return get(id);
            case "minecraft:charcoal":
                return get(new Identifier("coal"));
            case "minecraft:iron_ingot":
                return get(new Identifier("iron"));
        }
        return null;
    }

    private static boolean __init = true;
    public static void __init() {
        if (__init) {
            try {
                ClassLoader loader = MaterialRegistry.class.getClassLoader();
                Class.forName("ru.DmN.tech.common.material.DFM", true, loader);
                Class.forName("ru.DmN.tech.common.material.DTMM", true, loader);
            } catch (Throwable error) {
                throw new Error(error);
            }
            __init = false;
        }
    }
}