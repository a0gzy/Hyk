package me.a0g.hyk.core.features;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoGame {

    private final Hyk main = Hyk.getInstance();

    //This game has been recorded. Click here to watch the Replay!
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if(main.getHyConfig().isAutogame() && !main.getHyConfig().getAutoGameText().isEmpty()) {
            String unformattedText = event.message.getUnformattedText();
            String cleanText = TextUtils.stripColor(unformattedText);

            if(cleanText.contains("This game has been recorded. Click here to watch the Replay!")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(main.getHyConfig().getAutoGameText());
            }
        }
    }
}
