package skarlat.dev.ecoproject;

import java.io.Serializable;

/*
**
* Абстрактный класс AbstractEco от которого можно наследоваться.
* В классах наследниках нужно будет вызвать контсруктор суперкласса:
* super(name, description, status);
*
 */
abstract public class AbstractEco implements EcoInterface, Serializable {
	private final String title;
	private final String desription;
	private final String name;
	private final String fullDescription;
	private int status;
//<<<<<<< HEAD
	public boolean temp;
//=======
	private int i;
//>>>>>>> denis
	
	public AbstractEco(String name, String title, String desription, String fullDescription, int status){
		this.name = name;
		this.title = title;
		this.desription = desription;
		this.status = status;
		this.fullDescription = fullDescription;
	}
	public AbstractEco(String name, String title, String desription, String fullDescription){
		this.name = name;
		this.title = title;
		this.desription = desription;
		this.status = status;
		this.fullDescription = fullDescription;
	}
	
	public AbstractEco(String name, String title){
		this.name = name;
		this.title = title;
		desription = null;
		fullDescription = null;
	}

	public AbstractEco(String name, String title, String desription, boolean status){
		this.name = name;
		this.title = title;
		this.desription = desription;
		this.temp = status;
		this.fullDescription = null;
	}

	public AbstractEco(String title, String desription, boolean status){
		this.name = null;
		this.title = title;
		this.desription = desription;
		this.temp = status;
		this.fullDescription = null;
	}

	/**
	 *
	 * @return: 0 - закрытый курс, 1 - открытый курс, 2 - текущий посещенный курс
	 * Смотри ниже метод с Enum
	 */
//	@Override
//	public int getStatus() {
//		return status;
//	}
	/**
	 *
	 * @return: Enum - объект перпечисления.
	 * Метод переодпределяется в каждом классе
	 */
	@Override
	abstract public Enum getStatus();

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
