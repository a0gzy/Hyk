package me.a0g.hyk.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OneTimeJoinMessage {

    private final IChatComponent component;

    public OneTimeJoinMessage(final IChatComponent component) {
        this.component = component;
    }

    @SubscribeEvent
    public void onJoinWorld(final EntityJoinWorldEvent event) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return;
        }

        if (!(event.entity instanceof EntityPlayerSP)) {
            return;
        }

        event.entity.addChatMessage(this.component);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void register() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public IChatComponent getComponent() {
        return this.component;
    }

}
