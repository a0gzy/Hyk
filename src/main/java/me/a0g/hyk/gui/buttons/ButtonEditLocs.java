package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonEditLocs extends GuiButton {

    private static ResourceLocation elocs = new ResourceLocation("hyk", "gui/move.png");

    private HypixelKentik main = HypixelKentik.getInstance();

    /**
     * Create a button for toggling a feature on or off.
     */
    public ButtonEditLocs(double x, double y) {
        super(0, (int)x, (int)y, null);
        this.width = 30;
        this.height = 30;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {

            hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GlStateManager.enableBlend();
            GlStateManager.color(1,1,1,0.7F);
            if (hovered) {
                GlStateManager.color(1,1,1,1);
            }

            mc.getTextureManager().bindTexture(elocs);
            drawModalRectWithCustomSizedTexture(xPosition, yPosition,0,0,width,height,width,height);

        }
    }

}
