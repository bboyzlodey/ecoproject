package skarlat.dev.ecoproject.includes.dataclass;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.IOException;
import java.io.Serializable;

import skarlat.dev.ecoproject.EcoTipsApp;
import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;

@Entity
public class EcoCard implements Serializable {
    private final static String postfixPath = "_cover_hdpi";
    @PrimaryKey
    @NonNull
    public String cardNameID;

    public String courseNameID;

    public String title;

    public String description;

    public String fullDescription;

    public int isActive;

    public enum Status {
        CLOSED,
        OPENED,
        WATCHED,
    }

    public String getName() {
        return this.cardNameID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Enum getStatus() {
        switch (this.isActive) {
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
        return new BitmapDrawable(EcoTipsApp.instance.getAssets().open(getPathImage()));
    }

    public String getPathImage() {
        return Const.IMAGES_ROOT_FOLDER + courseNameID + "/" + cardNameID + "/" + cardNameID + postfixPath + ".png";
    }

    public void update(EcoCard.Status status) {
        AppDatabase db = EcoTipsApp.getDatabase();
        CardsDao cardsDao = db.cardsDao();
        setStatus(status);
        cardsDao.update(this);
    }

    public void setStatus(EcoCard.Status status) {
        switch (status) {
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
