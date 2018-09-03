package acctMgr.model;

import acctMgr.model.ModelEvent.*;
 

public class ModelEvent {
	public enum EventKind {
		BalanceUpdate, AgentStatusUpdate, AmountTransferredUpdate
	}
	private EventKind EvntKind;
	private double bal;
	private AgentStatus agentStat;
	
	public ModelEvent(EventKind MEkind, double MEbal, AgentStatus MEagent){
		this.bal = MEbal;
		this.EvntKind = MEkind;
		this.agentStat = MEagent;
	}
	
	public EventKind getKind(){
		return EvntKind;
	}
	
	public double getBalance(){
		return bal;
	}
	
	public AgentStatus getAgStatus(){return agentStat;}
}