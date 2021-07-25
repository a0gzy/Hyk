package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.events.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ButtonText extends GuiButton {

    private int x;
    private int y;
    private double scale;
    private String text;

    private static ResourceLocation FEATURE_BACKGROUND = new ResourceLocation("hyk", "gui/featurebackground.png");


    public ButtonText(int buttonId, int x, int y, double width, double height, double scale, String text) {
        super(buttonId, x, y, text);
        this.x = x;
        this.y = y;
        this.width = (int) width;
        this.height = (int) height;
        this.scale = scale;
        this.text = text;
    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        int longestText = mc.fontRendererObj.getStringWidth(text);

        //drawRect(x - 2, y - 2, (int) (x + longestText * scale + 3), (int) (y * scale), 0x40D3D3D3);

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        mc.getTextureManager().bindTexture(FEATURE_BACKGROUND);
        Gui.drawModalRectWithCustomSizedTexture(x - 2, y - 2, 0, 0, (int) (longestText * scale + 3), 12, (int) (longestText * scale + 3), 12);

        if(hovered){
            new TextRenderer(mc, text, x, y, scale,0xFFFFAA38);
        }
        else{
            new TextRenderer(mc, text, x, y, scale);
        }
    }

}
