package me.a0g.hyk.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import me.a0g.hyk.HypixelKentik;
import net.minecraftforge.fml.common.FMLLog;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class HykPos implements Serializable{

    private transient final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeSpecialFloatingPointValues().serializeNulls().setPrettyPrinting().create();

    private transient File file;

    public HykPos(){
        this.file = new File(HypixelKentik.dir,"hykpos.json");
    }

   /* public HykPos() {
        super("hykpos.json");
    }*/

    @Expose
    public PosTweak time = new PosTweak(5, 5, 1);

    @Expose
    public PosTweak fps = new PosTweak(35, 5, 1);

    @Expose
    public PosTweak sprint = new PosTweak(5, 15, 1);

    @Expose
    public PosTweak cps = new PosTweak(85, 5, 1);

    @Expose
    public PosTweak cakes = new PosTweak(75, 45, 1);

    @Expose
    public PosTweak armorhud = new PosTweak(5, 65, 1);

    @Expose
    public PosTweak commissions = new PosTweak(35, 65, 1);


    public void save(){
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        }
        catch (IOException e){

        }
    }

    public static void renew(){
        HykPos newConfig = new HykPos();
        newConfig.save();
    }

    public static HykPos load() throws IOException, JsonSyntaxException {
        final File file = new File(HypixelKentik.dir, "hykpos.json");

        if(!file.exists()) {
            throw new FileNotFoundException("Configuration file \"" + "hykpos.json" + "\" not found.");
        }

        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapterFactory(new GsonPostProcessorFactory()).create();
        final String contents = Files.toString(file, StandardCharsets.UTF_8);
      //  FMLLog.info("hykpos conents " + contents );

        HykPos newConfig;

        if(contents.isEmpty() || contents.contains("NaN") || contents.contains("null")){
         //   try {

            FMLLog.info("hykpos conents null or NaN");
                newConfig = new HykPos();
                newConfig.save();

                return newConfig;
          //  }
        }

       // gson.fromJson(contents, (Type) HykPos.class);

        try {
            newConfig = gson.fromJson(contents, (Type) HykPos.class);
        }
        catch (JsonSyntaxException e){
            FMLLog.info("hykpos s problemami het lishnee");
            newConfig = new HykPos();
            newConfig.save();
        }

        //this.file = file;

        return newConfig;
    }




    public static class PosTweak  {

        @Expose
        public int x;
        @Expose
        public int y;
        @Expose
        public float scale;

        public PosTweak(int x, int y, float scale) {
            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        public String toString(){
            String text = "( x: "+ this.x  + " y: "+ this.y +  " scale: "+this.scale + " )";

            return text;
        }

    }

}
