package me.a0g.hyk;

import lombok.Getter;
import lombok.Setter;
import me.a0g.hyk.chest.*;
import me.a0g.hyk.config.HyConfig;
import me.a0g.hyk.config.HykPos;
import me.a0g.hyk.events.*;
import me.a0g.hyk.commands.*;
import me.a0g.hyk.gui.EditLocationsGui;
import me.a0g.hyk.mytests.BankHook;
import me.a0g.hyk.mytests.DiscordRPCManager;
import me.a0g.hyk.mytests.SwHook;
import me.a0g.hyk.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Mod(modid = HypixelKentik.MODID, version = HypixelKentik.VERSION, name = HypixelKentik.NAME)
public class HypixelKentik {
    public static final String MODID = "hyk";
    public static final String VERSION = "3.0.1";
    public static final String NAME = "HyK";

   // private final HyConfig hyConfig = new HyConfig();
    private HyConfig hyConfig;

    @Setter
    private HykPos hykPos;

    public final KeyBinding[] keyBindings = new KeyBinding[4];

    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";

    @Getter
    private static HypixelKentik instance;

    public static File dir;

    private final Utils utils;

    private final TabCompletionUtil tabutil;

    private final ApiUtils apiUtils;
    
    private final TextUtils textUtils;

    private final NewScheduler newScheduler;

    private final DiscordRPCManager discordRPCManager;

    private NumberFormat numberFormatter;

    @Setter private  boolean isBinShow = false;

    public HypixelKentik() {
        instance = this;

        utils = new Utils();
        tabutil = new TabCompletionUtil();
        apiUtils = new ApiUtils();
        textUtils = new TextUtils();
        newScheduler = new NewScheduler();
        discordRPCManager = new DiscordRPCManager();
        numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        numberFormatter.setMaximumFractionDigits(0);
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        dir = new File(event.getModConfigurationDirectory() ,"hyk");
        dir.mkdirs();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        hyConfig = new HyConfig(new File(dir,"hyk.toml"));
        hyConfig.preload();

        enablepos();

        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new HyK());
        ClientCommandHandler.instance.registerCommand(new GetBw());
        ClientCommandHandler.instance.registerCommand(new GetNetworkStats());
        ClientCommandHandler.instance.registerCommand(new GetSw());
        ClientCommandHandler.instance.registerCommand(new GetSwAll());
        ClientCommandHandler.instance.registerCommand(new GetBwAll());
        ClientCommandHandler.instance.registerCommand(new SlotNbt());
        ClientCommandHandler.instance.registerCommand(new RemoveEntity());
        ClientCommandHandler.instance.registerCommand(new GetSmash());
        ClientCommandHandler.instance.registerCommand(new SbStats());

        //events
        MinecraftForge.EVENT_BUS.register(new EventKey());
        MinecraftForge.EVENT_BUS.register(new DianaEvent());
        MinecraftForge.EVENT_BUS.register(new Cakes());
        MinecraftForge.EVENT_BUS.register(new Render());
//         MinecraftForge.EVENT_BUS.register(new Puzzler());

        //chest
        MinecraftForge.EVENT_BUS.register(new ChestTest());
        MinecraftForge.EVENT_BUS.register(new Player());
        MinecraftForge.EVENT_BUS.register(new NameT());
        MinecraftForge.EVENT_BUS.register(new Skull());
        MinecraftForge.EVENT_BUS.register(new FairyTest());
        MinecraftForge.EVENT_BUS.register(new BatSp());
        MinecraftForge.EVENT_BUS.register(new Presents());

        MinecraftForge.EVENT_BUS.register(new TooltipListener());


        MinecraftForge.EVENT_BUS.register(new BankHook());
        MinecraftForge.EVENT_BUS.register(new SwHook());

        MinecraftForge.EVENT_BUS.register(newScheduler);

        keyBindings[0] = new KeyBinding("HYK Config", Keyboard.KEY_P, NAME);
        keyBindings[1] = new KeyBinding("Delete near entities", Keyboard.KEY_K, NAME);
        keyBindings[2] = new KeyBinding("Display stats", Keyboard.KEY_J, NAME);
        keyBindings[3] = new KeyBinding("Hyk", Keyboard.KEY_H, NAME);

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }

    }

    @Mod.EventHandler
    public void onPostInit(final FMLPostInitializationEvent event) {
        Updater.builder()
                .name("Hyk")
                .currentVersion(this.VERSION)
                .changelogUrl("https://raw.githubusercontent.com/a0gzy/Hyk/master/changelog")
                .downloadUrl("https://github.com/a0gzy/Hyk/releases/latest")
                .build()
                .checkAsync();

    }

    static int tickAmount = 1;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;

        // Checks every second
        tickAmount++;
        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }
    }

    public static String guiToOpen = null;
    public static String prevGui = null;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (guiToOpen.equalsIgnoreCase("editlocations")) {
                mc.displayGuiScreen(new EditLocationsGui());
            }
            guiToOpen = null;
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR)
            return;
        if (showTitle) {
            utils.drawTitle(titleText);
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    public void enablepos() {
        try {
            hykPos = HykPos.load();
            FMLLog.info("hykpos loaded");
           // hykPos = (HykPos) ConfigTweaker.load("hykpos.json",HykPos.class);
        } catch (IOException e) {
            //e.printStackTrace();
            if(hykPos == null){
                hykPos = new HykPos();
                FMLLog.info("hykpos new");
            }
            // Write the default config that we just made to save it
            hykPos.save();
            FMLLog.info("hykpos saved");
        }
    }


    @SubscribeEvent
    public void onLogin(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        if (hyConfig.getApikeyy().isEmpty()) {
            new Thread(() -> {

                try {
                    Thread.sleep(2000);

                    utils.sendMessage(EnumChatFormatting.GREEN + "You ApiKey isn't set upped - use /hyk api");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        /*onHypixel = !FMLClientHandler.instance().getClient().isSingleplayer()
                && (FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains("hypixel.net") ||
                FMLClientHandler.instance().getClient().getCurrentServerData().serverName.equalsIgnoreCase("HYPIXEL"));*/
    }


}


