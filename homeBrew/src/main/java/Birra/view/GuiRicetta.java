package Birra.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import Birra.controller.*;
import Birra.model.Attrezzatura;
import Birra.model.Ingrediente;
import Birra.model.Nota;
import Birra.model.Ricetta;
import Birra.model.TipoAttrezzatura;
import Birra.model.TipoIngrediente;


public class GuiRicetta implements Gui {

	private FacadeController controller;
	private ArrayList<Attrezzatura> strumenti = new ArrayList<>();
	HashMap<Ingrediente, Double> ingredienti = new HashMap<>();
	
	public GuiRicetta(FacadeController controller)
	{
		this.controller = controller;
		draw();
	}
	
	@Override
	public void draw() 
	{
		final JFrame guiFrame = new JFrame();
		guiFrame.setLocation(30, 100);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
		
		//Ascoltatori
		
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
		clickAggiungiNota(aggiungiNota, nomeText, notaText, descrizioneNotaText, guiFrame);
		
		guiFrame.add(elementiGraficiRicetta);
		guiFrame.setVisible(true);
	}

	//Ascoltatore dell'evento click del bottone mostraRicetta
	private void clickGetRicetta(JButton getRicetta, final JTextField nome, final JFrame guiFrame)
	{
		getRicetta.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Ricetta r =null;
				r = controller.getRicetta(nome.getText());
				Nota nota = null;
				if(r != null)
				{
					nota = r.getNota();
					String n ="";
					if(nota != null)
					{
						n = "nomeBirra: " + r.getNomeBirra() +
								" titolo nota: "+ nota.getTitolo() + " Descrizione: " + nota.getDescrizione();
					}
					else
					{
						n = "nomeBirra: " + r.getNomeBirra();
					}
					JDialog dialog = new JDialog(guiFrame, "Nota");
					JLabel label = new JLabel(n);
					dialog.add(label);
					dialog.setSize(500, 500);
					dialog.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Nessuna nota da visualizzare","Errore",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
	}

	//Ascoltatore dell'evento click del bottone elimina ricetta
	private void clickEliminaRicetta(JButton eliminaRicetta, final JTextField nome, final JFrame guiFrame)
	{
		eliminaRicetta.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) 
			{
				JDialog.setDefaultLookAndFeelDecorated(true); //Chiedo conferma che voglia davvero eliminare l'ingrediente 
				int response = JOptionPane.showConfirmDialog(null, "Vuoi davvero eliminare la ricetta?", "Conferma",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				boolean conferma = false;
				if (response == JOptionPane.NO_OPTION)
				{
					conferma = false;
				} 
				else if (response == JOptionPane.YES_OPTION) 
				{
					conferma = true;
				} 
				if(conferma)
				{
					controller.eliminaRicetta(nome.getText());
					JOptionPane.showMessageDialog(guiFrame, "Ricetta eliminata");
				}	
				else
				{
					JOptionPane.showMessageDialog(guiFrame, "Non elimino la ricetta");
				}
			}
		});
	}
	
	//Ascoltatore dell'evento click del bottone visualizza nota
	private void clickGetNota(JButton getNota, final JTextField nome, final JFrame guiFrame)
	{
		getNota.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Ricetta r = controller.getRicetta(nome.getText());
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
						JOptionPane.showMessageDialog(null,"Nessuna nota da visualizzare","Errore",JOptionPane.WARNING_MESSAGE);
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null,"La ricetta "+ nome.getText()+ " non esiste","Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
	}
	
	//Ascoltatore per il bottone aggiungiStrumenti
	private void clickAggiungiStrumento(JButton bottone, final JFrame guiFrame)
	{
		bottone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JDialog dialog = new JDialog(guiFrame, "Aggiungi strumento alla ricetta");
				JPanel elementiGraficiAggiungiStrumento = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				JLabel nomeLabel = new JLabel("Inserisci il nome dello strumento: ");
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(nomeLabel, gbc);
				
				JTextField nomeText = new JTextField(15);
				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(nomeText, gbc);
				
				JLabel portataLabel = new JLabel("Inserisci la portata dello strumento: ");
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(portataLabel, gbc);
				
				JTextField portataText = new JTextField(15);
				gbc.gridx = 1;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(portataText, gbc);
				
				JLabel tipoLabel = new JLabel("Scegli il tipo di strumento: ");
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(tipoLabel, gbc);
				
				TipoAttrezzatura[] opzioni = {TipoAttrezzatura.CISTERNA, TipoAttrezzatura.FERMENTATORE, TipoAttrezzatura.TUBO};
				JComboBox tipo = new JComboBox(opzioni);
				gbc.gridx = 1;
				gbc.gridy = 2;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(tipo, gbc);
				
				JButton aggiungi = new JButton("Aggiungi strumento");
				gbc.gridx = 1;
				gbc.gridy = 3;
				gbc.insets = new Insets(5, 0, 0, 10);
				gbc.anchor = GridBagConstraints.LINE_START;
				elementiGraficiAggiungiStrumento.add(aggiungi, gbc);
				
				dialog.add(elementiGraficiAggiungiStrumento);
				
				//Se clicco sul bottone aggiungi
				clickAssociaStrumento(aggiungi, nomeText, portataText, tipo, dialog);
				
				dialog.setSize(500, 500);
				dialog.setVisible(true);
			}
		});
	}
	private void clickAggiungiIngrediente(JButton bottone, final JFrame guiFrame)
	{
		bottone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
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
			}
		});
	}
	
	//Ascoltatore del bottone aggiungi strumento
	private void clickAssociaStrumento(JButton bottone, final JTextField nome, final JTextField portata, final JComboBox tipo, final JDialog dialog)
	{
		bottone.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String nomeAttrezzatura = nome.getText();
				double portataAttrezzatura = 0.0;
				try 
				{
					portataAttrezzatura = Double.parseDouble(portata.getText());
				}catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(dialog, "Inserire un numero");
				}
				String ta = tipo.getSelectedItem().toString();
				TipoAttrezzatura tipoAttrezzatura = null;
				if(ta.equals("CISTERNA"))
					tipoAttrezzatura = TipoAttrezzatura.CISTERNA;
				if(ta.equals("TUBO"))
					tipoAttrezzatura = TipoAttrezzatura.TUBO;
				if(ta.equals("FERMENTATORE"))
					tipoAttrezzatura = TipoAttrezzatura.FERMENTATORE;
				Attrezzatura a = new Attrezzatura(nomeAttrezzatura, portataAttrezzatura, tipoAttrezzatura);
				strumenti.add(a);
				System.out.println(strumenti.toString());
			}
		});
	}
	
	//Ascoltatore bottone aggiungiIngrediente della ricetta
	private void clickAssociaIngrediente(JButton bottone, final JTextField nome, final JTextField quantita, final JComboBox tipo, final JCheckBox bloccato, final JTextField percentuale, final JDialog dialog)
	{
		bottone.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String nomeIngrediente = nome.getText();		
				double quantitaIngrediente = 0.0;
				try {
					quantitaIngrediente = Double.parseDouble(quantita.getText());
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(dialog, "Inserire un numero");
				}
				String ti = tipo.getSelectedItem().toString();
				TipoIngrediente tipoIngrediente = null;
				if(ti.equals("MALTO"))
					tipoIngrediente = TipoIngrediente.MALTO;
				if(ti.equals("ACQUA"))
					tipoIngrediente = TipoIngrediente.ACQUA;
				if(ti.equals("LIEVITO"))
					tipoIngrediente = TipoIngrediente.LIEVITO;
				if(ti.equals("ZUCCHERO"))
					tipoIngrediente = TipoIngrediente.ZUCCHERO;
				if(ti.equals("LUPPOLI"))
					tipoIngrediente = TipoIngrediente.LUPPOLI;
				boolean bloccatoIngrediente = bloccato.isSelected();
				Ingrediente i = new Ingrediente(nomeIngrediente, quantitaIngrediente, bloccatoIngrediente, tipoIngrediente);
				double percentualeIngrediente = 0.0; 
				try {
					percentualeIngrediente = Double.parseDouble(percentuale.getText());
					ingredienti.put(i, percentualeIngrediente);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(dialog, "Inserire un numero");
				}
			}
		});
	}
	
	//Ascoltatore del bottone aggiungiRicetta
	private void clickAggiungiRicetta(JButton aggiungiRicetta, final JTextField nomeText, final JTextField tempoText, final JTextField procedimentoText, 
			final JTextField notaText, final JTextField descrizioneNotaText, final JFrame guiFrame)
	{
		aggiungiRicetta.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String nomeBirra = nomeText.getText();
				String tempo = tempoText.getText();
				String procedimento = procedimentoText.getText();
				String titoloNota = notaText.getText();
				String descrizioneNota = descrizioneNotaText.getText();
				
				Attrezzatura[] a = strumenti.toArray(new Attrezzatura[strumenti.size()]);
				try 
				{
					controller.aggiungiRicetta(nomeBirra, tempo, procedimento, a, ingredienti, titoloNota, descrizioneNota);
				}catch(IllegalArgumentException error)
				{
					JOptionPane.showMessageDialog(guiFrame, error.toString());
				}finally
				{
					ingredienti = new HashMap<>();
					strumenti = new ArrayList<Attrezzatura>();
				}	
			}
		});
	}
	
	//Ascoltatore dell'evento click sul bottone modifica ricetta
	private void clickModificaRicetta(JButton modificaRicetta, final JTextField nomeText, final JTextField tempoText, final JTextField procedimentoText, 
			final JTextField notaText, final JTextField descrizioneNotaText, final JFrame guiFrame)
	{
		modificaRicetta.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String nomeBirra = nomeText.getText();
				String tempo = tempoText.getText();
				String procedimento = procedimentoText.getText();
				String titoloNota = notaText.getText();
				String descrizioneNota = descrizioneNotaText.getText();
				Attrezzatura[] a = strumenti.toArray(new Attrezzatura[strumenti.size()]);
				try 
				{
					boolean risultato = controller.modificaRicetta(nomeBirra, tempo, procedimento, a, ingredienti, titoloNota, descrizioneNota);
					if (risultato)
					{
						JOptionPane.showMessageDialog(guiFrame, "Ricetta modificata correttamente");
						ingredienti = new HashMap<>();
						strumenti = new ArrayList<Attrezzatura>();
					}
					else
					{
						JOptionPane.showMessageDialog(guiFrame, "Impossibile modificare la ricetta");
					}
				}catch (Exception e2) 
				{
					JOptionPane.showMessageDialog(null,e2.getMessage(),"Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	//Ascoltatore del bottone aggiungi nota 
	private void clickAggiungiNota(JButton aggiungiNota, final JTextField nomeText,	final JTextField notaText, 
			final JTextField descrizioneNotaText, final JFrame guiFrame)
	{
		aggiungiNota.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String titoloNota = notaText.getText();
				String descrizioneNota = descrizioneNotaText.getText();
				Nota nota = new Nota(titoloNota, descrizioneNota);
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
						JOptionPane.showMessageDialog(null,"Impossibile aggiungere la nota","Errore",JOptionPane.WARNING_MESSAGE);
					}
				}
				catch(IllegalArgumentException exception)
				{
					JOptionPane.showMessageDialog(null,exception.getMessage(),"Errore",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}