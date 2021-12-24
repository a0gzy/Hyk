package me.a0g.hyk.gui;

import lombok.Getter;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.gui.buttons.ButtonLocation;
import me.a0g.hyk.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class LocationButton extends GuiButton {

    @Getter private int x;
    @Getter private int y;
    private float scale;
    private String text;

    private float boxXOne;
    private float boxXTwo;
    private float boxYOne;
    private float boxYTwo;

    public LocationButton(int buttonId, int x, int y, float scale, String text) {
        super(buttonId, x, y, text);
        this.x = x;
        this.y = y;
        this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        this.scale = scale;
        this.text = text;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        new TextRenderer(mc, text, x, y, scale);
        checkHoveredAndDrawBox(x,x + width,y,y+height, scale);

    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo, float scale) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

        hovered = floatMouseX >= boxXOne * scale && floatMouseY >= boxYOne * scale && floatMouseX < boxXTwo * scale && floatMouseY < boxYTwo * scale;
        int boxAlpha = 70;
        if (hovered) {
            boxAlpha = 120;
        }
        int boxColor = ColorUtils.setColorAlpha(Color.GRAY.getRGB(),boxAlpha);
        drawRect( (int)boxXOne, (int)boxYOne, (int)boxXTwo,(int) boxYTwo, boxColor);
        //DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);

        this.boxXOne = boxXOne;
        this.boxXTwo = boxXTwo;
        this.boxYOne = boxYOne;
        this.boxYTwo = boxYTwo;
        this.scale = scale;
    }

    @Override
    public void playPressSound(SoundHandler soundHandler) {

    }
}
