package skarlat.dev.ecoproject;

/**
 * Изменено значение getStatus c boolean на String
 */
public interface EcoInterface {
//	Integer getStatus();
	String getTitle();
	String getDescription();
	String getName();
	String getFullDescription();
	Enum getStatus();
}
