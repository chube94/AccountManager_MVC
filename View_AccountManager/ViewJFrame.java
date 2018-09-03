package view;

import javax.swing.JFrame;

import acctMgr.model.Model;
import acctMgr.model.ModelAbstr;
import acctMgr.model.ModelListener;
import controller.Controller;
import javax.swing.*;
import acctMgr.model.*;
import controller.*;

public abstract class ViewJFrame extends JFrame implements View, ModelListener {
	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;


	public ViewJFrame (Model model, Controller controller){
		setTitle(AccountListView.GetAcctName() + "....Acct: "+ Integer.toString(AccountListView.GetAcctNumb()));
		setModel(model);
		setController(controller);
	}

	public void registerWithModel(){
		((ModelAbstr)model).addModelListener(this);
	}
	public void unregisterWithModel(){
		((ModelAbstr)model).removeModelListener(this);
	}

	public Controller getController(){
		return controller;
	}
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	public Model getModel(){return model;}
	
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}
}