package me.a0g.hyk.core;

import com.google.gson.JsonObject;
import gg.essential.api.EssentialAPI;
import gg.essential.api.utils.Multithreading;
import gg.essential.universal.UDesktop;
import javafx.scene.control.Hyperlink;
import kotlin.Unit;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.handlers.APIHandler;
import me.a0g.hyk.mytests.DeleteHook;
import me.a0g.hyk.utils.ApiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.security.Key;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoUpdater {

    public static boolean isUpdated;
    public static boolean isUpdatedForPush;

    public void downloadDelete(){

        Thread sd = new Thread(() -> {


            JsonObject rep = APIHandler.getResponse("https://api.github.com/repos/a0gzy/Hyk/releases/latest");

            File taskDir = new File(HypixelKentik.dir,"update");
            taskDir.mkdir();


            //name
            String newJarVersion = rep.get("name").getAsString().replaceAll("[.]", "");
            String oldJarVersion = HypixelKentik.VERSION.replaceAll("[.]", "");

          //  FMLLog.info("Versions " + newJarVersion + "  " + oldJarVersion);

            //if( Integer.parseInt(newJarVersion) > Integer.parseInt(oldJarVersion) ){
            if (Integer.parseInt(newJarVersion) > Integer.parseInt(oldJarVersion)) {
                FMLLog.info("Hyk is now updating");

                String updateUrl = rep.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString();
                String jarName = rep.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();

                File taskFile = new File(taskDir,"SkytilsInstaller-1.1-SNAPSHOT.jar");
                if(!taskFile.exists()){
                    try {
                        URL urlTask = new URL( "https://raw.githubusercontent.com/a0gzy/Hyk/master/SkytilsInstaller-1.1-SNAPSHOT.jar");
                        FileUtils.copyURLToFile(urlTask, taskFile, 1000, 1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {

                   // FMLLog.info("Hyk is in development");

                    URL url = new URL(updateUrl);
                    FileUtils.copyURLToFile(url, new File(HypixelKentik.getInstance().jarFile.getParentFile(), jarName), 1000, 1000); //C:\Users\a0g\Desktop\Hyk\build\classes\java\main


                    isUpdated = true;
                    isUpdatedForPush = true;
                  //  FMLLog.info(isUpdated + " AYE " + isUpdatedForPush);
                    //System.out.println(isUpdated  + " " + isUpdatedForPush);


                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        File oldJar = HypixelKentik.getInstance().getJarFile();

                        if (oldJar == null || !oldJar.exists() || oldJar.isDirectory()) {
                            System.out.println("Old file not found.");
                            return;
                        }
                        if (oldJar.delete()) {
                            System.out.println("successfully deleted the files. skipping install tasks");
                            return;
                        }

                        if(taskFile.exists()){
                            try {
                                String runtime = getJavaRuntime();
                                Runtime.getRuntime().exec("java -jar " + taskFile.getAbsolutePath() + " " + oldJar.getAbsolutePath() +"");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }));
                } catch (IOException e) {
                    FMLLog.info(e + "");
                }

            } else FMLLog.info("HYK is up to date");

        });
        sd.setName("Hyk Updater thread");
        sd.start();
    }

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.DrawScreenEvent.Post e) {
        /*if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            new TextRenderer(Minecraft.getMinecraft(), e.gui.toString(), 10, 10, 1, Color.ORANGE.getRGB(), true);
        }*/
        if (e.gui instanceof GuiMainMenu) {
           // FMLLog.info("GDE " + isUpdatedForPush + " " + isUpdated);
            if (isUpdatedForPush) {
                FMLLog.info("GDE PUSH");
              //  Multithreading.runAsync(() -> {
                //EssentialAPI.getShutdownHookUtil().

                            EssentialAPI.getNotifications().push(
                                    "Hyk Updated.",
                                    "Reload your game!",
                                    15f
                            );

               // });

                isUpdatedForPush = false;
            }
        }
    }

    public String getJavaRuntime() throws IOException {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator +
                (os != null && os.toLowerCase(Locale.ENGLISH).startsWith("windows") ? "java.exe" : "java");
        if (!new File(java).isFile()) {
            throw new IOException("Unable to find suitable java runtime at "+java);
        }
        return java;
    }


}
