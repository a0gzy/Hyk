package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class MinecraftHook{

    private static HypixelKentik main = HypixelKentik.getInstance();

    public static void hitfix() {

        if(main.getHyConfig().isHitfix()){

            Minecraft.getMinecraft().leftClickCounter = 0;

        }

    }

}
