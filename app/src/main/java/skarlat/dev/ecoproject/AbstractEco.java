package skarlat.dev.ecoproject;

/*
**
* Абстрактный класс AbstractEco от которого можно наследоваться.
* В классах наследниках нужно будет вызвать контсруктор суперкласса:
* super(name, description, status);
*
 */
public class AbstractEco implements EcoInterface{
	private final String name;
	private final String desription;
	boolean status;
	
	public AbstractEco(String name, String desription, boolean status){
		this.name = name;
		this.desription = desription;
		this.status = status;
	}
	
	@Override
	public boolean getStatus() {
		return status;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getDescription() {
		return desription;
	}
}
