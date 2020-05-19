package skarlat.dev.ecoproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EcoSoviet implements EcoInterface{

	public String cardNameID;

	@PrimaryKey(autoGenerate = true)
	@NonNull
	public long sovietID;

	public int isFavorite;

	public String title;

	public String description;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getFullDescription() {
		return null;
	}

	@Override
	public Enum getStatus() {
		return isFavorite == 0 ? Status.UNLIKED : Status.LIKED;
	}

	private enum Status{
		UNLIKED,
		LIKED;
	}

	public void setStatus(EcoSoviet.Status status){
		switch (status){
			case UNLIKED:
				isFavorite = 0;
				break;
			case LIKED:
				isFavorite = 1;
				break;
		}
	}

	public void upDate(EcoSoviet.Status status){
		setStatus(status);
	}
}
