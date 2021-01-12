import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;


public class FlashCardBuilder {
	//instance variables for Flash Card Builder GUI
	
	private ArrayList<FlashCard> deck;
	private JTextArea front;
	private JTextArea back;
	private JFrame frame;
	
	
	public FlashCardBuilder() {
		//Formatting for labels, buttons, and text area 
		
		frame = new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		Font font = new Font("Calibri", Font.PLAIN, 18);
		
		front = new JTextArea(6, 20);
		front.setLineWrap(true);
		front.setWrapStyleWord(true);
		front.setFont(font);
		
		back = new JTextArea(6, 20);
		back.setLineWrap(true);
		back.setWrapStyleWord(true);
		back.setFont(font);
		
		JScrollPane frontJScrollPane = new JScrollPane(front);
		frontJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frontJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JScrollPane backJScrollPane = new JScrollPane(back);
		backJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		backJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
		
		JLabel frontJLabel = new JLabel("Front");
		JLabel backJLabel = new JLabel("Back");
		JButton addButton = new JButton("Add Card");

		mainPanel.add(frontJLabel);
		mainPanel.add(frontJScrollPane);
		mainPanel.add(backJLabel);
		mainPanel.add(backJScrollPane);
		mainPanel.add(addButton);
		
		addButton.addActionListener(new NextCardListener());
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		menuBar.add(fileMenu);	
		fileMenu.add(saveMenuItem);

		saveMenuItem.addActionListener(new SaveMenuListener());
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(375, 400);
		frame.setVisible(true);
	
		deck = new ArrayList<FlashCard>();
	}
	

	class NextCardListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//takes String in Front and Back, saves it to a flashcard object, then to the deck
			
			FlashCard card = new FlashCard(front.getText(), back.getText());
			deck.add(card);
			clearCard();
		}
	}
	
	
	class SaveMenuListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//opens save prompt and writes file using saveFile()
			
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}

	}	
		
	
	private void saveFile(File selectedFile) {
		//saves the String of Front and Back, writes them in a file to be saved
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			
			Iterator<FlashCard> cardIterator = deck.iterator(); 
			while (cardIterator.hasNext()) {
				FlashCard card = (FlashCard)cardIterator.next();
				writer.write(card.getFront() + "/");
				writer.write(card.getBack() + "\n");
			}
			writer.close();
			
		} catch (Exception e) {
			System.out.println("Can't write file.");
			e.printStackTrace();
		}
	}

	
	private void clearCard() {
		//clears the text in Front and Back
		
		front.setText("");
		back.setText("");
		front.requestFocus();
	}
	
	
	public static void main(String[] args) {
		//runs the flash card builder
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new FlashCardBuilder();
			}
		});
	}
}
	
