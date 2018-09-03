package acctMgr.model;

import java.math.BigDecimal;

public class OverdrawException extends Exception {
	private static final long serialVersionUID = 1L;

	/************************************
	 * provides error notification of an overdrawn account 
	 * pre: amount overdrawn
	 * post: overdrawn message 
	 * @param newB
	 ***********************************/
	OverdrawException(double newB){
		super("Overdrawn by " + newB);
	}
}