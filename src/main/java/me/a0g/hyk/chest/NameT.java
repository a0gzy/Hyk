package me.a0g.hyk.chest;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class NameT {

    public static boolean colorN(final int n, final int b, final int v) {
        return n >= b && n <= v;
    }
    private final Hyk main = Hyk.getInstance();

   /* private final ResourceLocation capea0g = new ResourceLocation("hyk:testcape2.png");
    private final ResourceLocation capelo1d = new ResourceLocation("hyk:lo1dcape.png");
    private final ResourceLocation caperocel = new ResourceLocation("hyk:mycape1.png");*/

    int murder = 0;
    int detective = 0;

    @SubscribeEvent
    public void onWorld(EntityJoinWorldEvent ev) {
        if(Minecraft.getMinecraft().theWorld != null && ev.entity != null && ev.entity.isEntityEqual(Minecraft.getMinecraft().thePlayer)) murder = 0;
        if(Minecraft.getMinecraft().theWorld != null && ev.entity != null && ev.entity.isEntityEqual(Minecraft.getMinecraft().thePlayer)) detective = 0;
    }

    // ✯ ✯✯✯✯✯
    @SubscribeEvent
    public void onRender(final RenderLivingEvent.Pre event){

        if(main.getHyConfig().isDungeonstared()){
            if(Minecraft.getMinecraft().theWorld != null) {
                if(event.entity != null){
                    if(main.getUtils().checkForDungeons()) {
                        // e.getDisplayName().getUnformattedText().contains("Shadow Assassin")
                        if(event.entity instanceof EntityArmorStand) {
                            EntityArmorStand e = (EntityArmorStand) event.entity;
                            if (e.getDisplayName().getUnformattedText().contains("✯")) {
                                renderLivingTag(e, event.x, event.y, event.z, e.getDisplayName().getUnformattedText());
                            }
                        }
                        if(event.entity.getDisplayName().getUnformattedText().contains("Shadow Assassin")){
                            renderLivingTag(event.entity, event.x, event.y, event.z, event.entity.getDisplayName().getUnformattedText());
                        }
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent e) {
        if(main.getHyConfig().isNameTag()) {
            Minecraft mc = Minecraft.getMinecraft();

            Entity renderViewEntity = mc.getRenderViewEntity();

            for (EntityPlayer entity : mc.theWorld.playerEntities) {

                if (renderViewEntity == entity) {
                    continue;
                }

                double x = entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX;
                double y = entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY;
                double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ;


                renderTag(entity,x,y,z,entity.getDisplayName().getUnformattedText());
            }
        }
    }

    @SubscribeEvent
    public void onRender(final RenderPlayerEvent.Pre event){

        if(main.getHyConfig().isNamet() ){
          //  FMLLog.info("1");
            if(Minecraft.getMinecraft().theWorld != null) {
               // FMLLog.info("2");
                if (event.entityPlayer != null) {
                   // FMLLog.info("3");
                    EntityPlayer player = event.entityPlayer;

                    if (!event.entityPlayer.isEntityEqual(Minecraft.getMinecraft().thePlayer)) {
                        //FMLLog.info("4");
                       // if(player.getName().equalsIgnoreCase("a0g")) return;

                        if (main.getHyConfig().isNamet() || main.getUtils().checkForGame("BED WARS") || main.getUtils().checkForGame("MURDER")) {
                           // FMLLog.info("4");
                            renderTag(player, event.x, event.y, event.z, event.entityPlayer.getDisplayName().getUnformattedText());
                        }
                        if (renderFromTeam(player) && !main.getHyConfig().isNamemm()) {
                           // FMLLog.info("5");
                            renderTag(player, event.x, event.y, event.z, event.entityPlayer.getDisplayName().getUnformattedText());
                        }

                    }
                }
            }
        }
    }


    // Damage look
    @SubscribeEvent
    public void onRender(final RenderLivingEvent.Specials.Pre event){

        if(event.entity != null ) {
            Entity entity = event.entity;

            if (main.getHyConfig().isDamagesb() && main.getUtils().checkForSkyblock()) {
                if (event.entity instanceof EntityArmorStand) {

                    String customnametag = entity.getCustomNameTag();

                    // FMLLog.info(customnametag + "");
                    if (customnametag.matches("§[267]([0-9])+")) {
                        event.setCanceled(true);
                    }
                    if (customnametag.startsWith("§f✧§f")) {
                        String clearnametag = TextUtils.stripColor(customnametag); // ✧3076✧
                        clearnametag = clearnametag.replaceAll("✧", ""); //3076
                        clearnametag = clearnametag.replaceAll("❤", ""); //3076

                        //eFMLLog.info(clearnametag);

                        if (clearnametag.matches("[0-9]+")) {

                            String finnametag = "";

                            int number = Integer.parseInt(clearnametag);

                            if (number > 1000000) {

                                double smnum = Math.round(number / 100000) / 10.0;

                                finnametag = smnum + "M";
                            } else if (number > 1000) {

                                //12333
                                //123
                                // 12.3

                                double smnum = Math.round(number / 100) / 10.0;

                                finnametag = smnum + "k";
                            } else {
                                return;
                            }

                            finnametag = finnametag + "✧";

                            String cg = "";//f e 6 c c 6 e f

                            for (int i = 0; i < finnametag.length(); i++) {
                                if (i == 1 || i == 6) {
                                    cg = cg + "§e" + finnametag.charAt(i);
                                } else if (i == 2 || i == 5) {
                                    cg = cg + "§6" + finnametag.charAt(i);
                                } else if (i == 3 || i == 4) {
                                    cg = cg + "§c" + finnametag.charAt(i);
                                } else {
                                    cg = cg + "§f" + finnametag.charAt(i);
                                }
                            }

                            // FMLLog.info(cg + "");

                            String colorednametag = "§f✧" + cg;
                            entity.setCustomNameTag(colorednametag);

                        }
                    }
                }
            }

            if (main.getHyConfig().isNamet()) {

                if (Minecraft.getMinecraft().theWorld == null) return;

                if (event.entity instanceof EntityPlayer) {
                    if (renderFromTeam((EntityPlayer) event.entity)) {
                        event.setCanceled(true);
                    }
                }

            }
        }
    }

    private void renderTag(final EntityPlayer entity, final double x, final double y, final double z, String unformattedText) {
        String displayTag = unformattedText;

        if(displayTag.contains("§k")){
            displayTag = displayTag.replaceAll("§k","");
        }

        double distance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        float f2 =  ( (float)distance * 0.1f + main.getHyConfig().getNamescale() / 10.0f ) * 0.0266f;

        if(f2 < 0.02666667F){
            f2 = 0.02666667F;
        }


       // double distanceScale = Math.max(1, Minecraft.getMinecraft().thePlayer.getPositionVector().distanceTo(entity.getPositionVector()) / 10F);

        final FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;

        String h = EnumChatFormatting.GREEN.toString();
        String health = "";
        if(main.getHyConfig().isNameh()) {
            final int healthh = (int) Math.ceil(entity.getHealth() + entity.getAbsorptionAmount());
            if (colorN( healthh, 5, 10)) {
                h = " " + EnumChatFormatting.YELLOW;
            } else if (colorN(healthh, 0, 5)) {
                h = " " + EnumChatFormatting.RED;
            }
            health = healthh + "" + EnumChatFormatting.RED + "❤";
        }

        String item = "";

        if (entity.getHeldItem() != null) {

            if (main.getUtils().checkForGame("MURDER")) {

               /* ItemStack filledmap = new ItemStack(Item.getByNameOrId("filled_map"));
                ItemStack boww = new ItemStack(Item.getByNameOrId("bow"));*/

                //murder
                if (entity.getHeldItem().getTagCompound() != null && entity.getHeldItem().getTagCompound().getCompoundTag("ExtraAttributes") != null && entity.getHeldItem().getTagCompound().getCompoundTag("ExtraAttributes").toString().contains("KNIFE")) {

                    item = " " + EnumChatFormatting.DARK_RED + "KNIFE";
                    if (murder == 0) {
                        main.getUtils().sendMessage(EnumChatFormatting.DARK_RED + entity.getName() + " is murder");
                        murder = 1;
                    }
                }

                //detective
                if (entity.getHeldItem().getItem() instanceof ItemBow) {
                    if (detective == 0) {
                        main.getUtils().sendMessage(EnumChatFormatting.AQUA + entity.getName() + " is detective");
                        detective = 1;
                    }
                }

            }
        }

        String ld = EnumChatFormatting.RESET.toString();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0f, (float)y + entity.height + 0.9f, (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-f2, -f2, f2);
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0f, 9.374999f, 0.0f);
        }
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

       // GlStateManager.enableTexture2D();
       // GlStateManager.color(1, 1, 1, 1);
        //GlStateManager.enableAlpha();

        String torender = displayTag + h + " " + health + ld + item;
        int i = fontrenderer.getStringWidth(torender) / 2;
        if(main.getHyConfig().isNameback()) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-i - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos(-i - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos(i + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos(i + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        tessellator.draw();
        }

        GlStateManager.enableTexture2D();
        fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender ) / 2 , 0 , -1,true);
        //new TextRenderer(Minecraft.getMinecraft(),torender,-fontrenderer.getStringWidth(torender ) / 2 , 0,1,-1,true);
        //fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender ) / 2 + 1.0f, 0 + 1.0f, 553648127,true);

       // fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender) / 2, 0, -1);
       // fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender) / 2, 0, -1,true);

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    private void renderLivingTag(final EntityLivingBase entity, final double x, final double y, final double z, String unformattedText) {
        String displayTag = unformattedText;

        String distance = "";

        if(main.getHyConfig().isDungeonstareddistance()) {
            distance = (int) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) + "m";
        }


        final FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;


        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0f, (float)y + entity.height + 0.9f, (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.02, -0.02, 0.02);
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0f, 9.374999f, 0.0f);
        }
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        String torender = displayTag + EnumChatFormatting.RED + distance;

        final int i = fontrenderer.getStringWidth(torender) / 2;
        if(main.getHyConfig().isNameback()) {
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(-i - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos(-i - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos(i + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos(i + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
        }

        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender ) / 2, 0, 553648127);

        fontrenderer.drawString(torender, -fontrenderer.getStringWidth(torender) / 2, 0, -1);

        GlStateManager.enableDepth();

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }


    public static boolean renderFromTeam(EntityPlayer player) {
        Team team = player.getTeam();
        Team team1 = Minecraft.getMinecraft().thePlayer.getTeam();

        if (team != null) {
            Team.EnumVisible enumVisible = team.getNameTagVisibility();
            switch (enumVisible) {
                case ALWAYS:
                    return true;
                case NEVER:
                    return false;
                case HIDE_FOR_OTHER_TEAMS:
                    return team1 == null || team.isSameTeam(team1);
                case HIDE_FOR_OWN_TEAM:
                    return team1 == null || !team.isSameTeam(team1);
                default:
                    return true;
            }
        }
        else {
            return false;
        }
    }

}
