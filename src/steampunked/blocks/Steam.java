package steampunked.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Steam extends Fluid {
	public Steam() {
		super("Steam");

		setDensity(-200);
		setViscosity(500);
		setGaseous(false);
		setTemperature(390);
		setUnlocalizedName("Steam");
		
		
	}

	
	@Override
	 public String getLocalizedName()
    {
        return "Steam";
    }
	
}
