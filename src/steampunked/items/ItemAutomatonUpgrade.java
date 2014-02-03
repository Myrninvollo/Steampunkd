package steampunked.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import steampunked.core.tabs.ModAutomatonTab;
import steampunked.entity.automatons.EntityAutomoton;
import steampunked.items.interfaces.IUpgrade;
import steampunked.lib.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class ItemAutomatonUpgrade extends Item implements IUpgrade{

	public ItemAutomatonUpgrade(int id) {
		super(id);
		setCreativeTab(ModAutomatonTab.INSTANCE);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	public static final String[] upgradeItemNames = new String[] {"range", "speed", "undefined"};
	@SideOnly(Side.CLIENT)
    private Icon[] upgradeIcons;

	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack,
			EntityPlayer par2EntityPlayer, List list, boolean par4) {

		switch (itemstack.getItemDamage()) {
		case 0:
			list.add("Range Upgrade");
			return;
		case 1:
			list.add("Speed Upgrade");
			return;

		case 2:
			list.add("Undefined");
			return;
		}
	}
	
	   public Icon getIconFromDamage(int par1)
	    {
	        int j = MathHelper.clamp_int(par1, 0, 3);
	        return this.upgradeIcons[j];
	    }
	
	 @SideOnly(Side.CLIENT)
	    public void registerIcons(IconRegister par1IconRegister)
	    {
	        this.upgradeIcons = new Icon[upgradeItemNames.length];

	        for (int i = 0; i < upgradeItemNames.length; ++i)
	        {
	            this.upgradeIcons[i] = par1IconRegister.registerIcon(Reference.ModID + ":upgrade_"+ + i);
	        }
	    }
	
	
	/*public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
    	if (par3EntityLivingBase instanceof EntityAutomoton){
       
        	EntityAutomoton entity = new EntityAutomoton(par3EntityLivingBase.worldObj);
      
        }
		return true;
    }*/

}
