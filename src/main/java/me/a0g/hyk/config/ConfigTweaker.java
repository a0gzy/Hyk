package me.a0g.hyk.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.core.Feature;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class ConfigTweaker implements Serializable {

    /**
     * Pretty-printing GSON instance
     */
    public transient final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    /**
     * Name of the file for this configuration
     */
    public transient String fileName;
    /**
     * File for this configuration
     */
    public transient File file;

    /**
     * Constructor
     * @param fileName name of the file this configuration should save to
     */
    public ConfigTweaker(String fileName) {
        setFile(new File(HypixelKentik.dir, fileName));
    }

    /**
     * Set the file of this configuration
     * @param file File to set
     * @return this
     */
    public ConfigTweaker setFile(File file) {
        this.fileName = file.toPath().getFileName().toString();
        this.file = file;

        return this;
    }

    private HypixelKentik main = HypixelKentik.getInstance();

    /**
     * Save this configuration
     * @return This
     * @throws IOException on a writing error
     */
    public ConfigTweaker save() throws IOException{

       /* main.getHykPos().time = new HykPos.PosTweak(Feature.TIME.getX(), Feature.TIME.getY(), Feature.TIME.getScale());
        main.getHykPos().fps = new HykPos.PosTweak(Feature.FPS.getX(), Feature.FPS.getY(), Feature.FPS.getScale());
        main.getHykPos().sprint = new HykPos.PosTweak(Feature.SPRINT.getX(), Feature.SPRINT.getY(), Feature.SPRINT.getScale());
        main.getHykPos().cps = new HykPos.PosTweak(Feature.CPS.getX(), Feature.CPS.getY(), Feature.CPS.getScale());
        main.getHykPos().cakes = new HykPos.PosTweak(Feature.CAKES.getX(), Feature.CAKES.getY(), Feature.CAKES.getScale());
        main.getHykPos().armorhud = new HykPos.PosTweak(Feature.ARMORHUD.getX(), Feature.ARMORHUD.getY(), Feature.ARMORHUD.getScale());
        main.getHykPos().commissions = new HykPos.PosTweak(Feature.COMMISSIONS.getX(), Feature.COMMISSIONS.getY(), Feature.COMMISSIONS.getScale());*/


        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        }

        /*String contents = GSON.toJson(this,HykPos.class);
        Files.write(contents.getBytes(), file);*/
        return this;
    }

    /**
     * Load a configuration
     * @param name Name of the configuration with the file extension
     * @param type Type of the configuration being loaded
     * @return The configuration loaded
     * @throws IOException Reading error
     * @throws JsonSyntaxException Invalid JSON
     */
    public static ConfigTweaker load(String name, Class<? extends ConfigTweaker> type) throws IOException, JsonSyntaxException {
        final File file = new File(HypixelKentik.dir, name);

        if(!file.exists()) {
            throw new FileNotFoundException("Configuration file \"" + name + "\" not found.");
        }

        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new GsonPostProcessorFactory()).create();
        final String contents = Files.toString(file, StandardCharsets.UTF_8);

        final ConfigTweaker newConfig = gson.fromJson(contents, type);
        newConfig.setFile(file);
        //this.fileName = file.toPath().getFileName().toString();
       // this.file = file;

        return newConfig;
    }

}
