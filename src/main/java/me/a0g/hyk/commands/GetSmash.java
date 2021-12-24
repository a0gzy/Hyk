package me.a0g.hyk.commands;


import com.google.gson.JsonObject;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;

import java.util.List;

public class GetSmash extends CommandBase {

    private final Hyk main = Hyk.getInstance();

    @Override
    public String getCommandName() {
        return "smash";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/smash";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
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
                    int lvl = main.getApiUtils().getSmash(playyer);


                    message = EnumChatFormatting.GRAY + pnamee + "'s " + EnumChatFormatting.GRAY + "Smash Lvl: " + EnumChatFormatting.DARK_PURPLE + lvl;
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
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, BlockPos pos) {
        return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
    }
}
