package ru.DmN.core.client.screen;

import net.minecraft.screen.ScreenHandlerType;

public interface IScreenHandlerTypeProvider {
    void setType(ScreenHandlerType<?> type);
}