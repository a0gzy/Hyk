package me.a0g.hyk.gui.buttons;

import javafx.scene.input.MouseButton;
import lombok.Getter;
import me.a0g.hyk.core.Feature;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.utils.ColorUtils;
import me.a0g.hyk.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Map;

public class ButtonSwitch extends ButtonInList {

    private boolean hover;
    public Feature feature;

    private long lastDown;

    @Getter private boolean on;

    public ButtonSwitch(int buttonId, int x, int y, int width, int height, String buttonText, Feature feature) {
        super(buttonId,x,y,width,height,buttonText);
        this.feature = feature;
    }

    @Override
    public void drawButtonL(Minecraft mc, int mouseX, int mouseY) {

        new TextRenderer(mc,textL,xL + 3,yL+ 3,1);

        if(feature.isOn) {
            DrawUtils.drawRect(xL + widthL - 15, yL + 3, 10, 10, 0xA400FF00, 4); //G
        }
        else {
            DrawUtils.drawRect(xL + widthL - 15, yL  + 3, 10, 10, 0xA4FF0000, 4); //R
        }

        //Minecraft.getMinecraft().gameSettings.guiScale

        checkHoveredAndDrawBox(xL,xL + widthL,yL,yL+heightL);

    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

        hovered = floatMouseX >= boxXOne && floatMouseY >= boxYOne && floatMouseX < boxXTwo && floatMouseY < boxYTwo;
        hover = floatMouseX >= boxXOne + widthL - 15 && floatMouseY >= boxYOne + 3 && floatMouseX < boxXTwo - 5 && floatMouseY < boxYTwo - 2;
        int boxAlpha = 70;
        if (hovered) {
            boxAlpha = 120;
        }
        int boxColor = ColorUtils.setColorAlpha(Color.GRAY.getRGB(),boxAlpha);
        drawRect( (int)boxXOne, (int)boxYOne, (int)boxXTwo,(int) boxYTwo, boxColor);
        //DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);

        if(hover) {
            if (Mouse.isButtonDown(0) && lastDown + 300 < System.currentTimeMillis()) {
                lastDown = System.currentTimeMillis();
                //on = !on;
                feature.isOn = !feature.isOn;
            }
        }

    }




}
