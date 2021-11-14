package ru.DmN.tech.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.block.entity.SimpleConfigurableLCBE;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.DTech;
import ru.DmN.tech.gui.DmNFurnaceSH;
import ru.DmN.tech.material.IMaterial;
import ru.DmN.tech.registry.MaterialRegistry;

import static ru.DmN.core.DCore.DMN_DATA;

public class DmNFurnaceBE extends SimpleConfigurableLCBE<ConfigurableInventory> implements SidedInventory, RecipeInputProvider, ExtendedScreenHandlerFactory {
    public IMaterial lastBurnMaterial;
    public int progress = 0;
    public int burn = 0;
    public int heat = 0;
    public int recipeNeededTemperature = 0;

    public DmNFurnaceBE(BlockPos pos, BlockState state) {
        super(DTech.DMN_FURNACE_BLOCK_ENTITY_TYPE, pos, state, new SimpleConfigurableInventory(3));
    }

    /// GUI

    public PropertyDelegate properties = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> 5;
                case 1 -> progress;
                case 2 -> burn;
                case 3 -> heat;
                case 4 -> recipeNeededTemperature;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 1 -> progress = value;
                case 2 -> burn = value;
                case 3 -> heat = value;
            }
        }

        @Override
        public int size() {
            return 5;
        }
    };

    @Override
    protected Text getContainerName() {
        return new LiteralText("DmNFurnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new DmNFurnaceSH(syncId, playerInventory, this, properties, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(this.inventory.size());
        buf.writeBlockPos(pos);
    }

    /// RECIPE

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {

    }

    /// NBT

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound dmnData = super.writeNbt(nbt).getCompound(DMN_DATA);
        if (lastBurnMaterial != null)
            dmnData.putString("lbm", MaterialRegistry.of(lastBurnMaterial).toString());
        dmnData.putInt("progress", progress);
        dmnData.putInt("burn", burn);
        dmnData.putInt("heat", heat);
        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtCompound dmnData = nbt.getCompound(DMN_DATA);
        lastBurnMaterial = MaterialRegistry.of(new Identifier(dmnData.getString("lbm")));
        progress = dmnData.getInt("progress");
        burn = dmnData.getInt("burn");
        heat = dmnData.getInt("heat");
    }
}
