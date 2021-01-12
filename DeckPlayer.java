import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Iterator;

public class DeckPlayer {
	
	//instance variables for Deck Player GUI
	private ArrayList<FlashCard> deck;
	private JTextArea display;
	private JButton showFront;
	
	private Iterator<FlashCard> cardIterator;
	private FlashCard currentCard;
	private boolean isShowFront;
	private JFrame frame;
	
	public DeckPlayer() {
		//Formatting for labels, buttons, and text area 
		
		frame = new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		Font font = new Font("Calibri", Font.PLAIN, 18);
		
		display = new JTextArea(6, 20);
		display.setFont(font);
		
		JScrollPane displayJScrollPane = new JScrollPane(display);
		displayJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		showFront = new JButton("Show Front");
		
		mainPanel.add(displayJScrollPane);
		mainPanel.add(showFront);
		showFront.addActionListener(new NextCardListener());
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load Deck");
		loadMenuItem.addActionListener(new OpenMenuListener());
		
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(375, 400);
		frame.setVisible(true);
	}
	
	private void showNextCard() {
		//shows the next card in the Iterator
		currentCard = (FlashCard) cardIterator.next();
		display.setText(currentCard.getFront());
		isShowFront = true;
		
	}
	
	
	class NextCardListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//Changes text display and button labels
			
			if (isShowFront) {
				display.setText(currentCard.getBack());
				showFront.setText("Next Card");
				isShowFront = false;
			}

			else {
				showFront.setText("Show Front");
				if (cardIterator.hasNext()) {
					showNextCard();
					
			
				} else {
					display.setText("End of Deck");
					showFront.setEnabled(false);
				}
			}
		}	
	}	
	
	
	class OpenMenuListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//opens selected file to be read by loadFile()
			
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
			}
		}	


	private void loadFile(File selectedFile) {
		//reads the opened file, separate the lines into flashcard parameters, then saves it to deck arraylist
		
		deck = new ArrayList<FlashCard>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
			
		} catch (Exception e) {
			System.out.println("Can't read file.");
			e.printStackTrace();
		}
		//starts displaying the first card
		cardIterator = deck.iterator();
		showNextCard();
		
	} 

	private void makeCard(String lineToParse) {
		//used by loadFile() to split the line into flashcard parameters
		
		String[] result = lineToParse.split("/");
		
		FlashCard card = new FlashCard(result[0], result[1]);
		deck.add(card);
	}


	public static void main(String[] args) {
		//runs the deck player
		
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					new DeckPlayer();
				}
			});
	}
}

	

