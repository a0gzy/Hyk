package me.a0g.hyk.utils;

import me.a0g.hyk.Hyk;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NewScheduler {

    private final Hyk main = Hyk.getInstance();

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

                if(main.getHyConfig().isRpc()) {

                    try {
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

        }

    }



}
