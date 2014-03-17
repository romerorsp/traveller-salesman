package br.com.pontoclass.traveller.solving;

import java.math.BigDecimal;

interface Maestro extends Solver {

	public BigDecimal getCostFor(String whereIAm, String whereIGo);

	public void registerPlayer(Player child, int step);

	public void registerWinner(Player child);

	public Player getWinner();

	public void unregisterStepper(Player player);
}