package com.group6;

import java.util.EventObject;

public class FormEvent extends EventObject {
	private int card;
	
	public FormEvent(Object source) { super(source);}
	
	public void setCard(int card) {
		this.card = card;
	}
	
	public int getCard() {
		return card;
	}
}
