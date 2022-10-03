package org.observations.utility;

/**
 * Simple enum for first start moment list
 */

public enum MomentsList {

	HOUR_1("prima ora"),
	
	HOUR_2("seconda ora"),
	
	HOUR_3("terza ora"), 
	
	HOUR_4("quarta ora"),
	
	LUNCH("pranzo"),
	
	SPORT("ginnastica"),
	
	ITALIAN("italiano"),
	
	MATHEMATICS("matematica"),
	
	SCIENCE("scienze");
	
	private String showText;
	
	private MomentsList(final String showText) {
		this.showText = showText;
	}

	public String toString() {
		return this.showText;
	}
	
	public String getText() {
		return this.showText;
	}
}
