package net.arbee.addola.blocks.backpack;

import net.arbee.addola.blockentities.backpack.NetheriteBackpackEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class NetheriteBackpackBlock extends BackpackBlock { 
    public NetheriteBackpackBlock(Settings settings) {
        super(settings);
    }
    
    @Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new NetheriteBackpackEntity();
	}
    
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    	BlockEntity blockEntity = world.getBlockEntity(pos);
    	if (blockEntity instanceof NetheriteBackpackEntity) {
    		NetheriteBackpackEntity bentity = (NetheriteBackpackEntity)blockEntity;
    		if (!world.isClient && !bentity.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
    			ItemStack itemStack = new ItemStack(this);
    			CompoundTag compoundTag = bentity.serializeInventory(new CompoundTag());
    			if (!compoundTag.isEmpty()) {
                    itemStack.putSubTag("BlockEntityTag", compoundTag);
                }
    			
    			ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
    			itemEntity.setToDefaultPickupDelay();
    			world.spawnEntity(itemEntity);
    		} else {
                bentity.checkLootInteraction(player);
            }
    	}
    	super.onBreak(world, pos, state, player);
    }
    
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    	if (itemStack.hasCustomName()) {
    		BlockEntity blockEntity = world.getBlockEntity(pos);
    		if (blockEntity instanceof NetheriteBackpackEntity) {
    			((NetheriteBackpackEntity)blockEntity).setCustomName(itemStack.getName());
    		}
    	}
    }
    
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
           BlockEntity blockEntity = world.getBlockEntity(pos);
           if (blockEntity instanceof NetheriteBackpackEntity) {
              world.updateComparators(pos, state.getBlock());
           }

           super.onStateReplaced(state, world, pos, newState, moved);
        }
     }
    
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
       ItemStack itemStack = super.getPickStack(world, pos, state);
       NetheriteBackpackEntity NetheriteBackpackBlockEntity = (NetheriteBackpackEntity)world.getBlockEntity(pos);
       CompoundTag compoundTag = NetheriteBackpackBlockEntity.serializeInventory(new CompoundTag());
       if (!compoundTag.isEmpty()) {
          itemStack.putSubTag("BlockEntityTag", compoundTag);
       }

       return itemStack;
    }
}
