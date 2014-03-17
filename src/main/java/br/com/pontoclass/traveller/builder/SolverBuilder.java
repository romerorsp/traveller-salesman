package br.com.pontoclass.traveller.builder;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.Solver;
import br.com.pontoclass.traveller.solving.SquaredMatrixMapping;
import br.com.pontoclass.traveller.solving.TravellerSalesmanMaestro;

public class SolverBuilder {
	private SquaredMatrixMapping mapping;

	private SolverBuilder() {}

	public static SolverBuilder createBuilder() {
		return new SolverBuilder();
	}

	public SolverBuilder withMapping(String... args) throws TravellerSalesmanException {
		this.mapping = SquaredMatrixMappingExtractor.singleton().extract(args);
		return this;
	}

	public Solver build() {
		return new TravellerSalesmanMaestro(mapping);
	};
}