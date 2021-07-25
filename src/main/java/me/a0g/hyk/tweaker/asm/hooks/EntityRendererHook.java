package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.HypixelKentik;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.FloatBuffer;

public class EntityRendererHook {

    private static HypixelKentik main = HypixelKentik.getInstance();

    public static void setupFog(int startCoords, float partialTicks) {

        /*if(main.getHyConfig().isAntiblidness()){

            return;
        }*/

        if(main.getHyConfig().isAntiblidness()){

            EntityRenderer er = Minecraft.getMinecraft().entityRenderer;
            Minecraft mc = Minecraft.getMinecraft();

            Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
            boolean flag = false;

            if (entity instanceof EntityPlayer)
            {
                flag = ((EntityPlayer)entity).capabilities.isCreativeMode;
            }

            GL11.glFog(GL11.GL_FOG_COLOR, (FloatBuffer)er.setFogColorBuffer(er.fogColorRed, er.fogColorGreen, er.fogColorBlue, 1.0F));
            GL11.glNormal3f(0.0F, -1.0F, 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entity, partialTicks);

            float hook = net.minecraftforge.client.ForgeHooksClient.getFogDensity(er, entity, block, partialTicks, 0.1F);
            if (hook >= 0)
                GlStateManager.setFogDensity(hook);
            else
            if (er.cloudFog)
            {
                GlStateManager.setFog(2048);
                GlStateManager.setFogDensity(0.1F);
            }
            else if (block.getMaterial() == Material.water)
            {
                GlStateManager.setFog(2048);

                if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
                {
                    GlStateManager.setFogDensity(0.01F);
                }
                else
                {
                    GlStateManager.setFogDensity(0.1F - (float) EnchantmentHelper.getRespiration(entity) * 0.03F);
                }
            }
            else if (block.getMaterial() == Material.lava)
            {
                GlStateManager.setFog(2048);
                GlStateManager.setFogDensity(2.0F);
            }
            else
            {
                float f = er.farPlaneDistance;
                GlStateManager.setFog(9729);

                if (startCoords == -1)
                {
                    GlStateManager.setFogStart(0.0F);
                    GlStateManager.setFogEnd(f);
                }
                else
                {
                    GlStateManager.setFogStart(f * 0.75F);
                    GlStateManager.setFogEnd(f);
                }

                if (GLContext.getCapabilities().GL_NV_fog_distance)
                {
                    GL11.glFogi(34138, 34139);
                }

                if (mc.theWorld.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ))
                {
                    GlStateManager.setFogStart(f * 0.05F);
                    GlStateManager.setFogEnd(Math.min(f, 192.0F) * 0.5F);
                }
                net.minecraftforge.client.ForgeHooksClient.onFogRender(er, entity, block, partialTicks, startCoords, f);
            }

            GlStateManager.enableColorMaterial();
            GlStateManager.enableFog();
            GlStateManager.colorMaterial(1028, 4608);

        }
        if(main.getHyConfig().isAntifog()) {
            GlStateManager.disableFog();
        }

        return;
    }

}
