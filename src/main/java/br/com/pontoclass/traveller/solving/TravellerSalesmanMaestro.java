package br.com.pontoclass.traveller.solving;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;

public class TravellerSalesmanMaestro implements Maestro {

	private SquaredMatrixMapping	mapping;
	private final Map<Integer, List<Player>> steps;
	private final Map<Integer, List<Player>> rounds;
	private final List<Player> players;
	private int actualRound = 0;
	private Player	winner;
	private final List<Player> tbremoved = new ArrayList<>();

	public TravellerSalesmanMaestro(SquaredMatrixMapping mapping) {
		this.mapping = mapping;
		this.players = new ArrayList<>();
		this.steps = new HashMap<>();
		this.rounds = new HashMap<>();
		createPlayers();
	}

	private void createPlayers() {
		List<String> dots = new ArrayList<>(mapping.getDots());
		int len = dots.size();
		List<Player> roundList = new ArrayList<>(len);
		List<Player> stepList = new ArrayList<>(len);
		Player p = new Player(this, dots, mapping.getInicialDot());
		players.add(p);
		stepList.add(p);
		roundList.add(p);
		steps.put(0, stepList);
		rounds.put(0, roundList);
	}

	@Override
	public Answer solve() throws TravellerSalesmanException {
		BigDecimal bestPosition = BigDecimal.ZERO;
		boolean init = true;
		do {
			List<Player> partialWinners = new ArrayList<>();
			int len;
			do {
				len = partialWinners.size();
				for(int i = actualRound; i >= 0; i--) {
					List<Player> stepped = steps.get(i);
					if(stepped != null && !stepped.isEmpty()) {
						for(Player player: stepped) {
							BigDecimal position = player.stepUp(bestPosition);
							if(position != BigDecimal.ZERO && (init || position.compareTo(bestPosition) < 0)) {
								bestPosition = position;
								partialWinners.add(player);
								init = false;
							}
						}
					}
				}
				for(Player player: tbremoved) {
					this.steps.get(player.getStep()).remove(player);
					this.players.remove(player);
				}
				tbremoved.clear();
			} while(len < partialWinners.size());
			rounds.put(++actualRound, partialWinners);
			init = true;
		} while((!this.hasWinner()) || hasEligiblePlayer(bestPosition));
		return new TravellerSalesmanAnswer(winner, players);
	}

	private boolean hasEligiblePlayer(BigDecimal bestPosition) {
		for(Player player: players) {
			if(player.isEligibleWinner(bestPosition)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasWinner() {
		return this.winner != null;
	}

	@Override
	public BigDecimal getCostFor(String whereIAm, String whereIGo) {
		if(whereIAm == null || whereIGo == null) {
			return BigDecimal.ZERO;
		}
		return mapping.getMatrix()[mapping.getDots().indexOf(whereIAm)][mapping.getDots().indexOf(whereIGo)];
	}

	@Override
	public void registerPlayer(Player player, int step) {
		if(this.steps.containsKey(step)) {
			this.steps.get(step).add(player);
		} else {
			ArrayList<Player> list = new ArrayList<>();
			list.add(player);
			this.steps.put(step, list);
		}
		this.players.add(player);
	}

	@Override
	public void registerWinner(Player player) {
		this.winner = player;
	}

	@Override
	public Player getWinner() {
		return this.winner;
	}

	@Override
	public void unregisterStepper(Player player) {
		this.tbremoved.add(player);
	}
}
