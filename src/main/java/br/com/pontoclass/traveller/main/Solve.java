package br.com.pontoclass.traveller.main;

import java.math.BigDecimal;

import br.com.pontoclass.traveller.Route;
import br.com.pontoclass.traveller.builder.SolverBuilder;
import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.Answer;
import br.com.pontoclass.traveller.solving.Solver;

public class Solve {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("##### The Traveller Salesman #####");
		SolverBuilder builder = null;
		try {
			builder = SolverBuilder.createBuilder().withMapping(args);
		} catch(TravellerSalesmanException e) {
			System.out.println("ERROR: The entries you typed might have tipo. Please correct it.");
			System.out.println(String.format("EXCEPTION: '%s'", e.getMessage()));
			System.out.println("### End ###");
			System.out.println("bye.");
			System.exit(-1);
		}
		final Solver solver = builder.build();
		System.out.print("Solving...");
		SolverRunner runner = new SolverRunner(solver);
		Thread t = new Thread(runner);
		t.start();
		
		while(!runner.hasFinished()) {
			Thread.sleep(700);
			System.out.print(".");
		}
		System.out.println();
		System.out.println();
		if(runner.hasException()) {
			System.out.println("ERROR: Something went wrong while trying to solve the traveller salesman problem with the given data.");
			System.out.println(String.format("EXCEPTION: '%s'", runner.getException().getMessage()));
			System.out.println("### End ###");
			System.out.println("bye.");
			System.exit(-1);
		}
		Answer answer = runner.getAnswer();
		Route best = answer.getBestRoute();
		String bestWayString = best.getWayString();
		BigDecimal totalValue = best.getTotalWayCost();
		Route[] tried = answer.getTriedRoutes();
		
		System.out.println(String.format("ANSWER: The following solution was found: [%s]", bestWayString));
		System.out.println(String.format("COST: The given solution has the following cost: [%s]", totalValue.toPlainString()));
		System.out.println("TRIES: Below you can see the tried solutions...");
		for(int i = 0; i < tried.length; i++) {
			System.out.println(String.format("\t%d - tried [%s] with %s of cost.", i+1, tried[i].getWayString(), tried[i].getTotalWayCost().toPlainString()));
		}
		
		System.out.println("### End ###");
		System.out.println("bye.");
	}
	
	private static class SolverRunner implements Runnable {

		private Solver solver;
		private Answer answer;
		private TravellerSalesmanException exception;
		private boolean finished;

		public SolverRunner(Solver solver) {
			this.solver = solver;
		}
		
		@Override
		public void run() {
			try {
				this.answer = solver.solve();
			} catch (TravellerSalesmanException e) {
				this.exception = e;
			}
			this.finished = true;
		}
		
		public boolean hasException() {
			return exception != null;
		}
		
		public Exception getException() {
			return this.exception;
		}
		
		public boolean hasFinished() {
			return this.finished;
		}
		
		public Answer getAnswer() {
			return this.answer;
		}
	}
}