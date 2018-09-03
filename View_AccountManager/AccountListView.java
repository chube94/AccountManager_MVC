/*******************************************************
 * *****************************************************
 * MUST PUT .TXT FILE YOU WISH TO READ INTO IN THE ReadFile 
 * METHOD AT THE BOTTOM OF THIS (AccountListView.java) FILE
 * *******DOES NOT PROMPT USER FROM COMMAND LINE********
 *******************************************************
 *******************************************************
 *******************************************************/



package view;

import controller.ControllerAgnt;
import controller.Controller;
import controller.ControllerAcct;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import acctMgr.model.Account;
import acctMgr.model.Agent;
import acctMgr.model.AgentCreate;
import acctMgr.model.Model;
import acctMgr.model.ModelEvent;

import javax.swing.JOptionPane;

import java.io.*;
import javax.swing.*;
import view.AccountListView.*;
import controller.*;
import java.util.*;
import acctMgr.model.*;
import java.awt.event.*;

public class AccountListView extends ViewJFrame {
	private static final long serialVersionUID = 1L;
	public final static String Deposit = "Deposit";
	public final static String Withdraw = "Withdraw";
	public final static String StartDepAgent = "Deposit Agent Start";
	public final static String StartWithdrawAgent = "Withdraw Agent Start";
	private JPanel topPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel balanceLabel;
	private JLabel amountLabel;
	
	//////////////////////////////
	private JLabel amountDollarLabel; 
	private JLabel amountEurosLabel; 
	private JLabel amountYenLabel; 
    //////////////////////////////
	
	private JTextPane balanceField;
	private JTextPane amountField;
	private JButton depButton;
	private JButton withdrawButton;
	private JButton startDepAgentButton;
	private JButton startWithdrawAgentButton;
	
	private Handler handler = new Handler();
	private List<ControllerAgnt> agentContrs = new ArrayList<ControllerAgnt>(10);
	
	public static int acctNumb;
	public static String acctName;
	public static double acctAmnt;
	
	
/****************************************************
 * Setting up Account Panel 
 * @param model
 * @param controller
 ****************************************************/
	private AccountListView(Model model, Controller controller){
		super(model, controller);
		this.getContentPane().add(getContent());
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.5f);
	    int y = (int) ((dim.getHeight() - this.getHeight()) * 0.5f);
	    this.setLocation(x, y);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent evt) {
		    	for(ControllerAgnt agContr : agentContrs) agContr.operation(ViewAgnt.Dismiss);
		    	AgentCreate.finishThreads();
		        dispose();
		        System.exit(0);
		    }
		});
		pack();
	}
	
/**************************************************
* Setting up panel 
* @return topPanel
****************************************************/
	private JPanel getContent() {
		if (topPanel == null) {
			topPanel = new JPanel();
			GridLayout layout = new GridLayout(2, 1);
			topPanel.setLayout(layout);
			GridBagConstraints ps = new GridBagConstraints();
			ps.gridx = 0;
			ps.gridy = 0;
			ps.fill = GridBagConstraints.HORIZONTAL;
			
			GridBagConstraints bs = new GridBagConstraints();
			bs.gridx = 0;
			bs.gridy = 1;
			topPanel.add(getTextFieldPanel(), null);
			topPanel.add(getButtonPanel(), null);
		}
		return topPanel;
	}
	
	private JButton getDepButton(){
		if(depButton == null){
			depButton = new JButton(Deposit);
			depButton.addActionListener(handler);
		}
		return depButton;
	}
	

	private JButton getWithdrawButton(){
		if(withdrawButton == null){
			withdrawButton = new JButton(Withdraw);
			withdrawButton.addActionListener(handler);
		}
		return withdrawButton;
	}

	private JButton getDepAgentButton(){
		if(startDepAgentButton == null){
			startDepAgentButton = new JButton(StartDepAgent);
			startDepAgentButton.addActionListener(handler);
		}
		return startDepAgentButton;
	}
	
	private JButton getWithdrawAgentButton(){
		if(startWithdrawAgentButton == null){
			startWithdrawAgentButton = new JButton(StartWithdrawAgent);
			startWithdrawAgentButton.addActionListener(handler);
		}
		return startWithdrawAgentButton;
	}
	
/**********************************************
* Setting up button panel
* @return buttonPanel
***********************************************/
	private JPanel getButtonPanel()
	{
		if(buttonPanel == null){
			GridBagConstraints depButtonCtr = new GridBagConstraints();
			depButtonCtr.gridx = 0;
			depButtonCtr.gridy = 0;
			
			GridBagConstraints wButtonCtr = new GridBagConstraints();
			wButtonCtr.gridx = 1;
			wButtonCtr.gridy = 0;
			
			GridBagConstraints depAgButtonCtr = new GridBagConstraints();
			depAgButtonCtr.gridx = 2;
			depAgButtonCtr.gridy = 0;
			
			GridBagConstraints wAgButtonCtr = new GridBagConstraints();
			wAgButtonCtr.gridx = 3;
			wAgButtonCtr.gridy = 0;
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getDepButton(), depButtonCtr);
			buttonPanel.add(getWithdrawButton(), wButtonCtr);
			buttonPanel.add(getDepAgentButton(), depAgButtonCtr);
			buttonPanel.add(getWithdrawAgentButton(), wAgButtonCtr);
		}
		
		return buttonPanel;
	}
	
	private JPanel getTextFieldPanel()
	{
		if(textPanel == null){
			GridBagConstraints bl = new GridBagConstraints();
			bl.gridx = 0;
			bl.gridy = 0;
			
			GridBagConstraints bf = new GridBagConstraints();
			bf.gridx = 1;
			bf.gridy = 0;
			
			GridBagConstraints aml = new GridBagConstraints();
			aml.gridx = 0;
			aml.gridy = 1;
			
			GridBagConstraints amf = new GridBagConstraints();
			amf.gridx = 1;
			amf.gridy = 1;
			
			textPanel = new JPanel();
			textPanel.setLayout(new GridBagLayout());
			textPanel.add(getBalanceLabel(), bl);
			textPanel.add(getBalanceField(), bf);
			textPanel.add(getAmountLabel(), aml);
			textPanel.add(getAmountField(), amf);
			
		}
		return textPanel;
	}
	

	private JLabel getBalanceLabel(){
		if(balanceLabel == null){
			balanceLabel = new JLabel();
			balanceLabel.setText("Account Balance:");
			balanceLabel.setPreferredSize(new Dimension(300, 40));
		}
		return balanceLabel;
	}
	

	private JTextPane getBalanceField(){
		if(balanceField == null){
			balanceField = new JTextPane();
			balanceField.setText(Double.toString(((Account)getModel()).getBalance()));
			balanceField.setSize(200, 40);
			balanceField.setEditable(false);
		}
		return balanceField;
	}
	

	private JLabel getAmountLabel(){
		if(amountLabel == null){
			amountLabel = new JLabel();
			amountLabel.setText("Amount in $:");
			amountLabel.setPreferredSize(new Dimension(300, 40));
		}
		return amountLabel;
	}
	
/*******************************************
* Displays amount to be adjusted 
* @return amountField
*******************************************/
	private JTextPane getAmountField(){
		if(amountField == null){
			amountField = new JTextPane();
			amountField.setText(Double.toString(((Account)getModel()).getBalance()*0.01));
			amountField.setSize(200, 40);
			amountField.setEditable(true);
		}
		return amountField;
	}
	
/***********************************
* Amount to be adjusted 
* @return amount 
************************************/
	public double getAmount() {
		double amount = 0;
		amount = Double.parseDouble(amountField.getText());
		return amount;
	}
	
	public void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
/************************************
* Displays Balance field 
* @param ModelEvent me	
*************************************/
	public void modelChanged(ModelEvent me){
		if(me.getKind() == ModelEvent.EventKind.BalanceUpdate) {
			System.out.println("Balance field to " + me.getBalance());
			balanceField.setText(Double.toString(me.getBalance()));
		}
	}
	
/**********************************************
* Sets up the Agent View
* @param ag
* @param agContr
* @return viewAgent app
************************************************/
	public ViewAgnt createViewAgnt(Agent ag, ControllerAgnt agContr){
		ViewAgnt app = new ViewAgnt(ag, agContr);
  	  	agContr.setView(app);
  	  	agentContrs.add(agContr);
  	  	app.setVisible(true);
  	  	return app;
	}
	
/*********************************************
*Determines what buttons are pushed
**********************************************/
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((ControllerAcct)getController()).operation(evt.getActionCommand());
		}
	}
	
/*********************************************
* MAIN: Runs program 
* @param args
**********************************************/
	public static void main(String[] args) {
//		Scanner in = new Scanner(System.in);
//		System.out.println("What is the filename?");
//		String input = in.nextLine();
//		File file = new File(input);
		ReadFile(args);
		final Account account = new Account(acctAmnt);
		final ControllerAcct contr = new ControllerAcct();
		contr.setModel(account);
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	    	  AccountListView app = new AccountListView(account, contr);
	    	  contr.setView(app);
	    	  app.setVisible(true);
	      }
	    });
	  }
	
	
/**************************************************
* Method to read AccountFile.txt with account
* numbers, names, and amounts instead of
* prompting user to input file name 
* post: file contents read into Accounts Array
* @param acct
***************************************************/
	public static void ReadFile(String[] acct) {
		
		FileInputStream fis = null; 
		
		try {
			fis = new FileInputStream("C:/AccountFile.txt"); 
			DataInputStream in = new DataInputStream(fis); 
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String accounts[] = new String[100];

			int i = 0;
			String strLine;
			while ((strLine = br.readLine()) != null) {
				accounts[i] = strLine;
				i++;
			}
			in.close();
			
			OptionPane(accounts);
		}
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}	
	}
	
	
	
/**********************************************
* Account choice pane is created
* pre: Accounts Array utilized
* post: Account chosen by user and split into
* number, name and amount 
* @param strLine
***********************************************/
	public static void OptionPane(String[] strLine) {		
		String input = (String) JOptionPane.showInputDialog(null, "Please Choose Account: ",
				"Account Choice Window", JOptionPane.WARNING_MESSAGE, null, strLine, strLine[0]);
		String[] parts = input.split(" ");
		acctNumb = Integer.parseInt(parts[0]);
		acctName = parts[1];
		acctAmnt = Double.parseDouble(parts[2]);
	}
	
/******* Returns account number chosen by user******/
	public static int GetAcctNumb() {
		return acctNumb;
	}
	
/****** Returns account name chosen by user ********/
	public static String GetAcctName() {
		return acctName;
	}
	
	 
}