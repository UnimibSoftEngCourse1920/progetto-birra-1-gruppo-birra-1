package homebrew.view;

import homebrew.controller.FacadeController;

public class BirraApplication 
{
	public static void main(String[] args)
	{
		FacadeController controller = new FacadeController();
		new GuiIngredienti(controller);
		new GuiRicetta(controller);
		new GuiRicetteDisponibili(controller);
	}
}