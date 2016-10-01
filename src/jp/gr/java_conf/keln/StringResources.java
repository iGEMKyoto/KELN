package jp.gr.java_conf.keln;

public class StringResources {
	
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
			"Ligation",
			"Electrophoresis",
			"Gel Extraction",
			"Gel Extraction(Measurement)",
			"Preparation",
			"PartsAwaking",
			"Sequence",
			"General"
	};
	
	//Write elements of each experiment
	//Note:you have to edit itemStateChanged() function after adding experiment.
	public static final String[] list_PCR_Target = {
			"Templates/(É l)",
			"Primer1/(É l)",
			"Primer2/(É l)",
			"Buffer/(É l)",
			"MilliQ/(É l)",
			"Any Other/(É l)",
			"Total/(É l)"
	};
	public static final String[] list_PCR_Steps = {
			"Steps",
			"Temparature",
			"Time",
			"Cycle"
	};
	public static final String[] list_PCR_Purification = {
			"Sample Name/(É l)",
			"Concentration(ng/É l)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_Transformation = {
			"Sample Name",
			"Sample Volume (É l)",
			"Competent Cells/(É l)",
			"Medium"
	};
	public static final String[] list_ColonyPCR_1 = {
			"Templates/(É l)",
			"Primers/(É l)",
			"MilliQ/(É l)",
			"PreMix/(É l)",
			"Total/(É l)"
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
			"Concentration/(É g/ml)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_RestrictionEnzymeDigestion = {
			"Sample",
			"DNA/(É l)",
			"EcoR1/(É l)",
			"Xba1/(É l)",
			"Spe1/(É l)",
			"Pst1/(É l)",
			"Buffer/(É l)",
			"BSA/(É l)",
			"MilliQ/(É l)",
			"Total/(É l)"
	};
	public static final String[] list_Ligation = {
			"Vector/(É l)",
			"Insert/(É l)",
			"Ligation High/(É l)",
			"Total/(É l)"
	};
	public static final String[] list_Electrophoresis = {
			"Lane",
			"sample/(É l)",
			"Length(bp)"
	};
	public static final String[] list_GelExtraction = {
			"Lane",
			"Restriction Enzyme Digestion Product",
			"Volume/(É l)"
	};
	public static final String[] list_GelExtraction_Measurement = {
			"Sample/(É l)",
			"Concentration(É g/É l)",
			"260/280",
			"260/230"
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
			"MilliQ(É l)"
	};
	public static final String[] list_Sequence = {
			"Sample",
			"DNA template/(É l)",
			"Primer/(É l)",
			"MilliQ(É l)",
			"Total(É l)"
	};
	public static final String[] list_General={
	};
}
