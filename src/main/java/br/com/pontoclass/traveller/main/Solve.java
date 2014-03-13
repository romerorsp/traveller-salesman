package br.com.pontoclass.traveller.main;

public class Solve {
	public static void main(String[] args) {
		Solver solver = SolverBuilder.createBuilder().withMapping(args).build();
		boolean fail = false;
		try {
			Answer answer = solver.solve();
		} catch (TravellerSalesmanException e) {
			String explaination = e.getExplaination();
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
