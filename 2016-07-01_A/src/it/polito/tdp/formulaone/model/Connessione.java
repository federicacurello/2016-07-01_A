package it.polito.tdp.formulaone.model;

public class Connessione {
	private Driver vincente;
	private Driver perdente;
	private int peso;
	public Connessione(Driver vincente, Driver perdente, int peso) {
		super();
		this.vincente = vincente;
		this.perdente = perdente;
		this.peso = peso;
	}
	public Driver getVincente() {
		return vincente;
	}
	public void setVincente(Driver vincente) {
		this.vincente = vincente;
	}
	public Driver getPerdente() {
		return perdente;
	}
	public void setPerdente(Driver perdente) {
		this.perdente = perdente;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perdente == null) ? 0 : perdente.hashCode());
		result = prime * result + ((vincente == null) ? 0 : vincente.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (perdente == null) {
			if (other.perdente != null)
				return false;
		} else if (!perdente.equals(other.perdente))
			return false;
		if (vincente == null) {
			if (other.vincente != null)
				return false;
		} else if (!vincente.equals(other.vincente))
			return false;
		return true;
	}
	
	
	

}
