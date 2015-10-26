package com.example.android.swipe.CalendarFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.swipe.ProfileMainActivity;
import com.example.android.swipe.R;
import com.example.android.swipe.vbs.HttpRequestResponse.HttpRequestTask;
import com.example.android.swipe.vbs.Parsers.jsonParser;
import com.example.android.swipe.vbs.userClasses.ConstantValues;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    final SimpleDateFormat formatterForReqDate = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void MessageBox(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Tamam",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
								/*
								 * Whatever you wanna do when Tamam is clicked.
								 */
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setCustomResourceForDates() {
        Random random = new Random();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        String todayStr = formatter.format(cal.getTime());
        Log.d("Today: ", todayStr);
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 2);
        String dateAfter4MonthStr = formatter.format(cal.getTime());
        Log.d("after 4 Month", dateAfter4MonthStr);
        cal = Calendar.getInstance();
        // Min date is last 7 days
        cal.add(Calendar.DATE, -11);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        /*cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 4);

        Date greenDate = cal.getTime();
        cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 7);
        Date thirdDate = cal.getTime();
        Date redDate = new Date(2015-1900, 10-1, 10);
        Date firstDate = new Date("10/27/2015");
        Date secondDate = new Date("10/30/2015");
        Date startDisDate = new Date("11/11/2015");
        Date endDisDate = new Date("11/19/2015");
        ArrayList <Date> disDates = new ArrayList<>();
        disDates.add(startDisDate);
        disDates.add(endDisDate);
        Log.d("greenDate", greenDate.toString());
        Log.d("blueDate", blueDate.toString());
        Log.d("redDate", redDate.toString());

        Log.d("Selected Date", formatterForReqDate.format(redDate));
        Log.d("Example Date", formatterForReqDate.format(Calendar.getInstance().getTime()));*/

        HttpRequestTask httpRequestTask = new HttpRequestTask();
        StringBuilder uri = new StringBuilder();
        uri.append(ConstantValues.uriCalendarEvents).append("start=").append(todayStr).append("&end=")
                .append(dateAfter4MonthStr).append("&userNo=").append(ProfileMainActivity.user.getUserNo())
                .append("&sezon=").append(ProfileMainActivity.user.getSezon());
        try {
            String response = httpRequestTask.execute(uri.toString()).get();
            Log.d("CalendarService", response);
            String [] eventTitle = jsonParser.jsonParserToArray("title", response);
            String [] eventDescription = jsonParser.jsonParserToArray("description", response);
            String [] eventStart = jsonParser.jsonParserToArray("event_start", response);
            String [] eventEnd = jsonParser.jsonParserToArray("event_end", response);

            //Disable Dates
            /*HashMap <Date, Integer> hashmap = new HashMap<Date, Integer>();
            hashmap.put(startDisDate, R.drawable.folder_blue_award_icon);
            hashmap.put(endDisDate, R.color.caldroid_darker_gray);*/
            Date startDate, endDate;
            HashMap <String, Object> extraData = caldroidFragment.getExtraData();
            //ArrayList list;
            if (caldroidFragment != null) {
                for (int i=0;i<eventTitle.length;i++){
                    //startDate = new Date(eventStart[i].substring(0,10));
                    //endDate = new Date(eventEnd[i].substring(0, 10));
                    try {
                        startDate = formatterForReqDate.parse(eventStart[i].substring(0, 10));
                        endDate = formatterForReqDate.parse(eventEnd[i].substring(0, 10));
                        Date dateIterator = startDate;
                        int backgroundColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                        /*caldroidFragment.setBackgroundResourceForDate(R.color.blue, dateIterator);
                        cal.setTime(dateIterator);
                        cal.add(Calendar.DATE, 1);
                        dateIterator = cal.getTime();
                        caldroidFragment.setBackgroundResourceForDate(R.color.blue, dateIterator);*/
                        do{
                            String dateString = formatter.format(dateIterator);
                            Log.d("DateI:", i + " " + formatter.format(dateIterator));
                            ArrayList <String> list;
                            list = (ArrayList <String>) extraData.get(dateString);
                            if (list == null)
                                list = new ArrayList<String>();
                            list.add(eventTitle[i]);
                            extraData.put(dateString, list);

                            caldroidFragment.setBackgroundResourceForDate(backgroundColor, dateIterator);
                            cal.setTime(dateIterator);
                            cal.add(Calendar.DATE, 1);
                            dateIterator = cal.getTime();
                        } while (dateIterator.before(endDate));
                        //caldroidFragment.setSelectedDates(startDate, endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                //String test = "TEST";
                //extraData.put("24.09.2015", test);
                caldroidFragment.refreshView();
                /*caldroidFragment.setBackgroundResourceForDates(hashmap);
                caldroidFragment.setBackgroundResourceForDate(R.color.blue, blueDate);
                caldroidFragment.setBackgroundResourceForDate(R.color.green, greenDate);
                caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_light_red, redDate);
                caldroidFragment.setTextColorForDate(R.color.white, blueDate);
                caldroidFragment.setTextColorForDate(R.color.white, greenDate);
                caldroidFragment.setSelectedDates(firstDate, secondDate);*/

                //caldroidFragment.setDisableDates(disDates);
                //caldroidFragment.setTextColorForDate(R.color.white, redDate);


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        //caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
        caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }

        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE,true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Attach to the activity
        //FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        FragmentTransaction t = getFragmentManager().beginTransaction();
        //Fragment a = getParentFragment();

        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                /*Toast.makeText(getContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
                //HashMap <String, Object> data = caldroidFragment.getCaldroidData();
                HashMap <String, Object> extraData = caldroidFragment.getExtraData();
                String selectedDate = formatter.format(date);
                Log.d("Selected Date", selectedDate);
                ArrayList <String> eventList;
                eventList = (ArrayList <String>) extraData.get(selectedDate);
                String message = "No Events";
                if (eventList != null) {
                    Log.d("Date data:", eventList.toString());
                    message = eventList.toString();
                }
                MessageBox(formatter.format(date).toString(), message);

            }

            @Override
            public void onChangeMonth(int month, int year) {
                /*String text = "month: " + month + " year: " + year;
                Toast.makeText(getContext(), text,
                        Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                /*Toast.makeText(getContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onCaldroidViewCreated() {
                /*if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }*/
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) view.findViewById(R.id.textview);

        /*final Button customizeButton = (Button) view.findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    caldroidFragment.setShowNavigationArrows(true);
                    caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 14);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                caldroidFragment.setShowNavigationArrows(false);
                caldroidFragment.setEnableSwipe(false);

                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);
            }
        });*/

        //Button showDialogButton = (Button) view.findViewById(R.id.show_dialog_button);

        final Bundle state = savedInstanceState;
        /*showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    /*dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);*/
           /*         dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                //dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
                dialogCaldroidFragment.show(getFragmentManager(), dialogTag);
            }
        });*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
