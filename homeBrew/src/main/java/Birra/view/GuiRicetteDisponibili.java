package Birra.view;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Birra.controller.*;
import Birra.model.Nota;
import Birra.model.Ricetta;

public class GuiRicetteDisponibili implements Gui 
{
	private RicetteDisponibili rd;
	
	
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
		guiFrame.setSize(400,450);
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

	public GuiRicetteDisponibili()  
	{
		//this.rd = new RicetteDisponibili();
		draw();
	}
	
	//Ascoltatore dell'evento click del bottone mostra ricette disponibili
	private void clickRicetteDisponibili(JButton button, JTextArea testo, final JFrame guiFrame)
	{
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//Ricetta r = rd.cosaDovreiPreparareOggi();
				JDialog dialog = new JDialog(guiFrame, "Ricette disponibili");
				//JLabel label = new JLabel(r.toString());
				//dialog.add(label);
				dialog.setSize(500, 500);
				dialog.setVisible(true);
			}
			
		});
	}
}
