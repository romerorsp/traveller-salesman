package test.br.com.pontoclass.traveller.solving;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.pontoclass.traveller.builder.SolverBuilder;
import br.com.pontoclass.traveller.exception.TravellerSalesmanException;
import br.com.pontoclass.traveller.solving.Answer;
import br.com.pontoclass.traveller.solving.Solver;

public class TravellerSalesmanMaestroTest {

	private Solver	solver;

	@Before 
	public void setUp() throws Exception {
		solver = SolverBuilder.createBuilder().withMapping("(SaoPaulo)->{437.7,40,.2}", 
														   "Curitiba->{437.7,437.9,477.7}",
														   "Osasco->{40,437.7,40.2}",
														   "StaEnfigenia->{.2,437.9,40.2}").build();
	}

	@Test
	public void testSolve() {
		try {
			Answer answer = solver.solve();
			Assert.assertNotNull(answer);
			Assert.assertEquals("(SaoPaulo)->StaEnfigenia->Osasco->Curitiba->(SaoPaulo)", answer.getBestRoute().getWayString());
			Assert.assertEquals(new BigDecimal("915.8"), answer.getBestRoute().getTotalWayCost());
		} catch (TravellerSalesmanException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test 
	public void testGetCostFor() {
		Assert.fail("Not yet implemented");
	}

	@Test 
	public void testRegisterPlayer() {
		Assert.fail("Not yet implemented");
	}
}