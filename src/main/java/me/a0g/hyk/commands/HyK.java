package me.a0g.hyk.commands;

import gg.essential.api.EssentialAPI;
import gg.essential.api.utils.GuiUtil;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.gui.PrivateGui;
import me.a0g.hyk.tweaker.asm.hooks.FontRendererHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.*;
import java.util.List;

public class HyK extends CommandBase{

	private final Hyk main = Hyk.getInstance();
	private String usage1 =
			EnumChatFormatting.DARK_AQUA + "§m------------§3[" + EnumChatFormatting.LIGHT_PURPLE + "HYK" + EnumChatFormatting.DARK_AQUA + "]§m------------" + "\n" +
				EnumChatFormatting.GOLD + "Open config -> /hyk or press " + GameSettings.getKeyDisplayString( main.keyBindings[0].getKeyCode() ) +
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

	public static PrivateGui privateGui = new PrivateGui();

	private final Set<String> waypoints = new LinkedHashSet<>(
			Arrays.asList("/skytilshollowwaypoint set Main 478 52 296",
			//ruby
			"/skytilshollowwaypoint set R1 478 52 282","/skytilshollowwaypoint set R2 456 49 302",
			"/skytilshollowwaypoint set R3 448 42 287",//"/skytilshollowwaypoint set 5 357 53 260",
					//topaz
			"/skytilshollowwaypoint set T2 463 49 301","/skytilshollowwaypoint set T3 453 47 304",
			"/skytilshollowwaypoint set T4 453 42 293","/skytilshollowwaypoint set T5 460 44 284",
			"/skytilshollowwaypoint set T6 466 55 312"
			));

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
			return getListOfStringsMatchingLastWord(args, "help","gui","api","fake","waypoints");
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


		else if(args.length == 1 && args[0].equalsIgnoreCase("a0g")){
			FontRendererHook.a0g = !FontRendererHook.a0g;
		}


		else if(args.length == 1 && args[0].equalsIgnoreCase("fake")){
			main.getUtils().sendMessage("§b/hyk fake §c<Message>");
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

		// Mb private
		/*else if(args.length == 1 && args[0].equalsIgnoreCase("edit")){
			HypixelKentik.guiToOpen = "editlocations";
		}*/


		/*else if(args.length == 1 && args[0].equalsIgnoreCase("edit2")){
			//HypixelKentik.guiToOpen = "edit2";
			//HypixelKentik.guiToOpen = "editlocations";
			//GuiUtil.open(Objects.requireNonNull(new LocationsEditGui()));
		}*/

		else if(args.length == 1 && args[0].equalsIgnoreCase("gui")){
			Hyk.guiToOpen = "hykguiGeneral1";
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("gui2")){
			//GuiUtil.open(Objects.requireNonNull(privateGui));
			privateGui = new PrivateGui();
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("getMods")){
			//FMLLog.info(main.mods + "");
			main.getUtils().sendMessage(main.mods + "");
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("bbh")){
			//FMLLog.info(main.mods + "");
			main.getUtils().sendMessage("§7" + main.getInGameBbhWords() + "");
		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("bbh")){
			//FMLLog.info(main.mods + "");
			//main.initBbh(new File(main.dir,"bbh"));

			main.getUtils().sendMessage("§7" + main.getBbhWords() + "");
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("push")){
			//FMLLog.info(main.mods + "");
			EssentialAPI.getNotifications().push("test","test1");
		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("waypoints")){

				new Thread(() -> {
					try {

						EntityPlayerSP pl = Minecraft.getMinecraft().thePlayer;

						for (String command : waypoints){
							if(ClientCommandHandler.instance.executeCommand(pl,command) != 0){
								//ClientCommandHandler.instance.executeCommand(pl,command); // nyshno li
							}
						}



					}catch (ConcurrentModificationException e){
						//
					}
				}).start();

		}

		else if(args.length == 1 && args[0].equalsIgnoreCase("dev")){

			main.isDev = !main.isDev;
			main.getUtils().sendMessage("Dev - " + main.isDev);

		}

		// Needed rework
		/*else if(args.length == 1 && args[0].equalsIgnoreCase("game")){

			main.getUtils().sendMessage("Example: /play arcade_party_games_1");
			main.getUtils().sendMessage("Command: " + Cakes.gamemsg);

		}
		else if(args.length == 2 && args[0].equalsIgnoreCase("game")){
			main.getUtils().sendMessage(" " + args[1]);
			Cakes.gamemsg = args[1];
		}*/

		//api
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

		else if(args.length == 1 && args[0].equalsIgnoreCase("entity")){
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
			AxisAlignedBB aabb = new AxisAlignedBB(p.posX - 4,p.posY - 4,p.posZ - 4,p.posX + 4,p.posY + 4,p.posZ + 4);
			for(Entity entity : Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(Entity.class,aabb) ){
			 main.getUtils().sendMessage(entity.getDisplayName().getUnformattedText());

			}

		}

		/*else if(args.length == 1 && args[0].equalsIgnoreCase("cn")){
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
		}*/

		/*else if(args.length == 1 && args[0].equalsIgnoreCase("mute")){
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
		}*/

		else {
			main.getUtils().sendMessage(usage1,false);
			main.getUtils().sendHoverMessage(EnumChatFormatting.YELLOW + "/hyk - Hover to see aliases" + "\n",EnumChatFormatting.GREEN + "/hyk <help,edit,cn,api>");
			main.getUtils().sendMessage(usage2,false);
		}

	}


}
