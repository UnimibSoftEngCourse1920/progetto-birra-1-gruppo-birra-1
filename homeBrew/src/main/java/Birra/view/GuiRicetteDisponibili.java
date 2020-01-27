package Birra.view;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import Birra.controller.*;
import Birra.model.Nota;
import Birra.model.Ricetta;

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
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("What should i brew today?");
		guiFrame.setSize(400,350);
		guiFrame.setLocation(1130, 100);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JTextArea testo = new JTextArea();
		panel.add(testo, BorderLayout.CENTER);
		JButton b = new JButton("Mostra ricette disponibili");
		panel.add(b, BorderLayout.NORTH);
		
		//Quando clicco sul bottone "Mostra ricette disponibili"
		clickRicetteDisponibili(b, testo, guiFrame);
		
		guiFrame.add(panel);
		guiFrame.setVisible(true);
	}

	public GuiRicetteDisponibili(FacadeController controller)  
	{
		this.controller = controller;
		draw();
	}
	
	//Ascoltatore dell'evento click del bottone mostra ricette disponibili
	private void clickRicetteDisponibili(JButton button, JTextArea testo, final JFrame guiFrame)
	{
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
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
					JLabel label = new JLabel(r.toString());
					dialog.add(label);
					dialog.setSize(500, 500);
					dialog.setVisible(true);
				}
			}
			
		});
	}
}