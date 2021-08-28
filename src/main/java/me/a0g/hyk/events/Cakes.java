package me.a0g.hyk.events;

import gg.essential.api.utils.mojang.TextureURL;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.core.features.BuildBattleHelper;
import me.a0g.hyk.utils.ColorUtils;
import me.a0g.hyk.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cakes {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private static final Pattern BBH = Pattern.compile("The theme is (.+)");

    public static double newUse;

    public static String gamemsg;

    private final List<Long> farmingalch = new ArrayList<>();

    private boolean wasfarmed = false;
    private long farmLastPressed;

    private boolean oneWord = false;
    private boolean sixWords = false;

    private int getFarmingPer5(String text) {

        if(text.contains("Farming") || text.contains("Carpentry") || text.contains("Alchemy")) {
            //wasfarmed = true;
            farmLastPressed = System.currentTimeMillis();
           // if(wasfarmed) {
                this.farmingalch.add(farmLastPressed);
          //  }
        }

        this.farmingalch.removeIf(looong -> looong + 1500 < System.currentTimeMillis());
        return this.farmingalch.size();
    }


    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String unformattedText = event.message.getUnformattedText();

       /* if(unformattedText.contains("a0g")){
            FMLLog.info(event.message.getFormattedText());
        }*/

        //actionbar
        if(event.type == 2){
            //if(main.getUtils().checkForGame("BUILD BATTLE")) {

            //bbh
            if(main.getUtils().checkForGame("GUESS THE BUILD") && main.getHyConfig().isBbh()) {

                String clearedTheme = TextUtils.stripColor(unformattedText);
               // FMLLog.info("GTD " + event.message.getUnformattedText());

                Matcher matcher = BBH.matcher(clearedTheme);
                if (matcher.matches()) {
                    String theme = matcher.group(1);
                   // FMLLog.info(theme);
                    /*if(!theme.contains("_") && !main.getBbhWords().contains(theme)){
                        main.getUtils().sendMessage("New world added: " + theme.toLowerCase());
                        main.getBbhWords().add(theme.toLowerCase());
                    }*/
                    BuildBattleHelper bbh = new BuildBattleHelper(theme);
                    main.getInGameBbhWords().addAll(bbh.getWords());

                   // FMLLog.info(oneWord + " is " + bbh.getWords().size());
                    if(bbh.getWords().size() == 1){

                       //if( oneWord && ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer,"/ac " + bbh.getWords().get(0) ) != 0){
                       if( oneWord ){
                           main.getUtils().sendMessage("Theme is: " + bbh.getWords().get(0));

                           Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + bbh.getWords().get(0));
                           FMLLog.info("executed ");
                           oneWord = false;
                       }
                    }else if(bbh.getWords().size() <= 6){
                        if(sixWords) {
                            main.getUtils().sendMessage("§7" + main.getInGameBbhWords() + "");
                            sixWords = false;
                        }
                    }
                }else {
                  //  FMLLog.info("ne mathces ????" + clearedTheme);
                }
            }


            //if(unformattedText.contains("Farming")){
                Render.farmclicks = getFarmingPer5(unformattedText);
           // }
        }

        if(unformattedText.contains("Round: ") && main.getUtils().checkForGame("GUESS THE BUILD")){
            oneWord = true;
            sixWords = true;
            main.getInGameBbhWords().clear();
        }
        if(unformattedText.contains("(Correct Guess)") && main.getUtils().checkForGame("GUESS THE BUILD")){
            oneWord = false;

            main.getInGameBbhWords().clear();
        }

        //Your new API key is
        if(unformattedText.contains("Your new API key is ")){
            String apikey[] = unformattedText.split("Your new API key is ");
           // FMLLog.info(apikey[1] + "");
            String key = apikey[1].replaceAll(" ","");
            main.getHyConfig().setApikeyy(key);
            main.getUtils().sendMessage(EnumChatFormatting.DARK_AQUA + "API key saved");
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
        }

        if(unformattedText.contains("Yum! You gain ")){

            Date date = new Date();
            String fdate = date.getTime() + "";

            main.getHyConfig().setCakepicked(fdate);
            //main.getUtils().sendMessage(main.getHyConfig().getCakepicked());
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();

        }
        if(unformattedText.contains("Mining Speed Boost is now available!") || unformattedText.contains("Pickobulus is now available!")){
            main.getUtils().createTitle(EnumChatFormatting.RED + "Ability",3);

        }

       // §
        if(unformattedText.contains("§cAutopet §eequipped your ") && main.getHyConfig().isSbUtils()){
            event.setCanceled(true);
        }

        /*if(unformattedText.contains("Danreal") && main.getHyConfig().isDaynreal()){
            event.setCanceled(true);
        }*/

        if((unformattedText.contains("GoOd GamE") || unformattedText.contains("You cannot say the same message twice!")) && main.getHyConfig().isAutogame() && gamemsg != null){

            Minecraft.getMinecraft().thePlayer.sendChatMessage("/play " + gamemsg);

        }

        //if(unformattedText.matches("((§.)+)?You used your §.[A-Za-z] §.Picaxe Ability!")){
        if(unformattedText.matches("You used your [A-Za-z ]+ Pickaxe Ability!")){

            final EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
            if( pl.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore") != null) {
                String tocompact = pl.getHeldItem().getTagCompound().getCompoundTag("display").getTag("Lore").toString();
                if(tocompact.contains("Cooldown: ")){
                    String[] tospl = tocompact.split("Cooldown: §a");
                    String[] cds = tospl[1].split("s");
                    int cd = Integer.parseInt( cds[0] ) ;

                    newUse = System.currentTimeMillis() / 1000L + cd;
                }
            }


          //  abilitykd = main.getUtils().getTimeBetween( lastUse, newUse );

           // Log.info(lastUse);
        }
    }


}
