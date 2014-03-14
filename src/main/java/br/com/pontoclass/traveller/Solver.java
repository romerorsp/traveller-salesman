package br.com.pontoclass.traveller;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;


public interface Solver {

	public Answer solve() throws TravellerSalesmanException;

}