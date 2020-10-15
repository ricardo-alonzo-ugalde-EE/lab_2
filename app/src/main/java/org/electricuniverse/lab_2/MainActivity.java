package org.electricuniverse.lab_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar; // specific for snackbar use

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener {
    /**
     * MainActivity Class data members declaration
     * */
    private TextView textView;
    private SeekBar seekBar;
    private ToggleButton toggleButton;
    private Integer oldValue = 50;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * View object initialization
         * */
        textView = findViewById(R.id.scoretext);
        seekBar = findViewById(R.id.seekBar);
        toggleButton = findViewById(R.id.disable_snack);
        seekBar.setOnSeekBarChangeListener(this);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        textView.setText(Integer.toString(progress)); // Show the progress in the textView,/mn xzcm,ncnm.,vcm,/.ncx/,.mn
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
        oldValue = seekBar.getProgress(); //saves initial progress into interger oldValue variable
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar)
    {
        /**
         * Snackbar provides lightweight feedback about an operatio
         * */
        if (toggleButton.isChecked())
        {
            final Snackbar snackbar = Snackbar.make(seekBar, "Progress chaged.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    textView.setText(Integer.toString(oldValue));
                    seekBar.setProgress(oldValue);
                    Snackbar snackbar1 = Snackbar.make(v,"Seekbar restored.", Snackbar.LENGTH_SHORT);
                    snackbar1.show();

                }
            });
            snackbar.show();
        }

    }

    /**
     * Part 2: createToast Method
     * */
    public void createToast()
    {
        /**
         * get a reference to the radio button
         * */
        RadioButton simpleRB = findViewById(R.id.rb_simple);

        /**
         * Create and show a simple toast of the rb_simple
         * */
        Toast.makeText(this, "Simple Toast MEssage", Toast.LENGTH_SHORT).show();

        /**
         * If the custom radio button is checked, inflate layout and show in toast message
         * Inflate the view hierarchy from the layout resource.
         * */
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toastRoot));

        /**
         * Get references to the seekbar and textview in this view hierarchy.
         * */
        SeekBar seekBarInToast = layout.findViewById(R.id.seekBarInToast);
        TextView textViewInToast = layout.findViewById(R.id.textViewinToast);


        /**
         * Set the values for the created above from the seekbar's progress
         * create and show the toast
         * */
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Implement selectDateTime method for this button's on Click handler
     * to show a date picker dialog
     * Notice we use our activity as listener here since its an onDateSetListener
     * */
    public void selectDateTime(View view)
    {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,yy,mm,dd);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth)
    {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        final TextView textViewDateTime = findViewById(R.id.datetime);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String oldValue = textViewDateTime.getText().toString();
                textViewDateTime.setText(String.valueOf(month) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year) +
                        " " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                Snackbar snackbar = Snackbar.make(textViewDateTime, "Date and Time Selected", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textViewDateTime.setText(oldValue);
                                Toast.makeText(MainActivity.this, "Done!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                snackbar.show();
            }
        }, h, m, false);
        timePickerDialog.show();
    }


}