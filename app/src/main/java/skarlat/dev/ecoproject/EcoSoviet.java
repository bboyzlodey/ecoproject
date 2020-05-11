package skarlat.dev.ecoproject;

import java.io.Serializable;

public class EcoSoviet extends AbstractEco implements Serializable {

	private enum Status{
		UNLIKED,
		LIKED;
	}
	
	Status status;
	
	public EcoSoviet(String name, String title, String desription, String fullDescription, int status) {
		super(name, title, desription, fullDescription, status);
		switch (status){
			case 0:
				this.status = Status.UNLIKED;
				break;
			case 1:
				this.status = Status.LIKED;
		}
	}
	
	public EcoSoviet(String name, String title, String desription, String fullDescription, Enum<EcoSoviet.Status> status) {
		super(name, title, desription, fullDescription);
		this.status = (EcoSoviet.Status) status;
	}
	
	public EcoSoviet(String name, String title, boolean status){
		super(name, title);
		this.status = (status ? Status.LIKED : Status.UNLIKED);
	}
	
	public EcoSoviet(String name, String title){
		super(name, title);
		this.status = Status.UNLIKED;
	}
	
	@Override
	public Enum<EcoSoviet.Status> getStatus() {
		return status;
	}
}
