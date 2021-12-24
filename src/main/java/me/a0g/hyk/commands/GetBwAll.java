package me.a0g.hyk.commands;

import com.google.gson.JsonObject;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;

import java.util.List;

public class GetBwAll extends CommandBase {

    private final Hyk main = Hyk.getInstance();

    @Override
    public String getCommandName() {
        return "bwall";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {

        return EnumChatFormatting.GRAY + "/bwall";
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
                StringBuilder nicekedplayers = new StringBuilder();
                StringBuilder message = new StringBuilder("Bw stats" + "\n");

                for (String lobbyname : lobbynames) {
                    String urll = "https://api.hypixel.net/player?key=" + apikey + "&name=" + lobbyname;
                    JsonObject apiData = APIHandler.getResponse(urll);
                    if (!apiData.get("player").isJsonNull()) {

                        JsonObject playyer = apiData.get("player").getAsJsonObject();

                        String level = main.getApiUtils().getBwLvl(playyer);
                        String kdwl = main.getApiUtils().getBwStats(playyer);
                        String bwwins = main.getApiUtils().getBwWins(playyer);
                        String pname = main.getApiUtils().getName(playyer);
                        int nlvl = main.getApiUtils().getNetLvl(playyer);

                        int morelvlarg = Integer.parseInt(args[0]);
                        int morelvl = Integer.parseInt(level);
                        if (morelvl > morelvlarg)
                            message.append(EnumChatFormatting.GRAY + pname + EnumChatFormatting.WHITE + " " + nlvl + " NetLvl " + EnumChatFormatting.GRAY +
                                    "|"+"Lvl: "+EnumChatFormatting.YELLOW+level+EnumChatFormatting.GRAY+", KD&WL: "+EnumChatFormatting.GOLD+kdwl+
                                    EnumChatFormatting.GRAY+", Wins: "+EnumChatFormatting.AQUA+bwwins+"\n");
                    } else {
                        nicekedplayers.append(lobbyname + ", ");
                        colnicked += 1;
                    }
                }
                main.getUtils().sendDataMessage(message.toString() + EnumChatFormatting.DARK_GRAY + colnicked + " Nicked Players" + "\n" + nicekedplayers);

            }).start();

        }
        else if(args.length > 0) {

            main.getUtils().sendMessage(getCommandUsage(sender), false);
        }
        else {
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
                StringBuilder message = new StringBuilder("Bw stats" + "\n");

                for (String lobbyname : lobbynames) {
                    String urll = "https://api.hypixel.net/player?key=" + apikey + "&name=" + lobbyname;
                    JsonObject apiData = APIHandler.getResponse(urll);
                    if (!apiData.get("player").isJsonNull()) {

                        JsonObject playyer = apiData.get("player").getAsJsonObject();

                        String level = main.getApiUtils().getBwLvl(playyer);
                        String kdwl = main.getApiUtils().getBwStats(playyer);
                        String bwwins = main.getApiUtils().getBwWins(playyer);
                        String pname = main.getApiUtils().getName(playyer);
                        int nlvl = main.getApiUtils().getNetLvl(playyer);

                        message.append(EnumChatFormatting.GRAY + pname + EnumChatFormatting.WHITE + " " + nlvl + " NetLvl " + EnumChatFormatting.GRAY +
                                "|"+"Lvl: "+EnumChatFormatting.YELLOW+level+EnumChatFormatting.GRAY+", KD&WL: "+EnumChatFormatting.GOLD+kdwl+
                                EnumChatFormatting.GRAY+", Wins: "+EnumChatFormatting.AQUA+bwwins+"\n");
                    } else {
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
        return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
    }

}
