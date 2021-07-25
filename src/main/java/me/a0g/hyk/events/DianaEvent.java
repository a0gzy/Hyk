package me.a0g.hyk.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class DianaEvent {

    private final HypixelKentik main = HypixelKentik.getInstance();
    JsonArray info = new JsonArray();

    @SubscribeEvent
    public void onWorld(EntityJoinWorldEvent ev) {
        if (main.getHyConfig().isDiana()) {

            if (Minecraft.getMinecraft().theWorld != null && ev.entity.isEntityEqual(Minecraft.getMinecraft().thePlayer) && Minecraft.getMinecraft().thePlayer != null) {

                FMLLog.info("world getted ... ");
                Minecraft mc = Minecraft.getMinecraft();
                EntityPlayerSP player = mc.thePlayer;
                WorldClient wolrd = mc.theWorld;

                FMLLog.info("ia zashel ... ");

                info = new JsonArray();

                new Thread(() -> {

                    try {
                        Thread.sleep(5000);
                        if (wolrd != mc.theWorld || player != mc.thePlayer) return;
                        if (!wolrd.playerEntities.contains(player)) return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!main.getUtils().checkForSkyblock()) return;
                    FMLLog.info("sb eto ... ");
                    final String apikey = main.getHyConfig().getApikeyy();
                    if (apikey.isEmpty()) {
                        main.getUtils().sendMessage(EnumChatFormatting.RED + "API key isn't set - /api new , then /hapi");
                        return;
                    }

                    String uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");

                    String latestProfile = APIHandler.getLatestProfileID(uuid, apikey);
                    if (latestProfile == null) return;

                    String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apikey;
                    FMLLog.info("Fetching profile ... ");
                    JsonObject profileResponse = APIHandler.getResponse(profileURL);
                    if (!profileResponse.get("success").getAsBoolean()) {
                        String reason = profileResponse.get("cause").getAsString();
                        player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                        return;
                    }

                    JsonArray burrows = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("griffin").getAsJsonObject().get("burrows").getAsJsonArray();
                    if (burrows.size() == 0) {
                        player.addChatMessage(new ChatComponentText("no data."));
                        return;
                    }
                    main.getUtils().sendMessage(EnumChatFormatting.GREEN + "" + burrows.size() + " burrows registered.", true);

                    for (int j = 0; j < burrows.size(); j++) {
                        JsonObject pnss = new JsonObject();
                        double x = burrows.get(j).getAsJsonObject().get("x").getAsDouble();
                        double y = burrows.get(j).getAsJsonObject().get("y").getAsDouble();
                        double z = burrows.get(j).getAsJsonObject().get("z").getAsDouble();
                        int chain = burrows.get(j).getAsJsonObject().get("chain").getAsInt();
                        int type = burrows.get(j).getAsJsonObject().get("type").getAsInt();
                        FMLLog.info("xyz: " + x + " " + y + " " + z);

                        pnss.addProperty("x", x);
                        pnss.addProperty("y", y);
                        pnss.addProperty("z", z);
                        pnss.addProperty("chain", chain);
                        pnss.addProperty("type", type);

                        info.add(pnss);
                    }
                }).start();
            }
        }
    }

    static int tickAmount = 1;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;

        // Checks every second
        tickAmount++;
        if (tickAmount % 4 == 0) {
            if (player != null && world != null) {
                if (info.size() != 0) {
                    for (int j = 0; j < info.size(); j++) {
                        JsonObject buroow = info.get(j).getAsJsonObject();

                        if (player.getDistance(buroow.get("x").getAsDouble(), buroow.get("y").getAsDouble(), buroow.get("z").getAsDouble()) < 4) {

                            info = Arrayelementdelete(info, j);

                            FMLLog.info("removed" + buroow);
                            return;
                        }

                    }
                }
            }
        }
    }

    public JsonArray Arrayelementdelete(JsonArray old, int index){
        JsonArray neww = new JsonArray();

        if(old == null || index < 0 || index >= old.size()){
            return old;
        }

        for(int i = 0;i < old.size();i++){

            if(i == index){
                continue;
            }

            neww.add(old.get(i));
        }
        return neww;
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (main.getHyConfig().isDiana() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
            if (info.size() != 0) {
                for (int j = 0; j < info.size(); j++) {
                    if(info.get(j).isJsonNull()) return;
                    JsonObject buroow = info.get(j).getAsJsonObject();

                    double x = buroow.get("x").getAsDouble()  - Minecraft.getMinecraft().getRenderManager().viewerPosX;
                    double y = buroow.get("y").getAsDouble()  - Minecraft.getMinecraft().getRenderManager().viewerPosY;
                    double z = buroow.get("z").getAsDouble()  - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
                    int chain = buroow.get("chain").getAsInt();
                    int type = buroow.get("type").getAsInt();

                    double baseY =  0 - Minecraft.getMinecraft().getRenderManager().viewerPosY;

                    renderBeam3(x, baseY,z);

                    String displayTag = "";

                    if(chain==0) displayTag = "§a[1/4] " + "§b§lStart";
                    if(chain==1) displayTag = "§a[2/4] " +  displayTag;
                    if(chain==2) displayTag = "§a[3/4] " +  displayTag;
                    if(chain==3) displayTag = "§a[4/4] " +  displayTag;
                    if(type==1) displayTag = displayTag + "§c§lCreature";
                    if(type==2 || type==3) displayTag = displayTag + "§c§6Reward";

                    double distance = Math.sqrt(x * x + y * y + z * z);
                    displayTag = displayTag + " §r[" + (int)distance + "m]";

                    final double maxDistance = FMLClientHandler.instance().getClient().gameSettings.renderDistanceChunks * 12.0;
                    if (distance > maxDistance) {
                        x = x / distance * maxDistance;
                        y = y / distance * maxDistance;
                        z = z / distance * maxDistance;
                        distance = maxDistance;
                    }

                    final FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;

                    final float f2 =  ((float)distance * 0.15f + 1.0f) * 0.0266f;
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) x + 1.0f, (float) y + 0.0f, (float) z + 1.0f);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-f2, -f2, f2);


                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);//

                    if (distance < maxDistance) {
                        GlStateManager.depthMask(true);
                    }

                    final int i = fontrenderer.getStringWidth(displayTag) / 2;
                    final Tessellator tessellator = Tessellator.getInstance();
                    final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldrenderer.pos(-i - 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldrenderer.pos(-i - 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldrenderer.pos(i + 1, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldrenderer.pos(i + 1, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                    fontrenderer.drawString(displayTag, -fontrenderer.getStringWidth(displayTag) / 2, 0, 553648127);
                    GlStateManager.depthMask(true);
                    fontrenderer.drawString(displayTag, -fontrenderer.getStringWidth(displayTag) / 2, 0,  -1);

                    GlStateManager.enableDepth();
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public static void renderBeam3(double baseX, double baseY, double baseZ){
        double distance = Math.sqrt(baseX * baseX + baseY * baseY + baseZ * baseZ);
        final double maxDistance = FMLClientHandler.instance().getClient().gameSettings.renderDistanceChunks * 12.0;
        if (distance > maxDistance) {
            baseX = baseX / distance * maxDistance;
            baseY = baseY / distance * maxDistance;
            baseZ = baseZ / distance * maxDistance;
            distance = maxDistance;
        }

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        int r = 196 ;
        int g = 0;
        int b = 255;

        GlStateManager.color(r / 255.0F , g / 255.0F , b / 255.0F , 0.8F);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GlStateManager.disableTexture2D();

        GlStateManager.enableDepth();
        if (distance < maxDistance) {
            GlStateManager.depthMask(true);
        }
        GlStateManager.disableTexture2D();
        final Tessellator tesselatorr = Tessellator.getInstance();
        final WorldRenderer tesselator = tesselatorr.getWorldRenderer();

        int height = 256;
        double topWidthFactor = 1.05D;
        double bottomWidthFactor = 1.05D;

        for (int width = 0; width < 1; ++width)
        {
            tesselator.begin(5,DefaultVertexFormats.POSITION);

            double var32 = 0.1D + (double)width * 0.2D;
            var32 *= topWidthFactor;

            double var34 = 0.1D + (double)width * 0.2D;
            var34 *= bottomWidthFactor;

            for (int side = 0; side < 5; ++side)
            {
                double vertX2 = baseX + 0.5D - var32;
                double vertZ2 = baseZ + 0.5D - var32;

                if (side == 1 || side == 2)
                {
                    vertX2 += var32 * 2.0D;
                }

                if (side == 2 || side == 3)
                {
                    vertZ2 += var32 * 2.0D;
                }

                double vertX1 = baseX + 0.5D - var34;
                double vertZ1 = baseZ + 0.5D - var34;

                if (side == 1 || side == 2)
                {
                    vertX1 += var34 * 2.0D;
                }

                if (side == 2 || side == 3)
                {
                    vertZ1 += var34 * 2.0D;
                }

                tesselator.pos(vertX1, baseY + (double)(0), vertZ1).endVertex(); // 0 instead of base cause we want the beam to go from bottom to top, and now waypoints store Y
                tesselator.pos(vertX2, baseY + (double)(height), vertZ2).endVertex();
            }

            tesselatorr.draw();
        }
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

}
