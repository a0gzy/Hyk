package me.a0g.hyk.mytests;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.handlers.WebHooks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;

public class BankHook {

    private final Hyk main = Hyk.getInstance();
    private static final String webhook = "https://discord.com/api/webhooks/791301654782803970/H_Uyo_vCZdQttYBy1IPzi5m-FQDQkYZ0YbDdvCDyOU5c7ATISkNftJRUdDmQP7foP33Z";

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if(!main.getHyConfig().isBankhook()) return;
        String unformattedText = event.message.getUnformattedText();
        if((unformattedText.matches("Withdrew ([0-9])+[a-zA-Z]? coins! There's now ([0-9])+[a-zA-Z]? coins left in the account!"))||
            unformattedText.matches("Deposited ([0-9])+[a-zA-Z]? coins! There's now ([0-9])+[a-zA-Z]? coins in the account!")){
            new Thread(() -> {
                try {
                    String allstring =  Minecraft.getMinecraft().thePlayer.getName() + " " + unformattedText;

                    WebHooks.sendData("", webhook);
                    WebHooks.sendData("```"+allstring+"```", webhook);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

}
