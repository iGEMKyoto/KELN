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
			"Templates/(ƒÊl)",
			"Primer name/(ƒÊl)",
			"Primer volume/(ƒÊl)",
			"Buffer/(ƒÊl)",
			"MilliQ/(ƒÊl)",
			"Polymerase/(ƒÊl)",
			"Total/(ƒÊl)"
	};
	public static final String[] list_PCR_Steps = {
			"Steps",
			"Temparature",
			"Time",
			"Cycle"
	};
	public static final String[] list_PCR_Purification = {
			"Sample Name/(ƒÊl)",
			"Concentration(ng/ƒÊl)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_Transformation = {
			"Sample Name",
			"Sample Volume (ƒÊl)",
			"Competent Cells/(ƒÊl)",
			"Medium"
	};
	public static final String[] list_ColonyPCR_1 = {
			"Templates/(ƒÊl)",
			"Primers/(ƒÊl)",
			"MilliQ/(ƒÊl)",
			"PreMix/(ƒÊl)",
			"Total/(ƒÊl)"
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
			"Concentration/(ƒÊg/ml)",
			"260/280", 
			"260/230"
	};
	public static final String[] list_RestrictionEnzymeDigestion = {
			"Sample",
			"DNA/(ƒÊl)",
			"EcoR1/(ƒÊl)",
			"Xba1/(ƒÊl)",
			"Spe1/(ƒÊl)",
			"Pst1/(ƒÊl)",
			"Buffer/(ƒÊl)",
			"BSA/(ƒÊl)",
			"MilliQ/(ƒÊl)",
			"Total/(ƒÊl)"
	};
	public static final String[] list_Ligation = {
			"Vector/(ƒÊl)",
			"Insert/(ƒÊl)",
			"Ligation High/(ƒÊl)",
			"Total/(ƒÊl)"
	};
	public static final String[] list_Electrophoresis = {
			"Lane",
			"sample/(ƒÊl)",
			"Length(bp)"
	};
	public static final String[] list_GelExtraction = {
			"Lane",
			"Restriction Enzyme Digestion Product",
			"Volume/(ƒÊl)"
	};
	public static final String[] list_GelExtraction_Measurement = {
			"Sample/(ƒÊl)",
			"Concentration(ƒÊg/ƒÊl)",
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
			"MilliQ(ƒÊl)"
	};
	public static final String[] list_Sequence = {
			"Sample",
			"DNA template/(ƒÊl)",
			"Primer/(ƒÊl)",
			"MilliQ(ƒÊl)",
			"Total(ƒÊl)"
	};
	public static final String[] list_General={
	};
}
