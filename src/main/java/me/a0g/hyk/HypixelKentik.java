package me.a0g.hyk;

import gg.essential.api.EssentialAPI;
import gg.essential.api.config.EssentialConfig;
import gg.essential.loader.stage0.EssentialSetupTweaker;
import lombok.Getter;
import lombok.Setter;
import me.a0g.hyk.chest.*;
import me.a0g.hyk.config.HyConfig;
import me.a0g.hyk.events.*;
import me.a0g.hyk.commands.*;
import me.a0g.hyk.gui.EditLocationsGui;
import me.a0g.hyk.gui.HykGui;
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
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

@Getter
@Mod(modid = HypixelKentik.MODID, version = HypixelKentik.VERSION, name = HypixelKentik.NAME)
public class HypixelKentik
{
    public static final String MODID = "hyk";
    public static final String VERSION = "2.0";
    public static final String NAME = "HyK";

    private HyConfig hyConfig = new HyConfig();

    public final KeyBinding[] keyBindings = new KeyBinding[4];

    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";

    @Getter private static HypixelKentik instance;

    private Utils utils;

    private TabCompletionUtil tabutil;

    private ApiUtils apiUtils;

    private TextUtils textUtils;

    private NewScheduler newScheduler;

    private DiscordRPCManager discordRPCManager;

    public HypixelKentik() {
        instance = this;

        utils = new Utils();
        tabutil = new TabCompletionUtil();
        apiUtils = new ApiUtils();
        textUtils = new TextUtils();
        newScheduler = new NewScheduler();
        discordRPCManager = new DiscordRPCManager();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        //ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
       // hyConfig = new HyConfig();
        hyConfig.preload();


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


         MinecraftForge.EVENT_BUS.register(new BankHook());
         MinecraftForge.EVENT_BUS.register(new SwHook());

         //gui
         ClientCommandHandler.instance.registerCommand(new Move());
         ClientCommandHandler.instance.registerCommand(new Scale());

         Move.mainXY[0] = hyConfig.getMainx();
         Move.mainXY[1] = hyConfig.getMainy();
         Move.cakeXY[0] = hyConfig.getCakex();
         Move.cakeXY[1] = hyConfig.getCakey();
         Move.armorXY[0] = hyConfig.getArmorx();
         Move.armorXY[1] = hyConfig.getArmory();
         Move.commsXY[0] = hyConfig.getCommsx();
         Move.commsXY[1] = hyConfig.getCommsy();
         Scale.mainScale = Double.parseDouble( hyConfig.getScale() );
         Scale.sizerScale = Double.parseDouble( hyConfig.getSizescale() );


         MinecraftForge.EVENT_BUS.register(newScheduler);

         keyBindings[0] = new KeyBinding("HYK Config", Keyboard.KEY_P, NAME);
         keyBindings[1] = new KeyBinding("Delete near entities", Keyboard.KEY_K, NAME);
         keyBindings[2] = new KeyBinding("Display stats", Keyboard.KEY_J, NAME);
         keyBindings[3] = new KeyBinding("Hyk", Keyboard.KEY_H, NAME);

         for (KeyBinding keyBinding : keyBindings) {
             ClientRegistry.registerKeyBinding(keyBinding);
         }

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
            if(guiToOpen.equalsIgnoreCase("editlocations")) {
                mc.displayGuiScreen(new EditLocationsGui());
            }
            if(guiToOpen.startsWith("hykgui")) {
                int page = Character.getNumericValue(guiToOpen.charAt(guiToOpen.length() - 1));
                String selection = guiToOpen.replaceAll("hykgui","");
                selection = selection.replaceAll("[0-9]","");
                mc.displayGuiScreen(new HykGui(page,selection));
            }
            guiToOpen = null;
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR) return;
        if (showTitle) {
           utils.drawTitle(titleText);
        }
    }

    public static boolean isInteger(String s) {
	    return isInteger(s, 10);
	  }

	public  static boolean isInteger(String s, int radix) {
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


    @SubscribeEvent
    public void onLogin(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        if(hyConfig.getApikeyy().isEmpty()){
            new Thread(() -> {

                try {
                    Thread.sleep(2000);

                    utils.sendMessage(EnumChatFormatting.GREEN + "You ApiKey isn't set upped - use /hyk api" );

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        /*onHypixel = !FMLClientHandler.instance().getClient().isSingleplayer()
                && (FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains("hypixel.net") ||
                FMLClientHandler.instance().getClient().getCurrentServerData().serverName.equalsIgnoreCase("HYPIXEL"));*/
    }

    /*@SubscribeEvent
    public void onPlayerLogOutEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        onHypixel = false;
    }*/


//    public void setOnHypixel(boolean onHypixel) {
//        this.onHypixel = onHypixel;
//    }

}


