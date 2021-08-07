package me.a0g.hyk.chest;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jline.internal.Log;
import me.a0g.hyk.HypixelKentik;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.UUID;
import java.util.logging.Logger;

public class Player {

    private final HypixelKentik main = HypixelKentik.getInstance();

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if(main.getHyConfig().isPsp()){
            for(Object e: Minecraft.getMinecraft().theWorld.loadedEntityList){
                if(e instanceof EntityPlayer && !(e instanceof EntityPlayerSP)) {

                    if(NameT.renderFromTeam((EntityPlayer)e)) {
                        Player.entityESPBox((Entity) e, 0);
                    }
                }
            }
        }


    }

    ////////////////     size changer

    /*@SubscribeEvent
    public void preRenderPlayer(RenderPlayerEvent.Pre event) {
        if( main.getHyConfig().isSizer() && !(event.entityPlayer instanceof EntityPlayerSP)
                && (event.entityPlayer instanceof EntityPlayer || event.entityPlayer instanceof EntityPlayerMP) ) {

            if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(event.entityPlayer) < 5) {
                GlStateManager.pushMatrix();


                GL11.glScalef((float) Scale.sizerScale,(float)Scale.sizerScale,(float)Scale.sizerScale);
                GL11.glTranslatef(0.0F,(float)Scale.sizerScale,0.0F);
               // GlStateManager.scale(Scale.sizerScale, Scale.sizerScale, Scale.sizerScale);
               // GlStateManager.scale(1, Scale.sizerScale, 1);

            }

        }
    }

    @SubscribeEvent
    public void postRenderPlayer(RenderPlayerEvent.Post event) {
        if( main.getHyConfig().isSizer() && !(event.entityPlayer instanceof EntityPlayerSP)
                && (event.entityPlayer instanceof EntityPlayer || event.entityPlayer instanceof EntityPlayerMP) ) {
            if(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(event.entityPlayer) < 5) {

                GlStateManager.popMatrix();

            }
        }
    }*/


    ////////////////

    public static void entityESPBox(Entity entity, int mode)
    {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        if(mode == 0)// Enemy
            GL11.glColor4d(
                    1 - Minecraft.getMinecraft().thePlayer
                            .getDistanceToEntity(entity) / 40,
                    Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
                    0, 0.5F);
        else if(mode == 1)// Friend
            GL11.glColor4d(0, 0, 1, 0.5F);
        else if(mode == 2)// Other
            GL11.glColor4d(1, 1, 0, 0.5F);
        else if(mode == 3)// Target
            GL11.glColor4d(1, 0, 0, 0.5F);
        else if(mode == 4)// Team
            GL11.glColor4d(0, 1, 0, 0.5F);
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawSelectionBoundingBox(
                new AxisAlignedBB(
                        entity.getEntityBoundingBox().minX
                                - 0.05
                                - entity.posX
                                + (entity.posX - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosX),
                        entity.getEntityBoundingBox().minY
                                - entity.posY
                                + (entity.posY - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosY),
                        entity.getEntityBoundingBox().minZ
                                - 0.05
                                - entity.posZ
                                + (entity.posZ - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosZ),
                        entity.getEntityBoundingBox().maxX
                                + 0.05
                                - entity.posX
                                + (entity.posX - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosX),
                        entity.getEntityBoundingBox().maxY
                                + 0.1
                                - entity.posY
                                + (entity.posY - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosY),
                        entity.getEntityBoundingBox().maxZ
                                + 0.05
                                - entity.posZ
                                + (entity.posZ - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosZ)));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
