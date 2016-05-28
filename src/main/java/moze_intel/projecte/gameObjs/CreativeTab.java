package moze_intel.projecte.gameObjs;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import moze_intel.projecte.PECore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab()
	{
		super(PECore.MODID);
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() 
	{
		return ObjHandler.philosStone;
	}
}
