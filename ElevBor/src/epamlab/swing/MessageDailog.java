package epamlab.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;



import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MessageDailog extends JPanel  {

	private  static JTextArea textArea;
	
	public static void writeMessage(String message){
		textArea.append(message);
		textArea.append("\n");
	}
	

	public MessageDailog() {
		Dimension size=new Dimension(300,25);
		setLayout(new BorderLayout());
		 textArea = new JTextArea(300,25);
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		JScrollPane scrol=new JScrollPane(textArea);
		add( scrol,BorderLayout.CENTER);	
		setSize(size);

		
	}

	

}
