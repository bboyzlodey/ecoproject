package skarlat.dev.ecoproject;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.IOException;
import java.io.Serializable;

import skarlat.dev.ecoproject.includes.database.App;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;

@Entity
public class EcoCard  implements EcoInterface, Serializable {
	private final static String postfixPath = "_cover_hdpi";
	@PrimaryKey
	@NonNull
	public String cardNameID;

	public String courseNameID;

	public String title;

	public String description;

	public String fullDescription;

	public int isActive;

	public enum Status{
		CLOSED,
		OPENED,
		WATCHED,
	}
	@Override
	public String getName() {
		return this.cardNameID;
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
	public String getFullDescription(){
		return this.fullDescription;
	}

	public String getCourseNameID(){
		return this.courseNameID;
	}

	@Override
	public Enum getStatus() {
		switch (this.isActive){
			case 0:
				return Status.CLOSED;
			case 1:
				return Status.OPENED;
			case 2:
				return Status.WATCHED;
		}
		return null;
	}
	
	public Drawable getImage() throws IOException {
		Drawable drawable = new BitmapDrawable(App.instance.getAssets().open(getPathImage()));
		return drawable;
	}
	
	@Override
	public String getPathImage() {
		return Const.IMAGES_ROOT_FOLDER + courseNameID  + "/" +  cardNameID + postfixPath + ".png";
	}
	
	public void upDate(EcoCard.Status status) {
		AppDatabase db = App.getInstance().getDatabase();
		CardsDao cardsDao = db.cardsDao();
		setStatus(status);
		cardsDao.update(this);
	}

	public void setStatus(EcoCard.Status status){
		switch (status){
			case OPENED:
				this.isActive = 1;
				break;
			case WATCHED:
				this.isActive = 2;
				break;
			case CLOSED:
				this.isActive = 0;
				break;
		}
	}
}
