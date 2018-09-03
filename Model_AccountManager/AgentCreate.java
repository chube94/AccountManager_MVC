package acctMgr.model;

import java.util.List;
import java.util.ArrayList;
import java.util.*;
 

public class AgentCreate {
	private static int depositAgent = 0;
	private static int withdrawAgent = 0;
	private static List<Thread> agentThrds = new ArrayList<Thread>(10);
	
	
	/*********************************
	 * starts deposit agent
	 * pre: account and amount passed
	 * post: agent is set up in deposit agent pane
	 * @param account
	 * @param amount
	 * @return
	 ************************************/
	public static Agent createDepAgent(Account account, double amount) {
		DepositAgent depAg = new DepositAgent(account, amount);
		Thread depAgT = new Thread(depAg);
		String name = "Deposit Agent " + depositAgent;
		depAg.setName(name);
		depAgT.setName(name);
		depositAgent++;
		agentThrds.add(depAgT);
		depAgT.start();
		return depAg;
	}
	
	/**********************************
	 * start withdraw agent
	 * @param account
	 * @param amount
	 * @return wAg
	 *************************************/
	public static Agent createWithdrawAgent(Account account, double amount) {
		WithdrawAgent wAg = new WithdrawAgent(account, amount);
		Thread wAgT = new Thread(wAg);
		String name = "Withdraw Agent " + withdrawAgent;
		wAg.setName(name);
		wAgT.setName(name);
		withdrawAgent++;
		agentThrds.add(wAgT);
		wAgT.start();
		return wAg;
	}
	
	
	public static void finishThreads(){
		for(Thread t: agentThrds) {
				System.out.println("Finishing thread " + Thread.currentThread().getName());
				t.interrupt();
			}
	}
}