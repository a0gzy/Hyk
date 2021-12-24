package me.a0g.hyk.utils;

import lombok.Getter;
import lombok.Setter;
import me.a0g.hyk.Hyk;

import me.a0g.hyk.events.Cakes;
import me.a0g.hyk.events.Render;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.handlers.ScoreboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public final String MESSAGE_PREFIX = EnumChatFormatting.DARK_AQUA + "[" + EnumChatFormatting.LIGHT_PURPLE + "HYK" + EnumChatFormatting.DARK_AQUA + "] " + EnumChatFormatting.RESET;
    private final Hyk main = Hyk.getInstance();

    private boolean depthEnabled;
    private boolean blendEnabled;
    private boolean alphaEnabled;
    private int blendFunctionSrcFactor;
    private int blendFunctionDstFactor;

    @Getter
    @Setter
    private boolean fadingIn;

    public void sendMessage(String text, boolean prefix) {
        ChatComponentText message = new ChatComponentText((prefix ? MESSAGE_PREFIX : "") + text);
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public void sendMessage(String text) {
        sendMessage(text, true);
    }

    public void sendDataMessage(String text) {

        int maxwidth = 1;

        String[] sda = text.split("\n");
        for(String line : sda){

            line = TextUtils.removeDuplicateSpaces(line) ;
            line = TextUtils.stripColor(line);

            FMLLog.info(line.length() + "");

            if(line.length() > maxwidth){
                maxwidth = line.length();
            }
        }

        if(maxwidth > 50){
            maxwidth = 50;
        }
        //maxwidth = maxwidth - 7;

        String eti =  StringUtils.repeat("-",maxwidth);

        String content =
                EnumChatFormatting.STRIKETHROUGH + eti + "\n" +
                        text + "\n" +
                        EnumChatFormatting.STRIKETHROUGH + eti;
        sendMessage(content, false);
    }

    public void sendClickableMessage(String text, String command, String hovertext) {
        IChatComponent clickableText = new ChatComponentText(MESSAGE_PREFIX + text);
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)).setChatHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hovertext)));
        clickableText.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(clickableText);
    }

    public void sendLink(String text, String link) {
        IChatComponent clickableText = new ChatComponentText(text);
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        clickableText.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(clickableText);
    }

    public void sendHoverMessage(String text, String hovertext) {
        IChatComponent clickableText = new ChatComponentText(text);
        ChatStyle style = new ChatStyle().setChatHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hovertext)));
        clickableText.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage(clickableText);
    }


    public void drawOnSlot(int size, int xSlotPos, int ySlotPos, int colour) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int guiLeft = (sr.getScaledWidth() - 176) / 2;
        int guiTop = (sr.getScaledHeight() - 222) / 2;
        int x = guiLeft + xSlotPos;
        int y = guiTop + ySlotPos;
        // Move down when chest isn't 6 rows
        if (size != 90) y += (6 - (size - 36) / 9) * 9;

        GL11.glTranslated(0, 0, 1);
        Gui.drawRect(x, y, x + 16, y + 16, colour);
        GL11.glTranslated(0, 0, -1);
    }

    public void clearRenderDesign(){
//        Render.todesign = null;
        Render.todesignpage = 1;
        Render.todesignp = null;
        Render.todesignsb = null;
        Render.todesignsw = null;
        Render.todesignbw = null;
    }

    public void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, boolean linearTexture) {
        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();

        if (linearTexture) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }
    }

    public void enableStandardGLOptions() {
        depthEnabled = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        blendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
        alphaEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        blendFunctionSrcFactor = GL11.glGetInteger(GL11.GL_BLEND_SRC);
        blendFunctionDstFactor = GL11.glGetInteger(GL11.GL_BLEND_DST);

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1, 1);
    }

    public void restoreGLOptions() {
        if (depthEnabled) {
            GlStateManager.enableDepth();
        }
        if (!alphaEnabled) {
            GlStateManager.disableAlpha();
        }
        if (!blendEnabled) {
            GlStateManager.disableBlend();
        }
        GlStateManager.blendFunc(blendFunctionSrcFactor, blendFunctionDstFactor);
    }

    public void renderItem(ItemStack item, float x, float y, float z) {

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();


        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(item, 0, 0);
        GlStateManager.popMatrix();

        GlStateManager.disableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    public static void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void renderArmor(ItemStack item, float x, float y, float z) {

        GlStateManager.pushMatrix();

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();

		/*GlStateManager.disableDepth();
		if(item != null && item.isItemEnchanted() && item.getMaxDamage() > 0  ){
			GlStateManager.enableDepth();
		}
		if(item.getItem().getRegistryName().contains("skull")){
			GlStateManager.enableDepth();
		}*/

        GlStateManager.translate(x, y, z);
        FontRenderer font = null;
        if (item != null) {
            font = item.getItem().getFontRenderer(item);
        }
        if (font == null) {
            font = Minecraft.getMinecraft().fontRendererObj;
        }
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(item, 0, 0);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(font, item, 0, 0, "");

//		GlStateManager.enableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();

        GlStateManager.popMatrix();
    }

    public ArrayList<String> getTabList(){
        if(Minecraft.getMinecraft().thePlayer == null && Minecraft.getMinecraft().theWorld == null)
            return null;
        Minecraft mc = Minecraft.getMinecraft();

        NetHandlerPlayClient netHandler = mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> fullList = GuiPlayerTabOverlay.field_175252_a.sortedCopy(netHandler.getPlayerInfoMap());
        GuiPlayerTabOverlay tabList = Minecraft.getMinecraft().ingameGUI.getTabList();

        ArrayList<String> tab = new ArrayList<>();

        for (int entry = 0; entry < fullList.size(); entry ++) {
            tab.add(tabList.getPlayerName(fullList.get(entry)));
        }
        return tab;
    }

    public boolean checkHollows(){
        return getTabList().contains("Crystal Hollows");
    }

    public String getCommissions(Minecraft mc){
        if(!main.getUtils().checkForSkyblock()) return "";

        NetHandlerPlayClient netHandler = mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> fullList = GuiPlayerTabOverlay.field_175252_a.sortedCopy(netHandler.getPlayerInfoMap());
        GuiPlayerTabOverlay tabList = Minecraft.getMinecraft().ingameGUI.getTabList();

        String allcomms = "";
        String powder = "";
        String comms = "";
        String ability = "";

        for (int entry = 0; entry < fullList.size(); entry ++) {

            if (tabList.getPlayerName(fullList.get(entry)).contains("Commissions")) {

                comms = TextUtils.stripColor(tabList.getPlayerName(fullList.get(entry)));

                for (int i = 1; i < 5; i++) {
                    String torender = tabList.getPlayerName(fullList.get(entry + i));
                    if (torender.contains("%") || torender.contains("DONE")) {
                        allcomms = allcomms + torender + "\n";
                    }
                }
            } else if (tabList.getPlayerName(fullList.get(entry)).contains("Mithril Powder:")) {
                powder = tabList.getPlayerName(fullList.get(entry));

                // Ability KD
                final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
                if (pl.getHeldItem() != null) {
                    if (Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore") != null) {
                        if (Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore").toString().contains("Cooldown:")) {
                            if (Cakes.newUse < System.currentTimeMillis() / 1000L) {
                                ability = EnumChatFormatting.GREEN + "Ability: §cReady";
                            } else
                                ability = EnumChatFormatting.GREEN + "Ability: §c" + (int) (Cakes.newUse - System.currentTimeMillis() / 1000L) + "s";

                        }
                    }
                }
            }
            else if(tabList.getPlayerName(fullList.get(entry)).contains("Gemstone Powder:")) {
                powder = powder + "\n" + tabList.getPlayerName(fullList.get(entry));
            }
        }
        allcomms = "§z§l" + comms + "\n" + allcomms + "\n" + powder + "\n" + ability;


        return allcomms;
    }

    public void createTitle(String text, int seconds) {
        Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, (float) 0.5);
        Hyk.titleTimer = seconds * 20;
        Hyk.showTitle = true;
        Hyk.titleText = text;
    }

    public void drawTitle(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        int height = scaledResolution.getScaledHeight();
        int width = scaledResolution.getScaledWidth();
        int drawHeight = 0;
        String[] splitText = text.split("\n");
        for (String title : splitText) {
            int textLength = mc.fontRendererObj.getStringWidth(title);

            double scale = 4;
            if (textLength * scale > (width * 0.9F)) {
                scale = (width * 0.9F) / (float) textLength;
            }

            int titleX = (int) ((width / 2) - (textLength * scale / 2));
            int titleY = (int) ((height * 0.45) / scale) + (int) (drawHeight * scale);
            new TextRenderer(mc, title, titleX, titleY, scale);
            drawHeight += mc.fontRendererObj.FONT_HEIGHT;
        }
    }

    public boolean checkForSkyblock() {
        if(main.isDev) return true;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
                if (scObjName.contains("SKYBLOCK")) {
                    //inSkyblock = true;
                    return true;
                }
            }
        }
        return false;
    }

    public String getGuiName(GuiScreen gui) {
        if (gui instanceof GuiChest)
            return ((ContainerChest)((GuiChest)gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
        return "";
    }

    public String getInventoryName() {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null)
            return "null";
        return ((Slot)Minecraft.getMinecraft().thePlayer.openContainer.inventorySlots.get(0)).inventory.getName();
    }

    public boolean checkForGame(String minigame) {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
                //FMLLog.info(scObjName);
                if (scObjName.contains(minigame)) {
                    //inSkyblock = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void setRPCGame() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && main.getDiscordRPCManager() != null && mc.getCurrentServerData() != null) {
           // mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
            main.getDiscordRPCManager().details =  mc.thePlayer.getName() + " -> " + mc.getCurrentServerData().serverIP;
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());

                String isMurder = "";

                if(scObjName.toLowerCase().replaceAll(" ","").equalsIgnoreCase("skywars")){
                    main.getDiscordRPCManager().smallImageKey = "skywars";
                    main.getDiscordRPCManager().smallImageText = "skywars";
                }
                else if (scObjName.toLowerCase().replaceAll(" ","").equalsIgnoreCase("murdermystery")){
                    String mRole = getScoreboardStringWith("Role: ");
                    if(mRole != null) {
                        if (mRole.toLowerCase().contains("murder")) isMurder = "! ";
                        else if (mRole.toLowerCase().contains("detective")) isMurder = "% ";
                    }
                }
                else {
                    main.getDiscordRPCManager().smallImageKey = "";
                    main.getDiscordRPCManager().smallImageText = "";
                }

                String map = getScoreboardStringWith("Map: ");
                if(map == null){ map = "";}
                else {map = " on "+ map.replace("Map: ","");}

                String isNt = main.getHyConfig().isNamet() ? "* " : "";


                main.getDiscordRPCManager().state =isMurder + isNt + "Playing " + capitalizeEvery( scObjName )  + map ;
            }
            else {
                main.getDiscordRPCManager().state = "AFK";
                main.getDiscordRPCManager().smallImageKey = "";
                main.getDiscordRPCManager().smallImageText = "";
            }
        }
        /*else {
            main.getDiscordRPCManager().details = "" ;
            main.getDiscordRPCManager().state = "";
            main.getDiscordRPCManager().smallImageKey = "";
            main.getDiscordRPCManager().smallImageText = "";
        }*/
        else {
            main.getDiscordRPCManager().details = mc.getSession().getUsername()  + " -> 1.8.9" ;
            main.getDiscordRPCManager().state = "Menu";
            main.getDiscordRPCManager().smallImageKey = "";
            main.getDiscordRPCManager().smallImageText = "";
        }
    }

    public String capitalizeEvery(final String text){
        int strLen;
        if (text == null || text.length() == 0) {
            return text;
        }
        String capitalized = "";
        String[] strings = text.toLowerCase().split(" ");
        for(int i = 0;i<strings.length;i++){
            char firstChar = strings[i].charAt(0);
            if(i == strings.length - 1){
                capitalized += Character.toTitleCase(firstChar) + strings[i].substring(1);
            }
            else capitalized += Character.toTitleCase(firstChar) + strings[i].substring(1) + " ";
        }

        return capitalized;
    }

    public static String getJavaRuntime() throws IOException {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator +
                (os != null && os.toLowerCase(Locale.ENGLISH).startsWith("windows") ? "java.exe" : "java");
        if (!new File(java).isFile()) {
            throw new IOException("Unable to find suitable java runtime at "+java);
        }
        return java;
    }

    public boolean checkForIsland() {

        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("Your Island")) {
                    return true;
                }
            }
        return false;
    }

    public boolean checkScoreboardString(String text) {

        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
        for (String s : scoreboard) {
            String sCleaned = ScoreboardHandler.cleanSB(s);
            if (sCleaned.contains(text)) {
                return true;
            }
        }
        return false;
    }

    public String getScoreboardStringWith(String text) {

        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
        for (String s : scoreboard) {
            String sCleaned = ScoreboardHandler.cleanSB(s);
            if (sCleaned.contains(text)) {
                return sCleaned;
            }
        }
        return null;
    }

    public boolean checkForDungeons() {
        if(main.isDev) return true;
        if (checkForSkyblock()) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardHandler.cleanSB(s);
                if (sCleaned.contains("The Catacombs")) {

                    return true;
                }
            }
        }
        return false;
    }

    public float denormalizeScale(float value, float min, float max, float step) {
        return snapToStepClamp(min + (max - min) *
                MathHelper.clamp_float(value, 0.0F, 1.0F), min, max, step);
    }

    private float snapToStepClamp(float value, float min, float max, float step) {
        value = step * (float) Math.round(value / step);
        return MathHelper.clamp_float(value, min, max);
    }

    private final ResourceLocation RARITY = new ResourceLocation("hyk:gui/rarity.png");
    private final Pattern PATTERN = Pattern.compile("(?<color>\\u00a7[0-9a-fk-or]).+");
    private final Pattern PET_PATTERN = Pattern.compile("\\u00a77\\[Lvl \\d+\\] (?<color>\\u00a7[0-9a-fk-or]).+");

    public void renderRarity(ItemStack itemStack, int xPos, int yPos)
    {
        if (itemStack != null && itemStack.hasTagCompound())
        {
            NBTTagCompound compound = itemStack.getTagCompound().getCompoundTag("display");
            NBTTagCompound extra = itemStack.getTagCompound().getCompoundTag("ExtraAttributes");
            String displayName = compound.getString("Name");
            String id = extra.getString("id");
            boolean upgrade = extra.hasKey("rarity_upgrades");

            if (extra.hasKey("id"))
            {
                if (id.equals("PARTY_HAT_CRAB"))
                {
                    SBRarity rarity = upgrade ? SBRarity.COMMON.getNextRarity() : SBRarity.COMMON;
                    renderRarity(xPos, yPos, rarity);
                    return;
                }
                if (id.equals("SKYBLOCK_MENU") || id.contains("GENERATOR") || id.contains("RUNE"))
                {
                    if (compound.getTagId("Lore") == Constants.NBT.TAG_LIST)
                    {
                        NBTTagList list = compound.getTagList("Lore", Constants.NBT.TAG_STRING);

                        if (list.tagCount() > 0)
                        {
                            for (int j1 = 0; j1 < list.tagCount(); ++j1)
                            {
                                String lore = list.getStringTagAt(j1);

                                if (lore.contains("COSMETIC")) // temp
                                {
                                    renderRarity(xPos, yPos, SBRarity.byBaseColor(lore.charAt(0) + "" + lore.charAt(1)));
                                }
                            }
                        }
                    }
                    return;
                }

                if (displayName.startsWith("\u00a7f\u00a7f"))
                {
                    displayName = displayName.substring(4);

                    if (displayName.matches("\\u00a7[0-9a-fk-or]\\d.*"))
                    {
                        displayName = displayName.replaceAll("\\u00a7[0-9a-fk-or]\\d.*x \\u00a7f", "");
                    }
                }

                Matcher mat = PATTERN.matcher(displayName);

                if (mat.matches())
                {
                    renderRarity(xPos, yPos, SBRarity.byBaseColor(mat.group("color")));
                }

                /*Matcher mat1 = PET_PATTERN.matcher(displayName);

                if (mat1.matches())
                {
                    renderRarity(xPos, yPos, SBRarity.byBaseColor(mat1.group("color")));
                }*/
            }
           /* else
            {
                Matcher mat1 = PET_PATTERN.matcher(displayName);

                if (mat1.matches())
                {
                    renderRarity(xPos, yPos, SBRarity.byBaseColor(mat1.group("color")));
                }
            }*/
        }
    }

    private void renderRarity(int xPos, int yPos, SBRarity rarity)
    {
        if (rarity != null)
        {
            float alpha = main.getHyConfig().getSbrarityalpha() / 100.0F;
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            Minecraft.getMinecraft().getTextureManager().bindTexture(RARITY);
            GlStateManager.color(rarity.getColorToRender().getRed()/255.0F, rarity.getColorToRender().getGreen()/255.0F, rarity.getColorToRender().getBlue()/255.0F, alpha);
            GlStateManager.blendFunc(770, 771);
            GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND);
            Gui.drawModalRectWithCustomSizedTexture(xPos, yPos, 0, 0, 16, 16, 16, 16);
            GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.disableAlpha();
        }
    }

    /*public String formatNumbers(String niceNumber){
        if(niceNumber.contains(".0")){
            niceNumber.replace(".0","");
        }
        return niceNumber;
    }*/

    public String nicenumbers(int number){

        if(number >= 1000000000){
            double smnum = Math.round(number / 100000000) / 10.0;

            return smnum + "B";
        }
        else if (number >= 1000000) {

            double smnum = Math.round(number / 100000) / 10.0;

            return smnum + "M";
        } else if (number >= 1000) {

            //12333
            //123
            // 12.3

            double smnum = Math.round(number / 100) / 10.0;

            return smnum + "k";
        } else {
            return number + "";
        }
    }

    public int getRGBColor() {
        return Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000.0f, 0.8f, 0.8f);
    }

    public int getRGBDarkColor() {
        return Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000.0f, 0.8f, 0.2f);
    }

    public String getTimeBetween(double timeOne, double timeTwo) {
        double secondsBetween = Math.floor(timeTwo - timeOne);

        String timeFormatted;
        int days;
        int hours;
        int minutes;
        int seconds;

        if (secondsBetween > 86400) {
            // More than 1d, display #d#h
            days = (int) (secondsBetween / 86400);
            hours = (int) (secondsBetween % 86400 / 3600);
            timeFormatted = days + "d" + hours + "h";
        } else if (secondsBetween > 3600) {
            // More than 1h, display #h#m
            hours = (int) (secondsBetween / 3600);
            minutes = (int) (secondsBetween % 3600 / 60);
            timeFormatted = hours + "h" + minutes + "m";
        } else {
            // Display #m#s
            minutes = (int) (secondsBetween / 60);
            seconds = (int) (secondsBetween % 60);
            timeFormatted = minutes + "m" + seconds + "s";
        }

        return timeFormatted;
    }



    public void drawText(String text, float x, float y, int color, boolean style) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        if (style) {
            int colorAlpha = Math.max(getAlpha(color), 4);
            int colorBlack = new Color(0, 0, 0, colorAlpha / 255F).getRGB();
            String strippedText = TextUtils.stripColor(text);
            fontRenderer.drawString(strippedText, x + 1, y + 0, colorBlack, false);
            fontRenderer.drawString(strippedText, x + -1, y + 0, colorBlack, false);
            fontRenderer.drawString(strippedText, x + 0, y + 1, colorBlack, false);
            fontRenderer.drawString(strippedText, x + 0, y + -1, colorBlack, false);
            fontRenderer.drawString(text, x + 0, y + 0, color, false);
        } else {
            fontRenderer.drawString(text, x + 0, y + 0, color, true);
        }
    }

    public int getAlpha(int color) {
        return color >> 24 & 0xFF;
    }
}
