package androidsamples.java.journalapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalRepository {

    private static final String DATABASE_NAME = "journal_table";
    private final JournalEntryDao mJournalEntryDao;

    private static JournalRepository sInstance;

    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private JournalRepository(@NonNull Context context) {
        JournalRoomDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                JournalRoomDatabase.class,
                DATABASE_NAME).build();
        mJournalEntryDao = db.journalEntryDao();
    }

    public static void init(Context context) {
        if (sInstance == null)
            sInstance = new JournalRepository(context);
    }

    public static JournalRepository getInstance() {
        if (sInstance == null)
            throw new IllegalStateException("JournalRepository must be initialized");
        return sInstance;
    }

    // exec
    public void insert(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.insert(entry));
    }

    public void update(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.update(entry));
    }

    public void delete(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.delete(entry));
    }


    public LiveData<List<JournalEntry>> getAllEntries() {
        return mJournalEntryDao.getAllEntries();
    }

    public LiveData<JournalEntry> getEntry(UUID id) {
        return mJournalEntryDao.getEntry(id);
    }
}
