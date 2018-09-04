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

	//variables
	int Col_Max; //Maximum number of the column will be initialized in constructor.
	final int ROW_MAX = 20; //Maximum number of the row
	String platform; //OS version
	String[] list_Current; //This array stores list_[experiment name]
	ArrayList<String> list_Researcher = new ArrayList<String>();
	LinkedHashMap<String, String[]> dict_exp = new LinkedHashMap<String, String[]>();
	
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
	JLabel label_Title;
	JTextField text_Title;
	JTextArea text_Memo;
	JScrollPane scroll_Memo;
	JLabel label_Output, label_Memo;
	
	DefaultTableModel tablemodel;
	DefaultTableColumnModel colmodel;
	
	public KELN(){ //Constructor
		readJson();
		platform = getPlatformName();
		String[] list_exps = dict_exp.keySet().toArray(new String[dict_exp.keySet().size()]);
		list_Current = dict_exp.get(list_exps[0]);
		Col_Max = list_Current.length;
		createTable();
		output = new JTextArea("This is output area.\nYou can add note in right text field.");
		output.setRows(10);
		scroll_o = new JScrollPane(output);
		generate = new JButton("Convert");
		generate.addActionListener(this);
		destroy = new JButton("Clear");
		destroy.addActionListener(this);
		selector = new JComboBox<String>(list_exps);
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
		text_Title = new JTextField();
		text_Title.setPreferredSize(new Dimension(100, 25));
		text_No.setPreferredSize(new Dimension(30, 25));
		label_Month = new JLabel("Month");
		label_Date = new JLabel("Day");
		label_No = new JLabel("No.");
		label_Author = new JLabel("Author");;
		label_Title = new JLabel("Title");
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
		panel_Date.add(label_Title);
		panel_Date.add(text_Title);
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
		tablemodel = new DefaultTableModel(list_Current, ROW_MAX);
		table = new JTable(tablemodel);
		
		Col_Max = list_Current.length;
		
		table.addKeyListener(this);
		table.setColumnSelectionAllowed(true); //User is allowed to select a single cell
		table.setGridColor(Color.decode("#4682B4"));
		
		colmodel = (DefaultTableColumnModel)table.getColumnModel();
		scroll_t = new JScrollPane(table);
		scroll_t.setPreferredSize(table.getPreferredSize());
		tablemodel = (DefaultTableModel) table.getModel();
		
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
			JOptionPane.showMessageDialog(this, "There are syntax errors in settings.txt");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Please create settings.txt in the same directory as KELN.jar");
			System.exit(0);
		}
		try {
			for(JsonNode n: root.get("member")){
				list_Researcher.add(n.asText());
			}
			for(JsonNode exp: root.get("experiments")){
				String name = exp.get("name").asText();
				ArrayList<String> cols = new ArrayList<String>();
				for (JsonNode jsonNode : exp.get("columns")) {
					cols.add(jsonNode.asText());
				}
				String [] cols_array = cols.toArray(new String[cols.size()]);
				dict_exp.put(name, cols_array);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "There are missing values or wrong keys in settings.txt");
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
	
	private ArrayList<String> getNameList(ArrayList<String> namelist, JCheckBox[] checkbox) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<namelist.size(); i++){
			if(checkbox[i].isSelected() == true){
				list.add(researcher[i].getText());
			}
		}
		return list;
	}
	
	private ArrayList<String> getHeader(){
		ArrayList<String> list = new ArrayList<String>();
		TableColumn col;
		for(int i=0; i<Col_Max; i++){
			col = colmodel.getColumn(i);
			list.add((String) col.getHeaderValue());
		}
		return list;
	}
	
	private ArrayList<ArrayList<String>> getTableValue(){
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		String data;
		
		for(int i=0; i<ROW_MAX; i++){
			//Stop after detecting an empty row
			int emptyCount = 0;
			for(int j=0; j<table.getColumnCount(); j++){
				if(tablemodel.getValueAt(i, j) == null || tablemodel.getValueAt(i, j).equals("")) emptyCount++ ;
			}
			if(emptyCount >= table.getColumnCount()) break;
			
			ArrayList<String> row = new ArrayList<String>();
			for(int j=0; j<table.getColumnCount(); j++){
				try{
					data = tablemodel.getValueAt(i, j).toString();
				}catch(NullPointerException e){
					data = "";
				}
				row.add(data);
			}
			list.add(row);
		}
		return list;
	}
	
	private void Convert() {
		String currentAuthor = author.getSelectedItem().toString();
		String month = text_Month.getText().toString();
		String date = text_Date.getText().toString();
		String title;
		if(text_Title.getText().equals("")){
			title = selector.getSelectedItem().toString();
		} else {
			title = text_Title.getText();
		}
		ArrayList<String> namelist = getNameList(list_Researcher, researcher);
		ArrayList<String> header = getHeader();
		String memo = text_Memo.getText();
		ArrayList<ArrayList<String>> table = getTableValue();
		DataProcessor dp = new DataProcessor(this);
		String str = dp.getHTML(currentAuthor, month, date, title, namelist, header, memo, table);
		output.setText(str);
		if(isAllowedOutput.isSelected()){
			saveStringToText(str);
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
				Convert();
			}
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
			String currentExpName = selector.getSelectedItem().toString();
			list_Current = dict_exp.get(currentExpName);
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
