package me.a0g.hyk.chest;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Skull {

    private final HypixelKentik main = HypixelKentik.getInstance();

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if(main.getHyConfig().isSkullsp()){
            for(Object o: Minecraft.getMinecraft().theWorld.loadedTileEntityList){
                if(o instanceof TileEntitySkull) {
                    SkullESPBox(((TileEntitySkull)o).getPos());
                }
            }
        }
    }

    public static void SkullESPBox(BlockPos blockPos)
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
        GL11.glColor4d(0, 0, 1, 0.5F);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x + 0.15, y, z+0.15,
                x + 0.85, y + 0.55, z + 0.85));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
