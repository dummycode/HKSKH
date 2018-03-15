package edu.gatech.cs2340.hkskh.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.gatech.cs2340.hkskh.Shelters.DAOs.ShelterDao;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Users.DAOs.UserDao;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.Models.User;

/**
 * Created by henry on 3/14/18.
 */
@Database(entities = {User.class, Shelter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract ShelterDao shelterDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "hkskh")
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
                    INSTANCE.userDao().insert(new User("henry", UserType.USER, "pass"));
            // TODO get this off the main thread, it may be slowing it down
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
