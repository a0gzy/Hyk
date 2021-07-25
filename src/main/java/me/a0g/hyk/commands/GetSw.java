package me.a0g.hyk.commands;

import com.google.gson.JsonObject;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;

import java.util.List;

public class GetSw extends CommandBase{

	private final HypixelKentik main = HypixelKentik.getInstance();

		@Override
		public String getCommandName() {
			// TODO Auto-generated method stub
			return "sws";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			// TODO Auto-generated method stub
			return
	                EnumChatFormatting.LIGHT_PURPLE + "|--------\n" +
	                EnumChatFormatting.GRAY + "- Usage: " + EnumChatFormatting.GREEN + "/sws <username>\n" +
	                EnumChatFormatting.GRAY + "- Description: " + EnumChatFormatting.GREEN + "Skywars stats for player.\n" +
	                EnumChatFormatting.LIGHT_PURPLE + "|--------";
		}
		
		 @Override
		    public boolean canCommandSenderUseCommand(ICommandSender sender)
		    {
		        return true;
		    }
	
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if(args.length > 0) {
	            final String username = args[0];

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

					String urll = "https://api.hypixel.net/player?key="+apikey+"&name="+username;
					JsonObject apiData = APIHandler.getResponse(urll);

					String message;

					if(!apiData.get("player").isJsonNull()) {
							JsonObject playyer = apiData.get("player").getAsJsonObject();

							String pnamee = main.getApiUtils().getName(playyer);
							String xptest = main.getApiUtils().sw_xp_to_lvl(playyer);
							String statsss = main.getApiUtils().getSwStats(playyer);

							message = EnumChatFormatting.GRAY + pnamee + "'s " + EnumChatFormatting.GRAY + "Sw Lvl: " + EnumChatFormatting.DARK_PURPLE + xptest + statsss;
					}
					else {
						message = EnumChatFormatting.GRAY + username + " Not found";
					}
	                main.getUtils().sendDataMessage(message);
	            }).start();
	        }else{
	            main.getUtils().sendMessage(getCommandUsage(sender), false);
	        }
	    }
			
		 @Override
			public List<String> addTabCompletionOptions(final ICommandSender sender,final String[] args, BlockPos pos) {
				return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
			}
}
