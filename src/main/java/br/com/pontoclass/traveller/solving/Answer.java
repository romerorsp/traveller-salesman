package br.com.pontoclass.traveller.solving;

import br.com.pontoclass.traveller.Route;


public interface Answer {

	public Route getBestRoute();

	public Route[] getTriedRoutes();
}
