package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.core.Feature;
import lombok.Getter;
import me.a0g.hyk.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
public class ButtonResize extends ButtonFeature  {

    private static final int SIZE = 2;

    private Hyk main = Hyk.getInstance();

    private Corner corner;

    public float x;
    public float y;

    private float cornerOffsetX;
    private float cornerOffsetY;

    public ButtonResize(float x, float y, Feature feature, Corner corner) {
        super(0, 0, 0, "", feature);
        this.corner = corner;
        this.x = x;
        this.y = y;
    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        float scale = feature.getScale();
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,1);

        hovered = mouseX >= (x- SIZE)*scale && mouseY >= (y- SIZE)*scale && mouseX < (x+ SIZE)*scale && mouseY < (y+ SIZE)* scale;
        int color = hovered ? Color.WHITE.getRGB() : ColorUtils.setColorAlpha(Color.GRAY.getRGB(),70);
        //DrawUtils.drawRectAbsolute(x- SIZE,y- SIZE, x+ SIZE, y+ SIZE, color);
        drawRect((int) x- SIZE,(int)y- SIZE, (int)x+ SIZE, (int)y+ SIZE, color);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale;

        cornerOffsetX = floatMouseX;
        cornerOffsetY = floatMouseY;

        return hovered;
    }

    public enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }
}
