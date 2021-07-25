package me.a0g.hyk.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class TextRenderer extends Gui {

    public TextRenderer(Minecraft mc, String text, int x, int y, double scale) {
        double scaleReset = Math.pow(scale, -1);

        GL11.glScaled(scale, scale, scale);
        y -= mc.fontRendererObj.FONT_HEIGHT;
        for (String line : text.split("\n")) {
            y += mc.fontRendererObj.FONT_HEIGHT * scale;

            mc.fontRendererObj.drawString(line, (int) Math.round(x / scale), (int) Math.round(y / scale), 0xFFFFFF, true);
        }
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
    }

    public TextRenderer(Minecraft mc, String text, int x, int y, double scale,int color) {
        double scaleReset = Math.pow(scale, -1);

        GL11.glScaled(scale, scale, scale);
        y -= mc.fontRendererObj.FONT_HEIGHT;
        for (String line : text.split("\n")) {
            y += mc.fontRendererObj.FONT_HEIGHT * scale;
            mc.fontRendererObj.drawString(line, (int) Math.round(x / scale), (int) Math.round(y / scale), color, true);
        }
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
    }

    public TextRenderer(Minecraft mc, String text, int x, int y, double scale, int color, int height) {
        double scaleReset = Math.pow(scale, -1);

        GL11.glScaled(scale, scale, scale);
        y -= height;
        for (String line : text.split("\n")) {
            y += height * scale;
            mc.fontRendererObj.drawString(line, (int) Math.round(x / scale), (int) Math.round(y / scale), color, true);
        }
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
    }

}
