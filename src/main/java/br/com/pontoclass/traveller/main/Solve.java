package br.com.pontoclass.traveller.main;

import br.com.pontoclass.traveller.Answer;
import br.com.pontoclass.traveller.Route;
import br.com.pontoclass.traveller.Solver;
import br.com.pontoclass.traveller.builder.SolverBuilder;
import br.com.pontoclass.traveller.exception.TravellerSalesmanException;

public class Solve {
	public static void main(String[] args) {
		Solver solver = SolverBuilder.createBuilder().withMapping(args).build();
		boolean fail = false;
		Answer answer = null;
		try {
			answer = solver.solve();
		} catch (TravellerSalesmanException e) {
			String explanation = e.getExplanation();
			fail = true;
		}
		if(fail) {
			
		} else {
			Route best = answer.getBestRoute();
			String bestWayString = best.getWayString();
			double totalValue = best.getTotalWayCost();
			Route[] tried = answer.getTriedRoutes();
		}
	}
}