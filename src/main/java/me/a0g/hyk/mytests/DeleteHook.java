package me.a0g.hyk.mytests;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.utils.Utils;

import java.io.File;
import java.io.IOException;

public class DeleteHook extends Thread {

    private File file;

    public DeleteHook(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            System.out.println("Deleting");
            String java = Utils.getJavaRuntime();
            System.out.println(java);

            File taskDir = new File(Hyk.dir, "update");
            File myFile = new File(taskDir, "HykFileDeleter.jar");

            System.out.println("File to delete " + this.file.getAbsolutePath());

            if (this.file == null || !this.file.exists() || this.file.isDirectory()) {
                System.out.println("Old file not found.");
                return;
            }
            System.out.println("myFile " + myFile.getAbsolutePath());
            if (myFile.exists()) {
                System.out.println("Exists");
                String cmd = String.format("\"%s\" -jar \"%s\" \"%s\"", java , myFile.getAbsolutePath() , this.file.getAbsolutePath());
             //   String cmd = String.format("\"%s\"", java) + " -jar " + myFile.getAbsolutePath() + " " + this.file.getAbsolutePath();
                //  String cmd = "java.exe -jar " + myFile.getAbsolutePath() + " " + this.file.getAbsolutePath();
                System.out.println("command " + cmd);
                Runtime.getRuntime().exec(cmd);
            } else {
                System.out.println("Ne exists");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
