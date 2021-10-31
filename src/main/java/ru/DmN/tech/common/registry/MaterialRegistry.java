package ru.DmN.tech.common.registry;

import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import ru.DmN.tech.common.material.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class MaterialRegistry {
    public static final Map<Identifier, IMaterial> materials = new HashMap<>();

    public static Identifier of(IMaterial material) {
        for (var pair : materials.entrySet())
            if (pair.getValue().equals(material))
                return pair.getKey();
        return null;
    }

    public static IMaterial of(Identifier id) {
        return materials.get(id);
    }

    public static void register(Identifier id, IMaterial material) {
        materials.put(id, material);
    }

    public static SimpleMaterial register(Identifier id, boolean isFuel, int burnTime, int burnCoefficient, boolean isMelt, int meltTime, int meltTemperature, boolean isCoil, int energyCoefficient, int maxTemperature, boolean isToolMaterial, int durability) {
        SimpleMaterial x = new SimpleMaterial(isFuel, burnTime, burnCoefficient, isMelt, meltTime, meltTemperature, isCoil, energyCoefficient, maxTemperature, isToolMaterial, durability);
        materials.put(id, x);
        return x;
    }

    public static SimpleFuel registerFuel(Identifier id, int bt, double bc, int mt) {
        SimpleFuel x = new SimpleFuel(calcBurnTime(bt), calcBurnCoefficient(bc), mt);
        materials.put(id, x);
        return x;
    }

    public static SimpleMetalTool registerMetalTool(Identifier id, int meltTime, int meltTemperature) {
        SimpleMetalTool x = new SimpleMetalTool(meltTime, meltTemperature, calcDurability(meltTemperature));
        materials.put(id, x);
        return x;
    }

    public static SimpleMetalTool registerMetalTool(Identifier id, int meltTime, int meltTemperature, int durability) {
        SimpleMetalTool x = new SimpleMetalTool(meltTime, meltTemperature, durability);
        materials.put(id, x);
        return x;
    }

    public static boolean init = true;
    public static void init() {
        if (init) {
            //
            register(new Identifier("minecraft", "air"), EmptyMaterial.INSTANCE);
            //
            setMaterial(registerFuel(new Identifier("minecraft", "coal"), 8, 1, 1600),
                    Items.COAL,
                    Items.CHARCOAL
            );
            //
            setMaterial(registerFuel(new Identifier("minecraft", "coal_block"), 100, 1, 1650),
                    Items.COAL_BLOCK
            );
            //
            setMaterial(registerFuel(new Identifier("minecraft", "stick"), 1, 0.5, 300),
                    Items.STICK
            );
            //
            setMaterial(registerFuel(new Identifier("minecraft", "planks"), 1, 1.5, 400),
                    Items.ACACIA_PLANKS,
                    Items.BIRCH_PLANKS,
                    Items.CRIMSON_PLANKS,
                    Items.DARK_OAK_PLANKS,
                    Items.JUNGLE_PLANKS,
                    Items.OAK_PLANKS,
                    Items.SPRUCE_PLANKS,
                    Items.WARPED_PLANKS
            );
            //
            registerMetalTool(new Identifier("minecraft", "tin"), 1, 232);
            registerMetalTool(new Identifier("minecraft", "bronze"), 1, 950);
            registerMetalTool(new Identifier("minecraft", "copper"), 1, 1085);
            //
            setMaterial(registerMetalTool(new Identifier("minecraft", "iron"), 1, 1538),
                    Items.IRON_INGOT,
                    Items.IRON_ORE,
                    Items.DEEPSLATE_IRON_ORE,
                    Items.RAW_IRON
            );
            //
            init = false;
        }
    }

    public static double calcBurnCoefficient(double sourceBurnCoefficient) {
        return sourceBurnCoefficient * 5d;
    }

    public static int calcBurnTime(int sourceBurnTime) {
        return sourceBurnTime * 20;
    }

    public static int calcDurability(int temperature) {
        return (int) (0.4876462938881664 * temperature);
    }

    public static void setMaterial(IMaterial material, Object... objects) {
        for (Object o : objects)
            ((IMaterialProvider) o).setMaterial(material);
    }
}