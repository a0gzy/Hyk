package me.a0g.hyk.commands;


import com.google.gson.JsonObject;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.events.Render;
import me.a0g.hyk.handlers.APIHandler;
import me.a0g.hyk.utils.ApiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLLog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SbStats extends CommandBase {

    private final Hyk main = Hyk.getInstance();

    @Override
    public String getCommandName() {
        return "sbhyk";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/sbhyk";
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

            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            WorldClient wolrd = mc.theWorld;

            final String apikey = main.getHyConfig().getApikeyy();
            if(apikey.isEmpty()){
                main.getUtils().sendMessage(EnumChatFormatting.RED + "API key isn't set - /api new , then /hapi");
                return;
            }

            new Thread(() -> {
                FMLLog.info("Grabbing uuid...");

                main.getUtils().sendMessage(EnumChatFormatting.GRAY+"Fetching data.\n" +
                        EnumChatFormatting.GRAY + "This may take some time depending on your connection.");

                String uuid = APIHandler.getUUID(username);

                String latestProfile = APIHandler.getLatestProfileID(uuid, apikey);
                if (latestProfile == null) return;

                String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apikey;
                FMLLog.info("Fetching profile ... ");

                JsonObject profileResponse = APIHandler.getResponse(profileURL);
                if (!profileResponse.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                    return;
                }


                //normal api
                String playerURL = "https://api.hypixel.net/player?uuid=" + uuid + "&key=" + apikey;
                System.out.println("Fetching skills from achievement API");
                JsonObject playerObject = APIHandler.getResponse(playerURL);

                if (!playerObject.get("success").getAsBoolean()) {
                    String reason = playerObject.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                    return;
                }
                JsonObject playyer = playerObject.get("player").getAsJsonObject();


                JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

//                ArrayList<Double> list = Collections.EMPTY_LIST;
                ArrayList<Double> list = new ArrayList<Double>();
                list = main.getApiUtils().skills(userObject,uuid,apikey,playerObject);

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
                }else bankCoins = 0;

                String purse = main.getUtils().nicenumbers((int)(purseCoins) );
                String bank = main.getUtils().nicenumbers((int)(bankCoins) );

                String message = "§7" + main.getApiUtils().getName(playyer) + "\n" +
                        "§aFarming: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(farmingLevel,"farmingLevel") + farmingLevel + "\n" +
                        "§aMining: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(miningLevel,"miningLevel") + miningLevel + "\n" +
                        "§aCombat: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(combatLevel,"combatLevel") + combatLevel + "\n" +
                        "§aForaging: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(foragingLevel,"foragingLevel") + foragingLevel + "\n" +
                        "§aFishing: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(fishingLevel,"fishingLevel") + fishingLevel + "\n" +
                        "§aEnchanting: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(enchantingLevel,"enchantingLevel") + enchantingLevel + "\n" +
                        "§aAlchemy: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(alchemyLevel,"alchemyLevel") + alchemyLevel + "\n" +
                        "§aTaming: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(tamingLevel,"tamingLevel") + tamingLevel + "\n" +
                        "§aAverage Skill Level: §3" + EnumChatFormatting.BOLD + apiUtils.maxskill(skillAvg,"skillAvg") + skillAvg + "\n" +
                        "§ePurse: " + EnumChatFormatting.GOLD + purse + " §eBank: " + EnumChatFormatting.GOLD + bank
                        ;

                main.getUtils().sendDataMessage(message);
                main.getUtils().sendLink("§2Open §3Sky§fCrypt","https://sky.shiiyu.moe/stats/" + username + "/" + latestProfile);
                main.getUtils().clearRenderDesign();
                Render.todesignsb = message;
                Render.todesignpage = 2;
                Render.todesignrender = true;

            }).start();
        }
        else{
            main.getUtils().sendMessage(getCommandUsage(sender), false);
        }


    }

    @Override
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, BlockPos pos) {
        // TODO Auto-generated method stub
        return (args.length == 1) ? main.getTabutil().getListOfStringsMatchingLastWord(args, main.getTabutil().getTabUsernames()) : null;
    }
}
