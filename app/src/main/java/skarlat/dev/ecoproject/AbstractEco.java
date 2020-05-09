package skarlat.dev.ecoproject;

/*
**
* Абстрактный класс AbstractEco от которого можно наследоваться.
* В классах наследниках нужно будет вызвать контсруктор суперкласса:
* super(name, description, status);
*
 */
public class AbstractEco implements EcoInterface{
	private final String title;
	private final String desription;
	private final String name;
	private final String fullDescription;
	boolean status;
	
	public AbstractEco(String name, String title, String desription, String fullDescription, boolean status){
		this.name = name;
		this.title = title;
		this.desription = desription;
		this.status = status;
		this.fullDescription = fullDescription;
	}

	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return desription;
	}

	@Override
	public String getName(){ return name; }

	@Override
	public String getFullDescription() {
		return fullDescription;
	}
}
