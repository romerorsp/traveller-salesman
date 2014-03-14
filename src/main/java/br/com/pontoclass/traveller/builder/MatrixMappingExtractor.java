package br.com.pontoclass.traveller.builder;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pontoclass.traveller.exception.TravellerSalesmanException;

class MatrixMappingExtractor {
	private static final String ENTRIES_SEPARATOR = ",";

	private MatrixMappingExtractor(){};
	
	private static MatrixMappingExtractor instance;
	
	private static final Pattern PATTERN_INSTANCE = 
			Pattern.compile(
					String.format("([a-zA-Z0-9]+)(\\ |\\:|\\=|\\-\\>)*\\{(([0-9]*(\\.[0-9]+)?)(\\%1$s([0-9]*(\\.[0-9]+)?))*)+\\}", ENTRIES_SEPARATOR));
	
	public synchronized static MatrixMappingExtractor singleton() {
		if(instance == null) {
			instance = new MatrixMappingExtractor();
		}
		return instance;
	}
	
	public synchronized BigDecimal[][] extract(String... args) throws TravellerSalesmanException {
		int constraint = args.length - 1;
		BigDecimal[][] result = new BigDecimal[constraint + 1][constraint + 1]; 
		for(String value: args) {
			Matcher matcher = PATTERN_INSTANCE.matcher(value);
			while(matcher.find()) {
				String dot = matcher.group(1);
				String[] children = matcher.group(3).split(ENTRIES_SEPARATOR);
				if(children.length != constraint) {
					throw new TravellerSalesmanException(
							String.format(
									"For a %d entries it is mandatory to have %d linking values. Found %d linking values for '%s'. Please correct it.",
									constraint+1, constraint, children.length, dot));
				}
				for(String child: children) {
					
				}
			}
		}
		return null;
	}
}