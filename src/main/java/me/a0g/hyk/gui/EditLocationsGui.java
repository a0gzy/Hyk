package me.a0g.hyk.gui;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.commands.Move;
import me.a0g.hyk.commands.Scale;
import me.a0g.hyk.events.Render;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class EditLocationsGui extends GuiScreen {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private String moving = null;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private LocationButton display;
    private LocationButton cake;
    private LocationButton armor;
    private LocationButton comms;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();

        String displayText = EnumChatFormatting.DARK_RED + "12:00 " +
                EnumChatFormatting.AQUA + "FPS " +
                EnumChatFormatting.RESET + "Sprint " +
                EnumChatFormatting.RESET + "CPS: 99 \n" +
                EnumChatFormatting.RED + "Psp " +
                EnumChatFormatting.BLUE + "Chest " +
                EnumChatFormatting.GREEN + "NameT " +
                EnumChatFormatting.LIGHT_PURPLE + "Fairy";

        String caket = EnumChatFormatting.GOLD + "1d5h";

        String armort = "    \n    \n    \n    \n    \n    \n    \n";

        String commst = EnumChatFormatting.BLUE + "Commissions\n" +
                EnumChatFormatting.RESET + "Mithrill miner " + EnumChatFormatting.DARK_GREEN + "80%\n" +
                EnumChatFormatting.RESET + "Titanium miner " + EnumChatFormatting.DARK_GREEN + "80%\n" +
                EnumChatFormatting.RESET + "Titanium miner " + EnumChatFormatting.DARK_GREEN + "80%\n" +
                EnumChatFormatting.RESET + "Titanium miner " + EnumChatFormatting.DARK_GREEN + "80%\n" +
                "\n" + EnumChatFormatting.RESET + "Mithrill powder " + EnumChatFormatting.DARK_GREEN + "10,425";

        display = new LocationButton(0, Move.mainXY[0], Move.mainXY[1], 135 * Scale.mainScale, 20 * Scale.mainScale, Scale.mainScale, displayText, null, null);
        cake = new LocationButton(0, Move.cakeXY[0] + 18, Move.cakeXY[1] + 3, 100 * Scale.mainScale, 20 * Scale.mainScale, Scale.mainScale, caket, null, null);
        armor = new LocationButton(0, Move.armorXY[0], Move.armorXY[1], 20 * Scale.mainScale, 64 * Scale.mainScale, Scale.mainScale, armort, null, null);
        comms = new LocationButton(0, Move.commsXY[0], Move.commsXY[1], 110 * Scale.mainScale, 70 * Scale.mainScale, Scale.mainScale, commst, null, null);

        this.buttonList.add(display);
        this.buttonList.add(cake);
        this.buttonList.add(armor);
        this.buttonList.add(comms);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        mouseMoved(mouseX, mouseY);

        double scale = Scale.mainScale;
        double scaleReset = Math.pow(scale, -1);
        GL11.glScaled(scale, scale, scale);
        mc.getTextureManager().bindTexture(Render.CAKE_ICON);
        Gui.drawModalRectWithCustomSizedTexture(Move.cakeXY[0], Move.cakeXY[1], 0, 0, 16, 16, 16, 16);

        for(int i = 0;i<4;i++) {
            ItemStack armorr = new ItemStack(Item.getItemById(313-i));
            int offset = (-16 * i) + 48;
            main.getUtils().renderArmor(armorr, Move.armorXY[0], Move.armorXY[1] + offset, -100);
        }

        GL11.glScaled(scaleReset, scaleReset, scaleReset);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void mouseMoved(int mouseX, int mouseY) {
        int xMoved = mouseX - lastMouseX;
        int yMoved = mouseY - lastMouseY;

        if (moving != null) {
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
        lastMouseY = mouseY;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button instanceof LocationButton) {
            if (button == display) {
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
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        moving = null;
        main.getHyConfig().setMainx( Move.mainXY[0] );
        main.getHyConfig().setMainy( Move.mainXY[1] );

        main.getHyConfig().setCakex( Move.cakeXY[0] );
        main.getHyConfig().setCakey( Move.cakeXY[1] );

        main.getHyConfig().setArmorx( Move.armorXY[0] );
        main.getHyConfig().setArmory( Move.armorXY[1] );

        main.getHyConfig().setCommsx( Move.commsXY[0] );
        main.getHyConfig().setCommsy( Move.commsXY[1] );

        main.getHyConfig().markDirty();
        main.getHyConfig().writeData();
    }

}
