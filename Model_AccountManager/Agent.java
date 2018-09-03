package acctMgr.model;

//Sets up agent class
public interface Agent extends Model {
	

	public void setName(String name);
	public String getName();
	public double getTransferred();
	public void onPause();
	public void onResume();
	public Account getAccount();
	public void setStatus(AgentStatus agSt);
	public AgentStatus getStatus();
	public void finish();
}