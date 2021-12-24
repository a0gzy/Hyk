package me.a0g.hyk.chest;

import me.a0g.hyk.Hyk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemSkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Presents {

    private List<BlockPos> blockPoss = new ArrayList<>();

    public Presents(){

      //  blockPoss.add(new BlockPos(-382,88,-225));
       // blockPoss.add(new BlockPos(-188, 79, -346));
      //  blockPoss.add(new BlockPos(-274, 99, -176));
       // blockPoss.add(new BlockPos(-296, 36, -268));
      //  blockPoss.add(new BlockPos(-348, 64, -202));
       // blockPoss.add(new BlockPos(-370, 88, -242));
        //blockPoss.add(new BlockPos(-354, 72, -285));
      //  blockPoss.add(new BlockPos(-301, 70, -316));
     //   blockPoss.add(new BlockPos(-204, 62, -301));
      //  blockPoss.add(new BlockPos(-181, 50, 252));
     //   blockPoss.add(new BlockPos(-234, 50, -237));
      //  blockPoss.add(new BlockPos(-355, 85, -211));
        blockPoss.add(new BlockPos(-300, 49, -218));
        blockPoss.add(new BlockPos(-282, 48, -234));
        blockPoss.add(new BlockPos(-298, 50, -254));
        blockPoss.add(new BlockPos(-273, 63, -272));
        blockPoss.add(new BlockPos(-252, 56, -279));
        blockPoss.add(new BlockPos(-270, 47, -291));
        blockPoss.add(new BlockPos(-311, 68, -249));
        blockPoss.add(new BlockPos(-311, 57, -248));
        blockPoss.add(new BlockPos(-282, 48, -232));
        blockPoss.add(new BlockPos(-326, 49, -236));
        blockPoss.add(new BlockPos(-215, 58, -302));
        blockPoss.add(new BlockPos(-225, 70, -314));

       // blockPoss.remove(new BlockPos(-225,70,-314));
       // blockPoss.remove(new BlockPos(-317, 68, -271));

    }


    private final Hyk main = Hyk.getInstance();
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if(main.getHyConfig().isPresentssp()){
            for(Object o: Minecraft.getMinecraft().theWorld.loadedEntityList){
                if(o instanceof EntityArmorStand) {
                    if (main.getUtils().checkForSkyblock()) {
                         if(((EntityArmorStand) o).getEquipmentInSlot(4) != null) {
                                if (((EntityArmorStand) o).getEquipmentInSlot(4).getItem() instanceof ItemSkull) {

                                    String message34 = "";
                                    if( ((EntityArmorStand) o).getEquipmentInSlot(4).hasTagCompound()) {
                                        message34 = ((EntityArmorStand) o).getEquipmentInSlot(4).getTagCompound().getCompoundTag("SkullOwner").getTag("Id") + "";
                                    }
                                    if (message34.contains("7732c5e4-1800-3b90-a70f-727d2969254b")) {

                                        Presents.entityESPBox((EntityArmorStand) o, 3);

                                    }
                                }
                         }
                    }
                }
            }
            if(main.getUtils().checkForSkyblock()) {
                ChestTest.blockESPBox(new BlockPos(-178, 135, -297), 0xFFFFFFFF);

                //22 [-341, 122, -252] 23 [-145, 82, -335] 24 [-317, 68, -271] 27 [-178, 135, -297]

                /*new BlockPos(-300, 49, -218);
                new BlockPos(-282, 48, -234);
                new BlockPos(-298, 50, -254);
                new BlockPos(-273, 63, -272);
                new BlockPos(-252, 56, -279);
                new BlockPos(-270, 47, -291);
                new BlockPos(-311, 68, -249);
                new BlockPos(-311, 57, -248);
                new BlockPos(-282, 48, -232);
                new BlockPos(-326, 49, -236);
                new BlockPos(-215, 58, -302);
                new BlockPos(-225, 70, -314);*/

               /* for (BlockPos bp : blockPoss) {
                    ChestTest.blockESPBox(bp, 0xFFFFFFFF);
                }*/
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
