package it.polito.tdp.extflightdelays.model;

public class Adiacenza {
	
	private Airport a1;
	private Airport a2;
	private double peso;
	/**
	 * @param a1
	 * @param a2
	 * @param peso
	 */
	public Adiacenza(Airport a1, Airport a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
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
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}
