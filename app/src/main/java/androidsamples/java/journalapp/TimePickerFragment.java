package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

  private boolean isStart;

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    // TODO implement the method

    TimePickerFragment fragment = new TimePickerFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO implement the method
    setHasOptionsMenu(true);

    isStart = TimePickerFragmentArgs.fromBundle(getArguments()).getIsStart();

  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // TODO implement the method
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    return new TimePickerDialog(getActivity(), (tp, h, m) -> putUserTime(h, m), hour, minute, DateFormat.is24HourFormat(getActivity()));
  }

  public void putUserTime(int h, int m) {
    String s = formatTime(h) + ":" + formatTime(m);
    Button b;

    b = isStart ? requireActivity().findViewById(R.id.btn_start_time) : requireActivity().findViewById(R.id.btn_end_time);
    b.setText(s);
  }
  private String formatTime(int value)
  {
    if(value <= 9)
      return "0" + value;
    return Integer.toString(value);
  }
}
