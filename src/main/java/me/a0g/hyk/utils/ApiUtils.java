package me.a0g.hyk.utils;

import com.google.gson.JsonObject;
import me.a0g.hyk.handlers.APIHandler;
import net.minecraft.util.EnumChatFormatting;
import org.apache.http.conn.ssl.SSLContexts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiUtils {


    public int swlvlforswall (JsonObject playyer){
        int lvl = 0;
        if(playyer.has("stats")) {
            JsonObject stats = playyer.get("stats").getAsJsonObject();
            if (stats.has("SkyWars")) {
                JsonObject sw = stats.get("SkyWars").getAsJsonObject();

                if (sw.has("skywars_experience")) {

                    int xp = sw.get("skywars_experience").getAsInt();

                    int[] xps = {0, 20, 70, 150, 250, 500, 1000, 2000, 3500, 6000, 10000, 15000};
                    if (xp >= 15000) {
                        lvl = Math.round(((xp - 15000) / 10000 + 12));
                    }
                    else {
                        for (int i = 0; i < xps.length; i++) {
                            if (xp < xps[i]) {
                                lvl = Math.round(i + (xp - xps[i - 1]) / (xps[i] - xps[i - 1]));
                            }
                        }
                    }
                }
            }
        }
        return lvl;
    }

    public String sw_xp_to_lvl(JsonObject playyer) {
        if(playyer.has("stats")) {
            JsonObject stats = playyer.get("stats").getAsJsonObject();
            if (stats.has("SkyWars")) {
                JsonObject sw = stats.get("SkyWars").getAsJsonObject();

                if (sw.has("skywars_experience")) {

                    int xp = sw.get("skywars_experience").getAsInt();

                    int[] xps = {0, 20, 70, 150, 250, 500, 1000, 2000, 3500, 6000, 10000, 15000};
                    if (xp >= 15000) {
                        int lvl = Math.round(((xp - 15000) / 10000 + 12));
                        String lvlfin = lvl + "";
                        String lvlcolo = "";

                        if (lvl<15){
                            lvlfin = EnumChatFormatting.GOLD + "" + lvl ;
                        }
                        else if (lvl<20){
                            lvlfin = EnumChatFormatting.AQUA + "" + lvl ;
                        }
                        else if (lvl<25){
                            lvlfin = EnumChatFormatting.DARK_GREEN + "" + lvl ;
                        }
                        else if (lvl<30){
                            lvlfin = EnumChatFormatting.DARK_AQUA + "" + lvl ;
                        }
                        else if (lvl<35){
                            lvlfin = EnumChatFormatting.DARK_RED + "" + lvl ;
                        }
                        else if (lvl<40){
                            lvlfin = EnumChatFormatting.LIGHT_PURPLE + "" + lvl ;
                        }
                        else if (lvl<45){
                            lvlfin = EnumChatFormatting.DARK_BLUE + "" + lvl ;
                        }
                        else if (lvl<50){
                            lvlfin = EnumChatFormatting.DARK_PURPLE + "" + lvl ;
                        }
                        else {
                            for(int i = 0; i < lvlfin.length();i++){
                                if(lvlfin.length() == 2) {
                                    if (i==0)
                                        lvlcolo += EnumChatFormatting.GOLD + "" + lvlfin.charAt(i) + "";
                                    if (i==1)
                                        lvlcolo += EnumChatFormatting.YELLOW + "" + lvlfin.charAt(i) + "";
                                }
                                else if (lvlfin.length() == 3){
                                    if (i==0)
                                        lvlcolo += EnumChatFormatting.GOLD + "" + lvlfin.charAt(i) + "";
                                    if (i==1)
                                        lvlcolo += EnumChatFormatting.YELLOW + "" + lvlfin.charAt(i) + "";
                                    if (i==2)
                                        lvlcolo += EnumChatFormatting.GREEN + "" + lvlfin.charAt(i) + "";

                                }

                            }
                            return lvlcolo;
                        }



                        return lvlfin;
                    } else {
                        for (int i = 0; i < xps.length; i++) {
                            if (xp < xps[i]) {
                                int lvl = Math.round(i + (xp - xps[i - 1]) / (xps[i] - xps[i - 1]));
                                String lvlfin = "";

                                if(lvl<5){
                                    lvlfin = EnumChatFormatting.DARK_GRAY + "" + lvl ;
                                }
                                else if (lvl<10){
                                    lvlfin = EnumChatFormatting.WHITE + "" + lvl ;
                                }
                                else if (lvl<15){
                                    lvlfin = EnumChatFormatting.GOLD + "" + lvl ;
                                }


                                return  lvlfin ;
                                //1 + 11 + (2286) / (5000)
                            }
                        }
                    }
                }
            }
        }

        return "";
    }

    public String getSwStats(JsonObject playyer) {
        if (playyer.has("stats")) {
            JsonObject stats = playyer.get("stats").getAsJsonObject();
            if (stats.has("SkyWars")) {
                JsonObject sw = stats.get("SkyWars").getAsJsonObject();
                if (sw.has("kills") && sw.has("deaths") && sw.has("losses")  &&
                        sw.has("wins")) {

                    Double killss = sw.get("kills").getAsDouble();
                    Double deathss = sw.get("deaths").getAsDouble();
                    Double winss = sw.get("wins").getAsDouble();
                    Double lossess = sw.get("losses").getAsDouble();

                    double wl = Math.round((winss / lossess) * 100.0) / 100.0;
                    double kd = Math.round((killss / deathss) * 100.0) / 100.0;

                    String kdwl = EnumChatFormatting.GRAY + "KD&WL: " + EnumChatFormatting.YELLOW + kd + " & " + wl + EnumChatFormatting.GRAY + " ";
                    String wins = EnumChatFormatting.GRAY + "Wins: " + EnumChatFormatting.GREEN + sw.get("wins").getAsString();
                    return kdwl + "\n" + wins;

                }
                else if (sw.has("kills") && sw.has("deaths") && sw.has("losses")  &&
                        !sw.has("wins")) {

                    Double killss = sw.get("kills").getAsDouble();
                    Double deathss = sw.get("deaths").getAsDouble();
                    Double winss = 0.0;
                    Double lossess = sw.get("losses").getAsDouble();

                    double wl = Math.round((winss / lossess) * 100.0) / 100.0;
                    double kd = Math.round((killss / deathss) * 100.0) / 100.0;

                    String kdwl = EnumChatFormatting.GRAY + "KD&WL: " + EnumChatFormatting.YELLOW + kd + " & " + wl + EnumChatFormatting.GRAY + " ";
                    String wins = EnumChatFormatting.GRAY + "Wins: " + EnumChatFormatting.GREEN + 0;
                    return kdwl + "\n" + wins;
                }
                else {
                    return " unknown or too low";
                }

            } else {
                return " unknown or too low";
            }
        }
        else return " unknown or too low";
    }

    public String getBwStats(JsonObject playyer){

        if(playyer.has("stats")) {

            JsonObject stats = playyer.get("stats").getAsJsonObject();

            if(stats.has("Bedwars")) {

                JsonObject bw = stats.get("Bedwars").getAsJsonObject();
                if(bw.has("kills_bedwars") && bw.has("deaths_bedwars") && bw.has("wins_bedwars") && bw.has("losses_bedwars")) {
                    Double kills = bw.get("kills_bedwars").getAsDouble();
                    Double deaths = bw.get("deaths_bedwars").getAsDouble();

                    Double winsw = bw.get("wins_bedwars").getAsDouble();
                    Double loses = bw.get("losses_bedwars").getAsDouble();

                    double wl = Math.round((winsw / loses) * 100.0) / 100.0;
                    double kd = Math.round((kills / deaths) * 100.0) / 100.0;

                    return kd + " & " + wl;
                }
                else
                    return "0 or too low";
            }
            else
                return "0 or too low";
        }
        else
            return "0 or too low";
    }

    public String getBwWins(JsonObject playyer){

        if(playyer.has("stats")) {

            JsonObject stats = playyer.get("stats").getAsJsonObject();

            if(stats.has("Bedwars")) {

                JsonObject bw = stats.get("Bedwars").getAsJsonObject();
                if(bw.has("wins_bedwars")) {
                    String wins = bw.get("wins_bedwars").getAsString();

                    return wins + "";
                }
                else
                    return "0";
            }
            else
                return "0";
        }
        else
            return "0";
    }

    public int getSmash(JsonObject playyer){

        if(playyer.has("stats")) {

            JsonObject stats = playyer.get("stats").getAsJsonObject();

            if(stats.has("SuperSmash")) {

                JsonObject bw = stats.get("SuperSmash").getAsJsonObject();
                if(bw.has("smashLevel")) {

                    return bw.get("smashLevel").getAsInt();
                }
                else
                    return 0;
            }
            else
                return 0;
        }
        else
            return 0;
    }

    //networkLevel = (sqrt((2 * networkEXP) + 30625) / 50) - 2.5
    public int getNetLvl(JsonObject playyer){
        if(playyer.has("networkExp")) {
            int leve = playyer.get("networkExp").getAsInt();

            return (int) Math.round((Math.sqrt((2 * leve) + 30625) / 50) - 2.5);
        }
        else {
            return 0;
        }
    }

    public int getAP(JsonObject playyer){
        if(playyer.has("achievementPoints")) {

            return playyer.get("achievementPoints").getAsInt();
        }
        return 0;
    }

    public int getQuests(JsonObject playyer){

        if(playyer.has("achievements")) {
            JsonObject ach = playyer.get("achievements").getAsJsonObject();

            if (ach.has("general_quest_master")) {
                return ach.get("general_quest_master").getAsInt();
            }
        }
        return 0;

    }

    public String getLLogin(JsonObject playyer){
        if(playyer.has("lastLogin")) {

            return playyer.get("lastLogin").getAsString();
        }
        else {
            return "staf";
        }
    }

    public String getFLogin(JsonObject playyer){

        return playyer.get("firstLogin").getAsString();
    }

    public String getBwLvl(JsonObject playyer){

        if(playyer.has("achievements")) {
            JsonObject ach = playyer.get("achievements").getAsJsonObject();

            if (ach.has("bedwars_level")) {

                return ach.get("bedwars_level").getAsString();
            }
        }
        return "0";
    }


    public String getName(JsonObject playyer){

        String Name = playyer.get("displayname").getAsString();
        String rank = "";
        EnumChatFormatting pluscolor = EnumChatFormatting.RED; //rankPlusColor

        if(playyer.has("prefix")){
            rank = playyer.get("prefix").getAsString();
        }
        else if(playyer.has("rank") && playyer.get("rank").getAsString() != "NORMAL"){
            if(playyer.get("rank").getAsString().equals("HELPER")) rank  = EnumChatFormatting.DARK_AQUA + "[HELPER]";
            else if(playyer.get("rank").getAsString().equals("MODERATOR")) rank = EnumChatFormatting.DARK_GREEN + "[MODERATOR]" ;
            else if(playyer.get("rank").getAsString().equals("YOUTUBER")) rank = EnumChatFormatting.RED + "[§rYOUTUBER§c]";
            else if(playyer.get("rank").getAsString().equals("ADMIN")) rank = EnumChatFormatting.RED + "[ADMIN]";
        }
        else if(playyer.has("newPackageRank")) {
            if(playyer.get("newPackageRank").getAsString().equals("MVP_PLUS")){
                if(playyer.has("rankPlusColor")){
                    pluscolor = EnumChatFormatting.getValueByName(playyer.get("rankPlusColor").getAsString());
                }
                rank = ( playyer.has("monthlyPackageRank") && playyer.get("monthlyPackageRank").getAsString().equals("SUPERSTAR")) ? "§6[MVP"+ pluscolor +"++§6]" : "§b[MVP"+ pluscolor +"+§b]";
            }
            else if(playyer.get("newPackageRank").getAsString().equals("MVP")) rank = EnumChatFormatting.AQUA + "MVP"  ;
            else if(playyer.get("newPackageRank").getAsString().equals("VIP_PLUS")) rank = EnumChatFormatting.GREEN + "[VIP§e+§a]" ;
            else if(playyer.get("newPackageRank").getAsString().equals("VIP"))	rank = EnumChatFormatting.GREEN + "[VIP]";
            else rank = "Non";
        }
        if(rank.isEmpty()){
            rank = "Non";
        }
        return rank + " " + Name;
    }

    public int[] skillXPPerLevel = {0, 50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000,
            75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000,
            1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000,
            2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000, 4300000,
            4600000, 4900000, 5200000, 5500000, 5800000, 6100000, 6400000, 6700000, 7000000};

    public double xpToSkillLevel(double xp, int limit) {
        for (int i = 0, xpAdded = 0; i < limit + 1; i++) {
            xpAdded += skillXPPerLevel[i];
            if (xp < xpAdded) {
                return (i - 1) + (xp - (xpAdded - skillXPPerLevel[i])) / skillXPPerLevel[i];
            }
        }
        return limit;
    }

    public ArrayList<Double> skills(JsonObject userObject, String uuid, String apikey, JsonObject playerObject){

        double farmingLevel = 0;
        double miningLevel = 0;
        double combatLevel = 0;
        double foragingLevel = 0;
        double fishingLevel = 0;
        double enchantingLevel = 0;
        double alchemyLevel = 0;
        double tamingLevel = 0;

        if (userObject.has("experience_skill_farming") || userObject.has("experience_skill_mining") || userObject.has("experience_skill_combat") || userObject.has("experience_skill_foraging") || userObject.has("experience_skill_fishing") || userObject.has("experience_skill_enchanting") || userObject.has("experience_skill_alchemy")) {
            if (userObject.has("experience_skill_farming")) {
                farmingLevel = xpToSkillLevel(userObject.get("experience_skill_farming").getAsDouble(), 60);
                farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_mining")) {
                miningLevel = xpToSkillLevel(userObject.get("experience_skill_mining").getAsDouble(), 60);
                miningLevel = (double) Math.round(miningLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_combat")) {
                combatLevel = xpToSkillLevel(userObject.get("experience_skill_combat").getAsDouble(), 60);
                combatLevel = (double) Math.round(combatLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_foraging")) {
                foragingLevel = xpToSkillLevel(userObject.get("experience_skill_foraging").getAsDouble(), 50);
                foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_fishing")) {
                fishingLevel = xpToSkillLevel(userObject.get("experience_skill_fishing").getAsDouble(), 50);
                fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_enchanting")) {
                enchantingLevel = xpToSkillLevel(userObject.get("experience_skill_enchanting").getAsDouble(), 60);
                enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_alchemy")) {
                alchemyLevel = xpToSkillLevel(userObject.get("experience_skill_alchemy").getAsDouble(), 50);
                alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
            }
            if (userObject.has("experience_skill_taming")) {
                tamingLevel = xpToSkillLevel(userObject.get("experience_skill_taming").getAsDouble(), 50);
                tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
            }
        } else {
            // Get skills from achievement API, will be floored

            if (!playerObject.get("success").getAsBoolean()) {
                return null;
            }

            JsonObject achievementObject = playerObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
            if (achievementObject.has("skyblock_harvester")) {
                farmingLevel = achievementObject.get("skyblock_harvester").getAsInt();
            }
            if (achievementObject.has("skyblock_excavator")) {
                miningLevel = achievementObject.get("skyblock_excavator").getAsInt();
            }
            if (achievementObject.has("skyblock_combat")) {
                combatLevel = Math.min(achievementObject.get("skyblock_combat").getAsInt(), 60);
            }
            if (achievementObject.has("skyblock_gatherer")) {
                foragingLevel = Math.min(achievementObject.get("skyblock_gatherer").getAsInt(), 50);
            }
            if (achievementObject.has("skyblock_angler")) {
                fishingLevel = Math.min(achievementObject.get("skyblock_angler").getAsInt(), 50);
            }
            if (achievementObject.has("skyblock_augmentation")) {
                enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
            }
            if (achievementObject.has("skyblock_concoctor")) {
                alchemyLevel = Math.min(achievementObject.get("skyblock_concoctor").getAsInt(), 50);
            }
            if (achievementObject.has("skyblock_domesticator")) {
                tamingLevel = Math.min(achievementObject.get("skyblock_domesticator").getAsInt(), 50);
            }
        }
        ArrayList<Double> list = new ArrayList<Double>();
        Collections.addAll(list,farmingLevel,miningLevel,combatLevel,foragingLevel,fishingLevel,enchantingLevel,alchemyLevel,tamingLevel);
        return list;
    }

    public String maxskill(double level,String type){
        String toreturn = "";
        if(type.contains("farmingLevel")){
            if(level == 60) {
                toreturn = "§c";
            }
        }
        if(type.contains("miningLevel")){
            if(level == 60) {
                toreturn = "§c";
            }
        }
        if(type.contains("combatLevel")){
            if(level == 60) {
                toreturn = "§c";
            }
        }
        if(type.contains("foragingLevel")){
            if(level == 50) {
                toreturn = "§c";
            }
        }
        if(type.contains("fishingLevel")){
            if(level == 50) {
                toreturn = "§c";
            }
        }
        if(type.contains("enchantingLevel")){
            if(level == 60) {
                toreturn = "§c";
            }
        }
        if(type.contains("alchemyLevel")){
            if(level == 50) {
                toreturn = "§c";
            }
        }
        if(type.contains("tamingLevel")){
            if(level == 50) {
                toreturn = "§c";
            }
        }
        if(type.contains("skillAvg")){
            if(level >= 50) {
                toreturn = "§c";
            }
        }

        return toreturn;
    }

}
