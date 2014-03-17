package br.com.pontoclass.traveller.solving;

import java.math.BigDecimal;
import java.util.List;

public class SquaredMatrixMapping {

	private final List<String>	dots;
	private final BigDecimal[][]	matrix;
	private final String	initial;

	public SquaredMatrixMapping(List<String> dots, BigDecimal[][] matrix, String initial) {
		this.dots = dots;
		this.matrix = matrix;
		this.initial = initial;
	}

	public BigDecimal[][] getMatrix() {
		return this.matrix;
	}

	public List<String> getDots() {
		return dots;
	}

	public String getInicialDot() {
		return this.initial;
	}
}
