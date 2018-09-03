package view;

import controller.ControllerAgnt;
import controller.Controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import acctMgr.model.Agent;
import acctMgr.model.AgentStatus;
import acctMgr.model.Model;
import acctMgr.model.ModelEvent;
import javax.swing.*;
import view.ViewAgnt.*;
import acctMgr.model.*;
import controller.*;
import java.awt.event.*;

public class ViewAgnt extends ViewJFrame {
	private static final long serialVersionUID = 1L;
	public final static String Pause = "Pause";
	public final static String Resume = "Resume";
	public final static String Dismiss = "Dismiss";
	
	private JPanel topPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	
	private JLabel transferredLabel;
	private JLabel statusLabel;
	private JTextPane transferredField;
	private JTextPane statusField;
	
	private JButton pauseButton;
	private JButton resumeButton;
	private JButton dismissButton;
	private Handler handler = new Handler();
	

	public ViewAgnt(Model model, Controller controller){
		super(model, controller);
		this.getContentPane().add(getContent());
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.5f);
	    int y = (int) ((dim.getHeight() - this.getHeight()) * 0.5f);
	    this.setLocation(x - 10, y - 10);
	    
		final Controller contr = controller;
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent evt) {
		         ((ControllerAgnt)contr).operation(Dismiss);
		    }
		});
		this.setTitle(((Agent)model).getName());
		pack();
	}
	

	private JPanel getContent() {
		if (topPanel == null) {
			topPanel = new JPanel();
			GridLayout layout = new GridLayout(4, 1);
			topPanel.setLayout(layout);
			topPanel.add(getTextFieldPanel(), null);
			topPanel.add(getButtonPanel(), null);
		}
		return topPanel;
	}
	

	private JPanel getTextFieldPanel()
	{
		if(textPanel == null){
			GridBagConstraints tl = new GridBagConstraints();
			tl.gridx = 0;
			tl.gridy = 0;
			
			GridBagConstraints tf = new GridBagConstraints();
			tf.gridx = 1;
			tf.gridy = 0;
			
			GridBagConstraints sl = new GridBagConstraints();
			sl.gridx = 0;
			sl.gridy = 1;
			
			GridBagConstraints sf = new GridBagConstraints();
			sf.gridx = 1;
			sf.gridy = 1;
			
			textPanel = new JPanel();
			textPanel.setLayout(new GridBagLayout());
			textPanel.add(getTransferredLabel(), tl);
			textPanel.add(getTransferredField(), tf);
			textPanel.add(getStatusLabel(), sl);
			textPanel.add(getStatusField(), sf);
		}
		return textPanel;
	}
	

	private JPanel getButtonPanel()
	{
		if(buttonPanel == null){
			GridBagConstraints pauseButtonCtr = new GridBagConstraints();
			pauseButtonCtr.gridx = 0;
			pauseButtonCtr.gridy = 0;
			
			GridBagConstraints resButtonCtr = new GridBagConstraints();
			resButtonCtr.gridx = 1;
			resButtonCtr.gridy = 0;
			
			GridBagConstraints dissButtonCtr = new GridBagConstraints();
			dissButtonCtr.gridx = 2;
			dissButtonCtr.gridy = 0;
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getPauseButton(), pauseButtonCtr);
			buttonPanel.add(getResumeButton(), resButtonCtr);
			buttonPanel.add(getDismissButton(), dissButtonCtr);
		}
		
		return buttonPanel;
	}
	

	private JButton getPauseButton(){
		if(pauseButton == null){
			pauseButton = new JButton(Pause);
			pauseButton.addActionListener(handler);
		}
		return pauseButton;
	}
	

	private JButton getResumeButton(){
		if(resumeButton == null){
			resumeButton = new JButton(Resume);
			resumeButton.setEnabled(false);
			resumeButton.addActionListener(handler);
		}
		return resumeButton;
	}
	

	private JButton getDismissButton(){
		if(dismissButton == null){
			dismissButton = new JButton(Dismiss);
			dismissButton.addActionListener(handler);
		}
		return dismissButton;
	}
	

	private JLabel getTransferredLabel(){
		if(transferredLabel == null){
			transferredLabel = new JLabel();
			transferredLabel.setText("Amount Transferred:");
			transferredLabel.setPreferredSize(new Dimension(200, 20));
		}
		return transferredLabel;
	}
	

	private JTextPane getTransferredField(){
		if(transferredField == null){
			transferredField = new JTextPane();
			transferredField.setText(Double.toString(((Agent)getModel()).getTransferred()));
			transferredField.setSize(300, 40);
			transferredField.setEditable(false);
		}
		return transferredField;
	}
	
	
	private JLabel getStatusLabel(){
		if(statusLabel == null){
			statusLabel = new JLabel();
			statusLabel.setText("Status:");
			statusLabel.setPreferredSize(new Dimension(200, 20));
		}
		return statusLabel;
	}
	
	
	private JTextPane getStatusField(){
		if(statusField == null){
			statusField = new JTextPane();
			statusField.setText(AgentStatus.Running.toString());
			statusField.setSize(200, 25);
			statusField.setEditable(false);
		}
		return statusField;
	}
	

	private void setPaused(boolean paused){
		resumeButton.setEnabled(paused);
		pauseButton.setEnabled(!paused);
	}
	
	
	public void modelChanged(ModelEvent me){
		System.out.println("ViewAgnt.modelChanged invoked " + me.getAgStatus().toString());
		ModelEvent.EventKind kind = me.getKind();
		if(kind == ModelEvent.EventKind.AmountTransferredUpdate) {
			transferredField.setText(Double.toString(me.getBalance()));
		}
		else if(kind == ModelEvent.EventKind.AgentStatusUpdate) {
			AgentStatus agSt = me.getAgStatus();
			System.out.println("Status to " + agSt.toString());
			if(agSt == AgentStatus.Paused) setPaused(true);
			else setPaused(false);
			statusField.setText(agSt.toString());
		}
	}
	
	
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((ControllerAgnt)getController()).operation(evt.getActionCommand());
		}
	}	
}