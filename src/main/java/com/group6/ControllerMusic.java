package com.group6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class ControllerMusic {
	static private MusicPlayer musicPlayer;
	
	static EntityManager em;
	static EntityManagerFactory emf;
	
	public ControllerMusic(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
	}
	
	public void createMusicEntity(int id, String name, String filePath, String duration, String modifiedName, String lyrics, String imagePath) {
		emf = Persistence.createEntityManagerFactory("musicdata");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		MusicEntity musicEntity = new MusicEntity();
		
		musicEntity.setId(id);
		musicEntity.setName(name);
		musicEntity.setfilePath(filePath);
		musicEntity.setDuration(duration);
		musicEntity.setModifiedName(modifiedName);
		musicEntity.setlyrics(lyrics);
		musicEntity.setImagePath(imagePath);
		
		em.persist(musicEntity);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	//Import music, returns false if there is duplicate, returns true if successful
	public boolean importMusicEntity(File selectedFile, String destinationPath, String fileName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata"); //creating entity manager factory object
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin(); // Initializing or beginning an entity manager

		
		//Loop that checks the next available primary key (ID) and duplicates
		boolean loop = true;
		int i=1;
		while(loop) {
			MusicEntity m2 = em.find(MusicEntity.class,i);
		
			if(m2 == null) {
				loop=false;
				try {
					// Copy the file to the destination folder
                    Path sourcePath = selectedFile.toPath();
                    Path destinationPathObj = Path.of(destinationPath);
                    Files.copy(sourcePath, destinationPathObj, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
			
				MusicEntity m1 = new MusicEntity();
				m1.setId(i);
				m1.setName(fileName);
				m1.setfilePath(destinationPath);
				em.persist(m1);

		    }else {
		    	i++;
		    	if(m2.getName().equals(fileName)){ //checks for duplicate
					loop = false;
					return false;
		    	}
		    }
		}
		loop = true;
		
		em.getTransaction().commit(); //commits the current transaction. this helps persist changes to database
		em.close(); // closing the transaction
		emf.close(); //Releasing the factory resources
		return true;
	}
	
	
	//Import image, returns false if image is already imported, true if successful
	public boolean importImage(File selectedFile, String destinationPath, String fileName, int songNum, JButton btnNewButton_4) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata"); //creating entity manager factory object
		EntityManager em = emf.createEntityManager();
		
		
		boolean loop = true;
		em.getTransaction().begin(); // Initializing or beginning an entity manager
        
		//Loop that checks duplicates
		int i=1;
		while(loop) {
			MusicEntity m2=em.find(MusicEntity.class,i);
				
			if(m2 == null) {
				loop=false;
				try {
                    // Copy the file to the destination folder
                    Path sourcePath = selectedFile.toPath();
                    Path destinationPathObj = Path.of(destinationPath);
                    Files.copy(sourcePath, destinationPathObj, StandardCopyOption.REPLACE_EXISTING);

      
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
				
				MusicEntity m1 = em.find(MusicEntity.class, songNum);

				m1.setImagePath("src/main/java/Resources/Images/"+fileName);
				em.persist(m1);
				
				btnNewButton_4.setIcon(new ImageIcon(destinationPath));

				
			}else {
				i++;
				if(m2.getImagePath()!=null) {
					if(m2.getImagePath().equals("src/main/java/Resources/Images/"+fileName)){ //checks for duplicate
    					loop = false;
    					
    					return false;
    				}
				}
			}
		}
		loop = true;
		em.getTransaction().commit(); //commits the current transaction. this helps persist changes to database
		em.close(); // closing the transaction
		emf.close(); //Releasing the factory resources
		return true;
	}
	
	//Add Duration to database, returns musicEntity
	public MusicEntity addDuration(int songId, String timeDuration) {
		//setting Duration in Database
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		MusicEntity m1 = em.find(MusicEntity.class,songId);
		
        if(m1.getDuration() == null) {
        	m1.setDuration(timeDuration);
        }
        em.persist(m1);
		em.getTransaction().commit(); //commits the current transaction. this helps persist changes to database
		em.close(); // closing the transaction
		emf.close(); //Releasing the factory resources
		
		return m1;
	}
	
	//Find song
	public MusicEntity findSong(int songNum) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
   	    EntityManager em = emf.createEntityManager();

   	    MusicEntity m1 = em.find(MusicEntity.class, songNum);
   	    
   	    em.close();
	    emf.close();
	    
	    return m1;
	}
	
	//Delete song
	public boolean deleteSong(MusicEntity m1) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
   	    EntityManager em = emf.createEntityManager();
   	    
   	    MusicEntity managedEntity = em.merge(m1);
   	    
		em.getTransaction().begin();
        em.remove(managedEntity); // remove the row for the specified music entity from the database
        em.getTransaction().commit();

        // Update primary keys
        em.getTransaction().begin();
        Query updateQuery = em.createQuery("UPDATE MusicEntity m SET m.id = m.id - 1 WHERE m.id > :deletedId");
        updateQuery.setParameter("deletedId", managedEntity.getId());
        updateQuery.executeUpdate();
        em.getTransaction().commit();
        
        em.close();
	    emf.close();
        
        return true;
	}
	
	public void setLyrics(MusicEntity m1, String fileName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
   	    EntityManager em = emf.createEntityManager();
   	    em.getTransaction().begin();
   	    
   	    MusicEntity existingEntity = em.find(MusicEntity.class, m1.getId());
   	    
		
   	    existingEntity.setlyrics(fileName);
        em.persist(existingEntity);
        em.getTransaction().commit();
        
        em.close();
	    emf.close();
	}
	
	public Query createQuery(String stringQuery) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
   	    EntityManager em = emf.createEntityManager();
   	    
		Query query = em.createQuery(stringQuery);
		
		em.close();
	    emf.close();
		
		return query;
	}
	
	public void setModifiedName(MusicEntity m1, String modifiedName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicdata");
   	    EntityManager em = emf.createEntityManager();
   	    
   	    m1.setModifiedName(modifiedName);
   	    
   	    em.persist(m1);
 		em.getTransaction().commit(); //commits the current transaction. this helps persist changes to database
 		em.close(); // closing the transaction
 		emf.close(); //Releasing the factory resources
   	    
   	    em.close();
	    emf.close();
	}
	
}
