package me.a0g.hyk.events;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.commands.HyK;
import me.a0g.hyk.core.Feature;
import me.a0g.hyk.gui.EditLocationsGui;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class Render {

	private final Hyk main = Hyk.getInstance();
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


			for(Feature feature : Feature.getGuiFeatures()){
				feature.draw(mc);
			}

			//DrawUtils.drawRect(200,200,80,50,0xA4000000,10);

			//new TextRenderer(mc,TooltipListener.hitReach,40,50,1);

			//renderAll();

			if(EventKey.shifting){
				new TextRenderer(mc,"§bShifting",250,60,1);
			}

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

			if(main.getHyConfig().isXr() && main.hray){
				new TextRenderer(mc,"X",5,30,1);
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

			if(main.getHyConfig().isAutoSprintEnabled()) {
				KeyBinding.setKeyBindState(getKeyCodeSprint(), main.getHyConfig().isAutoSprintEnabled());
			}
			if(EventKey.shifting){
				KeyBinding.setKeyBindState( Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode() , EventKey.shifting );
			}
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
	}

	private int getKeyCodeSprint() {
		return Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode();
	}

	public static String getrenderTime() {

		   Date d = new Date();
		   SimpleDateFormat format1;
		   format1 = new SimpleDateFormat("HH:mm");

		   return format1.format(d);
	}

	private static final List<Long> leftClicks = new ArrayList<>();

	private static boolean leftWasPressed = false;
	private static long leftLastPressed;


	public static int getPlayerLeftCPS() {

		final boolean pressed = Mouse.isButtonDown(0);

		if(pressed != leftWasPressed) {
			leftWasPressed = pressed;
			leftLastPressed = System.currentTimeMillis();
			if(pressed) {
				leftClicks.add(leftLastPressed);
			}
		}

		leftClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
		return leftClicks.size();
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

		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && main.isDev) {
			new TextRenderer(Minecraft.getMinecraft(), event.gui.toString(), 10, 10, 1, Color.ORANGE.getRGB(), true);
		}

		ScaledResolution sr = new ScaledResolution(mc);

		if(shouldRenderOverlay(event.gui) && event.gui instanceof GuiChest && isAuctionsGui(event.gui)){
			GuiChest eventGui = (GuiChest) event.gui;
			ContainerChest cc = (ContainerChest) eventGui.inventorySlots;
			String containerName = cc.getLowerChestInventory().getDisplayName().getUnformattedText();

			if(containerName != null && containerName.contains("Auction")) {
				Gui.drawRect(20, 28, 20 + 45, 40, Color.WHITE.getRGB());
				new TextRenderer(mc, "Bin show", 22, 30, 1, Color.ORANGE.getRGB(),true);
			}
		}

		if(shouldRenderOverlay(event.gui) && event.gui instanceof GuiInventory && main.getHyConfig().isGamemute()){

			Gui.drawRect(sr.getScaledWidth()/2 - 20,28, sr.getScaledWidth()/2 +20,40, Color.WHITE.getRGB());

			if(!HyK.ismuted) {
				new TextRenderer(mc, "Mute", sr.getScaledWidth()/2 - 10, 30, 1,Color.pink.getRGB(),true);
			}
			else {
				new TextRenderer(mc, "UnMute", sr.getScaledWidth()/2 - 17, 30, 1,Color.pink.getRGB(),true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onGuiDraw(GuiScreenEvent.MouseInputEvent.Post event) {

		if(shouldRenderOverlay(event.gui)) {

			ScaledResolution sr = new ScaledResolution(mc);
			GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
			int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
			int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
			boolean hoveringButton = false;

			if (event.gui instanceof GuiChest && isAuctionsGui(event.gui)) {
				if (mouseX >= 20 && mouseX <= ( 20 + 45 ) &&
						mouseY >= 28 && mouseY <= 40 && Mouse.isButtonDown(0)) {
					main.setBinShow(!main.isBinShow());
				}
			}


			if ( event.gui instanceof GuiInventory && main.getHyConfig().isGamemute()) {

				if (mouseX >= sr.getScaledWidth() / 2 - 20 && mouseX <= sr.getScaledWidth() / 2 + 20 &&
						mouseY >= 28 && mouseY <= 40 && Mouse.isButtonDown(0)) {

					if (HyK.ismuted) {
						float value = main.getHyConfig().getGamemutefloat();
						Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER, value);
					} else {
						main.getHyConfig().setGamemutefloat(Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER));
						main.getHyConfig().markDirty();
						main.getHyConfig().writeData();
						Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER, 0);
					}
					HyK.ismuted = !HyK.ismuted;

				}
			}
		}
	}
	private static boolean isAuctionsGui(Gui gui) {
		if(gui instanceof GuiChest) {
			GuiChest eventGui = (GuiChest) gui;
			ContainerChest cc = (ContainerChest) eventGui.inventorySlots;
			String containerName = cc.getLowerChestInventory().getDisplayName().getUnformattedText();
			if(containerName.trim().contains("Auction")) {
				return true;
			}
		}
		return false;
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
