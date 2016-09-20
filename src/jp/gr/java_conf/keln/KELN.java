package jp.gr.java_conf.keln;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KELN extends JPanel implements ActionListener, ItemListener, KeyListener{
	/*
	 * To modify this program for your team,
	 * you have to replace each value of variables in StringResources.java.
	 * 
	 */

	//variables
	int Col_Max; //Maximum number of the column will be initialized in constructor.
	final int ROW_MAX = 20; //Maximum number of the row
	String platform; //OS version
	String[] list_Current; //This array stores list_[experiment name]
	ArrayList<String> list_Researcher = new ArrayList<String>();
	
	//GUI
	JTable table;
	JScrollPane scroll_t, scroll_o;
	JTextArea output;
	JButton generate, destroy;
	JCheckBox[] researcher;
	JCheckBox isAllowedOutput;
	JComboBox<String> selector, author;
	JPanel panel_North, panel_Checkbox, panel_Date, panel_Text;
	JTextField text_Month, text_Date, text_No;
	JLabel label_Month, label_Date, label_No, label_Author;
	JLabel label_Colmn, label_Title;
	JTextField text_Column, text_Title;
	JButton button_Apply;
	JTextArea text_Memo;
	JScrollPane scroll_Memo;
	JLabel label_Output, label_Memo;
	
	DefaultTableModel tablemodel;
	DefaultTableColumnModel colmodel;
	
	public KELN(){ //Constructor
		readJson();
		platform = getPlatformName();
		list_Current = StringResources.list_PCR_Target;
		Col_Max = list_Current.length;
		createTable();
		output = new JTextArea("This is output area.\nYou can add note in right text field.");
		output.setRows(10);
		scroll_o = new JScrollPane(output);
		generate = new JButton("Convert");
		generate.addActionListener(this);
		destroy = new JButton("Clear");
		destroy.addActionListener(this);
		selector = new JComboBox<String>(StringResources.list_Experiment);
		selector.addItemListener(this);
		author = new JComboBox<String>(list_Researcher.toArray(new String[0]));
		researcher = new JCheckBox[list_Researcher.size()];
		for(int i = 0; i<list_Researcher.size(); i++){
			researcher[i] = new JCheckBox(list_Researcher.get(i));
		}
		isAllowedOutput = new JCheckBox("Save", true);
		text_Month = new JTextField();
		text_Date = new JTextField();
		text_No = new JTextField();
		text_Month.setPreferredSize(new Dimension(50, 25));
		text_Date.setPreferredSize(new Dimension(50, 25));
		text_Column = new JTextField();
		text_Title = new JTextField();
		text_Column.setPreferredSize(new Dimension(30, 25));
		text_Title.setPreferredSize(new Dimension(100, 25));
		text_No.setPreferredSize(new Dimension(30, 25));
		label_Month = new JLabel("Month");
		label_Date = new JLabel("Day");
		label_No = new JLabel("No.");
		label_Author = new JLabel("Author");
		label_Colmn = new JLabel("Cols");
		label_Title = new JLabel("Title");
		button_Apply = new JButton("apply");
		button_Apply.addActionListener(this);
		text_Memo = new JTextArea();
		text_Memo.setRows(10);
		scroll_Memo = new JScrollPane(text_Memo);
		scroll_Memo.setPreferredSize(new Dimension(100, 50));
		
		//Layout
		panel_Date = new JPanel();
		panel_North = new JPanel();
		panel_Checkbox = new JPanel();
		panel_Date.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel_North.setLayout(new BorderLayout());
		panel_Checkbox.setLayout(new GridLayout((int)list_Researcher.size() / 7 + 1 , 7));
		for(int i = 0; i<list_Researcher.size(); i++){
			panel_Checkbox.add(researcher[i]);
		}
		panel_Text = new JPanel();
		panel_Text.setLayout(new GridLayout(1, 2));
		setLayout(new BorderLayout());
		panel_Date.add(label_Month);
		panel_Date.add(text_Month);
		panel_Date.add(label_Date);
		panel_Date.add(text_Date);
		panel_Date.add(label_No);
		panel_Date.add(text_No);
		panel_Date.add(label_Author);
		panel_Date.add(author);
		panel_Date.add(destroy);
		panel_Date.add(isAllowedOutput);
		panel_Date.add(label_Colmn);
		panel_Date.add(text_Column);
		panel_Date.add(label_Title);
		panel_Date.add(text_Title);
		panel_Date.add(button_Apply);
		panel_North.add(panel_Date, BorderLayout.NORTH);
		panel_North.add(panel_Checkbox, BorderLayout.CENTER);
		panel_North.add(scroll_t, BorderLayout.SOUTH);
		panel_Text.add(scroll_o);
		panel_Text.add(scroll_Memo);
		add(panel_North, BorderLayout.NORTH);
		add(generate, BorderLayout.CENTER);
		add(panel_Text, BorderLayout.SOUTH);
		add(selector, BorderLayout.WEST);
	}
	
	public void createTable(){
		if(list_Current == StringResources.list_General){
			int columnNumber;
			try{
				columnNumber = Integer.parseInt(text_Column.getText());
			}catch(NumberFormatException e){
				columnNumber = 1;
			}
			
			table = new JTable(ROW_MAX, columnNumber);
		} else {
			tablemodel = new DefaultTableModel(list_Current, ROW_MAX);
			table = new JTable(tablemodel);
		}
		if(list_Current == StringResources.list_General){
			try{
				Col_Max = Integer.parseInt(text_Column.getText());
			}catch(NumberFormatException e){
				Col_Max = 1;
			}
			
		} else {
			Col_Max = list_Current.length;
		}
		table.addKeyListener(this);
		table.setColumnSelectionAllowed(true); //User is allowed to select a single cell
		table.setGridColor(Color.decode("#4682B4"));
		
		colmodel = (DefaultTableColumnModel)table.getColumnModel();
		scroll_t = new JScrollPane(table);
		scroll_t.setPreferredSize(table.getPreferredSize());
		if(list_Current != StringResources.list_General){
			TableColumn col;
			for(int i=0; i< Col_Max; i++){
				col = colmodel.getColumn(i);
				col.setHeaderValue(list_Current[i]);
			}
		} else {
			tablemodel = (DefaultTableModel) table.getModel();
		}
		
	}
	
	public void resetTable(){
		panel_North.remove(scroll_t);
		createTable();
		panel_North.add(scroll_t, BorderLayout.SOUTH);
		revalidate();
	}
	
	public void resetCheckbox(){
		for(int i=0; i<list_Researcher.size(); i++){
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
		if(Integer.parseInt(text_Month.getText().toString()) <= 9){
			out += "0";
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
		if(list_Current == StringResources.list_General){
			if(text_Title.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Please set a title for this table.");
				return;
			}else{
				out += text_Title.getText();
			}
			
		} else {
			out += selector.getSelectedItem().toString();
		}
		
		out += "</h4></span>\r\n";
		
		//Experimenter name
		out += "<span class=\"keln_researcher\">";
		
		int finalindex = 0; //final experimenter
		for(int i=0; i<list_Researcher.size(); i++){
			if(researcher[i].isSelected() == true){
				finalindex = i;
			}
		}
		for(int i=0; i<list_Researcher.size(); i++){
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
			if(list_Current == StringResources.list_General){
				out += tablemodel.getValueAt(0, i);
			} else {
				col = colmodel.getColumn(i);
				out += col.getHeaderValue();
			} 
			
			out += "</th>";
		}
		out += "</tr>\r\n";
		
		//Fill the table with data
		for(int i=0; i<ROW_MAX; i++){
			if((list_Current == StringResources.list_General) && i == 0){
				continue;
			}
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
		if(!text_Memo.getText().equals("")){
			String memo = text_Memo.getText();
			memo = replaceString(memo, "\n", "<br>");
			out += "<p class=\"keln_note\">" + memo + "</p>" + "\n";
		}
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
		String filename = text_Month.getText() + "_" + text_Date.getText() +  "_" + "No" + text_No.getText() + "_" + selector.getSelectedItem().toString() + "_" + author.getSelectedItem().toString() + "_" + ftime.format(time) + ".txt";
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
	
	public Boolean validateInputForm(){
		Boolean result = true;
		try{
			Integer.parseInt(text_Month.getText().toString());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Date is invalid or empty.");
			result = false;
		}
		if(text_No.getText().equals("")){
			JOptionPane.showMessageDialog(this, "No. is empty.");
			result = false;
		}
		return result;
	}
	
	public void readJson(){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(new File("settings.txt"));
		} catch (JsonProcessingException e) {
			JOptionPane.showMessageDialog(this, "Syntax Error");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Please create settings.txt in the same directory as KELN.jar");
			System.exit(0);
		}
		for(JsonNode n: root.get("member")){
			list_Researcher.add(n.asText());
		}
		
		
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
			if(validateInputForm()){
				convertStringToHTML();
			}
		}
		if(e.getSource() == destroy){
			resetTable();
			resetCheckbox();
			output.setText("");
		}
		if(e.getSource() == button_Apply){
			selector.setSelectedItem("General");
			list_Current = StringResources.list_General;
			resetTable();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == selector && e.getStateChange() == ItemEvent.SELECTED){
			switch (selector.getSelectedItem().toString()) {
			case "PCR (Target)":
				list_Current = StringResources.list_PCR_Target;
				break;
			case "PCR (Steps)":
				list_Current = StringResources.list_PCR_Steps;
				break;
			case "PCR (Purification)":
				list_Current = StringResources.list_PCR_Purification;
				break;
			case "Transformation":
				list_Current = StringResources.list_Transformation;
				break;
			case "Colony PCR (typeA)":
				list_Current = StringResources.list_ColonyPCR_1;
				break;
			case "Colony PCR (typeB)":
				list_Current = StringResources.list_ColonyPCR_2;
				break;
			case "Liquid Culture":
				list_Current = StringResources.list_LiquidCulture;
				break;
			case "Miniprep":
				list_Current = StringResources.list_Miniprep;
				break;
			case "Restriction Enzyme Digestion":
				list_Current = StringResources.list_RestrictionEnzymeDigestion;
				break;
			case "Electrophoresis":
				list_Current = StringResources.list_Electrophoresis;
				break;
			case "Gel Extraction":
				list_Current = StringResources.list_GelExtraction;
				break;
			case "Preparation":
				list_Current = StringResources.list_Preparation;
				break;
			case "PartsAwaking":
				list_Current = StringResources.list_PartsAwaking;
				break;
			case "General":
				list_Current = StringResources.list_General;
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
