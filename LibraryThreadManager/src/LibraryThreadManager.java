import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class LibraryThreadManager implements ActionListener, ItemListener {
	// declare Swing components as instance variables
	static JFrame outputFrame;
	static JPanel panel1, panel2, panel3;
	static Container container;
	static JLabel label1, label2, label3;
	static JButton button1, button2;
	static JComboBox comboBox;
	static String [] show = {"server", "client1", "client2"};
	static JScrollPane pane;
	static JTable table;
	static DefaultTableModel tableModel;
	static TableRowSorter<TableModel> sorter;
	
	public static void main(String[] args) {
		// Instantiate a LibraryThreadManager object
		LibraryThreadManager libraryThreadManager = new LibraryThreadManager();
		// Instantiate a LibraryServer object
		LibraryServer libraryServer = new LibraryServer();
		// Invoke a GUI method
		libraryThreadManager.LibraryGUI();
	}
	// GUI method
	public void LibraryGUI() {
		outputFrame = new JFrame(); // Instantiate a JFrame object
		outputFrame.setSize(1000,800);
		outputFrame.setTitle("LibraryThreadManager");
		outputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = outputFrame.getContentPane();  
		container.setLayout(new FlowLayout());
		
		panel1 = new JPanel(); // Instantiate a JPanel object
		
		// Instantiate a JLabel object, add it to container and panel
		label1 = new JLabel("Run threads :");
		container.add(label1);
		panel1.add(label1);
		
		button1 = new JButton("Run");
		container.add(button1);
		panel1.add(button1);
		button1.addActionListener(this);
		
		label2 = new JLabel("Show books :");
		container.add(label2);
		panel1.add(label2);
		
		comboBox = new JComboBox(show);
		comboBox.setMaximumRowCount(3);
		container.add( comboBox );
		panel1.add(comboBox);
		comboBox.addItemListener(this);
		
		button2 = new JButton("Show");
		container.add(button2);
		panel1.add(button2);
		button2.addActionListener(this);
		outputFrame.add(panel1);
		
		panel2 = new JPanel();
		label3 = new JLabel();
		container.add(label3);
		panel2.add(label3);
		outputFrame.add(panel2);
		
		panel3 = new JPanel();
		// create a JTable object
		String[] columnNames = {"Title", "Genre", "Price", "Author of Index", "Maximum Number"};
		Object[][] data = null;
		tableModel = new DefaultTableModel(data, columnNames);
		table = new JTable(tableModel);
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		panel3.add(table);
		pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(900, 550));
		panel3.add(pane);
		
		outputFrame.add(panel3);
		outputFrame.setVisible(true);
	}
	// Event handler for JButton events.
	public void actionPerformed(ActionEvent event) {
		tableModel.setRowCount(0); // reset table		
		// Create a thread pool with two threads
		ExecutorService executor = Executors.newFixedThreadPool(2);
		if (event.getSource() == button1) {
			label3.setText("Borrow/return actions being run between a server and two clients." +
					" Each client accesses the server 40 times.");
			executor.execute(new LibraryClient1());
			executor.execute(new LibraryClient2());
			executor.shutdown();
		} else if (event.getSource() == button2) {
			if (comboBox.getSelectedItem() == "server") {
				label3.setText("");
				for(int i=0; i<LibraryServer.showLibraryServerBooks().size(); i++){
					tableModel.addRow(LibraryServer.showLibraryServerBooks().get(i).convertArray());
				}
			} else if (comboBox.getSelectedItem() == "client1") {
				for(int i=0; i<LibraryClient1.showClient1Books().size(); i++){
					tableModel.addRow(LibraryClient1.showClient1Books().get(i).convertArray());
				}
			} else if (comboBox.getSelectedItem() == "client2") {
				for(int i=0; i<LibraryClient2.showClient2Books().size(); i++){
					tableModel.addRow(LibraryClient2.showClient2Books().get(i).convertArray());
				}
			}
		}
	}
	// Event handler for JComboBox events.
	public void itemStateChanged(ItemEvent event) {
		if ( event.getSource() == comboBox) {
			if (event.getStateChange() == ItemEvent.SELECTED) {}
		}
	}	
}
