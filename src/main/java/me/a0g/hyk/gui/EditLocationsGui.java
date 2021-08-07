package me.a0g.hyk.gui;

import lombok.SneakyThrows;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.config.HykPos;
import me.a0g.hyk.core.Feature;
import me.a0g.hyk.gui.buttons.ButtonLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class EditLocationsGui extends GuiScreen {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private final Map<Feature, ButtonLocation> buttonLocations = new EnumMap<>(Feature.class);

    private String moving = null;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private LocationButton display;
    private LocationButton cake;
    private LocationButton armor;
    private LocationButton comms;
    private Feature dragging;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();

        for (Feature feature : Feature.getGuiFeatures()){
            ButtonLocation buttonLocation = new ButtonLocation(feature);
            this.buttonList.add(buttonLocation);
            buttonLocations.put(feature, buttonLocation);
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        this.buttonList.add(new LocationButton(0,sr.getScaledWidth()/2-25,sr.getScaledHeight()/2-10,1,"Reset config"));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        mouseMoved(mouseX, mouseY);

        /*double scale = Scale.mainScale;
        double scaleReset = Math.pow(scale, -1);
        GL11.glScaled(scale, scale, scale);
        mc.getTextureManager().bindTexture(Render.CAKE_ICON);
        Gui.drawModalRectWithCustomSizedTexture(Move.cakeXY[0], Move.cakeXY[1], 0, 0, 16, 16, 16, 16);

        for(int i = 0;i<4;i++) {
            ItemStack armorr = new ItemStack(Item.getItemById(313-i));
            int offset = (-16 * i) + 48;
            main.getUtils().renderArmor(armorr, Move.armorXY[0], Move.armorXY[1] + offset, -100);
        }

        GL11.glScaled(scaleReset, scaleReset, scaleReset);*/
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.drawCenteredString(mc.fontRendererObj, "Use Mouse wheel to rescale", sr.getScaledWidth()/2, sr.getScaledHeight()/2, Color.CYAN.getRGB());
        this.drawCenteredString(mc.fontRendererObj, "Click and drag to edit location", sr.getScaledWidth()/2, sr.getScaledHeight()/2 + 10, Color.CYAN.getRGB());



        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void mouseMoved(int mouseX, int mouseY) {
        int xMoved = mouseX - lastMouseX;
        int yMoved = mouseY - lastMouseY;


        if(dragging != null){
            ButtonLocation buttonLocation = buttonLocations.get(dragging);
            dragging.getPosTweak().x += + xMoved;
            dragging.getPosTweak().y += + yMoved;
           // dragging.setX(dragging.getX() + xMoved);
          //  dragging.setY(dragging.getY() + yMoved);
            buttonLocation.drawButton(mc, mouseX, mouseY);
        }
      //  this.buttonList.clear();
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        /*if (moving != null) {
            switch (moving) {
                case "display":
                    Move.mainXY[0] += xMoved;
                    Move.mainXY[1] += yMoved;
                    display.xPosition = Move.mainXY[0];
                    display.yPosition = Move.mainXY[1];
                    break;
                case "cake":
                    Move.cakeXY[0] += xMoved;
                    Move.cakeXY[1] += yMoved;
                    display.xPosition = Move.cakeXY[0];
                    display.yPosition = Move.cakeXY[1];
                    break;
                case "armor":
                    Move.armorXY[0] += xMoved;
                    Move.armorXY[1] += yMoved;
                    display.xPosition = Move.armorXY[0];
                    display.yPosition = Move.armorXY[1];
                    break;
                case "comms":
                    Move.commsXY[0] += xMoved;
                    Move.commsXY[1] += yMoved;
                    display.xPosition = Move.commsXY[0];
                    display.yPosition = Move.commsXY[1];
                    break;
            }
            this.buttonList.clear();
            initGui();
        }

        lastMouseX = mouseX;
        lastMouseY = mouseY;*/
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button instanceof ButtonLocation) {
            ButtonLocation buttonLocation = (ButtonLocation) button;
            dragging = buttonLocation.getFeature();

            /*if (button == display) {
                moving = "display";
            }
            if (button == cake) {
                moving = "cake";
            }
            if (button == armor) {
                moving = "armor";
            }
            if (button == comms) {
                moving = "comms";
            }*/
        }else if(button instanceof LocationButton){
            main.setHykPos(new HykPos());
            main.getHykPos().save();

            Feature.setFromConfig();
           /* for(Feature feature : Feature.getGuiFeatures()){
                feature.
            }*/

        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        for(Feature feature : Feature.getGuiFeatures()){
            ButtonLocation buttonLocation = buttonLocations.get(feature);
            if(buttonLocation.isHovered){
                buttonLocation.getFeature().getPosTweak().scale = (buttonLocation.getFeature().getPosTweak().scale + Mouse.getEventDWheel() / 1000f);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
        /*moving = null;
        main.getHyConfig().setMainx( Move.mainXY[0] );
        main.getHyConfig().setMainy( Move.mainXY[1] );

        main.getHyConfig().setCakex( Move.cakeXY[0] );
        main.getHyConfig().setCakey( Move.cakeXY[1] );

        main.getHyConfig().setArmorx( Move.armorXY[0] );
        main.getHyConfig().setArmory( Move.armorXY[1] );

        main.getHyConfig().setCommsx( Move.commsXY[0] );
        main.getHyConfig().setCommsy( Move.commsXY[1] );*/

        main.getHykPos().sprint = Feature.SPRINT.getPosTweak();
        main.getHykPos().time = Feature.TIME.getPosTweak();
        main.getHykPos().cps = Feature.CPS.getPosTweak();
        main.getHykPos().fps = Feature.FPS.getPosTweak();
        main.getHykPos().armorhud = Feature.ARMORHUD.getPosTweak();
        main.getHykPos().commissions = Feature.COMMISSIONS.getPosTweak();
    }


    @SneakyThrows
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        main.getHyConfig().markDirty();
        main.getHyConfig().writeData();

        main.getHykPos().save();

        //Minecraft.getMinecraft().displayGuiScreen();


        // GuiUtil.open(Objects.requireNonNull(Minecraft.getMinecraft().ingameGUI);


    }

}
