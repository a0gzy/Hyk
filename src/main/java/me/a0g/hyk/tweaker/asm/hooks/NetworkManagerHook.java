package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.events.ReceivePacketEvent;
import me.a0g.hyk.tweaker.asm.utils.ReturnValue;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class NetworkManagerHook {

    public static void onReceivePacket(Packet<?> packet){
        MinecraftForge.EVENT_BUS.post((Event)new ReceivePacketEvent(packet));
    }
}
