package acctMgr.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import acctMgr.model.Account;
import acctMgr.model.DepositAgent;
import acctMgr.model.*;

public class DepositAgentTest {
	Account acc;
	DepositAgent depAg;
	@Before
	public void setUp() throws Exception {
		acc = new Account("Alice", "58392", 10.45);
		depAg = new DepositAgent(acc, 3.32);
	}

	@After
	public void tearDown() throws Exception {
		acc = null;
		depAg = null;
	}

	@Test
	public void testDepositAgent() {
		depAg.run();
		System.out.println("Balance " + acc.getBalance());
		assertEquals(acc.getBalance(), "60.25");
	}

	/*
	@Test
	public void testDepositAgentFail() {
		fail();
	}
	*/
}
