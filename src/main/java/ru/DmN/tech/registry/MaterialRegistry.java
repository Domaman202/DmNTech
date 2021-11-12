package ru.DmN.tech.registry;

import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import ru.DmN.tech.material.*;

import java.util.HashMap;
import java.util.Map;

import static ru.DmN.tech.DTech.MOD_ID;

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

    public static SimpleMelting registerMelting(Identifier id, int meltTime, int meltTemperature) {
        SimpleMelting x = new SimpleMelting(meltTime, meltTemperature);
        materials.put(id, x);
        return x;
    }

    public static boolean init = true;
    public static void init() {
        if (init) {
            //
            register(new Identifier(MOD_ID, "air"), EmptyMaterial.INSTANCE);
            //
            setMaterial(registerFuel(new Identifier(MOD_ID, "coal"), 12, 1, 1600),
                    Items.COAL,
                    Items.CHARCOAL
            );
            //
            setMaterial(registerFuel(new Identifier(MOD_ID, "coal_block"), 120, 1, 1650),
                    Items.COAL_BLOCK
            );
            //
            setMaterial(registerFuel(new Identifier(MOD_ID, "stick"), 1, 0.5, 150),
                    Items.STICK,
                    Items.ACACIA_SAPLING,
                    Items.SPRUCE_SAPLING,
                    Items.BIRCH_SAPLING,
                    Items.DARK_OAK_SAPLING,
                    Items.JUNGLE_SAPLING,
                    Items.OAK_SAPLING,
                    Items.ACACIA_LEAVES,
                    Items.AZALEA_LEAVES,
                    Items.BIRCH_LEAVES,
                    Items.DARK_OAK_LEAVES,
                    Items.JUNGLE_LEAVES,
                    Items.OAK_LEAVES,
                    Items.SPRUCE_LEAVES,
                    Items.AZALEA_LEAVES_FLOWERS,
                    Items.DEAD_BUSH
            );
            //
            setMaterial(registerFuel(new Identifier(MOD_ID, "planks"), 4, 1.5, 400),
                    Items.ACACIA_PLANKS,
                    Items.BIRCH_PLANKS,
                    Items.CRIMSON_PLANKS,
                    Items.DARK_OAK_PLANKS,
                    Items.JUNGLE_PLANKS,
                    Items.OAK_PLANKS,
                    Items.SPRUCE_PLANKS,
                    Items.WARPED_PLANKS
            );
            // TODO
            registerMetalTool(new Identifier(MOD_ID, "tin"), 1, 232);
            // TODO
            registerMetalTool(new Identifier(MOD_ID, "bronze"), 1, 950);
            //
            setMaterial(registerMetalTool(new Identifier(MOD_ID, "copper"), 10, 1085),
                    Items.COPPER_INGOT,
                    Items.COPPER_ORE,
                    Items.DEEPSLATE_COPPER_ORE,
                    Items.RAW_COPPER
            );
            //
            setMaterial(registerMetalTool(new Identifier(MOD_ID, "iron"), 12, 1538),
                    Items.IRON_INGOT,
                    Items.IRON_ORE,
                    Items.DEEPSLATE_IRON_ORE,
                    Items.RAW_IRON
            );
            //
            setMaterial(registerMetalTool(new Identifier(MOD_ID, "gold"), 6, 1064),
                    Items.GOLD_INGOT,
                    Items.GOLD_ORE,
                    Items.DEEPSLATE_GOLD_ORE,
                    Items.RAW_GOLD
            );
            //
            setMaterial(registerMelting(new Identifier(MOD_ID, "low_roast"), 4, 70),
                    Items.COD,
                    Items.SALMON,
                    Items.POTATO,
                    Items.RABBIT,
                    Items.KELP
            );
            //
            setMaterial(registerMelting(new Identifier(MOD_ID, "medium_roast"), 20, 120),
                    Items.PORKCHOP,
                    Items.BEEF,
                    Items.CHICKEN,
                    Items.MUTTON
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