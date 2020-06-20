package it.polito.tdp.extflightdelays.model;

public class Vicino implements Comparable<Vicino>{
	private Airport a;
	private double peso;
	/**
	 * @param a
	 * @param peso
	 */
	public Vicino(Airport a, double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}
	/**
	 * @return the a
	 */
	public Airport getA() {
		return a;
	}
	/**
	 * @param a the a to set
	 */
	public void setA(Airport a) {
		this.a = a;
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
	@Override
	public String toString() {
		return a.getAirportName() + ", peso=" + peso;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return Double.compare(this.peso, o.getPeso());
	}
	

}
