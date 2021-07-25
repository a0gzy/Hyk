package me.a0g.hyk.tweaker.asm.hooks;

import jline.internal.Log;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class RenderItemHook {
    private static HypixelKentik main = HypixelKentik.getInstance();

    public static void renderRarity(ItemStack itemStack, int xPosition, int yPosition) {

        if(main.getHyConfig().isSbrarity()){
            main.getUtils().renderRarity(itemStack, xPosition, yPosition);

        }

    }

    public static void renderE(ItemStack itemStack, int xPosition, int yPosition) {

        if(main.getHyConfig().isSbrarity()){

            if (itemStack != null && itemStack.hasTagCompound()) {
                if (itemStack.getDisplayName().contains("Enchanted")) {

                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();


                    new TextRenderer(Minecraft.getMinecraft(),"E",xPosition,yPosition + 3,0.7D,0xFF00FFFF);

                    GlStateManager.enableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.popMatrix();

                }
            }
        }

    }

}
