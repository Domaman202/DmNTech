package ru.DmN.core.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.item.MachineBlockItem;
import ru.DmN.core.utils.Lazy;

import java.util.function.Supplier;

import static ru.DmN.core.DCore.DMN_DATA;

public abstract class Machine extends HorizontalFacingBlock implements BlockEntityProvider, InventoryProvider {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public Lazy<MachineBlockItem> item;

    /// CONSTRUCTORS

    public Machine(Settings settings, Item.Settings settings_, Void unused) {
        super(settings);
        this.item = new Lazy<>(new MachineBlockItem(this, settings_));
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    public Machine(Settings settings, Item.Settings settings_) {
        this(settings, (Lazy<MachineBlockItem>) null);
        this.item = new Lazy<>(new MachineBlockItem(this, settings_));
    }

    public Machine(Settings settings, Supplier<MachineBlockItem> item) {
        this(settings, new Lazy<>(item));
    }

    public Machine(Settings settings, Lazy<MachineBlockItem> item) {
        super(settings.hardness(1).requiresTool());
        this.item = item;
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    /// ACTIONS

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Screen Open
        ((MachineBE) world.getBlockEntity(pos)).openScreen(player);
        //
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        // Creating Block Entity
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null)
            world.removeBlockEntity(pos);
        entity = createBlockEntity(pos, state);
        world.addBlockEntity(entity);
        // Setting Data
        if (stack.hasNbt() && stack.getNbt().contains(DMN_DATA)) {
            // Getting DmNData
            NbtCompound nbt = stack.getNbt().getCompound(DMN_DATA);
            // Setting BlockEntity Energy Data
            if (entity != null)
                ((MachineBE) entity).onPlace(world, pos, state, placer, stack);
            // Setting Active State
            world.setBlockState(pos, state.with(ACTIVE, nbt.getBoolean("active")));
        }
    }

    /// GET OVERRIDE

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return ((MachineBE) world.getBlockEntity(pos)).getInventory(state, world, pos);
    }

    @Override
    public MachineBlockItem asItem() {
        return item.get();
    }

    /// PROPERTIES

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

    public static void setFacing(Direction facing, World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(FACING, facing));
    }

    public static void setActive(Boolean active, World world, BlockPos pos) {
        var state = world.getBlockState(pos);
        if (state.getBlock() instanceof Machine machine)
            machine.setActive(world, pos, active);
    }

    public void setActive(World world, BlockPos pos, Boolean active) {
        world.setBlockState(pos, world.getBlockState(pos).with(ACTIVE, active));
    }

    public static boolean isActive(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof Machine ? state.get(ACTIVE) : false;
    }

    /// BLOCK ENTITY

    @Nullable
    @Override
    public abstract MachineBE createBlockEntity(BlockPos pos, BlockState state);

    /// NETWORK

    public static final Identifier MACHINE_DATA_PACKET_ID = new Identifier("dmncore", "machine_data_packet");

    public void receivePacketS(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender, BlockPos pos) {
        setActive(buf.readBoolean(), player.world, pos);
    }

    @Environment(EnvType.CLIENT)
    public void sendPacketC(PacketByteBuf buf) {
        ClientPlayNetworking.send(MACHINE_DATA_PACKET_ID, buf);
    }
}