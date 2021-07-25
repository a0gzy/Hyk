package me.a0g.hyk.events;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.commands.HyK;
import me.a0g.hyk.commands.Move;
import me.a0g.hyk.commands.Scale;
import me.a0g.hyk.config.HyConfig;
import me.a0g.hyk.gui.EditLocationsGui;
import me.a0g.hyk.gui.HykGui;
import me.a0g.hyk.utils.ChromaManager;
import me.a0g.hyk.utils.NewScheduler;
import me.a0g.hyk.utils.TextUtils;
import me.a0g.hyk.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.settings.KeyBinding;
import org.fusesource.jansi.Ansi;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import scala.Int;


public class Render {

	private final HypixelKentik main = HypixelKentik.getInstance();
	private FontRenderer rendererer = Minecraft.getMinecraft().fontRendererObj;
	private Minecraft mc = Minecraft.getMinecraft();
	public static final ResourceLocation CAKE_ICON = new ResourceLocation("hyk", "cake.png");
//	public static String todesign = null;
	public static int todesignpage = 1;
	public static String todesignp = null;
	public static String todesignsb = null;
	public static String todesignsw = null;
	public static String todesignbw = null;
	public static boolean todesignrender = false;


	public static boolean farm = false;
	public static boolean farmstart = false;
	public static boolean farmclicksrender = false;

	public static boolean farmLegit = true;

	public static float Pitch = 9.0f;
	private long lasttick = 0;
	public static int farmclicks = 0;

	@SubscribeEvent
	public void renderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
		if ((event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE
	          || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR)) {

			if (Minecraft.getMinecraft().currentScreen instanceof EditLocationsGui) return;

			renderAll();

			if(farmclicksrender) {
				new TextRenderer(mc, "§aSkill/s§r: " + farmclicks, 140, 18, 1);
			}

			//new design
			design();

			if(main.getHyConfig().isAutogame()){
				new TextRenderer(mc,"§aAutoGaming§r\n" + Cakes.gamemsg,250,20,1);
			}

			if(HyK.ismuted){
				new TextRenderer(mc,"§bGame Muted",250,50,1);
			}

			if(main.getHyConfig().isXr()){
				new TextRenderer(mc,"X",5,5,1);
			}

			//testcolor
			//new TextRenderer(mc,"TestColor",300,300,1,main.getHyConfig().getTestcolor().getRGB());

			if(farm){
				new TextRenderer(mc,"§aFarming\n"
						+ "§bLegit: " + farmLegit
						+ "\n Y: "+ mc.thePlayer.getPosition().getY()
						+ "\n Yaw: " + mc.thePlayer.rotationYaw
						+ "\n Pitch: " + mc.thePlayer.rotationPitch,250,20,1);

				if(main.getUtils().checkForSkyblock() && main.getUtils().checkForIsland()){
					World world = mc.getMinecraft().theWorld;
					try {
						if(world.getBlockState(Minecraft.getMinecraft().thePlayer.getPosition().down()).getBlock() == Blocks.bedrock){
							stopfarm();
							return;
						}
						else {
						//	FMLLog.info( world.getBlockState(Minecraft.getMinecraft().thePlayer.getPosition().down()).getBlock().getLocalizedName() );
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
					if(farmstart || farmclicks > 0) {

						if (farmstart) {
							mc.thePlayer.rotationYaw = 180;
							mc.thePlayer.rotationPitch = Pitch;
						}

						if(mc.thePlayer.rotationPitch != Pitch || mc.thePlayer.rotationYaw != 180 ){
							stopfarm();
							return;
						}

						if (mc.thePlayer.getPosition().getY() % 2 == 0) {
							//right
							KeyBinding.setKeyBindState(32, true);
							KeyBinding.setKeyBindState(30, false);
						} else {
							//left
							KeyBinding.setKeyBindState(30, true);
							KeyBinding.setKeyBindState(32, false);
						}
						long totalticks = main.getNewScheduler().getTotalTicks();

						if(farmLegit) {
							if (totalticks != lasttick) {
								lasttick = totalticks;
								breakblock(world);
							}
						}
						else {
							breakblock(world);
						}
					}
					else {
						stopfarm();
						return;
					}

				}
				else {
					stopfarm();
					return;
				}

			}

			KeyBinding.setKeyBindState(getKeyCodeSprint(), main.getHyConfig().isAutoSprintEnabled());
	    }
	}

	private void breakblock(World world){
		if (world != null) {
			try {
				BlockPos targetblock = mc.objectMouseOver.getBlockPos();
				Block tb = world.getBlockState(targetblock).getBlock();
				if (tb == Blocks.potatoes || tb == Blocks.nether_wart || tb == Blocks.carrots) {
					//KeyBinding.setKeyBindState(-100,true); // lkm
					mc.thePlayer.swingItem();
					mc.playerController.clickBlock(targetblock, mc.objectMouseOver.sideHit);
					farmstart = false;
					//mc.playerController.clickBlock()
				}
			} catch (Exception e) {
			}
		}
	}

	public static void stopfarm(){
		KeyBinding.setKeyBindState(32,false);
		KeyBinding.setKeyBindState(30,false);

		farm = false;
		farmstart = false;
		return;
	}

	private void design(){

		if(todesignrender){
			ScaledResolution sr = new ScaledResolution(mc.getMinecraft());

			if(todesignpage == 1 && todesignp != null){

				int maxwidth = 1;

				String[] sda = todesignp.split("\n");
				for(String line : sda){
					if(rendererer.getStringWidth(line) > maxwidth){
						maxwidth = rendererer.getStringWidth(line);
					}
				}

				Gui.drawRect(sr.getScaledWidth()- maxwidth - 20,10, sr.getScaledWidth()-10,35 + rendererer.FONT_HEIGHT * sda.length,0x7a000000);

				new TextRenderer(mc,todesignp + "\n\n§ks§r§6"  + Keyboard.getKeyName( main.keyBindings[2].getKeyCode() ) + " §copen/close§r§ks",sr.getScaledWidth()-maxwidth - 15,15,1);

			}
			if(todesignpage == 2 && todesignsb != null){
				int maxwidth = 1;

				String[] sda = todesignsb.split("\n");
				for(String line : sda){
					if(rendererer.getStringWidth(line) > maxwidth){
						maxwidth = rendererer.getStringWidth(line);
					}
				}

				Gui.drawRect(sr.getScaledWidth()- maxwidth - 20,10, sr.getScaledWidth()-10,35 + rendererer.FONT_HEIGHT * sda.length,0x7a000000);

				new TextRenderer(mc,todesignsb + "\n\n§ks§r§6"  + Keyboard.getKeyName( main.keyBindings[2].getKeyCode() ) + " §copen/close§r§ks",sr.getScaledWidth()-maxwidth - 15,15,1);

			}
			if(todesignpage == 3 && todesignsw != null){
				int maxwidth = 1;

				String[] sda = todesignsw.split("\n");
				for(String line : sda){
					if(rendererer.getStringWidth(line) > maxwidth){
						maxwidth = rendererer.getStringWidth(line);
					}
				}

				Gui.drawRect(sr.getScaledWidth()- maxwidth - 20,10, sr.getScaledWidth()-10,35 + rendererer.FONT_HEIGHT * sda.length,0x7a000000);

				new TextRenderer(mc,todesignsw + "\n\n§ks§r§6"  + Keyboard.getKeyName( main.keyBindings[2].getKeyCode() ) + " §copen/close§r§ks",sr.getScaledWidth()-maxwidth - 15,15,1);

			}
			if(todesignpage == 4 && todesignbw != null){
				int maxwidth = 1;

				String[] sda = todesignbw.split("\n");
				for(String line : sda){
					if(rendererer.getStringWidth(line) > maxwidth){
						maxwidth = rendererer.getStringWidth(line);
					}
				}

				Gui.drawRect(sr.getScaledWidth()- maxwidth - 20,10, sr.getScaledWidth()-10,35 + rendererer.FONT_HEIGHT * sda.length,0x7a000000);

				new TextRenderer(mc,todesignbw + "\n\n§ks§r§6"  + Keyboard.getKeyName( main.keyBindings[2].getKeyCode() ) + " §copen/close§r§ks",sr.getScaledWidth()-maxwidth - 15,15,1);

			}
		}


		/*if(todesign != null && todesignrender){
			ScaledResolution sr = new ScaledResolution(mc.getMinecraft());

			int maxwidth = 1;

			String[] sda = todesign.split("\n");
			for(String line : sda){
				if(rendererer.getStringWidth(line) > maxwidth){
					maxwidth = rendererer.getStringWidth(line);
				}
			}

			Gui.drawRect(sr.getScaledWidth()- maxwidth - 20,10, sr.getScaledWidth()-10,35 + rendererer.FONT_HEIGHT * sda.length,0x7a000000);

			new TextRenderer(mc,todesign + "\n\n§ks§r§6"  + Keyboard.getKeyName( main.keyBindings[2].getKeyCode() ) + " §copen/close§r§ks",sr.getScaledWidth()-maxwidth - 15,15,1);
		}*/
	}

	public void renderAll(){

			Minecraft mc = Minecraft.getMinecraft();

			String allmodules ="";
			String priv = "";

		   	HyConfig hy = main.getHyConfig();

		   	if(hy.isTimed())
				allmodules = allmodules  + EnumChatFormatting.DARK_RED + getrenderTime() + "  " ;
		   	if(hy.isFpsd())
				allmodules = allmodules  + EnumChatFormatting.AQUA + "FPS " + Minecraft.getDebugFPS() + "  " ;
			if(hy.isAutoSprintEnabled())
				allmodules = allmodules + EnumChatFormatting.RESET + "Sprint " + EnumChatFormatting.BLACK + "  ";
			if(hy.isCpsd())
		   		allmodules = allmodules + EnumChatFormatting.RESET + "CPS: " + getPlayerLeftCPS() + "  ";

		   	if(hy.isPsp())
		   		priv = priv + EnumChatFormatting.RED + "Psp ";

		   	if(hy.isChestsp())
			   priv = priv + EnumChatFormatting.BLUE + "Chest ";

		   	if(hy.isNamet())
			   priv = priv + EnumChatFormatting.GREEN + "NameT ";

		   	if(hy.isFairysp())
			   priv = priv + EnumChatFormatting.LIGHT_PURPLE + "Fairy ";

		   	int x = 2;
		   	int y = 2;
		   	double scale = 1.0;

		   	new TextRenderer(mc,allmodules + "\n" + priv, Move.mainXY[0],Move.mainXY[1], Scale.mainScale);


			if(hy.isCakedisplay()) {
				cakes();
			}
			if(hy.isArmorh()) armorhud();

			if(hy.isCommsdispl()) renderCom();

	}

	private int getKeyCodeSprint() {
		return Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode();
	}

	private String getrenderTime() {

		   Date d = new Date();
		   SimpleDateFormat format1;
		   format1 = new SimpleDateFormat("HH:mm");

		   return format1.format(d);
	}

	private void armorhud() {

		boolean renderedback = false;

		for(int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {

			ItemStack armor = mc.thePlayer.inventory.armorInventory[item];
			if(armor == null){

			}
			else {
				int offset = (-16 * item) + 48;

				if(!renderedback && main.getHyConfig().isBackgroung() ) {

					GlStateManager.disableDepth();
					Gui.drawRect(Move.armorXY[0] - 2, Move.armorXY[1] - 2, Move.armorXY[0] + 18, Move.armorXY[1] + 64, 0x69000000);
					GlStateManager.enableDepth();

					renderedback = true;
				}

				main.getUtils().renderArmor(armor, Move.armorXY[0], Move.armorXY[1] + offset, -100);
			}

		}


	}

	/*private void puzzler(int x,int y){
		if(!main.getUtils().checkForSkyblock()) return;

		String newpuzzler = main.getHyConfig().getPuzzler();
		if (newpuzzler.isEmpty()) {
			return;
		}

		double timeNow = System.currentTimeMillis() / 1000;
		double puzzlertime = Long.parseLong(newpuzzler) / 1000;
		String fdate;
		if(timeNow < puzzlertime) fdate = "Puzzler: " + main.getUtils().getTimeBetween(timeNow,puzzlertime);
		else fdate = EnumChatFormatting.LIGHT_PURPLE + "You can use Puzzler";

		ChromaManager.renderingText();
		rendererer.drawString(fdate, x, y + 15, 0xBF5C1F35, true);
		ChromaManager.doneRenderingText();
	}*/

	private void cakes(){
		if(!main.getUtils().checkForSkyblock()) return;

		String cakeget = main.getHyConfig().getCakepicked();

		if (cakeget.isEmpty()) {
			return;
		}

		double timeNow = System.currentTimeMillis() / 1000;
		double caketime = (Long.parseLong(cakeget) + 172800000 ) / 1000 ;
		String fdate;
		if(timeNow < caketime) {
			fdate = EnumChatFormatting.LIGHT_PURPLE +  main.getUtils().getTimeBetween(timeNow, caketime);
		}
		else fdate = EnumChatFormatting.LIGHT_PURPLE + "You dont have cakes";

		main.getUtils().enableStandardGLOptions();
		Minecraft.getMinecraft().getTextureManager().bindTexture(CAKE_ICON);
		main.getUtils().drawModalRectWithCustomSizedTexture(Move.cakeXY[0], Move.cakeXY[1], 0, 0, 16, 16, 16, 16,true);
		main.getUtils().restoreGLOptions();

		//ChromaManager.renderingText();
		new TextRenderer(mc,fdate, Move.cakeXY[0] + 18, Move.cakeXY[1] + 3, Scale.mainScale);
//		rendererer.drawString(fdate, Move.cakeXY[0], Move.cakeXY[1], -1, true);
		//ChromaManager.doneRenderingText();

	}


	private final List<Long> leftClicks = new ArrayList<>();

	private boolean leftWasPressed = false;
	private long leftLastPressed;

	private int getPlayerLeftCPS() {

		final boolean pressed = Mouse.isButtonDown(0);

		if(pressed != leftWasPressed) {
			leftWasPressed = pressed;
			leftLastPressed = System.currentTimeMillis();
			if(pressed) {
				this.leftClicks.add(leftLastPressed);
			}
		}

		this.leftClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
		return this.leftClicks.size();
	}

	private void renderCom(){
		Minecraft mc = Minecraft.getMinecraft();

		int x = Move.commsXY[0];
		int y = Move.commsXY[1];
		double scale = Scale.mainScale;

		NetHandlerPlayClient netHandler = mc.thePlayer.sendQueue;
		List<NetworkPlayerInfo> fullList = GuiPlayerTabOverlay.field_175252_a.sortedCopy(netHandler.getPlayerInfoMap());

		GuiPlayerTabOverlay tabList = Minecraft.getMinecraft().ingameGUI.getTabList();

		String allcomms = "";
		String powder = "";
		String comms = "";
		String ability = "";

		for (int entry = 0; entry < fullList.size(); entry ++) {

			if(tabList.getPlayerName(fullList.get(entry)).contains("Commissions") && main.getUtils().checkForSkyblock()){

				comms = TextUtils.stripColor( tabList.getPlayerName(fullList.get(entry)) );

				for(int i = 1;i<5;i++){
					String torender = tabList.getPlayerName(fullList.get(entry + i));
					if( torender.contains("%") || torender.contains("DONE") ){

						//FMLLog.info(" Length: " +torender.length());

						allcomms = allcomms + torender + "\n";
						//rendererer.drawString(torender, x, y + i*mc.fontRendererObj.FONT_HEIGHT, -1, true);
					}
				}
			}

			if(tabList.getPlayerName(fullList.get(entry)).contains("Mithril Powder:") && main.getUtils().checkForSkyblock()){
				powder = tabList.getPlayerName(fullList.get(entry));


				// Ability KD
				final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
				if(pl.getHeldItem() != null) {
					if (Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore") != null) {
						if (Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore").toString().contains("Cooldown:")) {
							if(Cakes.newUse < System.currentTimeMillis()/1000L ){
								ability = EnumChatFormatting.GREEN + "Ability: §cReady";
							}
							else
							ability = EnumChatFormatting.GREEN + "Ability: §c" + (int)( Cakes.newUse - System.currentTimeMillis()/1000L) +"s";

						}
					}
				}
				//

				//rendererer.drawString(tabList.getPlayerName(fullList.get(entry)), x, y + 5*mc.fontRendererObj.FONT_HEIGHT, -1, true);
			}

		}
		allcomms = allcomms + "\n" + powder + "\n" + ability;

		if(allcomms.contains("Mithril") && main.getHyConfig().isBackgroung()){

			int length = 0;
			int maxlength = 1;

			for (String line : allcomms.split("\n")) {

				line = TextUtils.removeDuplicateSpaces(line) ;
				line = TextUtils.stripColor(line);

				if(line.length() > maxlength) {
					maxlength = line.length();
				}

				length++;
			}

			Gui.drawRect(x - 2,y - 2,x + (int)( (maxlength * 1.46 ) * 3.4),y + rendererer.FONT_HEIGHT + length * rendererer.FONT_HEIGHT + 1 ,0x69000000);
		}

		if(!comms.isEmpty()){
			//ChromaManager.renderingText();
			new TextRenderer(mc,"§z§l"+comms, x, y,scale);
			//ChromaManager.doneRenderingText();
		}

		new TextRenderer(mc,allcomms,x, y + mc.fontRendererObj.FONT_HEIGHT,scale);
	}

	@SubscribeEvent
	public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {

		if (event.gui instanceof GuiChest) {
			GuiChest inventory = (GuiChest) event.gui;
			Container containerChest = inventory.inventorySlots;
			if (containerChest instanceof ContainerChest) {
				Minecraft mc = Minecraft.getMinecraft();
				ScaledResolution sr = new ScaledResolution(mc);
				int guiLeft = (sr.getScaledWidth() - 176) / 2;
				int guiTop = (sr.getScaledHeight() - 222) / 2;

				List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
				String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
				int chestSize = inventory.inventorySlots.inventorySlots.size();

				//Completed commissions
				if(displayName.startsWith("Commissions") && main.getUtils().checkForSkyblock()) {
					for (Slot slot : invSlots) {
						ItemStack item = slot.getStack();
						if (item == null) return;
						if (!item.hasTagCompound()) return;
						if( item.getTagCompound().hasNoTags()) return;
						if(item.getDisplayName().contains("#") && item.getTagCompound().getCompoundTag("display").getTag("Lore").toString().contains("COMPLETED") ) {
							int colour = 0xBF5C1F35; // weird magenta
							colour = 0xBFD62440; // Red
							//colour = 0xFF0000FF; // Red my
							main.getUtils().drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, colour);
						}
					}
				}
			}
		}
	}

	//public static boolean isMuted

	@SubscribeEvent
	public void onGuiDraw(GuiScreenEvent.DrawScreenEvent.Post event) {

		if(shouldRenderOverlay(event.gui) && event.gui instanceof GuiInventory && main.getHyConfig().isGamemute()){


			//main.getUtils().drawModalRectWithCustomSizedTexture();
			ScaledResolution sr = new ScaledResolution(mc);


			//FMLLog.info("Y");
			Gui.drawRect(sr.getScaledWidth()/2 - 20,28, sr.getScaledWidth()/2 +20,40, Color.WHITE.getRGB());

			if(!HyK.ismuted) {
				new TextRenderer(mc, "Mute", sr.getScaledWidth()/2 - 10, 30, 1,Color.pink.getRGB());
			}
			else {
				new TextRenderer(mc, "UnMute", sr.getScaledWidth()/2 - 17, 30, 1,Color.pink.getRGB());
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onGuiDraw(GuiScreenEvent.MouseInputEvent.Post event) {



		if(shouldRenderOverlay(event.gui) && event.gui instanceof GuiInventory && main.getHyConfig().isGamemute()){

			ScaledResolution sr = new ScaledResolution(mc);
			GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
			int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
			int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
			boolean hoveringButton = false;

			if(mouseX >= sr.getScaledWidth()/2 - 20 && mouseX <= sr.getScaledWidth()/2 + 20 &&
					mouseY >= 28 && mouseY <= 40 && Mouse.isButtonDown(0)){

				if(HyK.ismuted){
					float value = main.getHyConfig().getGamemutefloat();
					Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER,value);
				}
				else {
					main.getHyConfig().setGamemutefloat(Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER));
					main.getHyConfig().markDirty();
					main.getHyConfig().writeData();
					Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER,0);
				}
				HyK.ismuted = !HyK.ismuted;

			}
		}
	}

	private static boolean shouldRenderOverlay(Gui gui) {
		boolean validGui = gui instanceof GuiContainer;
		if(gui instanceof GuiChest) {
			GuiChest eventGui = (GuiChest) gui;
			ContainerChest cc = (ContainerChest) eventGui.inventorySlots;
			String containerName = cc.getLowerChestInventory().getDisplayName().getUnformattedText();
			if(containerName.trim().equals("Fast Travel")) {
				validGui = false;
			}
		}
		return validGui;
	}

}
