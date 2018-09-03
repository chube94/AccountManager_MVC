package acctMgr.model;
 

import javax.swing.SwingUtilities;

public class Account extends ModelAbstr {
	private double balance;
	
	
	public Account(double balance){
		this.balance = balance;
	}
	
	
	public Account(String string, String string2, double d) {
		// TODO Auto-generated constructor stub
	}


	public double getBalance(){
		return balance;
	}
	
/**********************************************
 * Determines synchronization of deposit
 * pre: amount is passed
 * post: redundant post acknowledgments
 * @param amount
 **********************************************/
	public synchronized void deposit(double amount) {
		balance += amount;
		final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance, AgentStatus.NA);
		SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		notifyAll();
	}
	
	/**************************************
	 * Determines synchronization of withdraw
	 * @param amount
	 * @throws OverdrawException
	 ************************************/
	public synchronized void withdraw(double amount) throws OverdrawException {
		double newB = balance - amount;
		if(newB < 0.0) throw new OverdrawException(newB);
		balance = newB;
		final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance, AgentStatus.NA);
		
		SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
	}
	
	/***********************************************
	 * Determines synchronization of agent withdraw to
	 * prevent overdraw
	 * pre: amount automatically withdrawn 
	 * post: blocks withdraw agent before overdraw is reached
	 * @param amount
	 * @param ag
	 **************************************************/
	public synchronized void autoWithdraw(double amount, Agent ag) {
		try {
			System.out.println("Trying to withdraw " + amount + " from balance " + balance);
			
			while(this.balance - amount < 0.0) {
				ag.setStatus(AgentStatus.Blocked);	
				wait();
			}
			if(ag.getStatus() == AgentStatus.Paused) return;
			ag.setStatus(AgentStatus.Running);
					
			this.balance -= amount;
			final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, this.balance, AgentStatus.Running);
			SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		}
		catch(InterruptedException ex){
			System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
		}
	}
}