package net.arbee.addola.blocks.backpack;

import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("rawtypes")
public abstract class BackpackBlock extends BlockWithEntity{
	public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
	
	protected BackpackBlock(Settings settings) {
		super(settings);
	    this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		Direction dir = state.get(Properties.HORIZONTAL_FACING);
		switch(dir) {
		case NORTH:
			return VoxelShapes.cuboid(0.125f, 0f, 0f, 0.875, 0.8125, 0.5f);
		case SOUTH:
			return VoxelShapes.cuboid(0.125f, 0f, 0.5f, 0.875, 0.8125, 1f );
		case EAST:
			return VoxelShapes.cuboid(0.5f, 0f, 0.125f, 1f, 0.8125, 0.875);
		case WEST:
			return VoxelShapes.cuboid(0f, 0f, 0.125f, 0.5f, 0.8125, 0.875);
		default:
			return VoxelShapes.fullCube();
		}
	}

	public BlockState getPlacementState(ItemPlacementContext context) {
		return (BlockState)this.getDefaultState().with(Properties.HORIZONTAL_FACING, context.getPlayerFacing());
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.SUCCESS;
	}
	
	@Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
       super.appendTooltip(stack, world, tooltip, options);
       CompoundTag compoundTag = stack.getSubTag("BlockEntityTag");
       if (compoundTag != null) {
          if (compoundTag.contains("LootTable", 8)) {
             tooltip.add(new LiteralText("???????"));
          }

          if (compoundTag.contains("Items", 9)) {
             DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(27, ItemStack.EMPTY);
             Inventories.fromTag(compoundTag, defaultedList);
             int i = 0;
             int j = 0;
			Iterator var9 = defaultedList.iterator();

             while(var9.hasNext()) {
                ItemStack itemStack = (ItemStack)var9.next();
                if (!itemStack.isEmpty()) {
                   ++j;
                   if (i <= 4) {
                      ++i;
                      MutableText mutableText = itemStack.getName().shallowCopy();
                      mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
                      tooltip.add(mutableText);
                   }
                }
             }

             if (j - i > 0) {
                tooltip.add((new TranslatableText("container.backpack.more", new Object[]{j - i})).formatted(Formatting.ITALIC));
             }
          }
       }
    }
	
	public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }
	
	public boolean hasComparatorOutput(BlockState state) {
    	return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    	return ScreenHandler.calculateComparatorOutput((Inventory)world.getBlockEntity(pos));
    }
}