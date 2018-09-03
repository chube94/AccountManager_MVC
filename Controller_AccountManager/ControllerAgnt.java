package controller;

import acctMgr.model.Account;
import acctMgr.model.Agent;
import acctMgr.model.WithdrawAgent;
import view.ViewAgnt;


public class ControllerAgnt extends ControllerAbst {
	public void operation(String opt) {
		if(opt == ViewAgnt.Pause) {
			((Agent)getModel()).onPause();

		} else if(opt == ViewAgnt.Resume) {
			((Agent)getModel()).onResume();

		} else if(opt == ViewAgnt.Dismiss) {
			Agent ag = (Agent)getModel();
			ViewAgnt agView = (ViewAgnt)getView();
			
			if(ag instanceof WithdrawAgent) {
				Account account = ag.getAccount();
				account.removeModelListener(agView);
			}
			ag.finish();
			agView.dispose();
		}
	}
}