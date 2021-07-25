package me.a0g.hyk.utils;

import lombok.Getter;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.Render;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class NewScheduler {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private final Object anchor = new Object();
    private volatile long totalTicks = 0;

    public synchronized long getTotalTicks() {
        return this.totalTicks;
    }

    @SubscribeEvent
    public void ticker(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            synchronized (this.anchor) {
                this.totalTicks++;
            }

            if (Minecraft.getMinecraft() != null) {

               /* if (main.getUtils().checkForSkyblock()) {
                   // main.getDiscordRPCManager().clearPresence();
                    main.getDiscordRPCManager().stop2();
                    return;
                }*/

               // main.getUtils().setRPCGame();
                if(main.getHyConfig().isRpc()) {

                    try {
                        /*if (main.getDiscordRPCManager().isActive()) {
                         *//*FMLLog.info(main.getDiscordRPCManager().details);
                        FMLLog.info(main.getDiscordRPCManager().state);
                        FMLLog.info(main.getDiscordRPCManager().smallImageKey);*//*

                        main.getDiscordRPCManager().updatePresence2();
                        //main.getDiscordRPCManager().updatePresence();
                    } else {
                        //main.getDiscordRPCManager().start();
                        main.getDiscordRPCManager().start2();
                    }
*/
                        if (!main.getDiscordRPCManager().isActive()) {
                            main.getDiscordRPCManager().start2();
                        }

                    } catch (Throwable ex) {
                        FMLLog.info("NE srabotalo");
                    }
                }
                else {

                    try {
                        if (main.getDiscordRPCManager().isActive()) {
                            main.getDiscordRPCManager().stop2();
                        }
                    } catch (Throwable ex) {
                        FMLLog.info("NE vikl");
                    }
                }


            }
            /*else {
                try {
                    if (main.getDiscordRPCManager().isActive()) {
                        main.getDiscordRPCManager().stop2();
                    }
                } catch (Throwable ex) {
                    FMLLog.info("NE srabotalo");
                }
            }*/

        }

    }



}
