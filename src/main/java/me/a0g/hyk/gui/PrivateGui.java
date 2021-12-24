package me.a0g.hyk.gui;

import lombok.SneakyThrows;
import me.a0g.hyk.Hyk;
import me.a0g.hyk.core.Feature;
import me.a0g.hyk.gui.buttons.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class PrivateGui extends GuiScreen {

    private final Hyk main = Hyk.getInstance();

    private int width = 120;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private ButtonList dragging;

    private ArrayList<ButtonInList> buttons1 = new ArrayList<>();
    //private int buttonList1x = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth()/2 - width/2;
    //private int buttonList1y = 50;

   // private ButtonList buttonList1 = new ButtonList(0,buttonList1x,buttonList1y,width,20,"Private",buttons1);
    private ButtonList buttonList1 = new ButtonList(0,new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth()/2 - width/2,50,width,20,"Private",buttons1);

    public PrivateGui(){
        buttons1.add(new ButtonSwitch(0,2,3,width,20,"Reach",Feature.REACH));
        buttons1.add(new ButtonSlider(0,2,3,width,20,3,6,40,"Range",Feature.REACHRANGE));
        buttons1.add(new ButtonSwitch(0,2,3,width,20,"NameTag",Feature.NAMETAG));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();


        //ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());



        //this.buttonList.add(new ButtonList(0,sr.getScaledWidth() - 100 - 20,20,width,20,"Private",buttons1));
        this.buttonList.add(buttonList1);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        mouseMoved(mouseX, mouseY);

        //new TextRenderer(mc,buttonList1.x + " " + buttonList1.y + " " + buttonList1.hover + " " + buttonList1.hoverd + " " + buttonList1.xPosition + " " + buttonList1.yPosition + " ",50,70,1);

        //buttonList1.drawButton(mc,mouseX,mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void mouseMoved(int mouseX, int mouseY) {
        int xMoved = mouseX - lastMouseX;
        int yMoved = mouseY - lastMouseY;


        if (dragging == buttonList1) {
       // if (buttonList1.hover) {


            buttonList1.x += +xMoved;
            buttonList1.y += +yMoved;

            buttonList1.xPosition = buttonList1.x;
            buttonList1.yPosition = buttonList1.y;

            buttonList1.drawButton(mc,mouseX,mouseY);
        }
        //  this.buttonList.clear();
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        this.buttonList.clear();
        initGui();
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == buttonList1) {
            dragging = buttonList1;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
        //buttonList1.hover = false;
        //initGui();
    }

    @SneakyThrows
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        main.getHyConfig().setNamet(Feature.NAMETAG.isOn);
        main.getHyConfig().setRech(Feature.REACH.isOn);
        main.getHyConfig().setRechr(Feature.REACHRANGE.floatValue);


        main.getHyConfig().markDirty();
        main.getHyConfig().writeData();

        //Minecraft.getMinecraft().displayGuiScreen();


        // GuiUtil.open(Objects.requireNonNull(Minecraft.getMinecraft().ingameGUI);
    }

}
