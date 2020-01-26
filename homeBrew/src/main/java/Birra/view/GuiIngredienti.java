package Birra.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Birra.controller.ControllerIngrediente;
import Birra.model.Ingrediente;
import Birra.model.Ricetta;
import Birra.model.TipoIngrediente;

public class GuiIngredienti implements Gui 
{
	private ControllerIngrediente ci;
	
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
	
	public GuiIngredienti()
	{
		this.ci = new ControllerIngrediente();
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
				Ingrediente i = ci.getIngrediente(testo.getText());
				String ingrediente = "nome: " + i.getNome() + " quantità: " + i.getQuantita() +
						" tipo: " + i.getTipo() + " bloccato: " + i.isBloccato();
				JOptionPane.showMessageDialog(guiFrame, ingrediente);
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
				Ingrediente i =parseIngrediente(nome, quantita, tipo, bloccato);
				ci.aggiungiIngrediente(i);
				JOptionPane.showMessageDialog(guiFrame, "Ingrediente aggiunto al database");
			}
		});
	}
	
	//Presi in input i campi compilati dall'utente, viene restituito l'oggetto di tipo ingrediente da aggiungere al database
	private Ingrediente parseIngrediente(JTextField nome, JTextField quantita, JComboBox tipo, JCheckBox bloccato)
	{
		String nomeIngrediente = nome.getText();
		Double quantitaIngrediente = null;
		try 
		{
			quantitaIngrediente = Double.parseDouble(quantita.getText());
		}
		catch(Exception e)
		{
			quantitaIngrediente = 0.0;
		}
		String tipoIngrediente = tipo.getSelectedItem().toString();
		TipoIngrediente ti = null;
		if(tipoIngrediente.equals("MALTO"))
			ti = TipoIngrediente.MALTO;
		if(tipoIngrediente.equals("ZUCCHERO"))
			ti = TipoIngrediente.ZUCCHERO;
		if(tipoIngrediente.equals("LIEVITO"))
			ti = TipoIngrediente.LIEVITO;
		if(tipoIngrediente.equals("LUPPOLI"))
			ti = TipoIngrediente.LUPPOLI;
		if(tipoIngrediente.equals("ACQUA"))
			ti = TipoIngrediente.ACQUA;
		boolean bloccatoIngrediente = bloccato.isSelected();
		return new Ingrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, ti);
	}
	
	private void clickModificaIngrediente(JButton modificaIngrediente, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato, final JFrame guiFrame)
	{
		modificaIngrediente.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Ingrediente i =parseIngrediente(nome, quantita, tipo, bloccato);
				ci.modificaIngrediente(i);
				JOptionPane.showMessageDialog(guiFrame, "Ingrediente modificato");
			}
		});
	}
}
