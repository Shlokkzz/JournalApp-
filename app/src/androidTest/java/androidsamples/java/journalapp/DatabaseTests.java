package androidsamples.java.journalapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;



import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private JournalRoomDatabase mDatabase;
    private JournalEntryDao mJournalEntryDao;

    @Before
    public void initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        JournalRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        mJournalEntryDao = mDatabase.journalEntryDao();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void insertAndGetJournalEntry() throws Exception {
        JournalEntry journalEntry = new JournalEntry("Title", "2023-10-10", "10:00", "11:00");
        mJournalEntryDao.insert(journalEntry);

        LiveData<JournalEntry> fetchedEntryLiveData = mJournalEntryDao.getEntry(journalEntry.getUid());
        JournalEntry fetchedEntry = LiveDataTestUtil.getValue(fetchedEntryLiveData);

        assertNotNull(fetchedEntry);
        assertEquals(fetchedEntry.title(), journalEntry.title());
        assertEquals(fetchedEntry.date(), journalEntry.date());
        assertEquals(fetchedEntry.start(), journalEntry.start());
        assertEquals(fetchedEntry.end(), journalEntry.end());
    }

    @Test
    public void insertAndDeleteJournalEntry() throws Exception {
        JournalEntry journalEntry = new JournalEntry("Title", "2023-10-10", "10:00", "11:00");
        mJournalEntryDao.insert(journalEntry);
        mJournalEntryDao.delete(journalEntry);

        LiveData<JournalEntry> fetchedEntryLiveData = mJournalEntryDao.getEntry(journalEntry.getUid());
        JournalEntry fetchedEntry = LiveDataTestUtil.getValue(fetchedEntryLiveData);

        assertNull(fetchedEntry);
    }

    @Test
    public void insertAndUpdateJournalEntry() throws Exception {
        JournalEntry journalEntry = new JournalEntry("Title", "2023-10-10", "10:00", "11:00");
        mJournalEntryDao.insert(journalEntry);

        journalEntry.setTitle("Updated Title");
        mJournalEntryDao.update(journalEntry);

        LiveData<JournalEntry> fetchedEntryLiveData = mJournalEntryDao.getEntry(journalEntry.getUid());
        JournalEntry fetchedEntry = LiveDataTestUtil.getValue(fetchedEntryLiveData);

        assertNotNull(fetchedEntry);
        assertEquals(fetchedEntry.title(), "Updated Title");
    }

    @Test
    public void insertAndGetAllJournalEntries() throws Exception {
        JournalEntry journalEntry1 = new JournalEntry("Title1", "2023-10-10", "10:00", "11:00");
        JournalEntry journalEntry2 = new JournalEntry("Title2", "2023-10-11", "12:00", "13:00");
        mJournalEntryDao.insert(journalEntry1);
        mJournalEntryDao.insert(journalEntry2);

        LiveData<List<JournalEntry>> allEntriesLiveData = mJournalEntryDao.getAllEntries();
        List<JournalEntry> allEntries = LiveDataTestUtil.getValue(allEntriesLiveData);

        assertNotNull(allEntries);
        assertEquals(allEntries.size(), 2);
    }
}