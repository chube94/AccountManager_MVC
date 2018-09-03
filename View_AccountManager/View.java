package view;

import acctMgr.model.Model;
import controller.Controller;
import acctMgr.model.*;
import controller.*;


public interface View {
	Controller getController();
	public void setController(Controller aController);
	public Model getModel();
	public void setModel(Model aModel);
}