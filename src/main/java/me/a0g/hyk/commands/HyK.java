package me.a0g.hyk.commands;

import gg.essential.api.utils.GuiUtil;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.chest.Player;
import me.a0g.hyk.events.Cakes;
import me.a0g.hyk.tweaker.asm.hooks.FontRendererHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLLog;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HyK extends CommandBase{

	private final HypixelKentik main = HypixelKentik.getInstance();
	private String usage1 =
			EnumChatFormatting.DARK_AQUA + "§m------------§3[" + EnumChatFormatting.LIGHT_PURPLE + "HYK" + EnumChatFormatting.DARK_AQUA + "]§m------------" + "\n" +
			EnumChatFormatting.RED	+ "General" + "\n";

	private String usage2 =

			EnumChatFormatting.DARK_AQUA + "HypixelStats" + "\n" +
					"\n" +
					EnumChatFormatting.YELLOW + "/bws <nick> - BedWars stats" + "\n" +
					EnumChatFormatting.YELLOW + "/bwall - BedWars stats for lobby" + "\n" +
					EnumChatFormatting.YELLOW + "/sws <nick> - SkyWars stats" + "\n" +
					EnumChatFormatting.YELLOW + "/swall - SkyWars stats for lobby" + "\n" +
					EnumChatFormatting.YELLOW + "/smash <nick>  - Smash Heroes stats" + "\n" +
					EnumChatFormatting.YELLOW + "/pstats <nick>  - NetworkStats" + "\n" +
					EnumChatFormatting.DARK_AQUA + "§m----------------------------";


	public static boolean ismuted = false;

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "hyk";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/hyk";
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "help","gui","api","cn","edit","fake","waypoints");
		}
		return null;
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		if(args.length == 0){

			GuiUtil.open(Objects.requireNonNull(main.getHyConfig().gui()));
			//ModCore.getInstance().getGuiHandler().open(main.getHyConfig().gui());

		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("help")){
			main.getUtils().sendMessage(usage1,false);
			main.getUtils().sendHoverMessage(EnumChatFormatting.YELLOW + "/hyk - Hover to see aliases" + "\n",EnumChatFormatting.GREEN + "/hyk <help,edit,gui,game,fake,cn,api>");
			main.getUtils().sendMessage(usage2,false);
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("fake")){
			main.getUtils().sendMessage("§b/hyk fake §c<Message>");
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("a0g")){
			FontRendererHook.a0g = !FontRendererHook.a0g;
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("mute")){
			main.getUtils().sendMessage("Game Muted " + Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER));
			ismuted=true;
			Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER,0);
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("unmute")){
			main.getUtils().sendMessage("Game UnMuted");
			ismuted=false;
			Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER,0.03F);
		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("unmute")){
			main.getUtils().sendMessage("Game UnMuted");
			ismuted=false;
			Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MASTER,Float.parseFloat(args[1] )+ 0.0F);
		}

		else if(args[0].equalsIgnoreCase("fake")){
			String fakemessage = "";
//			String fakemessage = args[1].replaceAll("&","§");
			for(int i = 1;i<args.length;i++){
				fakemessage += args[i].replaceAll("&","§") + " ";
			}
			//String fakemessage = args.toString().replaceAll("&","§");
			main.getUtils().sendMessage(fakemessage,false);
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("edit")){
			HypixelKentik.guiToOpen = "editlocations";
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("edit2")){
			//HypixelKentik.guiToOpen = "edit2";
			//HypixelKentik.guiToOpen = "editlocations";
			//GuiUtil.open(Objects.requireNonNull(new LocationsEditGui()));
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("gui")){
			HypixelKentik.guiToOpen = "hykguiGeneral1";
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("waypoints")){

				new Thread(() -> {
					try {

						EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;


						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 1 353 61 267");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 2 347 59 268");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 3 344 61 263");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 4 351 61 258");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 5 357 53 260");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 6 348 49 272");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 7 338 52 254");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 8 341 52 243");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 9 343 52 239");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 10 326 51 246");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 11 326 56 261");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 12 318 50 253");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 13 344 48 231");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 14 307 51 254");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 15 334 46 260");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 16 300 51 237");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 17 298 48 258");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set 18 291 47 266");
						Thread.sleep(1000);

						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb1 353 48 262");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb2 343 45 256");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb3 337 45 245");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb4 327 44 257");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb5 314 45 254");
						Thread.sleep(1000);
						ClientCommandHandler.instance.executeCommand(pl, "/skytilshollowwaypoint set bomb6 298 44 262");
						Thread.sleep(1000);




					}catch (ConcurrentModificationException | InterruptedException e){
						//
					}
				}).start();

		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("game")){

			main.getUtils().sendMessage("Example: /play arcade_party_games_1");
			main.getUtils().sendMessage("Command: " + Cakes.gamemsg);

		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("game")){

			main.getUtils().sendMessage(" " + args[1]);
			Cakes.gamemsg = args[1];

		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("api")){
			main.getHyConfig().setApikeyy(args[1]);
			main.getUtils().sendMessage(main.getHyConfig().getApikeyy());
			main.getHyConfig().markDirty();
			main.getHyConfig().writeData();
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("api")){
			if(main.getHyConfig().getApikeyy().isEmpty()){
				main.getUtils().sendMessage(EnumChatFormatting.YELLOW + "To get ApiKey -> /api new or /getapi");
				main.getUtils().sendMessage(EnumChatFormatting.YELLOW + "/hyk api <ApiKey>");
			}
			else {
				main.getUtils().sendClickableMessage(EnumChatFormatting.GREEN + "Your API key is " + EnumChatFormatting.AQUA +
						main.getHyConfig().getApikeyy(), main.getHyConfig().getApikeyy(),EnumChatFormatting.YELLOW + "Click to put in chat so you can copy!");
			}
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("test")){

//			Minecraft.getMinecraft().thePlayer.inventory.armorInventory[0] = new ItemStack(Item.getItemById(89));



			EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;

			main.getUtils().sendMessage(pl.getArmorVisibility() + "");

			main.getUtils().createTitle("TEST ",5);
		}

		else if(args[0].equalsIgnoreCase("rename")){

			if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null ){
				ItemStack item = Minecraft.getMinecraft().thePlayer.getHeldItem();
				String torename = "";
				for(int i = 1; i < args.length;i++){
					torename += args[i] + " ";
				}
				item.setStackDisplayName( torename.replaceAll("&","§") );
			}

		}

		else if(args.length == 3 && args[0].equalsIgnoreCase("test")){

//			Minecraft.getMinecraft().thePlayer.inventory.armorInventory[0] = new ItemStack(Item.getItemById(89));

			main.getUtils().sendMessage(EnumChatFormatting.AQUA + "Test");

			/*EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;
			EntityPlayerSP pl2 = Minecraft.getMinecraft().thePlayer;
			pl2.setSize(Float.parseFloat( args[1] ), Float.parseFloat( args[2] ));


			Minecraft.getMinecraft().thePlayer = pl2;
			main.getUtils().sendMessage(pl.height + " " + pl.width);*/

			main.getUtils().createTitle("TEST ",5);
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("time")){
			FMLLog.info(new Date().getTime() + "");
		}

		/*else if(args.length == 3 && args[0].equalsIgnoreCase("kb")){
			EntityPlayerSP pl =  Minecraft.getMinecraft().thePlayer;
		}*/

		else if(args.length == 1 && args[0].equalsIgnoreCase("entity")){
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
			AxisAlignedBB aabb = new AxisAlignedBB(p.posX - 4,p.posY - 4,p.posZ - 4,p.posX + 4,p.posY + 4,p.posZ + 4);
			for(Entity entity : Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(Entity.class,aabb) ){
			 main.getUtils().sendMessage(entity.getDisplayName().getUnformattedText());

			}

		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("cn")){
			if(!main.getHyConfig().getCustomname().isEmpty()){
				main.getUtils().sendMessage( EnumChatFormatting.YELLOW +"Custom name: " + EnumChatFormatting.RESET + main.getHyConfig().getCustomname() );
			}
			main.getUtils().sendMessage(EnumChatFormatting.AQUA + "/hyk cn <Custom name> - Use & to color | &p for chroma");
		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("cn")){
			main.getHyConfig().setCustomname(args[1]);
			main.getUtils().sendMessage(main.getHyConfig().getCustomname());
			main.getHyConfig().markDirty();
			main.getHyConfig().writeData();
		}

		else {
			main.getUtils().sendMessage(usage1,false);
			main.getUtils().sendHoverMessage(EnumChatFormatting.YELLOW + "/hyk - Hover to see aliases" + "\n",EnumChatFormatting.GREEN + "/hyk <help,edit,cn,api>");
			main.getUtils().sendMessage(usage2,false);
		}

	}


}
