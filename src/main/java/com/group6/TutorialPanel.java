package com.group6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TutorialPanel extends JPanel {

	
	private JPanel firstPanel, secondPanel;
    private JButton LogoButton, firstbutton, secondbutton;
    private Image backgroundImage;
    
    FormListener formListener;
    
    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }
	
    public TutorialPanel(int i) {
    	if (i == 1) {
            setPreferredSize(new Dimension(1000, 600));
    		
    		ImageIcon icon = new ImageIcon("src/main/java/Resources/Tutorial/first.png");
    		Image image = icon.getImage().getScaledInstance(800, 470, Image.SCALE_SMOOTH);
    		ImageIcon resizedIcon = new ImageIcon(image);
            
            firstbutton = new JButton(resizedIcon);
            firstbutton.setBorder(BorderFactory.createEmptyBorder()); 
            firstbutton.setContentAreaFilled(false); 
            
            setLayout(new BorderLayout()); 
            add(firstbutton, BorderLayout.CENTER);

            ImageIcon bgIcon = new ImageIcon("src/main/java/Resources/icons/bg.png");
            backgroundImage = bgIcon.getImage();
            
            firstbutton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				FormEvent ev = new FormEvent(this);
    				
    				ev.setCard(2);
    				
    				if (formListener != null) {
    					formListener.formEventOccurred(ev);
    				}
    			}
    		});
    	} else if (i == 2) {
            setPreferredSize(new Dimension(1000, 600));
    		
     		ImageIcon icon = new ImageIcon("src/main/java/Resources/Tutorial/second.png");
     		Image image = icon.getImage().getScaledInstance(800, 470, Image.SCALE_SMOOTH);
     		ImageIcon resizedIcon = new ImageIcon(image);
             

            secondbutton = new JButton(resizedIcon);
            secondbutton.setBorder(BorderFactory.createEmptyBorder()); 
            secondbutton.setContentAreaFilled(false); 
            
            setLayout(new BorderLayout()); 
            add(secondbutton, BorderLayout.CENTER);

            ImageIcon bgIcon = new ImageIcon("src/main/java/Resources/icons/bg.png");
            backgroundImage = bgIcon.getImage();
             
            secondbutton.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
     				FormEvent ev = new FormEvent(this);
     				
     				ev.setCard(3);
     				
     				if (formListener != null) {
     					formListener.formEventOccurred(ev);
     				}
     			}
     		});
    	}
    }
    
    public TutorialPanel() {
        setPreferredSize(new Dimension(1000, 600));
        
        ImageIcon icon = new ImageIcon("src/main/java/Resources/icons/welcomeMeloHub.png");
        Image image = icon.getImage().getScaledInstance(470, 470, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);

        LogoButton = new JButton(resizedIcon);
        LogoButton.setBorder(BorderFactory.createEmptyBorder()); 
        LogoButton.setContentAreaFilled(false); 


        setLayout(new BorderLayout()); 
        add(LogoButton, BorderLayout.CENTER);

        ImageIcon bgIcon = new ImageIcon("src/main/java/Resources/icons/bg.png");
        backgroundImage = bgIcon.getImage();
        
        LogoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormEvent ev = new FormEvent(this);
				
				ev.setCard(1);
				
				if (formListener != null) {
					formListener.formEventOccurred(ev);
				}
			}
		});
        
        
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
