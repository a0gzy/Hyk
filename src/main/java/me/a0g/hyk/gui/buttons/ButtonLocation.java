package me.a0g.hyk.gui.buttons;

import lombok.Getter;
import me.a0g.hyk.core.Feature;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.utils.ColorUtils;
import me.a0g.hyk.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
public class ButtonLocation extends ButtonFeature {

    private int x;
    private int y;

    private String text;

    private Feature feature;

    private float scale;
    private float boxXOne;
    private float boxXTwo;
    private float boxYOne;
    private float boxYTwo;

    public boolean isHovered = false;

    public ButtonLocation(Feature feature){
        super(-1,feature.getPosTweak().x,feature.getPosTweak().y,feature.getText(),feature);
        this.x = feature.getPosTweak().x;
        this.y = feature.getPosTweak().y;
       // this.scale = feature.getPosTweak().scale;
        this.text = feature.getText();
        this.feature = feature;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
       // float scale = feature.getScale();
        /*GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1);*/

        feature.drawForPos(mc,this);

        //new TextRenderer(mc,text, x, y, (double) scale);
       // checkHoveredAndDrawBox(feature.getPosTweak().x,feature.getPosTweak().x + width,feature.getPosTweak().y,feature.getPosTweak().y+height,feature.getPosTweak().scale);
       // GlStateManager.popMatrix();
    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo, float scale) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

        hovered = floatMouseX >= boxXOne * scale && floatMouseY >= boxYOne * scale && floatMouseX < boxXTwo * scale && floatMouseY < boxYTwo * scale;
        isHovered = floatMouseX >= boxXOne * scale && floatMouseY >= boxYOne * scale && floatMouseX < boxXTwo * scale && floatMouseY < boxYTwo * scale;
        int boxAlpha = 70;
        if (hovered) {
            boxAlpha = 120;
        }
        int boxColor = ColorUtils.setColorAlpha(Color.GRAY.getRGB(),boxAlpha);

       /* GlStateManager.pushMatrix();
        GlStateManager.scale(feature.getPosTweak().scale, feature.getPosTweak().scale, 1);
        drawRect( (int)boxXOne, (int)boxYOne, (int)boxXTwo,(int) boxYTwo, boxColor);
        GlStateManager.popMatrix();*/

        GlStateManager.pushMatrix();
        GlStateManager.scale(feature.getPosTweak().scale, feature.getPosTweak().scale, 1);
        DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);
        GlStateManager.popMatrix();

        //DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);

        this.boxXOne = boxXOne;
        this.boxXTwo = boxXTwo;
        this.boxYOne = boxYOne;
        this.boxYTwo = boxYTwo;
        this.scale = scale;
    }

    /**
     * Because the box changes with the scale, have to override this.
     */
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && hovered;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {}



}
