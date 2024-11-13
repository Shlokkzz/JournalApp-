package androidsamples.java.journalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private static final String TAG = "EntryListFragment";
  private JournalViewModel mJournalViewModel;
  private View view;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
     setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    // clickListeners
    FloatingActionButton mAddButton = view.findViewById(R.id.btn_add_entry);
    mAddButton.setOnClickListener(this::addNewEntry);


    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(getActivity());
    recyclerView.setAdapter(adapter);

    mJournalViewModel.getAllEntries().observe(requireActivity(),(List<JournalEntry> entries) -> adapter.setEntries(entries));

    return view;
  }

  public void addNewEntry(View view) {
    JournalEntry entry = new JournalEntry("", "", "", "");
    mJournalViewModel.insert(entry);

    EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
    action.setEntryId(entry.getUid().toString());

    Navigation.findNavController(view).navigate(action);
  }

  // menu items
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_entry_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.info) {
      Log.d(TAG, "Info button clicked");
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jamesclear.com/atomic-habits"));
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private static class EntryViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTxtTitle;
    private final TextView mTxtDate;
    private final TextView mTxtStart;
    private final TextView mTxtEnd;
    private JournalEntry mEntry;

    public EntryViewHolder(@NonNull View itemView) {
      super(itemView);

      mTxtTitle = itemView.findViewById(R.id.txt_item_title);
      mTxtDate = itemView.findViewById(R.id.txt_item_date);
      mTxtStart = itemView.findViewById(R.id.txt_item_start_time);
      mTxtEnd = itemView.findViewById(R.id.txt_item_end_time);

       itemView.setOnClickListener(this::launchJournalEntryFragment);
    }

    private void launchJournalEntryFragment(View v) {
      Log.d(TAG, "launchJournalEntryFragment with Entry: " + mEntry.title());

      EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
      action.setEntryId(mEntry.getUid().toString());

      @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
      try {
        Date date = formatter.parse(mEntry.date());
        Calendar cal = Calendar.getInstance();
        assert date != null;
        cal.setTime(date);

        action.setSelectedDate(cal.get(Calendar.DATE));
        action.setSelectedMonth(cal.get(Calendar.MONTH));
        action.setSelectedYear(cal.get(Calendar.YEAR));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      Navigation.findNavController(v).navigate(action);
    }

    void bind(JournalEntry entry) {
      mEntry = entry;
      this.mTxtTitle.setText(mEntry.title());
      this.mTxtDate.setText(mEntry.date());
      this.mTxtStart.setText(mEntry.start());
      this.mTxtEnd.setText(mEntry.end());
    }
  }


  private static class JournalEntryListAdapter extends RecyclerView.Adapter<EntryViewHolder>{

    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    public JournalEntryListAdapter(Context context) {
      mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
      if (mEntries != null) {
        JournalEntry current = mEntries.get(position);
        // bind now
        holder.bind(current);
      }
    }

    @Override
    public int getItemCount() {
      return (mEntries == null) ? 0 : mEntries.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEntries(List<JournalEntry> entries) {
      mEntries = entries;
      notifyDataSetChanged();
    }
  }
}