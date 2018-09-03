package controller;

import acctMgr.model.Model;
import view.View;
import view.*;
import acctMgr.model.*;


public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}