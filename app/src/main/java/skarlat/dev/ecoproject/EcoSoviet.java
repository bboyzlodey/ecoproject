package skarlat.dev.ecoproject;

public class EcoSoviet extends AbstractEco {

	public EcoSoviet(String name, String title, String desription, String fullDescription, int status) {
		super(name, title, desription, fullDescription, status);
	}
	public EcoSoviet(String name, String title, boolean status){
		super(name, title);
	}
}
