package homebrew.view;

import homebrew.controller.FacadeController;

/*
 * La classe BirraApplication crea le gui, che costituiscono il programma
 */
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