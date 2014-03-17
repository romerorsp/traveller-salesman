package br.com.pontoclass.traveller.solving;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;


public interface Solver {

	public Answer solve() throws TravellerSalesmanException;

}