package me.a0g.hyk.mytests;



import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLLog;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;


public class DiscordRPCManager implements IPCListener {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private boolean connected = false;
    private boolean firstconection = true;

    public String details = "Forge 1.8.9";
    public String state = "";
    public String smallImageKey = "";
    public String smallImageText = "";

    private static final long APPLICATION_ID = 862685928195096617L;
    private static final long UPDATE_PERIOD = 10L;
    private Timer updateTimer;


    private IPCClient client;
    private OffsetDateTime startTimestamp;


    public void start2() {
        try {
            //logger.info("Starting Discord RP...");
            if (isActive()) {
                return;
            }

            client = new IPCClient(APPLICATION_ID);
            client.setListener(this);
            if(firstconection) {
                startTimestamp = OffsetDateTime.now();
                firstconection = false;
            }

            try {
                client.connect();
                RichPresence presence = new RichPresence.Builder()
                        .setDetails(details)
                        .setState(state)
                        .setStartTimestamp(startTimestamp)
                        .setLargeImage("rpc","Hyk 2.0 by a0g#1387")
                        .build();
                client.sendRichPresence(presence);

            } catch (Exception e) {
               // logger.warn("Failed to connect to Discord RPC: " + e.getMessage());
            }
        } catch (Throwable ex) {
           // logger.error("DiscordRP has thrown an unexpected error while trying to start...");
            ex.printStackTrace();
        }
    }

    public boolean isActive(){
        return client != null && connected;
    }

    public void stop2(){
        if(isActive()){
            client.close();
            connected = false;
        }
    }

    public void updatePresence2() {
        //presence.endTimestamp   =  System.currentTimeMillis() / 1000 - presence.startTimestamp;
       // if(isActive()) {
        if(smallImageKey != "") {
            RichPresence presence = new RichPresence.Builder()
                    .setDetails(details)
                    .setState(state)
                    .setLargeImage("rpc", "Hyk 2.0 by a0g#1387")
                    .setSmallImage(smallImageKey, smallImageText)
                    .setStartTimestamp(startTimestamp)
                    .build();
            client.sendRichPresence(presence);
        }
        /*else if(Minecraft.getMinecraft() == null){
           stop2();
        }*/
        else
        {
            RichPresence presence = new RichPresence.Builder()
                    .setDetails(details)
                    .setState(state)
                    .setLargeImage("rpc", "Hyk 2.0 by a0g#1387")
                    .setStartTimestamp(startTimestamp)
                    .build();
            client.sendRichPresence(presence);
        }

        //}
    }

    @Override
    public void onReady(IPCClient client) {
        //logger.info("Discord RPC started");
        connected = true;
        updateTimer = new Timer();
        /*updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                main.getUtils().setRPCGame();
                FMLLog.info("DA");
                updatePresence2();
            }
        }, 0, UPDATE_PERIOD);*/
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                main.getUtils().setRPCGame();
             //   FMLLog.info("DA");
                updatePresence2();
            }
        },0,5*1000);
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
       // logger.info("Discord RPC closed");
        this.client = null;
        connected = false;
        cancelTimer();
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
       // logger.warn("Discord RPC disconnected");
        this.client = null;
        connected = false;
        cancelTimer();
    }

    private void cancelTimer() {
        if(updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
    }
}
