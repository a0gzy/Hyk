package me.a0g.hyk.chest;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;

import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ChestTest {

    private final HypixelKentik main = HypixelKentik.getInstance();

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if(main.getHyConfig().isChestsp()){
            for(Object o: Minecraft.getMinecraft().theWorld.loadedTileEntityList){
                if(o instanceof TileEntityChest) {
                    ChestTest.blockESPBox(((TileEntityChest)o).getPos(),0xFF0000FF);
                }
            }
        }
    }

    public static void blockESPBox(BlockPos blockPos,int color)
    {
        double x =
                blockPos.getX()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosX; //renderPosX;
        double y =
                blockPos.getY()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosY; //renderPosY;
        double z =
                blockPos.getZ()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosZ; //renderPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d(0, 1, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // color
        float red = ((float)(color >> 16 & 255) / 255.0F);
        float green = (float)(color >> 8 & 255) / 255.0F;;
        float blue = (float)(color & 255) / 255.0F;;
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        GL11.glColor4d(red , green, blue , alpha );

        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z,
                x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
