package controller;

import acctMgr.model.Model;
import view.View;
import view.*;
import acctMgr.model.*;

public abstract class ControllerAbst implements Controller {

	private View view;
	private Model model;
	

	public void setModel(Model model){
		this.model = model;
	}
	

	public Model getModel(){
		return model;
	}
	

	public void setView(View view){
		this.view = view;
	}
	

	public View getView(){
		return view;
	}	
}