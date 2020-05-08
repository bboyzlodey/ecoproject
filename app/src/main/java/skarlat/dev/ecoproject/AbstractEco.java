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
	boolean status;
	
	public AbstractEco(String title, String desription, boolean status){
		this.title = title;
		this.desription = desription;
		this.status = status;
	}

//	public AbstractEco(String title, String desription){
//
//	}

	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public String getName() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return desription;
	}
}
