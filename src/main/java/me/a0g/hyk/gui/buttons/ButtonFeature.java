package me.a0g.hyk.gui.buttons;

import me.a0g.hyk.core.Feature;
import net.minecraft.client.gui.GuiButton;

public class ButtonFeature extends GuiButton {

    public Feature feature;

    // asd

    ButtonFeature(int buttonId, int x, int y, String buttonText, Feature feature) {
        super(buttonId, x, y, buttonText);
        this.feature = feature;
    }

    public Feature getFeature() {
        return feature;
    }

}
