package ru.DmN.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.gui.CombinatorScreenHandler;

public class Combinator extends Block implements NamedScreenHandlerFactory {
    public static final Combinator INSTANCE = new Combinator();

    /// CONSTRUCTORS

    public Combinator() {
        super(Settings.of(Material.METAL));
    }

    /// ACTIONS

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.CONSUME;
        player.openHandledScreen(this);
        return ActionResult.SUCCESS;
    }


    /// GUI

    @Override
    public Text getDisplayName() {
        return getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CombinatorScreenHandler(syncId, inv);
    }
}