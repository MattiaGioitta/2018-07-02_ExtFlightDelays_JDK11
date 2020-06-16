package it.polito.tdp.extflightdelays.model;

public class Vicino implements Comparable<Vicino> {

	private Airport a;
	private double distanza;
	/**
	 * @param a
	 * @param distanza
	 */
	public Vicino(Airport a, double distanza) {
		super();
		this.a = a;
		this.distanza = distanza;
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
	 * @return the distanza
	 */
	public double getDistanza() {
		return distanza;
	}
	/**
	 * @param distanza the distanza to set
	 */
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return (int) (o.distanza-this.distanza);
	}
	@Override
	public String toString() {
		return a.getAirportName() + ", distanza=" + distanza;
	}
	
	
}
