package jp.gr.java_conf.keln;

public class StringResources {
	public static final String[] list_Researcher = {	//Write team members here
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
			"Yamada",
			"Watanabe"
	};
	
	public static final String[] list_Experiment = {	//Write experiments here
			"PCR (Target)",
			"PCR (Steps)",
			"PCR (Purification)",
			"Transformation",
			"Colony PCR (typeA)",
			"Colony PCR (typeB)",
			"Liquid Culture",
			"Miniprep",
			"Restriction Enzyme Digestion",
			"Electrophoresis",
			"Gel Extraction",
			"Preparation",
			"PartsAwaking",
			"General"
	};
	
	//Write elements of each experiment
	//Note:you have to edit itemStateChanged() function after adding experiment.
	public static final String[] list_PCR_Target = {
			"Templates/(��l)",
			"Primer1/(��l)",
			"Primer2/(��l)",
			"Buffer/(��l)",
			"MilliQ/(��l)",
			"Any Other/(��l)",
			"Total/(��l)"
	};
	public static final String[] list_PCR_Steps = {
			"Steps",
			"Temparature",
			"Time",
			"Cycle"
	};
	public static final String[] list_PCR_Purification = {
			"Sample Name/(��l)",
			"Concentration(ng/��l)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_Transformation = {
			"Sample Name",
			"Sample Volume (��l)",
			"Competent Cells/(��l)",
			"Medium"
	};
	public static final String[] list_ColonyPCR_1 = {
			"Templates/(��l)",
			"Primers/(��l)",
			"MilliQ/(��l)",
			"PreMix/(��l)",
			"Total/(��l)"
	};
	public static final String[] list_ColonyPCR_2 = {
			"Name I",
			"Name II"
	};
	public static final String[] list_LiquidCulture = {
			"Name",
			"medium"
	};
	public static final String[] list_Miniprep = {
			"DNA",
			"Concentration/(��g/ml)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_RestrictionEnzymeDigestion = {
			"Sample",
			"DNA/(��l)",
			"EcoR1/(��l)",
			"Xba1/(��l)",
			"Spe1/(��l)",
			"Pst1/(��l)",
			"Buffer/(��l)",
			"BSA/(��l)",
			"MilliQ/(��l)",
			"Total/(��l)"
	};
	public static final String[] list_Electrophoresis = {
			"Lane",
			"sample/(��l)",
			"Length(bp)"
	};
	public static final String[] list_GelExtraction = {
			"Lane",
			"Restriction Enzyme Digestion Product",
			"Volume/(��l)"
	};
	public static final String[] list_Preparation = {
			"Reagent",
			"Liquid"
	};
	public static final String[] list_PartsAwaking = {
			"Description",
			"PartName",
			"Backbone",
			"Well",
			"PlateYear",
			"PlateNumber",
			"MilliQ(��l)"
	};
	public static final String[] list_General={
	};
}
