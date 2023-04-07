package gui;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import converter.CsvToJson;

public class Gui {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int SIDEBAR_WIDTH = 200;
	
	
	private JFrame frame;
	private JButton chooseFile, convertFile;
	private JScrollPane scrollpane;
	private JPanel buttons;
	private JLabel buttonsTitle;
	

	public Gui() {
		frame = new JFrame("Calendar App");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		addFrameContent();
		frame.pack();
	}

	private void addFrameContent() {
		
		createHtmlPane();
		
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setPreferredSize(new Dimension(SIDEBAR_WIDTH, HEIGHT));
		
		buttonsTitle = new JLabel("Sidebar", SwingConstants.CENTER);
		buttonsTitle.setPreferredSize(new Dimension(SIDEBAR_WIDTH,25));
		buttons.add(buttonsTitle);
		
		chooseFile = new JButton("Choose File");
		chooseFile.addActionListener(e -> chooseFileAction());
		buttons.add(chooseFile);
		
		convertFile = new JButton("Convert File");
		convertFile.addActionListener(e -> convertFileAction());
		buttons.add(convertFile);
		

		frame.add(scrollpane, BorderLayout.CENTER);
		frame.add(buttons, BorderLayout.WEST);
	}
	
	public void createHtmlPane() {
		JEditorPane jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		URL url = Gui.class.getResource("test.html");

		try {
			jEditorPane.setPage(url);
		} catch (IOException e) {
			jEditorPane.setContentType("text/html");
			jEditorPane.setText("<html>Page not found.</html>");
		}

		scrollpane = new JScrollPane(jEditorPane);
		scrollpane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void chooseFileAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
//			new CsvToJson(selectedFile.getAbsolutePath(), "resources/json/teste3.json");
//			showMessageDialog(frame, "File Converted");
			//TODO convert cvs to html
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

	
	private void convertFileAction() {
		showMessageDialog(frame, "File Converted");
	}


	private void open() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Gui app = new Gui();
		app.open();
	}
}