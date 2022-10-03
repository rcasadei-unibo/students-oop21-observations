package org.observations.utility;

/**
 * Simple enum for first start observations type list 
 */

public enum ObservationsList {
	
	PLAY("gioca con oggetti in suo possesso"),
	
	SPEAK("interviene senza aspettare il proprio turno e in modo non adeguato"),
	
	LOW_ATTENTION("presta poca attenzione ai dettagli"),
	
	DISTRACT("si lascia distrarre da stimoli esterni o intorno a lui"),
	
	STAND_UP("si alza dal banco"),
	
	MAKE_ERROR("compie errori nella scelta dei dati da rielaborare");
	

	private String showText;
	
	private ObservationsList(final String showText) {
		this.showText = showText;
	}

	public String toString() {
		return this.showText;
	}
	
	public String getText() {
		return this.showText;
	}
}
