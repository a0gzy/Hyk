package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.utils.ColorUtils;
import me.a0g.hyk.utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class ButtonList extends GuiButton {

    public int x;
    public int y;
    private int width;
    private int height;
    private String text;

    private boolean covered;
    public boolean hover;
    public boolean hoverd;

    private ArrayList<ButtonInList> buttons;
    //private int buttonsWidth;
    private int buttonsHeight;



    public ButtonList(int buttonId, int x, int y, int width, int height, String buttonText, ArrayList<ButtonInList> buttons) {
        super(buttonId, x, y, buttonText);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = buttonText;
        this.buttons = buttons;

        for (ButtonInList button : buttons) {
            buttonsHeight += 10 + button.heightL;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {





        if(!covered) {
            for (ButtonInList button : buttons) {
                button.xL = x;
                button.yL = y + this.height + (buttons.indexOf(button) * 20) ;
                button.drawButtonL(mc, mouseX, mouseY);

            }

            // DrawUtils.drawRect(x,y,width,height,0xA4000000,4);

           // DrawUtils.drawRect(x, y + this.height, this.width, buttonsHeight, 0xA40d0d0d, 4);
        }

        if(covered){
            new TextRenderer(mc,"v " + this.text,this.x + 5,this.y + 5,1);
        }
        else {
            new TextRenderer(mc,"^ " + this.text,this.x + 5,this.y + 5,1);
        }


        //new TextRenderer(mc, text, x, y, scale);
        checkHoveredAndDrawBox(this.x,this.x + this.width,this.y,this.y + this.height);

    }

    public void checkHoveredAndDrawBox(float boxXOne, float boxXTwo, float boxYOne, float boxYTwo) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (Minecraft.getMinecraft().displayHeight - Mouse.getY()) / minecraftScale;

       // new TextRenderer(Minecraft.getMinecraft(),floatMouseX  +" " + floatMouseY,50,50,1);

        hovered = floatMouseX >= boxXOne && floatMouseY >= boxYOne && floatMouseX < boxXTwo && floatMouseY < boxYTwo;
        this.hover = floatMouseX >= boxXOne && floatMouseY >= boxYOne && floatMouseX < boxXTwo && floatMouseY < boxYTwo;
        this.hoverd = floatMouseX >= boxXOne && floatMouseY >= boxYOne  && floatMouseX < boxXOne + 20  && floatMouseY < boxYOne + 20;
        int boxAlpha = 70;
        if (hovered) {
            boxAlpha = 120;

        }
        int boxColor = ColorUtils.setColorAlpha(Color.DARK_GRAY.getRGB(),boxAlpha);
        drawRect( (int)boxXOne, (int)boxYOne, (int)boxXTwo,(int) boxYTwo, boxColor);
        //DrawUtils.drawRectAbsolute(boxXOne, boxYOne, boxXTwo, boxYTwo, boxColor);

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY)
    {
        if(this.hoverd){
            covered = !covered;
        }
    }

}
