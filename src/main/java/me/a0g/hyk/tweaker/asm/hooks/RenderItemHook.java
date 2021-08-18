package me.a0g.hyk.tweaker.asm.hooks;

import jline.internal.Log;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.utils.ItemUtils;
import me.a0g.hyk.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class RenderItemHook {
    private static HypixelKentik main = HypixelKentik.getInstance();

    public static void renderRarity(ItemStack itemStack, int xPosition, int yPosition) {

        if(main.getHyConfig().isSbrarity()){
            main.getUtils().renderRarity(itemStack, xPosition, yPosition);

        }

    }

    public static void renderE(ItemStack itemStack, int xPosition, int yPosition) {

        if(main.getHyConfig().isSbrarity() && main.getHyConfig().isSbIsEnch()){

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

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || main.isBinShow() ) {
            try {
                if (itemStack != null && itemStack.hasTagCompound() ) {
                    List<String> lores = ItemUtils.getItemLore(itemStack);
                    for (String lore : lores) {
                        lore = TextUtils.stripColor(lore);
                        if (lore.startsWith("Buy it now: ")) {


                            long price = main.getNumberFormatter().parse(StringUtils.substringBetween(lore, ": ", " coins")).longValue();
                            String priceEachString = main.getUtils().nicenumbers((int) price).replaceAll("[.]0","");
                            /*if(priceEachString.contains(".0")){
                                priceEachString.replaceAll(".0","");
                            }*/
                           // priceEachString = main.getUtils().formatNumbers(priceEachString);


                            GlStateManager.pushMatrix();
                            GlStateManager.disableLighting();
                            GlStateManager.disableDepth();


                            new TextRenderer(Minecraft.getMinecraft(), priceEachString, xPosition, yPosition + 3, 0.5D, Color.ORANGE.getRGB(),true);

                            GlStateManager.enableDepth();
                            GlStateManager.enableBlend();
                            GlStateManager.popMatrix();

                            //return;

                        }
                    }
                }
            } catch (ParseException ex) {
                //  return;
            }
        }


    }

}
