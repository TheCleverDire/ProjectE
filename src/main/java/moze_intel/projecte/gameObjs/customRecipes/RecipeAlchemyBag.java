package moze_intel.projecte.gameObjs.customRecipes;

import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

public class RecipeAlchemyBag implements IRecipe
{
	private ItemStack output;
	private ItemStack inputBag;
	private ItemStack inputDye;

	public RecipeAlchemyBag(ItemStack output, ItemStack inputBag, ItemStack inputDye)
	{
		this.output = output;
		this.inputBag = inputBag;
		this.inputDye = inputDye;

		if (inputBag.hasTagCompound())
		{
			output.setTagCompound(inputBag.getTagCompound());
		}
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world)
	{
		boolean foundBag = false;
		boolean foundDye = false;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack isInSlot = inv.getStackInSlot(i);

			if (isInSlot == null)
			{
				continue;
			}

			if (isInSlot.getItem() == ObjHandler.alchBag)
			{
				if (foundBag || isInSlot.getItemDamage() != inputBag.getItemDamage())
				{
					return false;
				}

				foundBag = true;
			}
			if (isInSlot.getItem() == Items.DYE)
			{
				if (foundDye || isInSlot.getItemDamage() != inputDye.getItemDamage())
				{
					return false;
				}

				foundDye = true;
			}
		}

		if (foundBag && foundDye)
		{
			if (inputBag.getItemDamage() != 0 && inputDye.getItemDamage() == 15)
			{
				return true;
			} else if (inputBag.getItemDamage() == 0 && inputDye.getItemDamage() != 15)
			{
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv)
	{
		return output.copy();
	}

	@Override
	public int getRecipeSize()
	{
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}

	@Nonnull
	@Override
	public ItemStack[] getRemainingItems(@Nonnull InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}

	public ItemStack getRecipeInputBag()
	{
		return inputBag;
	}

	public ItemStack getRecipeInputDye()
	{
		return inputDye;
	}
}
