package ru.DmN.core.client.gui;

public interface ClickableGuiComponent extends IGuiComponent {
    boolean mouseClicked(double mouseX, double mouseY, int button);
}