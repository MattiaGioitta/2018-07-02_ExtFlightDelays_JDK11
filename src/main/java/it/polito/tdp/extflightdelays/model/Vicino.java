package it.polito.tdp.extflightdelays.model;

public class Vicino implements Comparable<Vicino>{
	
	private Airport vicino;
	private double peso;
	/**
	 * @param vicino
	 * @param peso
	 */
	public Vicino(Airport vicino, double peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}
	/**
	 * @return the vicino
	 */
	public Airport getVicino() {
		return vicino;
	}
	/**
	 * @param vicino the vicino to set
	 */
	public void setVicino(Airport vicino) {
		this.vicino = vicino;
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
		return  vicino + ", peso=" + peso ;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return Double.compare(o.getPeso(), this.peso);
	}
	
	

}
