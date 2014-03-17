package test.br.com.pontoclass.traveller.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.pontoclass.traveller.builder.SquaredMatrixMappingExtractor;
import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.SquaredMatrixMapping;

public class MatrixMappingExtractorTest {

	private BigDecimal[][]	expected;

	@Before
	public void setUp() {
		expected = new BigDecimal[][] {
				{BigDecimal.ZERO, new BigDecimal("437.7"), new BigDecimal("40"), new BigDecimal(".2")},
				{new BigDecimal("437.7"), BigDecimal.ZERO, new BigDecimal("437.9"), new BigDecimal("477.7")},
				{new BigDecimal("40"), new BigDecimal("437.7"), BigDecimal.ZERO, new BigDecimal("40.2")},
				{new BigDecimal(".2"), new BigDecimal("437.9"), new BigDecimal("40.2"), BigDecimal.ZERO}
		};
	}
	
	@Test 
	public void testSingleton() {
		Assert.assertTrue(SquaredMatrixMappingExtractor.singleton() == SquaredMatrixMappingExtractor.singleton());
	}

	@Test
	public void testExtract() {
		try {
			SquaredMatrixMapping result = SquaredMatrixMappingExtractor.singleton().extract("(SaoPaulo)->{437.7,40,.2}", 
																						    "Curitiba->{437.7,437.9,477.7}",
																						    "Osasco->{40,437.7,40.2}",
																						    "StaEnfigenia->{.2,437.9,40.2}");
			Assert.assertEquals(expected.length, result.getMatrix().length);
			Assert.assertEquals(expected[0].length, result.getMatrix()[0].length);
			for(int i = 0; i < expected.length; i++) {
				Assert.assertArrayEquals(expected[i], result.getMatrix()[i]);
			}
		} catch (TravellerSalesmanException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=TravellerSalesmanException.class)
	public void testExtractError1() throws TravellerSalesmanException {
		SquaredMatrixMappingExtractor.singleton().extract("(SaoPaulo)->{437.7,40,.}", 
												   "Curitiba->{437.7,437.9,477.7}",
												   "Osasco->{40,437.7,40.2}",
												   "StaEnfigenia->{.2,437.9,40.2}");
	}

	@Test(expected=TravellerSalesmanException.class)
	public void testExtractError2() throws TravellerSalesmanException {
		SquaredMatrixMappingExtractor.singleton().extract("(SaoPaulo)->{437.7,40,.2}", 
												   "Curitiba->{437.7,437.9,477.7}",
												   "Osasco->{40,437.7,40.2}",
												   "StaEnfigenia->{.2,437.9}");
	}

	@Test(expected=TravellerSalesmanException.class)
	public void testExtractError3() throws TravellerSalesmanException {
		SquaredMatrixMappingExtractor.singleton().extract("SaoPaulo->{437.7,40,.2}", 
												   "Curitiba->{437.7,437.9,477.7}",
												   "Osasco->{40,437.7,40.2}",
												   "StaEnfigenia->{.2,437.9}");
	}

	@Test(expected=TravellerSalesmanException.class)
	public void testExtractError4() throws TravellerSalesmanException {
		SquaredMatrixMappingExtractor.singleton().extract("SaoPaulo->{437.7,40,.2}", 
												   "(Curitiba)->{437.7,437.9,477.7}",
												   "(Osasco)->{40,437.7,40.2}",
												   "StaEnfigenia->{.2,437.9}");
	}
	
	@Test
	public void testArrayListBehaviour() {
		List<String> list = new ArrayList<>(6);
		list.add("I");
		list.add("you");
		list.add("he/she/it");
		list.add("we");
		list.add("you");
		list.add("them");
		Assert.assertEquals(6, list.size());
		Assert.assertEquals(list.get(1), list.get(4));
	}

}