package ru.DmN.core.utils;

import net.minecraft.util.math.MathHelper;

public class ColorUtils {
    public static int calcColorWithEnergy(long energy, long maxEnergy) {
        return MathHelper.hsvToRgb(Math.max(0.0F, (float) energy / maxEnergy) / 3.0F, 1.0F, 1.0F);
    }

    public static int calcColorWithEnergy(long energy, long maxEnergy, float saturation, float value) {
        return MathHelper.hsvToRgb(Math.max(0.0F, (float) energy / maxEnergy) / 3.0F, saturation, value);
    }

    public static int calcStepWithEnergy(long energy, long maxEnergy) {
        return 13 - Math.round(13F - energy * 13F / maxEnergy);
    }
}