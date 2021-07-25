package me.a0g.hyk.commands;

import com.google.gson.JsonObject;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.Render;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;

import java.util.*;

public class GetBw extends CommandBase {

    private final HypixelKentik main = HypixelKentik.getInstance();

	
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "bws";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return
                EnumChatFormatting.LIGHT_PURPLE + "|--------\n" +
                EnumChatFormatting.GRAY + "- Usage: " + EnumChatFormatting.GREEN + "/bws <username>\n" +
                EnumChatFormatting.GRAY + "- Description: " + EnumChatFormatting.GREEN + "Bedwars stats for player.\n" +
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

                main.getUtils().sendMessage(EnumChatFormatting.GRAY+"Fetching data.\n" +
                        EnumChatFormatting.GRAY + "This may take some time depending on your connection.");

                String urll = "https://api.hypixel.net/player?key="+apikey+"&name="+username;
                JsonObject apiData = APIHandler.getResponse(urll);



                if(!apiData.get("player").isJsonNull()) {

                    JsonObject playyer = apiData.get("player").getAsJsonObject();

                    String level = main.getApiUtils().getBwLvl(playyer);
                    String kdwl = main.getApiUtils().getBwStats(playyer);
                    String bwwins = main.getApiUtils().getBwWins(playyer);
                    String pname = main.getApiUtils().getName(playyer);
                    int nlvl = main.getApiUtils().getNetLvl(playyer);


                    String message = "§7" + pname + " §r" + nlvl + "\n" +
                            "§aLvl: §e" + level + "\n" + "§7KD & WL: §6" + kdwl + "\n" +
                            "§cWins: §b" + bwwins;

                    main.getUtils().sendDataMessage(message);
                   /* Render.todesign = message;
                    Render.todesignrender = true;*/

                }
                else {
                    main.getUtils().sendDataMessage("Not found or nicked");
                }

            }).start();
        }else{
            main.getUtils().sendMessage(getCommandUsage(sender), false);
        }
    }
		
	 @Override
		public List<String> addTabCompletionOptions(final ICommandSender sender,final String[] args, BlockPos pos) {
			// TODO Auto-generated method stub
			return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
		}
	
}
