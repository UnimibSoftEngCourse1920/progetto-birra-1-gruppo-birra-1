package homebrew.view;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;

import homebrew.model.Ingrediente;
import homebrew.model.Nota;
import homebrew.model.Ricetta;
import homebrew.controller.*;


/**
 * La classe GuiRicetta firnisce una interfaccia grafica, mediante la quale l'utente può 
 * visualizzare i dati di una ricetta, eliminare la ricetta, visualizzare l'eventuale nota ad essa associata,
 * modificare la ricetta, aggiungere una nota, oppure aggiungere una ricetta 
 */
public class GuiRicetta implements Gui {

	private FacadeController controller;
	private HashSet<String> strumenti = new HashSet<>();
	private HashMap<Ingrediente, Double> ingredienti = new HashMap<>();
	private static final String ERRORE = "Errore";
	
	public GuiRicetta(FacadeController controller)
	{
		this.controller = controller;
		draw();
	}
	
	/*
	 * Viene disegnata l'interfaccia grafica
	 */
	@Override
	public void draw() 
	{
		final JFrame guiFrame = new JFrame();
		guiFrame.setLocation(30, 100);
		guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		guiFrame.setTitle("Ricetta");
		guiFrame.setSize(600,350);
		JPanel elementiGraficiRicetta = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel nomeLabel = new JLabel("Inserisci il nome della ricetta: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(nomeLabel, gbc);
		
		JTextField nome = new JTextField(15);
		nome.setCaretColor(Color.DARK_GRAY);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(nome, gbc);
		
		JButton getRicetta = new JButton("Visualizza ricetta");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(getRicetta, gbc);
		
		JButton getNota = new JButton("Visualizza nota");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(getNota, gbc);
		
		JButton eliminaRicetta = new JButton("Elimina ricetta");
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(eliminaRicetta, gbc);
		
		
		JLabel nomeRicettaLabel = new JLabel("nome della ricetta: ");
		gbc.gridx =0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(nomeRicettaLabel, gbc);
		
		JTextField nomeText = new JTextField(15);
		gbc.gridx =1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(nomeText, gbc);
		
		JLabel tempoLabel = new JLabel("tempo: ");
		gbc.gridx =0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(tempoLabel, gbc);
		
		JTextField tempoText = new JTextField(15);
		gbc.gridx =1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(tempoText, gbc);
		
		JLabel procedimentoLabel = new JLabel("procedimento: ");
		gbc.gridx =0;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(procedimentoLabel, gbc);
		
		JTextField procedimentoText = new JTextField(15);
		gbc.gridx =1;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(procedimentoText, gbc);
		
		JLabel strumentiLabel = new JLabel("Strumenti che servono per la ricetta ");
		gbc.gridx =0;
		gbc.gridy = 7;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(strumentiLabel, gbc);
		
		JButton strumentiButton = new JButton("Aggiungi strumento");
		gbc.gridx =1;
		gbc.gridy = 7;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(strumentiButton, gbc);
		
		JLabel ingredientiLabel = new JLabel("Ingredienti: ");
		gbc.gridx =0;
		gbc.gridy = 8;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(ingredientiLabel, gbc);

		JButton ingredientiButton = new JButton("Aggiungi ingrediente alla ricetta");
		gbc.gridx =1;
		gbc.gridy = 8;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(ingredientiButton, gbc);
		
		JLabel notaLabel = new JLabel("titolo della nota: ");
		gbc.gridx =0;
		gbc.gridy = 10;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(notaLabel, gbc);
		
		JTextField notaText = new JTextField(15);
		gbc.gridx =1;
		gbc.gridy = 10;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(notaText, gbc);
		
		JLabel descrizioneNotaLabel = new JLabel("Testo della nota: ");
		gbc.gridx =0;
		gbc.gridy = 11;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(descrizioneNotaLabel, gbc);
		
		JTextField descrizioneNotaText = new JTextField(15);
		gbc.gridx =1;
		gbc.gridy = 11;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(descrizioneNotaText, gbc);
		
		JButton aggiungiRicetta = new JButton("Aggiungi ricetta");
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(aggiungiRicetta, gbc);
		
		JButton modificaRicetta = new JButton("Modifica ricetta");
		gbc.gridx = 1;
		gbc.gridy = 12;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(modificaRicetta, gbc);
		
		JButton aggiungiNota = new JButton("Aggiungi nota");
		gbc.gridx = 2;
		gbc.gridy = 12;
		gbc.insets = new Insets(5, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		elementiGraficiRicetta.add(aggiungiNota, gbc);
		
		//Ascoltatori relativi agli eventi click sui vari bottoni
		
		//Se clicco sul bottone visualizza ricetta
		clickGetRicetta(getRicetta, nome, guiFrame);
		
		//Se clicco sul bottone Visualizza nota
		clickGetNota(getNota, nome, guiFrame);
		
		//Se clicco sul bottone elimina ricetta
		clickEliminaRicetta(eliminaRicetta, nome, guiFrame);
		
		//Se clicco sul bottone strumenti 
		clickAggiungiStrumento(strumentiButton, guiFrame);
		
		//Se clicco sul bottone aggiungi ingrediente alla ricetta
		clickAggiungiIngrediente(ingredientiButton, guiFrame);
		
		//Se clicco sul bottone aggiungi ricetta
		clickAggiungiRicetta(aggiungiRicetta, nomeText, tempoText, procedimentoText, notaText, descrizioneNotaText, guiFrame);
		
		//Se clicco sul bottone modfica ricetta
		clickModificaRicetta(modificaRicetta, nomeText, tempoText, procedimentoText, notaText, descrizioneNotaText, guiFrame);
		
		//Se clicco sul bottone aggiungi nota 
		clickAggiungiNota(aggiungiNota, nomeText, notaText, descrizioneNotaText);
		
		guiFrame.add(elementiGraficiRicetta);
		guiFrame.setVisible(true);
	}

	/*
	 * Ascoltatore dell'evento click del bottone mostraRicetta
	 */
	private void clickGetRicetta(JButton getRicetta, final JTextField nome, final JFrame guiFrame)
	{
		getRicetta.addActionListener(e -> {
			Ricetta r =null;
			try {
				r = controller.getRicetta(nome.getText());
			} catch (SQLException | NullPointerException | IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			String output="";
			if(r != null)
			{
				output = r.toString();
				JDialog dialog = new JDialog(guiFrame, "Nota");
				JTextArea text = new JTextArea(output);
				text.setLineWrap(true);
				text.setWrapStyleWord(true);
				dialog.add(text);
				dialog.setSize(500, 500);
				dialog.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"La ricetta "+ nome.getText()+ " non esiste",ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			nome.setText("");
		});
	}

	/*
	 * Ascoltatore dell'evento click del bottone elimina ricetta
	 */
	private void clickEliminaRicetta(JButton eliminaRicetta, final JTextField nome, final JFrame guiFrame)
	{
		eliminaRicetta.addActionListener(e -> {
			
			JDialog.setDefaultLookAndFeelDecorated(true); //Chiedo conferma che voglia davvero eliminare l'ingrediente 
			int response = JOptionPane.showConfirmDialog(null, "Vuoi davvero eliminare la ricetta?", "Conferma",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			boolean conferma = false;
			if (response == JOptionPane.YES_OPTION) 
			{
				conferma = true;
			} 
			if(conferma)
			{
				try {
					controller.eliminaRicetta(nome.getText());
				} catch (SQLException | NullPointerException | IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
				}
			}	
			else
			{
				JOptionPane.showMessageDialog(guiFrame, "Non elimino la ricetta");
			}
			nome.setText("");
		});
	}
	
	/*
	 * Ascoltatore dell'evento click del bottone visualizza nota
	 */
	private void clickGetNota(JButton getNota, final JTextField nome, final JFrame guiFrame)
	{
		getNota.addActionListener(e -> {
			Ricetta r=null;
			try {
				r = controller.getRicetta(nome.getText());
			} catch (SQLException | NullPointerException | IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			Nota nota = null;
			if(r != null)
			{
				nota = r.getNota();
				if (nota != null)
				{
					String n = "titolo nota: "+ nota.getTitolo() + " Descrizione: " + nota.getDescrizione();
					JDialog dialog = new JDialog(guiFrame, "Nota");
					JLabel label = new JLabel(n);
					dialog.add(label);
					dialog.setSize(500, 500);
					dialog.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Nessuna nota da visualizzare",ERRORE,JOptionPane.WARNING_MESSAGE);
				}
				
			}
			else
			{
				JOptionPane.showMessageDialog(null,"La ricetta "+ nome.getText()+ " non esiste",ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			nome.setText("");
		});
	}
	
	/*
	 * Ascoltatore per il bottone aggiungiStrumenti
	 */
	private void clickAggiungiStrumento(JButton bottone, final JFrame guiFrame)
	{
		bottone.addActionListener(e -> {				
			JDialog dialog = new JDialog(guiFrame, "Aggiungi strumento alla ricetta");
			JPanel elementiGraficiAggiungiStrumento = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel nomeLabel = new JLabel("Scegli lo strumento da associare alla ricetta tra quelli disponibili ");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.LINE_START;
			elementiGraficiAggiungiStrumento.add(nomeLabel, gbc);
			
			HashSet<String> opzioni=new HashSet<>();
			try {
				opzioni = controller.getNomiStrumenti();//Prendo tutti i nomi degli strumenti salvati nel database
			} catch (SQLException | NullPointerException | IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			String[] o = opzioni.toArray(new String[50]);
			JComboBox s = new JComboBox(o);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.CENTER;
			elementiGraficiAggiungiStrumento.add(s, gbc);
			
			JButton aggiungi = new JButton("Aggiungi strumento");
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.CENTER;
			elementiGraficiAggiungiStrumento.add(aggiungi, gbc);
			
			dialog.add(elementiGraficiAggiungiStrumento);
				
			//Se clicco sul bottone aggiungi
			clickAssociaStrumento(aggiungi, s);
				
			dialog.setSize(500, 500);
			dialog.setVisible(true);
		});
	}
	
	/*
	 * Ascoltatore dell'evento click sul bottone aggiungiIngrediente  
	 */
	private void clickAggiungiIngrediente(JButton bottone, final JFrame guiFrame)
	{
		bottone.addActionListener(e -> {
				
			JDialog dialog = new JDialog(guiFrame, "Aggiungi ingrediente alla ricetta");
			JPanel campiIngrediente = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel nomeLabel = new JLabel("Inserisci il nome dell'ingrediente: ");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.LINE_END;
			campiIngrediente.add(nomeLabel, gbc);
			JTextField nome = new JTextField(15);
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
			
			JLabel percentualeIngrediente = new JLabel("quantità assoluta (percentuale): ");
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.LINE_END;
			campiIngrediente.add(percentualeIngrediente, gbc);
			
			JTextField percentuale = new JTextField(15);
			gbc.gridx = 1;
			gbc.gridy = 4;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.LINE_END;
			campiIngrediente.add(percentuale, gbc);
			
			//Il bottone aggiungiIngrediente permette di associare l'ingrediete, i cui parametri,
			//sono stati inseriti dall'utente, alla ricetta che si sta creando
			JButton aggiungiIngrediente = new JButton("Aggiungi ingrediente");
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.insets = new Insets(5, 0, 0, 10);
			gbc.anchor = GridBagConstraints.CENTER;
			campiIngrediente.add(aggiungiIngrediente, gbc);
				
			dialog.add(campiIngrediente);
			dialog.setSize(500, 500);
				
			//Se clicco su aggiungi ingrediente
			clickAssociaIngrediente(aggiungiIngrediente, nome, quantita, tipo, bloccato, percentuale, dialog);
				
			dialog.setVisible(true);
		});
	}
	
	/*
	 * Ascoltatore del bottone aggiungi strumento
	 */
	private void clickAssociaStrumento(JButton bottone, final JComboBox attrezzatura)
	{
		//Attrezzatura[] a = new 
		bottone.addActionListener(e -> {
			String strumentoScelto = attrezzatura.getSelectedItem().toString();
			strumenti.add(strumentoScelto);
		});
	}
	
	/*
	 * Ascoltatore bottone aggiungiIngrediente della ricetta
	 */
	private void clickAssociaIngrediente(JButton bottone, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato, final JTextField percentuale, final JDialog dialog)
	{
		bottone.addActionListener(e -> {
			Ingrediente i = null;
			try 
			{
				i = controller.creaIngrediente(nome.getText(), quantita.getText(), bloccato.isSelected(), tipo.getSelectedItem().toString());
			}catch(IllegalArgumentException | NullPointerException e2)
			{
				JOptionPane.showMessageDialog(null,e2.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			double percentualeIngrediente = 0.0; 
			try {
				percentualeIngrediente = Double.parseDouble(percentuale.getText())/100;
				ingredienti.put(i, percentualeIngrediente);
			}catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(dialog, "Inserire un numero nel campo percentuale");
			}
		});
	}
	
	/*
	 * Ascoltatore del bottone aggiungiRicetta
	 */
	private void clickAggiungiRicetta(JButton aggiungiRicetta, final JTextField nomeText, final JTextField tempoText, final JTextField procedimentoText, 
			final JTextField notaText, final JTextField descrizioneNotaText, final JFrame guiFrame)
	{
		aggiungiRicetta.addActionListener(e -> {
			String nomeBirra = nomeText.getText();
			String tempo = tempoText.getText();
			String procedimento = procedimentoText.getText();
			String titoloNota = notaText.getText();
			boolean aggiunta = false;
			if(titoloNota.equals(""))
				titoloNota=null;
			String descrizioneNota = descrizioneNotaText.getText();
			if(descrizioneNota.equals(""))
				descrizioneNota = null;	
			try 
			{
				aggiunta = controller.aggiungiRicetta(nomeBirra, tempo, procedimento, strumenti, ingredienti, titoloNota, descrizioneNota);
				if(aggiunta)
					JOptionPane.showMessageDialog(null,"Ricetta aggiunta correttamente","Ricetta aggiunta",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"Non è stato possibile aggiungere la ricetta","Ricetta non aggiunta",JOptionPane.WARNING_MESSAGE);
			}catch(IllegalArgumentException | SQLException error)
			{
				JOptionPane.showMessageDialog(guiFrame, error.toString());
			}catch(NullPointerException error2)
			{
				JOptionPane.showMessageDialog(guiFrame, "Impossibile aggiungere la ricetta, dopo aver ottenuto un errore reinserire tutti i campi di ricetta da aggiungere");
			}
			finally
			{
				ingredienti = new HashMap<>();
				strumenti = new HashSet<>();
			}
			//Elimino il testo inserito dall'utente
			pulisciCampiInput(nomeText, tempoText, procedimentoText, notaText, descrizioneNotaText);
		});
	}
	
	/*
	 * Ascoltatore dell'evento click sul bottone modifica ricetta
	 */
	private void clickModificaRicetta(JButton modificaRicetta, final JTextField nomeText, final JTextField tempoText, final JTextField procedimentoText, 
			final JTextField notaText, final JTextField descrizioneNotaText, final JFrame guiFrame)
	{
		modificaRicetta.addActionListener(e -> {
			String nomeBirra = nomeText.getText();
			String tempo = tempoText.getText();
			String procedimento = procedimentoText.getText();
			String titoloNota = notaText.getText();
			if(titoloNota.equals(""))
				titoloNota=null;
			String descrizioneNota = descrizioneNotaText.getText();
			if(descrizioneNota.equals(""))
				descrizioneNota = null;	
			try 
			{
				boolean risultato = controller.modificaRicetta(nomeBirra, tempo, procedimento, strumenti, ingredienti, titoloNota, descrizioneNota);
				ingredienti = new HashMap<>();
				strumenti = new HashSet<>();
				if (risultato) //Ricetta modificata correttamenete
				{
					JOptionPane.showMessageDialog(guiFrame, "Ricetta modificata correttamente");
				}
				else //Ricetta non modificata
				{
					JOptionPane.showMessageDialog(guiFrame, "Impossibile modificare la ricetta");
				}
			}catch (IllegalArgumentException | SQLException e2) 
			{
				JOptionPane.showMessageDialog(null,e2.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}catch(NullPointerException error)
			{
				JOptionPane.showMessageDialog(null,"Impossibile modificare la ricetta, dopo che si ottiene un errore reinserire tutti i campi della ricetta",ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			pulisciCampiInput(nomeText, tempoText, procedimentoText, notaText, descrizioneNotaText);
		});
	}
	
	/*
	 * Ascoltatore del bottone aggiungi nota 
	 */
	private void clickAggiungiNota(JButton aggiungiNota, final JTextField nomeText,	final JTextField notaText, 
			final JTextField descrizioneNotaText)
	{
		aggiungiNota.addActionListener(e -> {
			String titoloNota = notaText.getText();
			String descrizioneNota = descrizioneNotaText.getText();
			String nomeBirra = nomeText.getText();
			boolean risultato = false;
			try 
			{
				risultato = controller.aggiungiNota(nomeBirra, titoloNota, descrizioneNota);
				if(risultato)
				{
					JOptionPane.showMessageDialog(null,"nota modificata correttamenete");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Impossibile aggiungere la nota",ERRORE,JOptionPane.WARNING_MESSAGE);
				}
			}
			catch(IllegalArgumentException | NullPointerException | SQLException  exception)
			{
				JOptionPane.showMessageDialog(null,exception.getMessage(),ERRORE,JOptionPane.WARNING_MESSAGE);
			}
			nomeText.setText("");
			notaText.setText("");
			descrizioneNotaText.setText("");
		});
	}
	
	/*
	 * Permette di eliminare il testo che è stato inserito dall'utente 
	 */
	private void pulisciCampiInput(JTextField nomeText, final JTextField tempoText, final JTextField procedimentoText, 
			final JTextField notaText, final JTextField descrizioneNotaText)
	{
		nomeText.setText("");
		tempoText.setText("");
		procedimentoText.setText("");
		notaText.setText("");
		descrizioneNotaText.setText("");
	}
}