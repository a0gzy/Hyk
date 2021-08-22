package me.a0g.hyk.mytests;

import javafx.application.Application;
import me.a0g.hyk.HypixelKentik;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteHook extends Thread {

    private File file;

    public DeleteHook(File file){
        this.file = file;
    }

    @Override
    public void run() {
        System.out.println("Deleting");
      //  try {
            File taskDir = new File(HypixelKentik.dir,"update");
            taskDir.mkdir();

            File taskFile = new File(taskDir,"SkytilsInstaller-1.1-SNAPSHOT.jar");

            System.out.println("File to delete " + this.file.getAbsolutePath());

            if (this.file == null || !this.file.exists() || this.file.isDirectory()) {
                System.out.println("Old file not found.");
                return;
            }

            if(taskFile.exists()){
                try {
                    String runtime = HypixelKentik.getInstance().getAutoUpdater().getJavaRuntime();
                    Runtime.getRuntime().exec("java -jar " + taskFile.getAbsolutePath() + " " + file.getAbsolutePath());
                  //  System.out.println("Task." + runtime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

           // FileUtils.forceDelete(this.file);
          //  FileUtils.forceDeleteOnExit(this.file);
       // }catch (IOException e){
      //      System.err.println(e);
       // }
    }

}
