package me.a0g.hyk.chest;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemSkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class FairyTest {

    private final HypixelKentik main = HypixelKentik.getInstance();
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if( main.getHyConfig().isFairysp() ){
            for(Object o: Minecraft.getMinecraft().theWorld.loadedEntityList){
                if(o instanceof EntityArmorStand) {
                    if (main.getUtils().checkForSkyblock()) {
                         if(((EntityArmorStand) o).getEquipmentInSlot(4) != null) {
                                if (((EntityArmorStand) o).getEquipmentInSlot(4).getItem() instanceof ItemSkull) {

                                    String message34 = "";
                                    if( ((EntityArmorStand) o).getEquipmentInSlot(4).hasTagCompound()) {
                                        message34 = ((EntityArmorStand) o).getEquipmentInSlot(4).getTagCompound().getCompoundTag("SkullOwner").getTag("Id") + "";
                                    }
                                    if (message34.contains("57a4c8dc-9b8e-3d41-80da-a608901a6147")) {

                                        FairyTest.entityESPBox((EntityArmorStand) o, 5);

                                    }
                                }
                         }
                    }
                }
            }
        }
    }
    
    public static void entityESPBox(EntityArmorStand entity, int mode)
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
        else if(mode == 1)// blue
            GL11.glColor4d(0, 0, 1, 0.5F);
        else if(mode == 2)// Other
            GL11.glColor4d(1, 1, 0, 0.5F);
        else if(mode == 3)// red
            GL11.glColor4d(1, 0, 0, 0.5F);
        else if(mode == 4)// green
            GL11.glColor4d(0, 1, 0, 0.5F);
        else if(mode == 5)// purple
            GL11.glColor4d(1, 0, 1, 0.5F);
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawSelectionBoundingBox(
                new AxisAlignedBB(
                        entity.getEntityBoundingBox().minX
                                - 0.05
                                - entity.posX
                                + (entity.posX - Minecraft.getMinecraft()
                                .getRenderManager().viewerPosX),
                        entity.getEntityBoundingBox().minY
                                + 1.5
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
