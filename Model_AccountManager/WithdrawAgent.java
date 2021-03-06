package acctMgr.model;

import javax.swing.SwingUtilities;


public class WithdrawAgent extends ModelAbstr implements Runnable, Agent {
	public volatile boolean isactive;
	private Object pauseLk;
	private volatile boolean pause;
	private Account account;
	private double amount;
	private double transferred;
	private String name = new String("Default");
	private AgentStatus status;
	private volatile boolean blocked;
	

	public WithdrawAgent(Account account, double amount){
		this.isactive = true;
		this.pauseLk = new Object();
		this.pause = false;
		this.account = account;
		this.amount = amount;
		this.transferred = 0;
		this.status = AgentStatus.Running;
		this.blocked = false;	
	}
	

	public void run() {
		while(isactive) {
			synchronized (pauseLk) {
				
                while (pause) {
                    try {
                        pauseLk.wait();
                    } catch (InterruptedException e) {
                    	System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    }
                }
                
            }
			account.autoWithdraw(amount, this);
			this.transferred += amount;
			final ModelEvent me = new ModelEvent(ModelEvent.EventKind.AmountTransferredUpdate, transferred, AgentStatus.NA);
			SwingUtilities.invokeLater(
					new Runnable() {
					    public void run() {
					    	notifyChanged(me);
					    }
					});
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException ex){
				System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
			}
		}
	}
	

	public void onPause() {
        synchronized (pauseLk) {
            pause = true;
            setStatus(AgentStatus.Paused);
        }
    }


    public void onResume() {
        synchronized (pauseLk) {
        	if(blocked) setStatus(AgentStatus.Blocked);
        	else setStatus(AgentStatus.Running);
            pause = false;
            pauseLk.notify();
        }
    }
   

    public void setStatus(AgentStatus agSt) {
    	status = agSt;
    	if(status == AgentStatus.Blocked) blocked = true;
    	if(status == AgentStatus.Running) blocked = false;
    	final ModelEvent me = new ModelEvent(ModelEvent.EventKind.AgentStatusUpdate, 0.0, agSt);
    	SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
    }
    

	public void setName(String name) {
	this.name = name;
	}
	

	public String getName(){
	return name;
	}
	

	public double getTransferred(){
	return transferred;
	}
	

	public AgentStatus getStatus(){
	return status;
	}
	

	public Account getAccount(){
	return account;
	}
	

	public void finish(){
	isactive = false;
	}
}