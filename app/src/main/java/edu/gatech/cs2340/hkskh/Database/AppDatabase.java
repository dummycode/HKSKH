package edu.gatech.cs2340.hkskh.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.Nullable;

import edu.gatech.cs2340.hkskh.Shelters.DAOs.ShelterDao;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Users.DAOs.UserDao;
import edu.gatech.cs2340.hkskh.Users.Enums.UserType;
import edu.gatech.cs2340.hkskh.Users.Models.User;

/**
 * Created by henry on 3/14/18.
 * The database class for implementing persistence
 */
@Database(entities = {User.class, Shelter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    @Nullable
    private static AppDatabase INSTANCE;

    /**
     * @return user data access object
     */
    public abstract UserDao userDao();

    /**
     * @return shelter data access object
     */
    public abstract ShelterDao shelterDao();

    /**
     * Get the current database
     *
     * @param context context
     * @return the database object
     */
    public static AppDatabase getDatabase(Context context) {
        if (AppDatabase.INSTANCE == null) {
            AppDatabase.INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hkskh")
                            .allowMainThreadQueries()
                            .build();
            if (AppDatabase.INSTANCE != null) {
                AppDatabase.INSTANCE.defaultUsers();
            }
        }
        return AppDatabase.INSTANCE;
    }

    /**
     * Destroy the database instance
     */
    @SuppressWarnings("unused")
    public static void destroyInstance() {
        AppDatabase.INSTANCE = null;
    }

    /**
     * Insert some default users
     */
    private void defaultUsers() {
        //noinspection LawOfDemeter
        userDao().insert(new User("henry", UserType.USER, "pass"));
    }
}
