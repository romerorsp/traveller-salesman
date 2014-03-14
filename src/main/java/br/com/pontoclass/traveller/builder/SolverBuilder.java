package br.com.pontoclass.traveller.builder;

import java.math.BigDecimal;

import br.com.pontoclass.traveller.Solver;

public class SolverBuilder {
	private BigDecimal[][] mapping;

	private SolverBuilder() {}

	public static SolverBuilder createBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	public SolverBuilder withMapping(String... args) {
		this.mapping = MatrixMappingExtractor.singleton().extract(args);
		return this;
	}

	public Solver build() {
		// TODO Auto-generated method stub
		return null;
	};
	
	
}