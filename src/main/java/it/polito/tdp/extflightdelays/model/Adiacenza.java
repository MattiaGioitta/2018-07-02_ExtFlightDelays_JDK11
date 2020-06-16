package it.polito.tdp.extflightdelays.model;

public class Adiacenza {

	private Airport a1;
	private Airport a2;
	private int distanza;
	/**
	 * @param a1
	 * @param a2
	 * @param distanza
	 */
	public Adiacenza(Airport a1, Airport a2, int distanza) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.distanza = distanza;
	}
	/**
	 * @return the a1
	 */
	public Airport getA1() {
		return a1;
	}
	/**
	 * @param a1 the a1 to set
	 */
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	/**
	 * @return the a2
	 */
	public Airport getA2() {
		return a2;
	}
	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	/**
	 * @return the distanza
	 */
	public int getDistanza() {
		return distanza;
	}
	/**
	 * @param distanza the distanza to set
	 */
	public void setDistanza(int distanza) {
		this.distanza = distanza;
	}
	
	
}
