package me.a0g.hyk.mytests;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.handlers.WebHooks;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;

public class SwHook {

    private final HypixelKentik main = HypixelKentik.getInstance();
    private static final String webhook = "https://discord.com/api/webhooks/791328152516821042/kmip-QBylmZQHU763fni24f7WBaM_bhLt6ohnn5Zg6HF8dU4PpZjgyqZt16eyo3URkkX";

    private String allstring = "";

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if(!main.getHyConfig().isSwhook()) return;
        String unformattedText = event.message.getUnformattedText();
           /* if(unformattedText.equalsIgnoreCase("Skywars") || unformattedText.matches("Winner - ") ||
                    unformattedText.matches("1st Killer - ") || unformattedText.matches("2nd Killer - ") || unformattedText.matches("3rd Killer - ")){*/

        if(unformattedText.contains("Winner - ") || unformattedText.contains("Winners - ")){
            allstring=unformattedText;
           // FMLLog.info(allstring);
        }
        if(unformattedText.contains("1st Killer - ")){
            allstring=allstring + "\\n" + "\\n" + unformattedText;
           // FMLLog.info(allstring);
        }
        if(unformattedText.contains("2nd Killer - ")){
            allstring=allstring +"\\n" +  unformattedText;
           // FMLLog.info(allstring);
        }
        if(unformattedText.contains("3rd Killer - ")){
            allstring=allstring + "\\n" + unformattedText;

            //String finalstr = "```" + allstring;

            new Thread(() -> {

            try {
                Thread.sleep(500);
                FMLLog.info(allstring);
                WebHooks.sendData("|",webhook);
                WebHooks.sendData("```"+allstring+"```",webhook);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            }).start();
        }


    }

}
