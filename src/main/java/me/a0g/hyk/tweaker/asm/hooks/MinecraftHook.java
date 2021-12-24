package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.Hyk;
import net.minecraft.client.Minecraft;

public class MinecraftHook{

    private static Hyk main = Hyk.getInstance();

    public static void hitfix() {
        if(main.getHyConfig().isHitfix()){
            Minecraft.getMinecraft().leftClickCounter = 0;
        }
    }

    public static void disableRpc(){
        if (main.getDiscordRPCManager().isActive()) {
            System.out.println("HYK RPC STOPPING");
            main.getDiscordRPCManager().stop2();
        }
    }

}
