package moze_intel.projecte.gameObjs.container.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nonnull;

public class MercurialEyeInventory implements IInventory
{
	private final ItemStack invItem;
	private ItemStack kleinStar;
	private ItemStack target;
	public final EnumHand hand;
	
	public MercurialEyeInventory(ItemStack stack, EnumHand hand)
	{
		invItem = stack;
		
		if (!invItem.hasTagCompound())
		{
			invItem.setTagCompound(new NBTTagCompound());
		}
		
		readFromNBT(invItem.getTagCompound());

		this.hand = hand;
	}

	@Override
	public int getSizeInventory() 
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return slot == 0 ? kleinStar : target;
	}
	
	public ItemStack getKleinStack()
	{
		return getStackInSlot(0);
	}
	
	public ItemStack getTargetStack()
	{
		return getStackInSlot(1);
	}

	@Override
	public ItemStack decrStackSize(int slot, int qty)
	{
		ItemStack stack = getStackInSlot(slot);
		
		if(stack != null)
		{
			if(stack.stackSize > qty)
			{
				stack = stack.splitStack(qty);
				markDirty();
			}
			else
			{
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		
		if(stack != null)
		{
			setInventorySlotContents(slot, null);
		}
		
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		if (slot == 0)
		{
			kleinStar = stack;
		}
		else 
		{
			target = stack;
		}

		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		markDirty();
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "item.pe_mercurial_eye.name";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(getName());
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public void markDirty() 
	{
		if (kleinStar != null && kleinStar.stackSize == 0)
		{
			kleinStar = null;
		}
		if (target != null && target.stackSize == 0)
		{
			target = null;
		}
		
		writeToNBT(invItem.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(@Nonnull EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(@Nonnull EntityPlayer player) {}

	@Override
	public void closeInventory(@Nonnull EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, @Nonnull ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		target = null;
		kleinStar = null;
	}

	public void update()
	{
		readFromNBT(invItem.getTagCompound());
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList list = nbt.getTagList("Items", NBT.TAG_COMPOUND);
		
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			this.setInventorySlotContents(subNBT.getByte("Slot"), ItemStack.loadItemStackFromNBT(subNBT));
		}	
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		
		for (int i = 0; i < 2; i++)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound subNBT = new NBTTagCompound();
				subNBT.setByte(("Slot"), (byte) i);
				getStackInSlot(i).writeToNBT(subNBT);
				list.appendTag(subNBT);
			}
		}
		
		nbt.setTag("Items", list);
	}
}
