package skarlat.dev.ecoproject;

import java.io.Serializable;

public class EcoCard extends AbstractEco implements Serializable {
	public enum Status{
		CLOSED,
		OPENED,
		WATCHED,
	}
	public Status status;
	
	public EcoCard(String name, String title, String desription, String fullDescription, int status){
		super(name, title, desription, fullDescription, status);
		switch (status){
			case 0:
				this.status = Status.CLOSED;
				break;
			case 1:
				this.status = Status.OPENED;
				break;
			case 2:
				this.status = Status.WATCHED;
				break;
		}
	}
	
	public EcoCard(String name, String title, String desription, String fullDescription, Enum status) {
		super(name, title, desription, fullDescription);
		this.status = (Status) status;
	}
	
	@Override
	public Enum getStatus() {
		return status;
	}
	
	public Enum<Status> getStatusEnum(){
		return status;
	}
}
