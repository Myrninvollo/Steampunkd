package steampunked.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import steampunked.core.tabs.ModAutomatonTab;
import steampunked.entity.automatons.EntityAutomoton;
import steampunked.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNamePlate extends Item
{
    public ItemNamePlate(int par1)
    {
        super(par1);
        setCreativeTab(ModAutomatonTab.INSTANCE);
    }

   @SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Reference.ModID + ":NamePlate");
	}
    
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
    	if (par3EntityLivingBase instanceof EntityAutomoton){
        if (!par1ItemStack.hasDisplayName())
        {
        	if (!par2EntityPlayer.worldObj.isRemote)
        	par2EntityPlayer.addChatMessage(EnumChatFormatting.ITALIC+"You must name the Nameplate in an Anvil First");
            return false;
        }
        else if (par3EntityLivingBase instanceof EntityAutomoton)
        {
            EntityAutomoton entityAutomoton = (EntityAutomoton)par3EntityLivingBase;
            entityAutomoton.setCustomNameTag(par1ItemStack.getDisplayName());
            entityAutomoton.func_110163_bv();
            --par1ItemStack.stackSize;
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
    
    else{
    	if (!par2EntityPlayer.worldObj.isRemote)
    	par2EntityPlayer.addChatMessage(EnumChatFormatting.ITALIC+"You can only use this on an Automoton");
        return false;
    }
    }	
}