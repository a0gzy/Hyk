package me.a0g.hyk.utils;

import lombok.Getter;
import me.a0g.hyk.HypixelKentik;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.awt.*;

public class ChromaManager {

    private static HypixelKentik main = HypixelKentik.getInstance();

    @Getter private static boolean coloringTextChroma;
    private static float featureScale;

    private static MutableFloat chromaSpeed = new MutableFloat(0.19354838F); // 2.0
    private static MutableFloat chromaFadeWidth = new MutableFloat(0.22580644F); // 10° Hue

    private static float[] defaultColorHSB = {0, 0.75F, 0.9F};

    /**
     * Before rending a string that supports chroma, call this method so it marks the text
     * to have the color fade applied to it.<br><br>
     *
     * After calling this & doing the drawString, make sure to call {@link ChromaManager#doneRenderingText()}.
     *
     */
    public static void renderingText() {

            coloringTextChroma = true;
            featureScale = 1.0F;
            //featureScale = SkyblockAddons.getInstance().getConfigValues().getGuiScale(feature);
    }

    public static int getChromaColor(float x, float y, int alpha) {
        return getChromaColor(x, y, defaultColorHSB, alpha);
    }

    public static int getChromaColor(float x, float y, float[] currentHSB, int alpha) {
        x *= featureScale;
        y *= featureScale;

        float chromaWidth = main.getUtils().denormalizeScale(chromaSpeed.getValue(), 1, 43, 0.3f) / 360F;
        float chromaSpeed = main.getUtils().denormalizeScale(chromaFadeWidth.getValue(), 0.1F, 10, 0.5F) / 360F;

       // long ticks = SkyblockAddons.getInstance().getNewScheduler().getTotalTicks();
//        long ticks = System.currentTimeMillis() % 1000L / 1000L;
        long ticks = main.getNewScheduler().getTotalTicks();

        float newHue = (x / 2.7F * chromaWidth + y / 2.1F * chromaWidth - ticks * chromaSpeed) % 1;

        if (currentHSB[2] < 0.3) { // Keep shadows as shadows
            return setColorAlpha(Color.HSBtoRGB(newHue, currentHSB[1], currentHSB[2]), alpha);
        } else {
            return setColorAlpha(Color.HSBtoRGB(newHue, defaultColorHSB[1], defaultColorHSB[2]), alpha);
        }
    }

    /**
     * Disables any chroma stuff.
     */
    public static void doneRenderingText() {
        coloringTextChroma = false;
    }

    public static int setColorAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00FFFFFF);
    }
}
