package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.core.Feature;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.utils.ColorUtils;
import me.a0g.hyk.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ButtonSlider extends ButtonInList{

    private float currentValue;
    private float minValue;
    private float maxValue;
    private int sliderWidth;
    public Feature feature;

    private boolean hoverSlider;

    public ButtonSlider(int buttonId, int x, int y, int width, int height, float minValue, float maxValue, int sliderWidth , String buttonText, Feature feature) {
        super(buttonId, x, y, width, height, buttonText);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.sliderWidth = sliderWidth;
        this.feature = feature;
    }

    @Override
    public void drawButtonL(Minecraft mc, int mouseX, int mouseY) {

        String textt = String.format(textL + ": %.2f",feature.floatValue  );

        new TextRenderer(mc,textt,xL + 3,yL+ 3,1);



        drawSlider(xL + 10 + mc.fontRendererObj.getStringWidth(String.format(textL + ": %.2f",maxValue)),yL + 5);
        //DrawUtils.drawRect(xL + widthL - 15, yL  + 3, 10, 10, 0xA4FF0000, 4); //R


        //Minecraft.getMinecraft().gameSettings.guiScale

        checkHoveredAndDrawBox(xL,xL + widthL,yL,yL+heightL);

    }

    public void drawSlider(float sliderX1,float sliderY1){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

        //float slPercent = sliderWidth / 10;
        float minMax = maxValue - minValue;

        float curValueX = sliderX1 + ((feature.floatValue - minValue )/ minMax) * sliderWidth;

       // new TextRenderer(Minecraft.getMinecraft(),feature.floatValue + "",50,60,1);

       // DrawUtils.drawRect(sliderX1,sliderX1 + sliderWidth ,sliderY1,sliderY1 + 5,Color.BLACK.getRGB(),2);
      //  DrawUtils.drawRect(sliderX1,curValueX ,sliderY1,sliderY1 + 5,Color.GREEN.getRGB(),2);

        //slider back
        drawRect((int)sliderX1,(int)sliderY1,(int)sliderX1 + sliderWidth,(int)sliderY1 + 5,Color.BLACK.getRGB());
        //slider now
        drawRect((int)sliderX1, (int)sliderY1 , (int)curValueX,(int)sliderY1 + 5,0xA600c206);
        //slider current square
        drawRect((int)curValueX - 3,(int)sliderY1-1,(int)curValueX+3,(int)sliderY1+6,Color.GREEN.getRGB());

        new TextRenderer(Minecraft.getMinecraft(),minValue + "",(int)sliderX1,(int)sliderY1+8,0.6);
        new TextRenderer(Minecraft.getMinecraft(),maxValue + "",(int)sliderX1 - (int)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(maxValue + "") * 0.6) + sliderWidth,(int)sliderY1+8,0.6);

        hoverSlider = floatMouseX >= sliderX1 && floatMouseY >= sliderY1 + 3 && floatMouseX <= sliderX1 + sliderWidth && floatMouseY <= sliderY1 + 5;


        //hovering slider
        if(hoverSlider) {
            if (Mouse.isButtonDown(0)) {
                feature.floatValue = minValue + ( minMax * (( floatMouseX - sliderX1) / sliderWidth) );
            }
        }
    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

        hovered = floatMouseX >= boxXOne && floatMouseY >= boxYOne && floatMouseX < boxXTwo && floatMouseY < boxYTwo;

        int boxAlpha = 70;
        if (hovered) {
            boxAlpha = 120;
        }
        int boxColor = ColorUtils.setColorAlpha(Color.GRAY.getRGB(),boxAlpha);
        drawRect( (int)boxXOne, (int)boxYOne, (int)boxXTwo,(int) boxYTwo, boxColor);
        //DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);



    }

}
