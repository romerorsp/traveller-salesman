package br.com.pontoclass.traveller.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.SquaredMatrixMapping;

public class SquaredMatrixMappingExtractor {
	private static final String ENTRIES_SEPARATOR = ",";

	private static final String	OPEN_SIMBOL	= "(";

	private static final String	CLOSE_SIMBOL	= ")";

	private SquaredMatrixMappingExtractor(){};
	
	private static SquaredMatrixMappingExtractor instance;
	
	private static final Pattern PATTERN_INSTANCE = 
			Pattern.compile(
					String.format("(\\%2$s[a-zA-Z0-9]+\\%3$s|[a-zA-Z0-9]+)(\\ |\\:|\\=|\\-\\>)*\\{((([0-9]+\\.[0-9]+)|\\.[0-9]+|[0-9]+)(\\%1$s(([0-9]+\\.[0-9]+)|\\.[0-9]+|[0-9]+))*)+\\}", ENTRIES_SEPARATOR, OPEN_SIMBOL, CLOSE_SIMBOL));
	
	public synchronized static SquaredMatrixMappingExtractor singleton() {
		if(instance == null) {
			instance = new SquaredMatrixMappingExtractor();
		}
		return instance;
	}
	
	public synchronized SquaredMatrixMapping extract(String... args) throws TravellerSalesmanException {
		int constraint = args.length - 1;
		BigDecimal[][] result = new BigDecimal[constraint + 1][constraint + 1];
		List<String> dots = new ArrayList<>(args.length);
		int i = 0;
		String initial = null;
		boolean hasInitial = false;
		for(String value: args) {
			Matcher matcher = PATTERN_INSTANCE.matcher(value);
			boolean found = false;
			while(matcher.find()) {
				found = true;
				String dot = matcher.group(1);
				if(dot.startsWith(OPEN_SIMBOL)) {
					if(hasInitial) {
						throw new TravellerSalesmanException(String.format("It seems more than one initial route was configured using the '%s%s' notation. Please correct it.", OPEN_SIMBOL, CLOSE_SIMBOL));
					}
					initial = dot;
					hasInitial = true;
				}
				dots.add(dot);
				String[] children = matcher.group(3).split(ENTRIES_SEPARATOR);
				if(children.length != constraint) {
					throw new TravellerSalesmanException(
							String.format(
									"For a %d entries it is mandatory to have %d linking values. Found %d linking values for '%s'. Please correct it.",
									constraint+1, constraint, children.length, dot));
				}
				int j = 0;
				for(String child: children) {
					if(i == j) {
						result[i][j] = BigDecimal.ZERO;
						j++;
					}
					try {
						result[i][j++] = new BigDecimal(child);
					} catch(Exception e) {
						throw new TravellerSalesmanException(e);
					}
				}
				i++;
			}
			if(!found) {
				throw new TravellerSalesmanException(String.format("No pattern was found for Entries [%s].", Arrays.toString(args)));
			}
			result[constraint][constraint] = BigDecimal.ZERO;
		}
		if(!hasInitial) {
			throw new TravellerSalesmanException(String.format("It seems you have not set an initial route using the '%s%s' notation. Please correct it.", OPEN_SIMBOL, CLOSE_SIMBOL));
		}
		return new SquaredMatrixMapping(dots, result, initial);
	}
}