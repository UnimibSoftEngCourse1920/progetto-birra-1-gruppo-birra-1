package homebrew.view;
import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.*;

import homebrew.model.Ricetta;
import homebrew.controller.*;

public class GuiRicetteDisponibili implements Gui 
{
	private FacadeController controller;
	
	
	/*
	 * Disegno il frame 
	 */
	@Override
	public void draw() 
	{
		JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		guiFrame.setTitle("What should i brew today?");
		guiFrame.setSize(400,350);
		guiFrame.setLocation(1130, 100);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JButton b = new JButton("Mostra ricette disponibili");
		panel.add(b, BorderLayout.CENTER);
		
		//Quando clicco sul bottone "Mostra ricette disponibili"
		clickRicetteDisponibili(b, guiFrame);
		
		guiFrame.add(panel);
		guiFrame.setVisible(true);
	}

	public GuiRicetteDisponibili(FacadeController controller)  
	{
		this.controller = controller;
		draw();
	}
	
	//Ascoltatore dell'evento click del bottone mostra ricette disponibili
	private void clickRicetteDisponibili(JButton button, final JFrame guiFrame)
	{
		button.addActionListener(e -> {
			Ricetta r=null;
			try {
				r = controller.cosaDovreiPreparareOggi();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Errore",JOptionPane.WARNING_MESSAGE);
			}
			if(r == null) //Non ci sono ricette disponibili
				JOptionPane.showMessageDialog(guiFrame, "Non ci sono ricette disponibili al momento");
			//Se ci sono le mostro
			else
			{
				JDialog dialog = new JDialog(guiFrame, "Ricette disponibili");
				JTextArea text = new JTextArea(r.toString());
				text.setLineWrap(true);
				text.setWrapStyleWord(true);
				dialog.add(text);
				dialog.setSize(500, 500);
				dialog.setVisible(true);
			}
		});
	}
}