package br.com.pontoclass.traveller.main;

import java.math.BigDecimal;

import br.com.pontoclass.traveller.Route;
import br.com.pontoclass.traveller.builder.SolverBuilder;
import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.Answer;
import br.com.pontoclass.traveller.solving.Solver;

public class Solve {
	public static void main(String[] args) {
		boolean fail = false;
		Answer answer = null;
		try {
			Solver solver = SolverBuilder.createBuilder().withMapping(args).build();
			answer = solver.solve();
		} catch (TravellerSalesmanException e) {
			String explanation = e.getMessage();
			fail = true;
		}
		if(fail) {
			
		} else {
			Route best = answer.getBestRoute();
			String bestWayString = best.getWayString();
			BigDecimal totalValue = best.getTotalWayCost();
			Route[] tried = answer.getTriedRoutes();
		}
	}
}