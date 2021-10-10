package ru.DmN.core.test.item;

import ru.DmN.core.api.item.MachineBlockItem;
import ru.DmN.core.client.DCoreClient;
import ru.DmN.core.test.TestMain;

public class TestMachineItem extends MachineBlockItem {
    public TestMachineItem() {
        super(TestMain.TEST_MACHINE_BLOCK, new Settings().group(DCoreClient.DCoreItemGroup));
    }
}