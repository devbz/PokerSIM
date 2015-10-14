package pokerCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.Getter;
import pokerGUI_JavaFX.OddsOutModel;
import pokerSimCore.Card;
import pokerSimCore.CardContainer;
import pokerSimCore.GameStage;
import pokerSimCore.Player;
import pokerSimCore.Table;

/** Calculator class:
 * Calculates the odds of the available combinations in Texas Hold'em by brute force. 
 * Injected with a Table object and a Player to figure out the perspective of the calculation. 
 * */
public class Calculator {
	
	private Player player;
	private Table table;
	// private HashMap<CombinationRanks, Integer> outsCount;
	private @Getter LinkedHashMap<String, String> results;
	private HashMap<CombinationRanks, ArrayList<Set<Card>>> rawOuts;
	private int outsCounter;
			
//	Calculator(Table) Ctor only to be used for statistic purposes:
	public Calculator (Table t) {
		this.table = t;
		
		results = new LinkedHashMap<>();
		results.clear();
		doCalculation(t);
	}

	public Calculator(Player p, Table t) {
		this.player = p;
		this.table = t;
		// outsCount = new HashMap<>();
		results = new LinkedHashMap<>();
		results.clear();
		calculate();
		return;
	}
	
	private RecursiveCalculator doCalculation(CardContainer c) {
		RecursiveCalculator powerCalc = new RecursiveCalculator(c);
		powerCalc.calc();
		outsCounter = powerCalc.getCheckCounter();
		rawOuts = powerCalc.getOuts();
		return powerCalc;
	}
	
	private void calculate() {
		//TODO needs separate thread
		results.put("No", "info");
		
		if (	table.getStage() == GameStage.FLOP ||
				table.getStage() == GameStage.TURN) 
		{
			results.clear();
			results.put("Your odds are:","");
			results.put("COMBINATION", "CHANCE");
			
			RecursiveCalculator powerCalc = doCalculation(new Hand(player, table));
			formatOuts(powerCalc.getCheckCounter(), powerCalc.getOuts());
		} else if (table.getStage() == GameStage.RIVER) {
			results.clear();
			results.put("You have a:", new Hand(player, table).getHandRanker().getHighestMade().toString());
			results.put("Your opponents", "have these odds");
			results.put("COMBINATION", "CHANCE");
			
			RecursiveCalculator powerCalc = doCalculation(table);
			formatOuts(powerCalc.getCheckCounter(), powerCalc.getOuts());
		} else {
			results.clear();
			results.put("Cannot", "calculate");
		}
	}
	
	/** formatOuts formats the targeted outs Map:
	 * - in order of the combination ranks
	 * - calculates percentages (with the totalOuts)
	 * - removes the combinations with 0% chance.
	 * The result is stored in the results class field
	 * @param the total number of outs as processed by the calculator, for calculating the percentages
	 * @param target outs Map
	 * */
	private void formatOuts(int totalOuts, HashMap<CombinationRanks, ArrayList<Set<Card>>> outsToFormat) {
		LinkedHashMap<CombinationRanks, Collection<Set<Card>>> sortedOutsCards = 
				new LinkedHashMap<>(sortOutsCardsByValueSize(outsToFormat));
		
		for(CombinationRanks cr: sortedOutsCards.keySet()) {
			if (!(sortedOutsCards.get(cr).size() == 0)) {
				results.put(
						cr.toString() + " ",
						String.format("%.02f",
								(double) (sortedOutsCards.get(cr)).size()
										/ totalOuts * 100)
								+ "%");
			}
		}
	}
	
	/** Sorts an outs collection by the size of the cards making them up */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LinkedHashMap<CombinationRanks, Collection<Set<Card>>> 
		sortOutsCardsByValueSize(HashMap<CombinationRanks, ArrayList<Set<Card>>> outsCardsToSort) 
		{ 
		LinkedList<Set<Card>> list = new LinkedList(outsCardsToSort.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Collection) ((Map.Entry) (o2)).getValue()).size() - 
						((Collection) ((Map.Entry) (o1)).getValue()).size();
			}
		});

		LinkedHashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	/** getResults2 gives out a more elaborate form of results, to be interpreted before shown in GUI */
	public ArrayList<OddsOutModel> getResults2() {
		ArrayList<OddsOutModel> newOuts = new ArrayList<>();
		if (rawOuts == null) {
			return newOuts;	//TODO return nice exception
		} else {
			for (Entry<CombinationRanks, ArrayList<Set<Card>>> i: rawOuts.entrySet()) {
				newOuts.add(new OddsOutModel(i.getKey(), 
											((0.0 + i.getValue().size()) / outsCounter),
											new ArrayList<Set<Card>>(i.getValue())));
			}
		}
		return newOuts;
	}
}
