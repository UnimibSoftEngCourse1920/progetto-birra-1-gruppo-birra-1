package Birra.view;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Birra.model.Ingrediente;
import Birra.controller.FacadeController;


public class GuiIngredienti implements Gui 
{
	private FacadeController controller;
	private static final String ERRORE = "errore";
	
	@Override
	public void draw() 
	{
		final JFrame guiFrame = new JFrame();

		guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		guiFrame.setTitle("Ingredienti");
		guiFrame.setSize(500,350);
		guiFrame.setLocation(630, 100);
		
		JPanel campiIngrediente = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel visualizzaLabel = new JLabel("Inserisci il nome dell'ingrediente");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(visualizzaLabel, gbc);
		
		JTextField testo = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(testo, gbc);
		
		JButton getIngrediente = new JButton("Visualizza ingrediente");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(getIngrediente, gbc);
		
		JButton eliminaIngrediente = new JButton("Elimina ingrediente");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(eliminaIngrediente, gbc);
		
		
		JLabel nomeLabel = new JLabel("Inserisci il nome dell'ingrediente: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(nomeLabel, gbc);
		
		JTextField nome = new JTextField(15);
		nome.setCaretColor(Color.DARK_GRAY);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(nome, gbc);
		
		
		JLabel quantitaLabel = new JLabel("quantità: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(quantitaLabel, gbc);
		
		JTextField quantita = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(quantita, gbc);
		
		
		JLabel tipoLabel = new JLabel("tipo: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(tipoLabel, gbc);
		
		String[] opzioni = {"MALTO", "LUPPOLI", "ZUCCHERO", "ACQUA", "LIEVITO"};
		JComboBox tipo = new JComboBox(opzioni);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(tipo, gbc);
		
		
		JLabel bloccatoLabel = new JLabel("Bloccato: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(bloccatoLabel, gbc);
		
		JCheckBox bloccato = new JCheckBox();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(bloccato, gbc);
		
		
		JButton aggiungiIngrediente = new JButton("Aggiungi ingrediente");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		campiIngrediente.add(aggiungiIngrediente, gbc);
		
		JButton modificaIngrediente = new JButton("Modfica ingrediente");
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		campiIngrediente.add(modificaIngrediente, gbc);
		
		guiFrame.add(campiIngrediente);
		
		//Se clicco su visualizza ingrediente
		clickGetIngrediente(getIngrediente, testo, guiFrame);
		
		//Se clicco su elimina ingrediente
		clickEliminaIngrediente(eliminaIngrediente, testo);
		
		//Se clicco su aggiungi ingrediente 
		clickAggiungiIngrediente(aggiungiIngrediente, nome, quantita, tipo, bloccato); 
		
		//Se clicco su modifica ingrediente
		clickModificaIngrediente(modificaIngrediente, nome, quantita, tipo, bloccato);
		
		
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
		getIngrediente.addActionListener(e -> {
			Ingrediente i=null;
			try 
			{
				i = controller.getIngrediente(testo.getText());
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,"Errore "+testo.getText(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			if(i != null)
			{
				String ingrediente = "nome: " + i.getNome() + " quantità: " + i.getQuantita() +
						" tipo: " + i.getTipo() + " bloccato: " + i.isBloccato();
				JDialog dialog = new JDialog(guiFrame, "Dettagli ingrediente");
				JLabel label = new JLabel(ingrediente);
				dialog.add(label);
				dialog.setSize(500, 500);
				dialog.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Non c'è nessun ingrediente che si chiama "+testo.getText(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			testo.setText("");
		});
	}
	
	//Ascoltatore dell'evento elimina ingrediente
	private void clickEliminaIngrediente(JButton eliminaIngrediente, final JTextField testo)
	{
		eliminaIngrediente.addActionListener(e -> {
			try {
				controller.eliminaIngrediente(testo.getText());
			} catch (SQLException e1) 
			{
				if(e1.getMessage().contains("foreign key"))
				{
					//Significa che l'ingrediente non può essere eliminato perchè c'è una ricetta che lo contiene 
					JOptionPane.showMessageDialog(null,"Impossibile eliminare  "+testo.getText()+ " perchè c'è almeno una ricetta che lo contiene",ERRORE,JOptionPane.WARNING_MESSAGE);
				}
			}
			testo.setText("");
		});
	}
	
	//Ascoltatore dell'evento click sul bottone aggiungiIngrediente
	private void clickAggiungiIngrediente(JButton aggiungiIngrediente, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato)
	{
		aggiungiIngrediente.addActionListener(e -> {
			String nomeIngrediente = nome.getText();
			String quantitaIngrediente = quantita.getText();
			boolean bloccatoIngrediente = bloccato.isSelected();
			String tipoIngrediente = tipo.getSelectedItem().toString();
			boolean aggiunto = false;
			try 
			{
				aggiunto = controller.aggiungiIngrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
				if(aggiunto)
					JOptionPane.showMessageDialog(null,"Ingrediente aggiunto correttamente","Ingrediente aggiunto",JOptionPane.INFORMATION_MESSAGE);
			}catch (IllegalArgumentException | SQLException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			pulisciCampiInput(nome, quantita);
		});
	}
	
	//Ascoltatore del bottone modifica ingrediente
	private void clickModificaIngrediente(JButton modificaIngrediente, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato)
	{
		modificaIngrediente.addActionListener(e -> {
			String nomeIngrediente = nome.getText();
			String quantitaIngrediente = quantita.getText();
			boolean bloccatoIngrediente = bloccato.isSelected();
			String tipoIngrediente = tipo.getSelectedItem().toString();
			try {
				controller.modificaIngrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
			}catch (IllegalArgumentException | SQLException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			pulisciCampiInput(nome, quantita);
		});
	}
	
	private void pulisciCampiInput(final JTextField testo0, final JTextField testo1)
	{
		testo0.setText("");
		testo1.setText("");
	}
}