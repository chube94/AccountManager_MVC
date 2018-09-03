package acctMgr.test;

import acctMgr.model.Account;
import acctMgr.model.DepositAgent;
import java.math.BigDecimal;

public class DepositAgentMainTest {

	public static void main(String[] args) {
		Account acc = new Account("Alice", "58392", 10.45);
		DepositAgent depAg = new DepositAgent(acc, 3.3);
		depAg.run();
		System.err.println("Balance " + acc.getBalance());

	}

}
