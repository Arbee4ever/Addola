package net.arbee.addola.blockentities.backpack;

import net.arbee.addola.Inventory.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public abstract class BackpackEntity extends LootableContainerBlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
	private DefaultedList<ItemStack> items = DefaultedList.ofSize(36, ItemStack.EMPTY);
	
	public BackpackEntity(BlockEntityType<?> type) {
		super(type);
	}
    
    @Override
	public DefaultedList<ItemStack> getInvStackList() {
	      return this.items;
	}
    
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.items = list;
     }
    
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.deserializeInventory(tag);
     }

     public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        return this.serializeInventory(tag);
     }
    
    @Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}
     
     public void deserializeInventory(CompoundTag tag) {
         if (!this.deserializeLootTable(tag) && tag.contains("Items", 9)) {
            Inventories.fromTag(tag, this.items);
         }

      }
     
     public CompoundTag serializeInventory(CompoundTag tag) {
    	 if (!this.serializeLootTable(tag)) {
    		 Inventories.toTag(tag, this.items, false);
    	 }

    	 return tag;
     }
 	
 	@Override
 	protected Text getContainerName() {
 	      return new TranslatableText("container.backpack");
 	}
}
