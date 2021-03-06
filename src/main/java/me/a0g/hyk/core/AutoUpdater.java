package me.a0g.hyk.core;

import com.google.gson.JsonObject;
import gg.essential.api.EssentialAPI;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.handlers.APIHandler;
import me.a0g.hyk.handlers.WebHooks;
import me.a0g.hyk.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.net.*;

public class AutoUpdater {

    public static boolean isUpdated;
    public static boolean isUpdatedForPush;

    private static String updateLog;

    public void downloadDelete(){

        Thread sd = new Thread(() -> {


            JsonObject rep = APIHandler.getResponse("https://api.github.com/repos/a0gzy/Hyk/releases/latest");

            File taskDir = new File(Hyk.dir,"update");
            if(!taskDir.exists()) {
                taskDir.mkdir();
            }

            //name
            String newJarVersion = rep.get("name").getAsString().replaceAll("[.]", "");
            String oldJarVersion = Hyk.VERSION.replaceAll("[.]", "");

            if (Integer.parseInt(newJarVersion) > Integer.parseInt(oldJarVersion)) {
                FMLLog.info("Hyk is now updating");

                String updateUrl = rep.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString();
                String jarName = rep.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();

                updateLog = rep.get("body").getAsString();

                if(updateUrl.isEmpty()) {
                    FMLLog.info("updateUrl broken");
                    return;
                }

               /* File taskFile = new File(taskDir,"SkytilsInstaller-1.1-SNAPSHOT.jar");
                if(!taskFile.exists()){
                    try {
                        URL urlTask = new URL( "https://raw.githubusercontent.com/a0gzy/Hyk/master/SkytilsInstaller-1.1-SNAPSHOT.jar");
                        FileUtils.copyURLToFile(urlTask, taskFile, 1000, 1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/

                File deleter = new File(taskDir,"HykFileDeleter.jar");
                if(!deleter.exists()){
                    try {
                        URL urlTask = new URL( "https://raw.githubusercontent.com/a0gzy/Hyk/master/HykFileDeleter.jar");
                        FileUtils.copyURLToFile(urlTask, deleter, 1000, 1000);
                    } catch (IOException e) {
                        try {
                            WebHooks.sendData(Minecraft.getMinecraft().getSession().getUsername() + " autoupdate download fail");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }

                try {

                    URL url = new URL(updateUrl);
                    FileUtils.copyURLToFile(url, new File(Hyk.getInstance().jarFile.getParentFile(), jarName), 1000, 1000); //C:\Users\a0g\Desktop\Hyk\build\classes\java\main


                    isUpdated = true;
                    isUpdatedForPush = true;

                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        File oldJar = Hyk.getInstance().getJarFile();

                        if (oldJar == null || !oldJar.exists() || oldJar.isDirectory()) {
                            System.out.println("Old file not found.");
                            return;
                        }
                        if (oldJar.delete()) {
                            System.out.println("successfully deleted the files. skipping install tasks");
                            return;
                        }

                        if(deleter.exists()){
                            try {
                                //String runtime = getJavaRuntime();
                                String runtime = Utils.getJavaRuntime();
                               // Runtime.getRuntime().exec("java -jar " + deleter.getAbsolutePath() + " " + oldJar.getAbsolutePath() );
                                String cmd = String.format("\"%s\" -jar \"%s\" \"%s\"", runtime , deleter.getAbsolutePath() , oldJar.getAbsolutePath());
                                Runtime.getRuntime().exec(cmd);
                              //  Runtime.getRuntime().exec(runtime + " -jar " + deleter.getAbsolutePath() + " " + oldJar.getAbsolutePath() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {
                                Desktop.getDesktop().open(oldJar.getParentFile());
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
        if (e.gui instanceof GuiMainMenu) {
            if (isUpdatedForPush) {

                EssentialAPI.getNotifications().push(
                        "Hyk Updated. Please reload.",
                        updateLog,
                        15f
                );

                isUpdatedForPush = false;
            }
        }
    }

    /*public String getJavaRuntime() throws IOException {
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator +
                (os != null && os.toLowerCase(Locale.ENGLISH).startsWith("windows") ? "java.exe" : "java");
        if (!new File(java).isFile()) {
            throw new IOException("Unable to find suitable java runtime at "+java);
        }
        return java;
    }*/
}
