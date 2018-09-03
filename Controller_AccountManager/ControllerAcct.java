package controller;

import view.AccountListView;
import view.ViewAgnt;

import javax.swing.SwingUtilities;
 

import acctMgr.model.Account;
import acctMgr.model.Agent;
import acctMgr.model.AgentCreate;
import acctMgr.model.OverdrawException;

public class ControllerAcct extends ControllerAbst {

	/********************************
	 * Determines if user chose to deposit or withdraw
	 * from buttons in account window 
	 * pre:option must be chosen 
	 * post: account window or agent window is run 
	 * @param opt
	 **********************************/
	public void operation(String opt) {
		if(opt == AccountListView.Deposit) {
			double amount = ((AccountListView)getView()).getAmount();
			((Account)getModel()).deposit(amount);
		} else if(opt == AccountListView.Withdraw) {
			double amount = ((AccountListView)getView()).getAmount();
			try {
				((Account)getModel()).withdraw(amount);
			}
			catch(OverdrawException ex) {
				final String msg = ex.getMessage();
				SwingUtilities.invokeLater(new Runnable() {
				      public void run() {
				    	  ((AccountListView)getView()).showError(msg);
				      }
				    });
			}
		} else if(opt == AccountListView.StartDepAgent) {
			final AccountListView acView = (AccountListView)getView();
			double amount = acView.getAmount();
			final Agent ag = AgentCreate.createDepAgent(((Account)getModel()), amount);
			final ControllerAgnt agContr = new ControllerAgnt();
			
			agContr.setModel(ag);
			SwingUtilities.invokeLater(new Runnable() {
			      public void run() {
			    	  acView.createViewAgnt(ag, agContr);
			      }
			    });
			
		} else if(opt == AccountListView.StartWithdrawAgent) {
			final AccountListView acView = (AccountListView)getView();
			double amount = acView.getAmount();
			final Account accnt = (Account)getModel();
			final Agent ag = AgentCreate.createWithdrawAgent(((Account)getModel()), amount);
			final ControllerAgnt agContr = new ControllerAgnt();
			agContr.setModel(ag);
			SwingUtilities.invokeLater(new Runnable() {
			      public void run() {
			    	  ViewAgnt app = acView.createViewAgnt(ag, agContr);
			    	  accnt.addModelListener(app);
			      }
			});
		}
	}
}