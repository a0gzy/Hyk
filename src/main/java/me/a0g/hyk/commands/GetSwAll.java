package me.a0g.hyk.commands;

import com.google.gson.JsonObject;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.FMLLog;

import java.util.*;

public class GetSwAll extends CommandBase{

	private final Hyk main = Hyk.getInstance();

		@Override
		public String getCommandName() {
			return "swall";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return EnumChatFormatting.GRAY + "/swall";

		}
		
		@Override
		    public boolean canCommandSenderUseCommand(ICommandSender sender)
		    {
		        return true;
		    }
	
		@Override
		public void processCommand(ICommandSender sender, String[] args) {

			if(args.length == 1 && Hyk.isInteger(args[0])){

				List<String> lobbynames = main.getTabutil().getTabUsernames();

				final String apikey = main.getHyConfig().getApikeyy();
				if(apikey.isEmpty()){
					main.getUtils().sendMessage(EnumChatFormatting.RED + "API key isn't set - /api new , then /hapi");
					return;
				}

				new Thread(() -> {
					FMLLog.info("Grabbing uuid...");
					//
					main.getUtils().sendMessage(EnumChatFormatting.GRAY+"Fetching data.\n" +
							EnumChatFormatting.GRAY + "This may take some time depending on your connection.");

					int colnicked = 0;
					StringBuilder nicekedplayers = new StringBuilder("\n" + "");
					StringBuilder message = new StringBuilder("Sw stats" + "\n");

					for (String lobbyname : lobbynames) {

						String urll = "https://api.hypixel.net/player?key=" + apikey + "&name=" + lobbyname;
						JsonObject apiData = APIHandler.getResponse(urll);

						if (!apiData.get("player").isJsonNull()) {
							JsonObject playyer = apiData.get("player").getAsJsonObject();

							String pname = main.getApiUtils().getName(playyer);
							String xptest = main.getApiUtils().sw_xp_to_lvl(playyer);
							String statsss = main.getApiUtils().getSwStats(playyer);
							int lvlforarg = main.getApiUtils().swlvlforswall(playyer);

							int morelvlarg = Integer.parseInt(args[0]);

							if (lvlforarg > morelvlarg)
								message.append(EnumChatFormatting.GRAY + pname + EnumChatFormatting.GRAY + " | SwLvl: " + EnumChatFormatting.DARK_PURPLE +
										xptest + statsss + "\n");

						} else {
							nicekedplayers.append(lobbyname + ", ");
							colnicked += 1;
						}
					}

					main.getUtils().sendDataMessage(message.toString() + EnumChatFormatting.DARK_GRAY + colnicked + " Nicked Players" + nicekedplayers);
				}).start();
			}
			else if(args.length > 0) {

				main.getUtils().sendMessage(getCommandUsage(sender), false);
			}

			else{

	            List<String> lobbynames = main.getTabutil().getTabUsernames();
				final String apikey = main.getHyConfig().getApikeyy();
				if(apikey.isEmpty()){
					main.getUtils().sendMessage(EnumChatFormatting.RED + "API key isn't set - /api new , then /hapi");
					return;
				}

				new Thread(() -> {
					FMLLog.info("Grabbing uuid...");
					//
					main.getUtils().sendMessage(EnumChatFormatting.GRAY+"Fetching data.\n" +
							EnumChatFormatting.GRAY + "This may take some time depending on your connection.");

					int colnicked = 0;
					StringBuilder nicekedplayers = new StringBuilder("\n" + "");
					StringBuilder message = new StringBuilder("Sw stats" + "\n");

					for (String lobbyname : lobbynames) {

						String urll = "https://api.hypixel.net/player?key=" + apikey + "&name=" + lobbyname;
						JsonObject apiData = APIHandler.getResponse(urll);

						if (!apiData.get("player").isJsonNull()) {
							JsonObject playyer = apiData.get("player").getAsJsonObject();

								String pname = main.getApiUtils().getName(playyer);
								String xptest = main.getApiUtils().sw_xp_to_lvl(playyer);
								String statsss = main.getApiUtils().getSwStats(playyer);

								message.append(EnumChatFormatting.GRAY + pname +  EnumChatFormatting.GRAY + " | SwLvl: " + EnumChatFormatting.DARK_PURPLE +
										xptest + statsss + "\n");
	                	}
	                else {
							nicekedplayers.append(lobbyname + ", ");
							colnicked += 1;
						}
	                }
	                main.getUtils().sendDataMessage(message.toString() + EnumChatFormatting.DARK_GRAY + colnicked + " Nicked Players" + nicekedplayers);
	            }).start();


	        }
	    }
			
		 @Override
			public List<String> addTabCompletionOptions(final ICommandSender sender,final String[] args, BlockPos pos) {
				// TODO Auto-generated method stub
				return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
			}
	
	
	
}
