package Birra.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Birra.controller.ControllerIngrediente;
import Birra.controller.FacadeController;
import Birra.model.Ingrediente;
import Birra.model.Ricetta;
import Birra.model.TipoIngrediente;

public class GuiIngredienti implements Gui 
{
	private FacadeController controller;
	
	@Override
	public void draw() 
	{
		final JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Ingredienti");
		guiFrame.setSize(450,450);
		guiFrame.setLocation(650, 100);
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel visualizzaIngrediente = new JPanel(new BorderLayout());
		final JLabel messaggio = new JLabel("Inserisci il nome dell'ingrediente che vuoi visualizzare");
		JTextField testo = new JTextField(40);
		JButton getIngrediente = new JButton("Visualizza ingrediente");
		visualizzaIngrediente.add(messaggio, BorderLayout.NORTH);
		visualizzaIngrediente.add(testo, BorderLayout.CENTER);
		visualizzaIngrediente.add(getIngrediente, BorderLayout.SOUTH);
		panel.add(visualizzaIngrediente, BorderLayout.NORTH);
		guiFrame.add(panel, BorderLayout.NORTH);
		
		
		JPanel campiIngrediente = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		JLabel nomeLabel = new JLabel("Inserisci il nome dell'ingrediente: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(nomeLabel, gbc);
		
		JTextField nome = new JTextField(15);
		nome.setCaretColor(Color.DARK_GRAY);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(nome, gbc);
		
		
		
		JLabel quantitaLabel = new JLabel("quantità: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(quantitaLabel, gbc);
		
		JTextField quantita = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(quantita, gbc);
		
		
		JLabel tipoLabel = new JLabel("tipo: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(tipoLabel, gbc);
		
		String[] opzioni = {"MALTO", "LUPPOLI", "ZUCCHERO", "ACQUA", "LIEVITO"};
		JComboBox tipo = new JComboBox(opzioni);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(tipo, gbc);
		
		
		JLabel bloccatoLabel = new JLabel("Bloccato: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(bloccatoLabel, gbc);
		
		JCheckBox bloccato = new JCheckBox();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(bloccato, gbc);
		
		
		JButton aggiungiIngrediente = new JButton("Aggiungi ingrediente");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(aggiungiIngrediente, gbc);
		
		JButton modificaIngrediente = new JButton("Modfica ingrediente");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(modificaIngrediente, gbc);
		
		guiFrame.add(campiIngrediente);
		
		//Se clicco su visualizza ingrediente
		clickGetIngrediente(getIngrediente, testo, guiFrame);
		
		//Se clicco su aggiungi ingrediente 
		clickAggiungiIngrediente(aggiungiIngrediente, nome, quantita, tipo, bloccato, guiFrame); 
		
		//Se clicco su modifica ingrediente
		clickModificaIngrediente(modificaIngrediente, nome, quantita, tipo, bloccato, guiFrame);
		
		
		guiFrame.setVisible(true);
	}
	
	public GuiIngredienti(FacadeController controller)
	{
		this.controller = controller;
		draw();
	}
	
	
	//Ascoltatore dell' evento click sul bottone getIngrediente
	private void clickGetIngrediente(JButton getIngrediente, final JTextField testo, final JFrame guiFrame)
	{
		getIngrediente.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Ingrediente i = controller.getIngrediente(testo.getText());
				if(i != null)
				{
					String ingrediente = "nome: " + i.getNome() + " quantità: " + i.getQuantita() +
							" tipo: " + i.getTipo() + " bloccato: " + i.isBloccato();
					JDialog dialog = new JDialog(guiFrame, "Ricette disponibili");
					JLabel label = new JLabel(ingrediente);
					dialog.add(label);
					dialog.setSize(500, 500);
					dialog.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Non c'è nessun ingrediente che si chiama "+testo.getText(),"Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	//Ascoltatore dell'evento click sul bottone aggiungiIngrediente
	private void clickAggiungiIngrediente(JButton aggiungiIngrediente, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato, final JFrame guiFrame)
	{
		aggiungiIngrediente.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				String nomeIngrediente = nome.getText();
				String quantitaIngrediente = quantita.getText();
				boolean bloccatoIngrediente = bloccato.isSelected();
				String tipoIngrediente = tipo.getSelectedItem().toString();
				try 
				{
					Ingrediente i = controller.creaIngrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
					controller.aggiungiIngrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
				}catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null,"Valori inseriti non corretti","Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	//Ascoltatore del bottone modifica ingrediente
	private void clickModificaIngrediente(JButton modificaIngrediente, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato, final JFrame guiFrame)
	{
		modificaIngrediente.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				String nomeIngrediente = nome.getText();
				String quantitaIngrediente = quantita.getText();
				boolean bloccatoIngrediente = bloccato.isSelected();
				String tipoIngrediente = tipo.getSelectedItem().toString();
				try {
					controller.modificaIngrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
				}catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null,"Valori inseriti non corretti","Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
}