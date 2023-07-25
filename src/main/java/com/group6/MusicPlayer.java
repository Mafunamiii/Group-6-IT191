package com.group6;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.Timer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class MusicPlayer extends JFrame {
	static private ControllerMusic controller;
    public JPanel contentPane;
    public MediaPlayer mediaPlayer;
    private boolean loop = true;
    private boolean paused = false;
    private boolean repeat = false;
    private boolean playing = false;
    private boolean isMuted = false;
    private int songNum = 1;
    Timer timer;
    public JLabel displayTimer;
    public JSlider slider;
    public JSlider volumeSlider;
    public JButton btnNewButton_4;
    public int sec;
    public int min;
    public int sec10;  
    public int timeSec;
    public int timeMin;
    public String timeDuration;
    private JTextField songname;
    private JButton deleteBtn;
    private JButton volumeButton;
    private String[] randomImages = {"image.png","image1.png","image2.png","image3.png","image4.png","image5.png"};
    int randomNumber;
    Random random = new Random();
    double volumeLevel;
    int volumeLevelSave;
    //Enables the TimerTask1 class to get displayTimer Jlabel and update it there
    public JLabel getDisplayTimer() {
        return displayTimer;
    }
    
    //Starts the timer
    public void musicTime(){
        TimerTask1 task = new TimerTask1(this); 
        timer = new Timer();
        timer.schedule(task,0,1000);
}
    //sets the album cover of music currently playing
    public void albumCover(MusicEntity m1) {
    	if(m1.getImagePath() != null) {
      	   btnNewButton_4.setIcon(new ImageIcon(m1.getImagePath()));
         }else {
      	   randomNumber = random.nextInt(5);
              btnNewButton_4.setIcon(new ImageIcon("src/main/java/Resources/Rnadom Images/"+randomImages[randomNumber]));
         }
    }
    
    
	public MusicPlayer() {
		//Add controller
		super("MeloHub");
		
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/Resources/icons/MeloHubICON.png"));

		controller = new ControllerMusic(this);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        this.setResizable(false);
	       
        //LAYOUT
    	JPanel musicMenu = new JPanel();
    	musicMenu.setBounds(191, 407, 643, 46);
	    contentPane.add(musicMenu);
	    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 1, 10);
	    musicMenu.setOpaque(false);
	    musicMenu.setLayout(flowLayout);
	    
	     //Import Image                 
         btnNewButton_4 = new JButton("");
         btnNewButton_4.addMouseListener(new MouseAdapter() {
                 	@Override
         	public void mouseClicked(MouseEvent e) {
         		JFileChooser fileChooser = new JFileChooser();
                		
        		// Set file filter to accept only MP3 files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
                fileChooser.setFileFilter(filter);
                		
                int result = fileChooser.showOpenDialog(contentPane);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // User selected a file
                	File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    // Perform the desired operations with the selected file
                    
                    //Get the relative file path of current user
                    String baseFolder = System.getProperty("user.dir");
                    String destinationFolder = baseFolder + "/src/main/java/Resources/Images";
                    String destinationPath = destinationFolder + "/" + fileName;
                    
                    if(controller.importImage(selectedFile, destinationPath, fileName, songNum, btnNewButton_4) == false) {
                    	JOptionPane.showMessageDialog(contentPane, "The file you are trying to import is already imported" , "ERROR",
    					        JOptionPane.ERROR_MESSAGE);
                    }
                    
                                				
                }
         	}
         });
         btnNewButton_4.setBounds(400, 90, 209, 209);
         contentPane.add(btnNewButton_4);
         btnNewButton_4.setContentAreaFilled(false);
         btnNewButton_4.setBorderPainted(false);
	     
	     // Customize the slider colors
	     Color thumbColor = Color.WHITE; // Change this to your desired thumb color
	     Color trackColor = Color.gray; // Change this to your desired track color
	     
	       //Layout
	       JPanel middle = new JPanel();
	       middle.setBounds(191, 335, 643, 62);
	       middle.setOpaque(false);
	       contentPane.add(middle);
	       GridBagLayout gbl_middle = new GridBagLayout();
	       gbl_middle.columnWidths = new int[]{64, 374, 131, 0};
	       gbl_middle.rowHeights = new int[]{29, 22, 0};
	       gbl_middle.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
	       gbl_middle.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
	       middle.setLayout(gbl_middle);
 		     
           //Music Name     
	       songname = new JTextField();
	       GridBagConstraints gbc_songname = new GridBagConstraints();
	       gbc_songname.fill = GridBagConstraints.BOTH;
	       gbc_songname.insets = new Insets(0, 0, 5, 5);
	       gbc_songname.gridx = 1;
	       gbc_songname.gridy = 0;
	       middle.add(songname, gbc_songname);
	       songname.setForeground(new Color(255, 255, 255));
	       songname.setOpaque(false);
	       songname.setText("placeholder");
	       songname.setEditable(false);
	       songname.setHorizontalAlignment(SwingConstants.CENTER);
	       songname.setFont(new Font("Verdana", Font.BOLD, 16));
	       songname.setBorder(BorderFactory.createEmptyBorder());
	       songname.setBackground(null);
	       songname.setColumns(10);
	       
 	    //Music Name Change when user types new name
	       songname.addActionListener(new ActionListener() {
	 	       	public void actionPerformed(ActionEvent e) {
	 	       		
	 	       		MusicEntity m1 = controller.findSong(songNum);
	 	       		controller.setModifiedName(m1, songname.getText());
	 	       		
	 	       	}
	       });
	     
 	      //LYRICS BUTTON      
 		     JButton btnNewButton_5 = new JButton("...");
 		     GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
 		     gbc_btnNewButton_5.anchor = GridBagConstraints.SOUTH;
 		     gbc_btnNewButton_5.insets = new Insets(0, 0, 0, 5);
 		     gbc_btnNewButton_5.gridx = 0;
 		     gbc_btnNewButton_5.gridy = 1;
 		     middle.add(btnNewButton_5, gbc_btnNewButton_5);
 		     btnNewButton_5.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\lyrics.png"));
 		     btnNewButton_5.setContentAreaFilled(false);
 		     btnNewButton_5.setBorderPainted(false);
 		     //End of Volume Button
 		     
 		     //Lyrics button clicked
             btnNewButton_5.addMouseListener(new MouseAdapter() {
                 	@Override
                 	public void mouseClicked(MouseEvent e) {

                         MusicEntity m1 = controller.findSong(songNum);
                         if (m1 != null) {
                             String lyrics = m1.getlyrics();
                             
                             if (lyrics != null) {
                             	// Read the content of the text file
                             	StringBuilder lyricsBuilder = new StringBuilder();
                             	try (BufferedReader reader = new BufferedReader(new FileReader(m1.getlyrics()))) {
                             	    String line;
                             	    while ((line = reader.readLine()) != null) {
                             	        lyricsBuilder.append(line);
                             	        lyricsBuilder.append("\n\n");
                             	    }
                             	} catch (IOException ee) {
                             	    // Handle file reading exception
                             	    ee.printStackTrace();
                             	}
                             	// Create a font object with the desired font family and size
                             	 Font lyricsFont = new Font("Arial", Font.PLAIN, 16); // Replace "Arial" with the desired font family
                             	
                                 // Lyrics available, display them in resizable dialog
                                 JTextArea lyricsTextArea = new JTextArea(lyricsBuilder.toString());
                                 lyricsTextArea.setFont(lyricsFont);
                                 lyricsTextArea.setEditable(false);
                                 lyricsTextArea.setLineWrap(true);
                                 lyricsTextArea.setWrapStyleWord(true);
                                 lyricsTextArea.setEditable(true);
                                 
                                 JScrollPane scrollPane = new JScrollPane(lyricsTextArea);
                                 scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                                 JDialog dialog = new JDialog();
                                 dialog.setTitle(m1.getName()+" Lyrics");
                                 dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                 dialog.setResizable(true);
                                 dialog.getContentPane().add(scrollPane);
                                 dialog.pack();
                                 dialog.setVisible(true);
                                 dialog.setLocationRelativeTo(null);
                                 dialog.setBounds(100, 100, 450, 650);
                                 
                                 dialog.addWindowListener(new WindowAdapter() {
                                     @Override
                                     public void windowClosing(WindowEvent e) {
                                         int option = JOptionPane.showConfirmDialog(contentPane, "Save changes?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                                         if (option == JOptionPane.YES_OPTION) {
                                             // Save the changes
                                             String updatedLyrics = lyricsTextArea.getText();
                                             updatedLyrics = updatedLyrics.replaceAll("(?m)^[ \t]*\r?\n", "");
                                             try (PrintWriter writer = new PrintWriter(m1.getlyrics())) {
                                                 writer.write(updatedLyrics);
                                             } catch (FileNotFoundException e1) {
                                                 // Handle file not found exception
                                                 e1.printStackTrace();
                                             }
                                             
                                             // TODO: Save the updated lyrics to the database or file
                                             JOptionPane.showMessageDialog(contentPane, "Changes saved successfully", "Lyrics", JOptionPane.INFORMATION_MESSAGE);
                                         } else if (option == JOptionPane.NO_OPTION) {
                                             // Discard the changes
                                             JOptionPane.showMessageDialog(contentPane, "Changes discarded", "Lyrics", JOptionPane.INFORMATION_MESSAGE);
                                         }
                                         dialog.dispose();
                                     }
                                 });
                             } else {
                                 // Lyrics not available, prompt the user to input lyrics
                                 JTextArea lyricsTextArea = new JTextArea();
                                 lyricsTextArea.setLineWrap(true);
                                 lyricsTextArea.setWrapStyleWord(true);

                                 JScrollPane scrollPane = new JScrollPane(lyricsTextArea);
                                 scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                                 JOptionPane.showMessageDialog(contentPane, scrollPane, "Enter Lyrics", JOptionPane.PLAIN_MESSAGE);

                                 int option = JOptionPane.showConfirmDialog(contentPane, "Save lyrics?", "Confirmation",
                                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                 if (option == JOptionPane.YES_OPTION) {
                                     // User clicked Yes, save the lyrics in the database
 		                            	 String inputLyrics = lyricsTextArea.getText();                                        
 
 		                            	  // Save lyrics in a text file inside the specified folder
                                     String fileName = "src/main/java/Resources/" + m1.getName() + " (text).txt";
                                     
                                     try (PrintWriter writer = new PrintWriter(fileName)) {
                                         writer.write(inputLyrics);
                                     } catch (FileNotFoundException e1) {
                                         // Handle file not found exception
                                         e1.printStackTrace();
                                     }

                                     controller.setLyrics(m1, fileName);

                                     JOptionPane.showMessageDialog(contentPane, "Lyrics saved successfully", "Lyrics",
                                             JOptionPane.INFORMATION_MESSAGE);
                                 }
                             }
                         }
                     }
                 	//Mouse Entered and Exit lyrics button
                 	@Override
                 	public void mouseEntered(MouseEvent e) {
                 		btnNewButton_5.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\lyrics1.png"));
                 	}
                 	@Override
                 	public void mouseExited(MouseEvent e) {
                 		btnNewButton_5.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\lyrics.png"));
                 	}
           });
	       
       //END of IMPORT IMAGE
          
       //Display Timer Label          
	     displayTimer = new JLabel("0:00");
	     GridBagConstraints gbc_displayTimer = new GridBagConstraints();
	     gbc_displayTimer.gridheight = 2;
	     gbc_displayTimer.anchor = GridBagConstraints.SOUTH;
	     gbc_displayTimer.fill = GridBagConstraints.HORIZONTAL;
	     gbc_displayTimer.gridx = 2;
	     gbc_displayTimer.gridy = 0;
	     gbc_displayTimer.insets = new Insets(0,0,6,0);
	     middle.add(displayTimer, gbc_displayTimer);
	     displayTimer.setHorizontalAlignment(SwingConstants.LEFT);
	     displayTimer.setForeground(new Color(128, 128, 128));
	     displayTimer.setFont(new Font("Verdana", Font.PLAIN, 20));
	       
	       //Music Slider 
	       slider = new JSlider();
	       GridBagConstraints gbc_slider = new GridBagConstraints();
	       gbc_slider.fill = GridBagConstraints.BOTH;
	       gbc_slider.insets = new Insets(0, 0, 0, 5);
	       gbc_slider.gridx = 1;
	       gbc_slider.gridy = 1;
	       middle.add(slider, gbc_slider);
	       slider.setOpaque(false);
	       slider.setValue(0);
	       slider.setUI(new CustomSliderUI(slider, thumbColor, trackColor));
	       //Slider mouse entered and exited
	       slider.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseEntered(MouseEvent e) {
	        		Color thumbColor = Color.DARK_GRAY; 
	              slider.setUI(new CustomSliderUI(slider, thumbColor, trackColor));
	        		
	        	}
	       	@Override
	       	public void mouseExited(MouseEvent e) {
	       		Color thumbColor = Color.WHITE;   
	              slider.setUI(new CustomSliderUI(slider, thumbColor, trackColor));
	       	}
	        });
           
	       //Music Image Frame
 	       JLabel lblNewLabel_1 = new JLabel("");
 	       lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
 	       lblNewLabel_1.setBounds(355, 78, 299, 233);
 	       lblNewLabel_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\frame.png"));
 	       contentPane.add(lblNewLabel_1);
 	      
 	      // Customize the slider colors
 	     Color thumbColor1 = Color.WHITE; // Change this to your desired thumb color
 	     Color trackColor1 = Color.gray; // Change this to your desired track color
	     
	     JPanel volumePanel = new JPanel();
	     volumePanel.setBounds(300, 493, 375, 46);
	     volumePanel.setOpaque(false);
	     contentPane.add(volumePanel);
	     GridBagLayout gbl_volumePanel = new GridBagLayout();
	     gbl_volumePanel.columnWidths = new int[]{35, 200, 0};
	     gbl_volumePanel.rowHeights = new int[]{38, 0};
	     gbl_volumePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
	     gbl_volumePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	     volumePanel.setLayout(gbl_volumePanel);
 	      
 	      //Volume Button
	     volumeButton = new JButton("...");
	     GridBagConstraints gbc_volumeButton = new GridBagConstraints();
	     gbc_volumeButton.anchor = GridBagConstraints.NORTH;
	     gbc_volumeButton.fill = GridBagConstraints.HORIZONTAL;
	     gbc_volumeButton.insets = new Insets(5, 5, 0, 5);
	     gbc_volumeButton.gridx = 0;
	     gbc_volumeButton.gridy = 0;
	     volumePanel.add(volumeButton, gbc_volumeButton);
	     volumeButton.setContentAreaFilled(false);
	     volumeButton.setBorderPainted(false);
	     volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\vol.png"));
	     volumeButton.addMouseListener(new MouseAdapter() {
	    	 @Override
	    	 public void mouseClicked(MouseEvent e) {
	    		if(isMuted) {
	    			volumeSlider.setValue(volumeLevelSave);
	    			isMuted = false;
	    			volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\vol1.png"));
	    		}else {
	    			volumeLevelSave = (int) (volumeLevel*100);
	    			System.out.println();
		    		volumeSlider.setValue(0);
		    		isMuted = true;
		    		volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\mute1.png"));
	    		}
	    	 }
	     	@Override
	     	public void mouseEntered(MouseEvent e) {
	     		if(isMuted) {
	     			volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\mute1.png"));
	     		}else {
	     			volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\vol1.png"));
	     		}
	     	}
	     	@Override
	     	public void mouseExited(MouseEvent e) {
	     		if(isMuted) {
	     			volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\mute.png"));
	     		}else {
	     			volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\vol.png"));
	     		}
	     	}
	     });
 	      
 		     
 		     
 	      //volume slider
 	      volumeSlider = new JSlider();
 	      GridBagConstraints gbc_volumeSlider = new GridBagConstraints();
 	      gbc_volumeSlider.anchor = GridBagConstraints.SOUTHWEST;
 	      gbc_volumeSlider.gridx = 1;
 	      gbc_volumeSlider.gridy = 0;
 	      gbc_volumeSlider.insets = new Insets(0,0,5,5);
 	      volumePanel.add(volumeSlider, gbc_volumeSlider);
 	      volumeSlider.setVisible(true);
 	      volumeSlider.setOpaque(false);
 	      volumeSlider.setUI(new CustomSliderUI(volumeSlider, thumbColor1, trackColor1));
 	      //Slider mouse entered and exited
 	      volumeSlider.addMouseListener(new MouseAdapter() {
 	       	@Override
 	       	public void mouseEntered(MouseEvent e) {
 	       		Color thumbColor1 = Color.DARK_GRAY; 
 	       		volumeSlider.setUI(new CustomSliderUI(volumeSlider, thumbColor1, trackColor1));
 	       		
 	       	}
 	      	@Override
 	      	public void mouseExited(MouseEvent e) {
 	      		Color thumbColor1 = Color.WHITE;   
 	      		volumeSlider.setUI(new CustomSliderUI(volumeSlider, thumbColor1, trackColor1));
 	      	}
 	       });
 	      
 	      volumeSlider.addChangeListener(new ChangeListener() {
	       	  public void stateChanged(ChangeEvent e) {
	 	 	       	int volume = volumeSlider.getValue();
	 	 	        volumeLevel = volume / 100.0; // Convert the slider value to a double from 0.0 to 1.0
	 	 	        if (mediaPlayer != null) {
	 	 	        	mediaPlayer.setVolume(volumeLevel); // Update the MediaPlayer's volume
	 	 	        }
	 	 	        if(volumeLevel == 0) {
	 	 	        	volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\mute.png"));
	 	 	        }else {
	 	 	        	volumeButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\vol.png"));
	 	 	        }
	 	 	  }  	
 	 	  });
 	      volumeLevel = volumeSlider.getValue() / 100.0;
           
             //END OF LYRICS BUTTON
         
             
       //First Music when launch//
  		MusicEntity m1 = controller.findSong(songNum);
  		if(m1 != null) {
  			albumCover(m1); 
  			if(m1.getDuration() != null) {
  	    		displayTimer.setText(m1.getDuration());
  	    	}
  	    	if(m1.getModifiedName() != null) {
  	    		songname.setText(m1.getModifiedName());
  	    	}else {
  	    		songname.setText(m1.getName());
  	    	}
  		}
  		    
    	
    	  
	    //Import music      
	    JButton btnNewButton = new JButton("...");
	    btnNewButton.setBounds(0, 0, 35, 25);
	    btnNewButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		JFileChooser fileChooser = new JFileChooser();
	    		
	    		// Set file filter to accept only MP3 files
	            FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
	            fileChooser.setFileFilter(filter);
	    		
	            int result = fileChooser.showOpenDialog(contentPane);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                // User selected a file
	            	File selectedFile = fileChooser.getSelectedFile();
	                String fileName = selectedFile.getName();
	                // Perform the desired operations with the selected file
	                
	                //Get relative file path
	                String baseFolder = System.getProperty("user.dir");
                    String destinationFolder = baseFolder + "/src/main/java/Resources/Songs";
                    String destinationPath = destinationFolder + "/" + fileName;
                    
                    if(controller.importMusicEntity(selectedFile, destinationPath, fileName) == false) {
                    	JOptionPane.showMessageDialog(contentPane, "The file you are trying to import is already imported" , "ERROR",
    					        JOptionPane.ERROR_MESSAGE);
                    }
	            }
	       }
    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		btnNewButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\import1.png"));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		btnNewButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\import.png"));
	    	}
	    });
	    btnNewButton.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\import.png"));
	    btnNewButton.setContentAreaFilled(false);
	    btnNewButton.setBorderPainted(false);
	    
	       //loop music button
	       JButton btnNewButton_2 = new JButton("...");
	       
	       btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop.png"));
	       btnNewButton_2.setContentAreaFilled(false);
	       btnNewButton_2.setBorderPainted(false);
	       
	       //loop music enter, exit, clicked
	       btnNewButton_2.addMouseListener(new MouseAdapter() {
	           	@Override
	           	public void mouseClicked(MouseEvent e) {
	           		if(repeat == true) {
	           			repeat = false;
	           			btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop1.png"));
	           		}else {
	           			repeat = true;
	           			btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop.png"));
	           		}
	           		
	           	}
	           	@Override
	           	public void mouseEntered(MouseEvent e) {
	           		btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop2.png"));
	           	}
	           	@Override
	           	public void mouseExited(MouseEvent e) {
	           		if(repeat == true) {
	           			btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop1.png"));
	           		}else {
	           			btnNewButton_2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\loop.png"));
	           		}
	           	}
	           });
	       
	 // Play Button
 	    JButton button3 = new JButton("...");
 	    button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\play.png"));
 	    button3.setContentAreaFilled(false);
 	    button3.setBorderPainted(false);
 	    
 	    //play button when clicked
 	    button3.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
             	if(!playing) {//if music not yet playing
	 	               MusicEntity m1 = controller.findSong(songNum);
	 	               
	 	               if(m1 == null) {
							//error handler for no music in database
							JOptionPane.showMessageDialog(contentPane, "The music player is empty. Import files first!" , "ERROR",
	   					        JOptionPane.ERROR_MESSAGE);
						}else {
							playMusic("src/main/java/Resources/Songs/"+m1.getName(),songNum);
							playing = true;  
		 	               	button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause.png"));
						}
 	               	
             	
             	}else {//if music is playing
                 pauseResumeMusic();
                 if(paused) {
 	           			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\play1.png"));
 	           		}else {
 	           			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause1.png"));
 	           		}

             	}
             }
             //mouse entered and exit play/pause/resume button
         	@Override
         	public void mouseEntered(MouseEvent e) {
         		
         		if(paused) {
         			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\play1.png"));
         		}else {
         			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause1.png"));
         		}
         	}
         	@Override
         	public void mouseExited(MouseEvent e) {
         		
         		if(paused) {
         			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\play.png"));
         		}else {
         			button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause.png"));
         		}
         	}
         }); 
	    
 	 //previous music button  
	       JButton btnNewButton_1_1 = new JButton("...");
	       btnNewButton_1_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\prev.png"));
	       btnNewButton_1_1.setContentAreaFilled(false);
	       btnNewButton_1_1.setBorderPainted(false);
	       //previous music button when clicked  
	         btnNewButton_1_1.addMouseListener(new MouseAdapter() {
	             	@Override
	             	public void mouseClicked(MouseEvent e) {
						songNum--;
						//checks if music ID exist
						if (songNum <= 0) {
						    Query query = controller.createQuery("SELECT MAX(m.id) FROM MusicEntity m");
						    Integer maxId = (Integer) query.getSingleResult();
						    if (maxId != null) {
						        songNum = maxId;
						    }
					}
					
				MusicEntity m1 = controller.findSong(songNum);
				if(m1 == null) {
					//error handler for no music in database
					JOptionPane.showMessageDialog(contentPane, "The music player is empty. Import files first!" , "ERROR",
					        JOptionPane.ERROR_MESSAGE);
				}else {
	                     playMusic("src/main/java/Resources/Songs/"+m1.getName(),songNum);
	                     button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause.png"));
	                     paused = false;
	                     albumCover(m1);
	                     
				}
	           }
	             	@Override
	             	public void mouseEntered(MouseEvent e) {
	             		btnNewButton_1_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\prev1.png"));
	             	}
	             	@Override
	             	public void mouseExited(MouseEvent e) {
	             		btnNewButton_1_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\prev.png"));
	             	}
	         });
 	    
	       //next music button
	       JButton btnNewButton_1 = new JButton("...");      
	       btnNewButton_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\next.png"));
	       btnNewButton_1.setContentAreaFilled(false);
	       btnNewButton_1.setBorderPainted(false);
	       
	       // END OF IMPORT MUSIC//
	         
		  // STOP BUTTON
		JButton button2 = new JButton("...");
		button2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\stop.png"));
		button2.setContentAreaFilled(false);
		button2.setBorderPainted(false);
		
		//DELETE Music   
		deleteBtn = new JButton("...");
		deleteBtn.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\delete.png"));
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		
		//delete button when clicked
		deleteBtn.addMouseListener(new MouseAdapter() {
           	@Override
           	public void mouseClicked(MouseEvent e) {
           	    MusicEntity m1 = controller.findSong(songNum);
           		
           	    if (m1 == null) {
           	        JOptionPane.showMessageDialog(contentPane, "No music selected", "ERROR",
           	                JOptionPane.ERROR_MESSAGE);
           	    } else {
           	        int confirmDelete = JOptionPane.showConfirmDialog(contentPane,
           	                "Are you sure you want to delete " + m1.getName() + "?");
           	        if (confirmDelete == JOptionPane.YES_OPTION) {
           	            if(controller.deleteSong(m1)) {
           	            	JOptionPane.showMessageDialog(contentPane, m1.getName() + " has been deleted", "Success",
               	                    JOptionPane.DEFAULT_OPTION);
           	            }
           	        }
           	    }
           	}
           	//delete button when entered or exited
           	@Override
           	public void mouseEntered(MouseEvent e) {
           		deleteBtn.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\delete1.png"));
           	}
           	@Override
           	public void mouseExited(MouseEvent e) {
           		deleteBtn.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\delete.png"));
           	}
           });
		
		//when stop button is clicked, entered, and exited
		button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopMusic();
                playing = false;
                
            }
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		button2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\stop1.png"));
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		button2.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\stop.png"));
        	}
        });
	       
	       //next button clicked
	       btnNewButton_1.addMouseListener(new MouseAdapter() {
	           	@Override
	           	public void mouseClicked(MouseEvent e) {
					songNum++;
					//checks if music ID exist
					MusicEntity m1 = controller.findSong(songNum);
	    				if(m1 == null) {
	    					songNum = 1;
	    				}
	    			//check again in case database is empty	
	    			m1 = controller.findSong(songNum);
					if(m1 == null) {
						//error handler for no music in database
						JOptionPane.showMessageDialog(contentPane, "The music player is empty. Import files first!" , "ERROR",
    					        JOptionPane.ERROR_MESSAGE);
					}else {
	                   playMusic("src/main/java/Resources/Songs/" + m1.getName(),songNum);
	                   button3.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\pause.png"));
	                   
	                   albumCover(m1);
	                   paused = false;
					}
	    	                            	                   		
	           	}
	           	//Mouse entered and exited next music button
	           	@Override
	           	public void mouseEntered(MouseEvent e) {
	           		btnNewButton_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\next1.png"));
	           	}
	           	@Override
	           	public void mouseExited(MouseEvent e) {
	           		btnNewButton_1.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\next.png"));
	           	}
	       });
	    
	    
	    //Music Player Background Image
	    JLabel lblNewLabel = new JLabel(""); 
	    lblNewLabel.setIcon(new ImageIcon("src\\main\\java\\Resources\\icons\\bg.png"));
	    lblNewLabel.setBounds(-11, 0, 999, 590);
	    contentPane.add(lblNewLabel);
    	
	  
	    //Adding all components to JPanel MusicMenu
	    musicMenu.add(btnNewButton);
	    musicMenu.add(btnNewButton_2);
	    musicMenu.add(btnNewButton_1_1);
	    musicMenu.add(button3);
	    musicMenu.add(btnNewButton_1);
	    musicMenu.add(button2);
	    musicMenu.add(deleteBtn);
	    
    } //End of MusicPlayer()

	//Plays the music
    private void playMusic(String filePath, int songId) {
        stopMusic(); // Stop any currently playing music
        slider.setValue(0);
        
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            Duration duration = mediaPlayer.getMedia().getDuration();
            double durationInSeconds = duration.toSeconds();
            timeSec = (int) Math.round(durationInSeconds%60);
            timeMin = (int) durationInSeconds/60;

            if (timeMin < 10) {
                timeDuration = String.format("%d:%02d", timeMin, timeSec);
            } else {
                timeDuration = String.format("%02d:%02d", timeMin, timeSec);
            }
            
         // Update slider maximum value based on music duration
            slider.setMaximum((int) durationInSeconds);
            
            slider.addMouseMotionListener(new MouseMotionAdapter() {
            	@Override
            	public void mouseDragged(MouseEvent e) {
            		if (mediaPlayer != null) {
            			if(paused == true) {
            				
            				int sliderValue = slider.getValue();
                            Duration seekTime = Duration.seconds(sliderValue);
                            mediaPlayer.seek(seekTime);
                            timer.cancel();
                            min = (int)seekTime.toSeconds() /60;
                            sec = (int)seekTime.toSeconds() %60;
                            sec10 = (int)sec / 10;
                            sec = (int)sec % 10;
                            displayTimer.setText((min+":"+sec10+sec+"/"+timeDuration));
            			}else {
            				int sliderValue = slider.getValue();
                            Duration seekTime = Duration.seconds(sliderValue);
                            mediaPlayer.seek(seekTime);
                            timer.cancel();
                            min = (int)seekTime.toSeconds() /60;
                            sec = (int)seekTime.toSeconds() %60;
                            sec10 = (int)sec / 10;
                            sec = (int)sec % 10;
                            musicTime();
            			}
            			
                        
                    }
            	}
            });
            
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (!slider.getValueIsAdjusting()) {
                    double currentTime = newValue.toSeconds();
                    slider.setValue((int) currentTime);
                }
            });
            
            //setting Duration in Database
            MusicEntity m1 = controller.addDuration(songId, timeDuration);
    		
    		//reset variables
    		sec = 0;
    		sec10 = 0;
    		min = 0;
    		
    		if (timer!=null) {
    		timer.cancel();
    		}
    		musicTime();
    		if(m1.getModifiedName() == null) {
    			songname.setText(m1.getName());
    		}else {
    			songname.setText(m1.getModifiedName());
    		}
    		
    		songname.setEditable(true);
    		songname.setBorder(BorderFactory.createEmptyBorder());
            songname.setBackground(null);	
    		
        });
        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
            	if (repeat) {
                    // If loopMusic is true, replay the music
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                    sec = 0;
            		sec10 = 0;
            		min = 0;
            		
            		if (timer!=null) {
            		timer.cancel();
            		}
            		musicTime();
                    
                } else {
                    // If loopMusic is false, stop the music
                    stopMusic();
    				songNum++;
    				//checks if music ID exist
    				MusicEntity m1 = controller.findSong(songId);
        				if(m1 == null) {
        					songNum=1;
        				}
        			m1 = controller.findSong(songId);	
                    playMusic("src/main/java/Resources/"+m1.getName(),songNum);
                    
                    albumCover(m1);
                    paused = false;     
                }
            }
        });
        mediaPlayer.play();
        mediaPlayer.setVolume(volumeLevel);
        
    }
    //end of playMusic()
    
    //stops the music
    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
            if (timer!=null) {
        		timer.cancel();
        		}
        }
    }
    //end of stopMusic()
    
    //pauses and resumes the music 
    private void pauseResumeMusic(){
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                if (timer != null) {
                    timer.cancel();
                    paused = true;
                }
                
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                mediaPlayer.setVolume(volumeLevel);
                paused = false;
                musicTime();
            }
        }
    }//end of pauseResume music
}//end of class MusicPlayer