package com.group6;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //class as entity
@Table (name="music") //mapped to a specific table in DB
public class MusicEntity{
	
	@Id //primary key
	private int id;
	private String name;
	private String filePath;
	private String duration;
	private String modifiedName;
	private String lyrics;
	private String imagePath;
	
	public MusicEntity(int id, String name, String filePath, String duration, String modifiedName, String lyrics, String imagePath) {
		super();
		this.id = id;
		this.name = name;
		this.filePath = filePath;
		this.duration = duration;
		this.modifiedName = modifiedName;
		this.lyrics = lyrics;
		this.imagePath = imagePath;
	}
	
	public MusicEntity() {
	
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
			this.id = id;
	}
	
	public String getfilePath() {
		return filePath;
	}
	
	public void setfilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDuration() {
		return duration;
	}
	
	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
	public String getModifiedName() {
		return modifiedName;
	}
	
	public void setlyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public String getlyrics() {
		return lyrics;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePath() {
		return imagePath;
	}

}
