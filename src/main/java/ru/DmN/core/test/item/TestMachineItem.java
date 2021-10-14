package ru.DmN.core.test.item;

import ru.DmN.core.api.item.MachineBlockItem;
import ru.DmN.core.test.TestMain;
import ru.DmN.core.test.block.TestMachineBlock;

public class TestMachineItem extends MachineBlockItem {
    public static final TestMachineItem INSTANCE = new TestMachineItem();

    public TestMachineItem() {
        super(TestMachineBlock.INSTANCE, new Settings().group(TestMain.DTestGroup));
    }
}