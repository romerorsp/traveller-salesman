package br.com.pontoclass.traveller.solving;

import java.util.List;

import br.com.pontoclass.traveller.Route;

public class TravellerSalesmanAnswer implements Answer {

	private final Route	best;
	private final List<? extends Route>	tried;

	public TravellerSalesmanAnswer(Route best, List<? extends Route> tried) {
		this.best = best;
		this.tried = tried;
	}
	
	@Override
	public Route getBestRoute() {
		return best;
	}

	@Override
	public Route[] getTriedRoutes() {
		return tried.toArray(new Route[]{});
	}
}
