package epamlab.swing;

import java.awt.BorderLayout;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import epamlab.Runnable.TransportationTask;

import epamlab.container.Floor;
import epamlab.controllers.AbstractController;



public class MainWindows extends JFrame implements Runnable  {
	private static final Logger LOG=Logger.getLogger(MainWindows.class);
	private Thread mainWindows;
	private List <Floor> floors=new ArrayList<Floor>();
	
	private AbstractController controller;
	
	List<TransportationTask> transportationTask=new ArrayList<TransportationTask>();

	JButton start;
	
	Desktop desktop;
	public MainWindows( List <Floor> floors,AbstractController controller2,List<TransportationTask> transportationTask) throws HeadlessException {		
		super("MainWindows"); 
		Dimension size=new Dimension(900,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 	
		setLayout(new BorderLayout());
		this.setSize(size);
		setPreferredSize(size);
		start = new JButton("START");
		ActionListener headPressStartButton=new HandButtonEvent();
	    start.addActionListener(headPressStartButton);
	    getContentPane().add(start,BorderLayout.SOUTH);
	   Building build=new Building(floors,controller2,start); 
	   JScrollPane scroll=new JScrollPane(build);
		add(scroll, BorderLayout.CENTER);
		scroll.setPreferredSize(new Dimension(100,200));
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    add(new MessageDailog(),BorderLayout.EAST);
	    this.floors=floors;
		this.controller=controller2;
		this.transportationTask=transportationTask;
		mainWindows=new Thread(this);
		mainWindows.start();
		
	}

	class HandButtonEvent implements ActionListener{
         
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if("START".equals(start.getText())){
			LOG.info("STARTING_TRANSPORTATION");
			MessageDailog.writeMessage("STARTING_TRANSPORTATION");
			}
			synchronized(controller){
			controller.notify();	 
			}
			
			if("VIEW LOG FILE".equals(start.getText())){
				  try {
					Runtime.getRuntime().exec("notepad.exe application.log");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				     }
			
		
	     		
			if("ABORT".equals(start.getText())){
				LOG.info("ABORTING_TRANSPORTATION");
				MessageDailog.writeMessage("ABORTING_TRANSPORTATION");
				start.setText("VIEW LOG FILE");
				 for(TransportationTask t:transportationTask){
					 t.stop();
				}
				controller.validation();
				
				 
				
				start.setText("VIEW LOG FILE");
				
				
			}
	
			if("START".equals(start.getText())){
			start.setText("ABORT");
			}
	      }
		
		}
	
		
		
		
		
	
	
	public JButton getStartBotton(){
		return start;
	}
	
	public void run() {
		

		Thread window=Thread.currentThread();
		if (window==mainWindows) {
			

			setVisible(true);
		}
		}
	}


