package models;

public class texturedModel {
	
	private TDModel	tdm;
	private modelTexture txtr;
	
	public texturedModel(TDModel tdm, modelTexture txtr) {
		this.tdm = tdm;
		this.txtr = txtr;
	}

	public TDModel getTdm() {
		return tdm;
	}

	public modelTexture getTxtr() {
		return txtr;
	}
	
	
	

}
