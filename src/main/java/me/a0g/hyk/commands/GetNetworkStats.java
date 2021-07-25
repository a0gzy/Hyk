package me.a0g.hyk.commands;

import com.google.gson.JsonObject;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.Render;
import me.a0g.hyk.handlers.APIHandler;
import me.a0g.hyk.utils.ApiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetNetworkStats extends CommandBase{

    private final HypixelKentik main = HypixelKentik.getInstance();

    @Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "pstats";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return
            EnumChatFormatting.LIGHT_PURPLE + "--------|" +
            EnumChatFormatting.GRAY + "/pstats" +
            EnumChatFormatting.LIGHT_PURPLE + "|--------\n" ;
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

                Minecraft mc = Minecraft.getMinecraft();
                EntityPlayerSP player = mc.thePlayer;
                WorldClient wolrd = mc.theWorld;
               // 
                main.getUtils().sendMessage(EnumChatFormatting.GRAY+"Fetching data.\n" +
                    EnumChatFormatting.GRAY + "This may take some time depending on your connection.");

                String urll = "https://api.hypixel.net/player?key="+apikey+"&name="+username;
                JsonObject apiData = APIHandler.getResponse(urll);

                    if(!apiData.get("player").isJsonNull()) {
                    JsonObject playyer = apiData.get("player").getAsJsonObject();

                    String pname = main.getApiUtils().getName(playyer);
                    int nlvl = main.getApiUtils().getNetLvl(playyer);
                    int aps = main.getApiUtils().getAP(playyer);
                    int quests = main.getApiUtils().getQuests(playyer);
                    String lastlogin = main.getApiUtils().getLLogin(playyer);
                    String firstlogin = main.getApiUtils().getFLogin(playyer);

                    String uuid = APIHandler.getUUID(username);

                    System.out.println("Fetching guild...");
                    String guildURL = "https://api.hypixel.net/guild?player=" + uuid + "&key=" + apikey;
                    JsonObject guildResponse = APIHandler.getResponse(guildURL);
                    if (!guildResponse.get("success").getAsBoolean()) {
                            String reason = guildResponse.get("cause").getAsString();
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                            return;
                    }
                        String guildName = "N/A";

                        if (!guildResponse.get("guild").isJsonNull()) {
                            guildName = guildResponse.get("guild").getAsJsonObject().get("name").getAsString();
                        }



                        String latestProfile = APIHandler.getLatestProfileID(uuid, apikey);
                        String sbmessage;
                        if (latestProfile != null) {

                            String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apikey;
                            FMLLog.info("Fetching profile ... ");

                            JsonObject profileResponse = APIHandler.getResponse(profileURL);
                            if (!profileResponse.get("success").getAsBoolean()) {
                                String reason = profileResponse.get("cause").getAsString();
                                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                                return;
                            }

                            JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();
                            ArrayList<Double> list = new ArrayList<Double>();
                            list = main.getApiUtils().skills(userObject, uuid, apikey, apiData);

                            ApiUtils apiUtils = main.getApiUtils();

                            double farmingLevel = list.get(0);
                            double miningLevel = list.get(1);
                            double combatLevel = list.get(2);
                            double foragingLevel = list.get(3);
                            double fishingLevel = list.get(4);
                            double enchantingLevel = list.get(5);
                            double alchemyLevel = list.get(6);
                            double tamingLevel = list.get(7);

                            double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel) / 8;
                            skillAvg = (double) Math.round(skillAvg * 100) / 100;

                            double purseCoins = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("coin_purse").getAsDouble();
                            //purseCoins = Math.floor(purseCoins * 100.0) / 100.0;
                            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

                            double bankCoins;
                            if (profileResponse.get("profile").getAsJsonObject().has("banking")) {
                                bankCoins = profileResponse.get("profile").getAsJsonObject().get("banking").getAsJsonObject().get("balance").getAsDouble();
                                //bankCoins = Math.floor(bankCoins * 100.0) / 100.0;
                            } else bankCoins = 0;

                            String purse = main.getUtils().nicenumbers((int) (purseCoins));
                            String bank = main.getUtils().nicenumbers((int) (bankCoins));

                            sbmessage = "§7" + main.getApiUtils().getName(playyer) + "\n" +
                                    "§aFarming: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(farmingLevel, "farmingLevel") + farmingLevel + "\n" +
                                    "§aMining: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(miningLevel, "miningLevel") + miningLevel + "\n" +
                                    "§aCombat: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(combatLevel, "combatLevel") + combatLevel + "\n" +
                                    "§aForaging: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(foragingLevel, "foragingLevel") + foragingLevel + "\n" +
                                    "§aFishing: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(fishingLevel, "fishingLevel") + fishingLevel + "\n" +
                                    "§aEnchanting: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(enchantingLevel, "enchantingLevel") + enchantingLevel + "\n" +
                                    "§aAlchemy: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(alchemyLevel, "alchemyLevel") + alchemyLevel + "\n" +
                                    "§aTaming: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(tamingLevel, "tamingLevel") + tamingLevel + "\n" +
                                    "§aAverage Skill Level: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(skillAvg, "skillAvg") + skillAvg + "\n" +
                                    "§ePurse: " + EnumChatFormatting.GOLD + purse + " §eBank: " + EnumChatFormatting.GOLD + bank;
                        }else {
                            sbmessage = "-------------";
                        }


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                    String formattedDate;

                    if(!lastlogin.equalsIgnoreCase("staf")) {
                        long loglog = Long.parseLong(lastlogin, 10);

                        Date date = new Date(loglog);
                        formattedDate = sdf.format(date);
                    }
                    else{
                        formattedDate = "invalid";
                    }

                    long lglog = Long.parseLong(firstlogin, 10);
                    Date datee = new Date(lglog);
                    String formattedDatee = sdf.format(datee);

                        String beforelvl = "§3";
                        if (nlvl > 250) {
                            beforelvl = "§6";
                        }

                    String message ="§7"    + pname  +  "\n" +
                           "§aLevel: "      + beforelvl + nlvl  + "\n" +
                           "§fGuild: §b§l"  + guildName + "\n" +
                           "§aAP:  §e"      + aps + "\n" +
                           "§aQuests:  §e"  + quests + "\n" +
                           "§fFL: §7"       + formattedDatee + "\n" +
                           "§fLL: §7"       + formattedDate ;


                        String bwlevel = main.getApiUtils().getBwLvl(playyer);
                        String kdwl = main.getApiUtils().getBwStats(playyer);
                        String bwwins = main.getApiUtils().getBwWins(playyer);

                        String bwmessage = "§7" + pname + " §r" + nlvl + "\n" +
                                "§aBw Lvl: §e" + bwlevel + "\n" + "§7KD & WL: §6" + kdwl + "\n" +
                                "§cWins: §b" + bwwins;


                        String xptest = main.getApiUtils().sw_xp_to_lvl(playyer);
                        String statsss = main.getApiUtils().getSwStats(playyer);
                        String swmessage = "§7" + pname + " §r" + nlvl + "\n" +
                                "§aSw Lvl: §e" + xptest + "\n" +
                                statsss;


                        main.getUtils().sendDataMessage(message);
//                        Render.todesign = message;
                        Render.todesignpage = 1;
                        Render.todesignp = message;
                        Render.todesignbw = "§dBW\n" + bwmessage;
                        Render.todesignsw = "§dSW\n" + swmessage;
                        Render.todesignsb = "§dSB\n" + sbmessage;
                        Render.todesignrender = true;
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
