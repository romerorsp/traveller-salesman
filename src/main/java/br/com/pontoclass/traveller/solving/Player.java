package br.com.pontoclass.traveller.solving;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.pontoclass.traveller.Route;

class Player implements Route {

	private final String	whereIAm;
	private final List<? extends String>	dots;
	private final String	whereIGo;
	private final List<String> routeOfMine;
	private final Maestro	maestro;
	private final List<Player> children = new ArrayList<>();
	private final List<String> childrenTrack = new ArrayList<>();
	private BigDecimal	totalCost = BigDecimal.ZERO;
	private int	stepOfMine;

	public Player(Maestro maestro, List<? extends String> dots, String whereIGo) {
		this.maestro = maestro;
		this.whereIAm = null;
		this.dots = dots;
		this.whereIGo = whereIGo;
		this.stepOfMine = 0;
		this.routeOfMine = new ArrayList<>();
	}

	public Player(Maestro maestro, List<? extends String> dots, List<String> routeOfMine, String whereIAm, String whereIGo, BigDecimal totalCost, int stepOfMine) {
		this.maestro = maestro;
		this.dots = dots;
		this.routeOfMine = routeOfMine;
		this.whereIAm = whereIAm;
		this.whereIGo = whereIGo;
		this.totalCost = totalCost;
		this.stepOfMine = stepOfMine;
	}

	public BigDecimal stepUp(BigDecimal bestPosition) {
		BigDecimal partialBestCost = BigDecimal.ZERO;
		if(bestPosition.compareTo(totalCost) < 0) {
			return partialBestCost;
		}
		if(childrenTrack.size()+1 >= dots.size()) {
			return partialBestCost;
		}
		final List<String> routeOfChildren = new ArrayList<>(routeOfMine);
		routeOfChildren.add(whereIGo);
		if(routeOfChildren.size() == dots.size()) {
			String dot = routeOfChildren.get(0);
			BigDecimal helperCost = totalCost.add(maestro.getCostFor(whereIGo, dot));
			Player child = new Player(maestro, dots, routeOfChildren, whereIGo, dot, helperCost, stepOfMine+1);
			children.add(child);
			childrenTrack.add(dot);
			maestro.registerPlayer(child, child.getStep());
			if(maestro.getWinner() == null || helperCost.compareTo(maestro.getWinner().getTotalWayCost()) < 0) {
				maestro.registerWinner(child);
			}
			return totalCost = helperCost;
		}
		for(String dot: dots) {
			if(!(childrenTrack.contains(dot) || routeOfChildren.contains(dot))) {
				BigDecimal helperCost = totalCost.add(maestro.getCostFor(whereIGo, dot));
				if((partialBestCost == BigDecimal.ZERO || partialBestCost.compareTo(helperCost) > 0)) {
					partialBestCost = helperCost;
				}
				Player child = new Player(maestro, dots, routeOfChildren, whereIGo, dot, helperCost, stepOfMine+1);
				children.add(child);
				childrenTrack.add(dot);
				maestro.registerPlayer(child, child.getStep());
			}
		}
		if(childrenTrack.size() + routeOfChildren.size() >= dots.size()) {
			maestro.unregisterStepper(this);
		}
		return partialBestCost;
	}

	int getStep() {
		return stepOfMine;
	}

	public boolean isEligibleWinner(BigDecimal bestPosition) {
		BigDecimal cost = maestro.getCostFor(whereIAm, whereIGo);
		return cost == BigDecimal.ZERO? false: bestPosition.compareTo(totalCost.add(cost)) >= 0;
	}

	@Override
	public String getWayString() {
		StringBuilder builder = new StringBuilder();
		if(routeOfMine == null || routeOfMine.size() == 0) {
			return "";
		}
		if(routeOfMine.size() == 1) {
			return String.format("%s->%s", whereIAm, whereIGo);
		}
		for(String way: routeOfMine) {
			builder.append(way);
			builder.append("->");
		}
		return builder.append(whereIGo).toString();
	}

	@Override
	public BigDecimal getTotalWayCost() {
		return totalCost;
	}
	
	@Override public String toString() {
		return getWayString();
	}
}