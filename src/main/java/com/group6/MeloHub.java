package com.group6;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;

public class MeloHub extends JFrame {
	
	MusicPlayer musicPlayerFrame;
	TutorialPanel tutorialPanel, tutorialPanel1, tutorialPanel2;
	Container container;
	CardLayout cardLayout;
	
	public void formlisten(int card) {
		if (card == 1) {
			cardLayout.show(container, "firstTutorial");
		} else if (card == 2) {
			cardLayout.show(container, "secondTutorial");
		} else if (card == 3) {
			musicPlayerFrame.setVisible(true);
			MeloHub.this.setVisible(false);
		}
	}
	
	public MeloHub() {
		super("MeloHub");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/Resources/icons/MeloHubICON.png"));
		
		tutorialPanel = new TutorialPanel();
		tutorialPanel1 = new TutorialPanel(1);
		tutorialPanel2 = new TutorialPanel(2);
		musicPlayerFrame = new MusicPlayer();
		
		container = getContentPane();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		
		container.add("Tutorial", tutorialPanel);
		container.add("firstTutorial", tutorialPanel1);
		container.add("secondTutorial", tutorialPanel2);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        this.setResizable(false);
        pack();
        setVisible(true);
        
        tutorialPanel.setFormListener(new FormListener() {
        	public void formEventOccurred(FormEvent e) {
        		int card = e.getCard();
        		formlisten(card);
        	}
        });
        tutorialPanel1.setFormListener(new FormListener() {
        	public void formEventOccurred(FormEvent e) {
        		int card = e.getCard();
        		formlisten(card);
        	}
        });
        tutorialPanel2.setFormListener(new FormListener() {
        	public void formEventOccurred(FormEvent e) {
        		int card = e.getCard();
        		formlisten(card);
        	}
        });
	}
	
    public static void main(String[] args) {
        JFXPanel fxPanel = new JFXPanel(); 

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	new MeloHub();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}
