package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

public class MinecraftHook{

    private static HypixelKentik main = HypixelKentik.getInstance();

    public static void hitfix() {
        if(main.getHyConfig().isHitfix()){
            Minecraft.getMinecraft().leftClickCounter = 0;
        }
    }

    public static void disableRpc(){
        if (main.getDiscordRPCManager().isActive()) {
          //  FMLLog.info("");
            System.out.println("HYK RPC STOPPING");
            main.getDiscordRPCManager().stop2();
        }
    }

}
