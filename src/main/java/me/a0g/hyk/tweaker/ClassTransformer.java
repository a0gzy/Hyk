package me.a0g.hyk.tweaker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.a0g.hyk.tweaker.asm.*;
import me.a0g.hyk.tweaker.transformer.ITransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ClassTransformer implements IClassTransformer {

    // create a logger to distinguish our errors from a normal error
    private static final Logger LOGGER = LogManager.getLogger("ASMWorkspace");

    // create a map of transformers
    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public static boolean isDeobfuscated() {
        return deobfuscated;
    }

    public static boolean isUsingNotchMappings() {
        return usingNotchMappings;
    }

    private static boolean deobfuscated = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    private static boolean usingNotchMappings = !deobfuscated;

    // make a jvm flag that could be used to dump transformed classes
    // usable by adding -DdebugBytecode=true to the jvm arguments
    public static final boolean outputBytecode = Boolean.parseBoolean(System.getProperty("debugBytecode", "false"));

    public ClassTransformer() {
        // any transformer will be registered here
        registerTransformer(new MinecraftTransformer());
        registerTransformer(new FontRendererTransformer());
        registerTransformer(new RenderItemTransformer());
        registerTransformer(new EntityRendererTransformer());
        registerTransformer(new BlockTransformer());
        registerTransformer(new ChunkTransformer());
       // registerTransformer(new NetworkManagerTransformer());
    }

    private void registerTransformer(ITransformer transformer) {
        // loop through names of classes
        for (String cls : transformer.getClassName()) {
            // put the classes into the transformer map
            transformerMap.put(cls, transformer);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        // get the list of transformers
        Collection<ITransformer> transformers = transformerMap.get(transformedName);
        // if empty, don't bother trying to run through transformation
        if (transformers.isEmpty()) return bytes;


        log(Level.INFO, String.format("Found %s transformers for %s", transformers.size(), transformedName));

        // wjat
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);

        // for every transformer, perform the transformations
        for (ITransformer transformer : transformers) {
            log(Level.INFO, String.format("Applying transformer %s on %s...", transformer.getClass().getName(), transformedName));
            transformer.transform(node, transformedName);
        }

        // what?????
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            // write???
            node.accept(writer);
        } catch (Throwable t) {
            LOGGER.error("Exception when transforming " + transformedName + " : " + t.getClass().getSimpleName());
            t.printStackTrace();
        }

        if (outputBytecode) {
            File bytecodeDirectory = new File("bytecode");
            String transformedClassName;

            // anonymous classes
            if (transformedName.contains("$")) {
                transformedClassName = transformedName.replace('$', '.') + ".class";
            } else {
                transformedClassName = transformedName + ".class";
            }

            if (!bytecodeDirectory.exists()) {
                bytecodeDirectory.mkdirs();
            }

            File bytecodeOutput = new File(bytecodeDirectory, transformedClassName);

            try {
                if (!bytecodeOutput.exists()) {
                    bytecodeOutput.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (FileOutputStream os = new FileOutputStream(bytecodeOutput)) {
                // write to the generated class to /run/bytecode/classfile.class
                // with the class bytes from transforming
                os.write(writer.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // return the written bytes and finalize transform
        return writer.toByteArray();
    }

    public void log(Level level, String message) {
        String name = "hyk/" + this.getClass().getSimpleName();
        FMLRelaunchLog.log(name, level, (ClassTransformer.isDeobfuscated() ? "" : "[" + name + "] ") + message);
    }
}
