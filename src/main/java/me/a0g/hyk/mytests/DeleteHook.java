package me.a0g.hyk.mytests;

import javafx.application.Application;
import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DeleteHook extends Thread {

    private File file;

    public DeleteHook(File file){
        this.file = file;
    }

    @Override
    public void run() {
        System.out.println("Deleting");
      //  try {
            /*File taskDir = new File(HypixelKentik.dir,"update");
            taskDir.mkdir();
            File taskFile = new File(taskDir,"SkytilsInstaller-1.1-SNAPSHOT.jar");*/


            File myFile = new File(HypixelKentik.dir,"FileDeleter-1.0-SNAPSHOT.jar");

            System.out.println("File to delete " + this.file.getAbsolutePath());

            if (this.file == null || !this.file.exists() || this.file.isDirectory()) {
                System.out.println("Old file not found.");
                return;
            }
            System.out.println("myFile " + myFile.getAbsolutePath());
            try {
                if(myFile.exists()) {
                    String cmd = "java -jar " + myFile.getAbsolutePath() + " " + this.file.getAbsolutePath();
                    System.out.println("command " + cmd);
                    Runtime.getRuntime().exec(cmd);
                }

            }catch (IOException e){

            }


            /*if(taskFile.exists()){
                try {
                    String runtime = HypixelKentik.getInstance().getAutoUpdater().getJavaRuntime();
                    Runtime.getRuntime().exec("java -jar " + taskFile.getAbsolutePath() + " " + file.getAbsolutePath());
                  //  System.out.println("Task." + runtime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

           // FileUtils.forceDelete(this.file);
          //  FileUtils.forceDeleteOnExit(this.file);
       // }catch (IOException e){
      //      System.err.println(e);
       // }
    }

}
