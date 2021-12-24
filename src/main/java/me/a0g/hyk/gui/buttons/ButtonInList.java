package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.core.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonInList extends GuiButton {

    public int xL;
    public int yL;
    public int widthL;
    public int heightL;
    public String textL;

    public ButtonInList(int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.xL = x;
        this.yL = y;
        this.widthL = width;
        this.heightL = height;
        this.textL = buttonText;
    }

    public void drawButtonL(Minecraft mc, int mouseX, int mouseY) {

    }
}
