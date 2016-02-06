package jp.gr.java_conf.keln;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;

public class KELN extends JPanel implements ActionListener, ItemListener, KeyListener{
	/*
	 * To modify this program for your team,
	 * you have to replace each value of variables.
	 * 
	 */
	
	String[] list_Researcher = {	//Write team members here
			"Sukegawa",
			"Matsumoto",
			"Li",
			"Tsujii",
			"Uchino",
			"Iguchi",
			"Michimori",
			"Notsu",
			"Kaneko",
			"Egashira",
			"Wan",
			"Yamamoto",
			"Yoshida",
			"Kim",
			"Nakamura",
			"Yamada"
	};
	
	String[] list_Experiment = {	//Write experiments here
			"PCR (Target)",
			"PCR (Steps)",
			"Transformation",
			"Colony PCR (1Column)",
			"Colony PCR (2Column)",
			"Liquid Culture",
			"Miniprep",
			"Restriction Enzyme Digestion",
			"Electrophoresis",
			"Gel Extraction",
			"Preparation"
	};
	
	//Write elements of each experiment
	//Note:you have to edit itemStateChanged() function after adding experiment.
	String[] list_PCR_Target = {
			"Templates/(μl)",
			"Primer1/(μl)",
			"Primer2/(μl)",
			"Buffer/(μl)",
			"MilliQ/(μl)",
			"Any Other/(μl)",
			"Total/(μl)"
	};
	String[] list_PCR_Steps = {
			"Steps",
			"Temparature",
			"Time",
			"Cycle"
	};
	String[] list_Transformation = {
			"Sample Name",
			"Sample Volume",
			"Competent Cells/(μl)",
			"Medium"
	};
	String[] list_ColonyPCR_1 = {
			"Name I"
	};
	String[] list_ColonyPCR_2 = {
			"Name I",
			"Name II"
	};
	String[] list_LiquidCulture = {
			"Name",
			"medium"
	};
	String[] list_Miniprep = {
			"DNA",
			"Concentration/(μg/ml)",
			"260/280", 
			"260/230"
	};
	String[] list_RestrictionEnzymeDigestion = {
			"Sample",
			"DNA/(μl)",
			"EcoR1/(μl)",
			"Xba1/(μl)",
			"Spe1/(μl)",
			"Pst1/(μl)",
			"Buffer/(μl)",
			"BSA/(μl)",
			"MilliQ/(μl)",
			"Total/(μl)"
	};
	String[] list_Electrophoresis = {
			"Lane",
			"Restriction Enzyme Digestion Products"
	};
	String[] list_GelExtraction = {
			"Lane",
			"Restriction Enzyme Digestion Product",
			"Volume/(μl)"
	};
	String[] list_Preparation = {
			"Reagent",
			"Liquid"
	};

	//variables
	int Col_Max; //Maximum number of the column will be initialized in constructor.
	final int ROW_MAX = 20; //Maximum number of the row
	String platform; //OS version
	String[] list_Current; //This array stores list_[experiment name]
	
	//GUI
	JTable table;
	JScrollPane scroll_t, scroll_o;
	JTextArea output;
	JButton generate, destroy;
	JCheckBox[] researcher;
	JCheckBox isAllowedOutput;
	JComboBox<String> selector, author;
	JPanel panel_North, panel_Checkbox, panel_Date;
	JTextField text_Month, text_Date;
	JLabel label_Month, label_Date, label_Author;
	
	DefaultTableModel tablemodel;
	DefaultTableColumnModel colmodel;
	
	public KELN(){ //Constructor
		platform = getPlatformName();
		list_Current = list_PCR_Target;
		Col_Max = list_Current.length;
		createTable();
		output = new JTextArea();
		output.setRows(10);
		scroll_o = new JScrollPane(output);
		generate = new JButton("Convert");
		generate.addActionListener(this);
		destroy = new JButton("Clear");
		destroy.addActionListener(this);
		selector = new JComboBox<String>(list_Experiment);
		selector.addItemListener(this);
		author = new JComboBox<String>(list_Researcher);
		researcher = new JCheckBox[list_Researcher.length];
		for(int i = 0; i<list_Researcher.length; i++){
			researcher[i] = new JCheckBox(list_Researcher[i]);
		}
		isAllowedOutput = new JCheckBox("Write data to text", true);
		text_Month = new JTextField();
		text_Date = new JTextField();
		text_Month.setPreferredSize(new Dimension(50, 25));
		text_Date.setPreferredSize(new Dimension(50, 25));
		label_Month = new JLabel("Month");
		label_Date = new JLabel("Day");
		label_Author = new JLabel("Author");
		//Layout
		panel_Date = new JPanel();
		panel_North = new JPanel();
		panel_Checkbox = new JPanel();
		panel_Date.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel_North.setLayout(new BorderLayout());
		panel_Checkbox.setLayout(new GridLayout((int)list_Researcher.length / 7 + 1 , 7));
		for(int i = 0; i<list_Researcher.length; i++){
			panel_Checkbox.add(researcher[i]);
		}
		setLayout(new BorderLayout());
		panel_Date.add(label_Month);
		panel_Date.add(text_Month);
		panel_Date.add(label_Date);
		panel_Date.add(text_Date);
		panel_Date.add(label_Author);
		panel_Date.add(author);
		panel_Date.add(destroy);
		panel_Date.add(isAllowedOutput);
		panel_North.add(panel_Date, BorderLayout.NORTH);
		panel_North.add(panel_Checkbox, BorderLayout.CENTER);
		panel_North.add(scroll_t, BorderLayout.SOUTH);
		add(panel_North, BorderLayout.NORTH);
		add(generate, BorderLayout.CENTER);
		add(scroll_o, BorderLayout.SOUTH);
		add(selector, BorderLayout.WEST);
	}
	
	public void createTable(){
		Col_Max = list_Current.length;
		tablemodel = new DefaultTableModel(list_Current, ROW_MAX);
		table = new JTable(tablemodel);
		table.addKeyListener(this);
		table.setColumnSelectionAllowed(true); //User is allowed to select a single cell
		table.setGridColor(Color.decode("#4682B4"));
		colmodel = (DefaultTableColumnModel)table.getColumnModel();
		scroll_t = new JScrollPane(table);
		scroll_t.setPreferredSize(table.getPreferredSize());
		TableColumn col;
		for(int i=0; i< Col_Max; i++){
			col = colmodel.getColumn(i);
			col.setHeaderValue(list_Current[i]);
		}
	}
	
	public void resetTable(){
		panel_North.remove(scroll_t);
		createTable();
		panel_North.add(scroll_t, BorderLayout.SOUTH);
		revalidate();
	}
	
	public void resetCheckbox(){
		for(int i=0; i<list_Researcher.length; i++){
			researcher[i].setSelected(false);
		}
	}
	
	public void convertStringToHTML(){ //Convert JTable into HTML format
		TableColumn col;
		String out = "";
		
		//Author
		out += "<!-- Table Generated by KELN" + " ";
		out += "Author: " + author.getSelectedItem().toString();
		out += " -->\r\n<div class=\"keln_container\">\r\n";
		//Date
		out += "<a name=\"";
		try{
			if(Integer.parseInt(text_Month.getText().toString()) <= 9){
				out += "0";
			}
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Date is invalid or empty.");
			return;
		}
		out += text_Month.getText().toString();
		try{
			if(Integer.parseInt(text_Date.getText().toString()) <= 9){
				out += "0";
			}
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Date is invalid or empty.");
			return;
		}
		out += text_Date.getText().toString();
		out += "\" class = \"kyoto-jump\"></a>\r\n";
		out += "<span class=\"keln_date\"><h3>";
		out += text_Month.getText().toString() + "/" + text_Date.getText().toString();
		out += "</h3></span>\r\n";
		
		//Experiment name
		out += "<span class=\"keln_exp\"><h4>";
		out += selector.getSelectedItem().toString();
		out += "</h4></span>\r\n";
		
		//Experimenter name
		out += "<span class=\"keln_researcher\">";
		
		int finalindex = 0; //final experimenter
		for(int i=0; i<list_Researcher.length; i++){
			if(researcher[i].isSelected() == true){
				finalindex = i;
			}
		}
		for(int i=0; i<list_Researcher.length; i++){
			if(researcher[i].isSelected() == true){
				out += researcher[i].getText();
				if(i != finalindex){
					out += ", "; //Do not add comma after the last name
				}
			}
		}
		out += "</span>\r\n";
		
		//Table start
		out += "<table class=\"keln_table\">\r\n";
		out += "<tr>";
		
		//Add header
		for(int i=0; i<Col_Max; i++){
			out += "<th>";
			col = colmodel.getColumn(i);
			out += col.getHeaderValue();
			out += "</th>";
		}
		out += "</tr>\r\n";
		
		//Fill the table with data
		for(int i=0; i<ROW_MAX; i++){
			//Stop after detecting an empty row
			int emptyCount = 0;
			for(int j=0; j<table.getColumnCount(); j++){
				if(tablemodel.getValueAt(i, j) == null || tablemodel.getValueAt(i, j).equals("")) emptyCount++ ;
			}
			if(emptyCount >= table.getColumnCount()) break;
			
			out += "<tr>";
			for(int j=0; j<table.getColumnCount(); j++){
				out += "<td>";
				try{
					out += tablemodel.getValueAt(i, j).toString();
				}catch(NullPointerException e){
					out += "";
				}
				out += "</td>";
			}
			out += "</tr>\r\n";
		}
		
		out += "</table>\r\n</div>\r\n";
		out += "<!------------ Table END ------------>";
		out = replaceString(out, "μ", "&micro");
		output.setText(out);
		if(isAllowedOutput.isSelected()){
			saveStringToText(out);
		}
	}
	
	public void saveStringToText(String output){
		Date time = new Date();
		SimpleDateFormat ftime = new SimpleDateFormat("MM_dd_hh_mm_ss");
		String filename = text_Month.getText() + "_" + text_Date.getText() +  "_" + selector.getSelectedItem().toString() + "_" + author.getSelectedItem().toString() + "_" + ftime.format(time) + ".txt";
		String parentdir = System.getProperty("user.dir");
		String fullpath = "";
		if(platform.equals("linux") || platform.equals("mac")){
			parentdir += "/KELN";
			fullpath = parentdir + "/" + filename;
		} else if(platform.equals("windows")){
			parentdir += "\\KELN";
			fullpath = parentdir + "\\" + filename;
		}
		File dir = new File(parentdir);
		if(!dir.exists()){
			dir.mkdir();
		}
		try {
			PrintWriter pw = new PrintWriter(fullpath);
			pw.printf(output);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPlatformName(){
		String name = System.getProperty("os.name").toLowerCase();
		if(name.startsWith("linux")) return "linux";
		else if (name.startsWith("mac")) return "mac";
		else if (name.startsWith("windows")) return "windows";
		else return "unknown";
	}
	
	public String replaceString(String str, String match, String replace){
		String regex = match;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		String result = m.replaceAll(replace);
		return result;
	}
	
	public static void main(String[] args){
		KELN keln = new KELN();
		JFrame frame = new JFrame("KELN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(keln);
		frame.pack();
		frame.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == generate){
			convertStringToHTML();
		}
		if(e.getSource() == destroy){
			resetTable();
			resetCheckbox();
			output.setText("");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == selector && e.getStateChange() == ItemEvent.SELECTED){
			switch (selector.getSelectedItem().toString()) {
			case "PCR (Target)":
				list_Current = list_PCR_Target;
				break;
			case "PCR (Steps)":
				list_Current = list_PCR_Steps;
				break;
			case "Transformation":
				list_Current = list_Transformation;
				break;
			case "Colony PCR (1Column)":
				list_Current = list_ColonyPCR_1;
				break;
			case "Colony PCR (2Column)":
				list_Current = list_ColonyPCR_2;
				break;
			case "Liquid Culture":
				list_Current = list_LiquidCulture;
				break;
			case "Miniprep":
				list_Current = list_Miniprep;
				break;
			case "Restriction Enzyme Digestion":
				list_Current = list_RestrictionEnzymeDigestion;
				break;
			case "Electrophoresis":
				list_Current = list_Electrophoresis;
				break;
			case "Gel Extraction":
				list_Current = list_GelExtraction;
				break;
			case "Preparation":
				list_Current = list_Preparation;
			default:
				break;
			}
			resetTable();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == table){
			if(e.getKeyCode() == KeyEvent.VK_DELETE){
				table.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
				revalidate();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
