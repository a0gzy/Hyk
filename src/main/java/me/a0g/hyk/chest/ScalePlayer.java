package me.a0g.hyk.chest;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScalePlayer {

    @SubscribeEvent
    public void preRenderPlayer(RenderPlayerEvent.Pre event) {
        if(event.entityPlayer instanceof EntityPlayerSP) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.25F, 0.25F, 0.25F);
        }
    }

    @SubscribeEvent
    public void postRenderPlayer(RenderPlayerEvent.Post event) {
        if(event.entityPlayer instanceof EntityPlayerSP) {
            GlStateManager.popMatrix();
        }
    }

}
