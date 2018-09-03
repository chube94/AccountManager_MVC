package acctMgr.model;

import java.util.List;
import java.util.ArrayList;
import java.util.*;

public abstract class ModelAbstr implements Model {
	
	private List<ModelListener> listeners = new ArrayList<ModelListener>(5);
	
	public void addModelListener(ModelListener l){
		listeners.add(l);
	}
	
	public void removeModelListener(ModelListener l){
		listeners.remove(l);
	}
	
	public void notifyChanged(ModelEvent event) {
		for(ModelListener ml : listeners){
			ml.modelChanged(event);
		}
	}
}