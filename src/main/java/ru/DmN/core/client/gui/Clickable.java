package ru.DmN.core.client.gui;

public interface Clickable extends IComponent {
    boolean mouseClicked(double mouseX, double mouseY, int button);
}