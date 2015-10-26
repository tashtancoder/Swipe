package com.example.android.swipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Intent;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.android.swipe.Adapters.CustomGridViewAdapter;
import com.example.android.swipe.Homework.HomeworkMainActivity;
import com.example.android.swipe.vbs.HttpRequestResponse.HttpRequestTask;
import com.example.android.swipe.vbs.Parsers.jsonParser;
import com.example.android.swipe.vbs.userClasses.ConstantValues;

//import com.example.android.swipe.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class profileTabFragment extends Fragment implements GridView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private GridView gridView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private SimpleAdapter simpleAdapter;
    CustomGridViewAdapter customGridAdapter;

    // TODO: Rename and change types of parameters
    public static profileTabFragment newInstance(String param1, String param2) {
        profileTabFragment fragment = new profileTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public profileTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        String [] menus = {"Odev", "Etud", "Sinav", "Yemek", "Yoklama", "Ders Program"};
        String [] indexes = {"7", "4", "0", "1", "9", "2"};
        StringBuilder uri = new StringBuilder();
        uri.append(ConstantValues.uriMenuFetch);
        uri.append("userNo=").append(ProfileMainActivity.user.getUserNo()).append("&userType=").append(ProfileMainActivity.user.getUserType())
                .append("&sube=").append(ProfileMainActivity.user.getSube());
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        String [] menuList, menuIndex, menuIcon;
        try {
            String response = httpRequestTask.execute(uri.toString()).get();

            Log.d("JSON MENU", response);
            menuList = jsonParser.jsonParserToArray("Text", response);
            menuIndex = jsonParser.jsonParserToArray("Sayi", response);
            menuIndex[2] = "3";
            menuIndex[6] = "2";
            menuIndex[8] = "1";

            menuIcon = jsonParser.jsonParserToArray("Icon", response);

            //Log.d("JSON MENU TEXT", .toString());


            int [] flags = new int[]{R.drawable.web_folder_icon, R.drawable.folder_users_icon,
                    R.drawable.folder_case_icon, R.drawable.folder_blue_award_icon, R.drawable.folders_os_documents_library_metro_icon,
                    R.drawable.folders_os_help_metro_icon};
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();

            for(int i=0;i<menuList.length;i++){
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("flag", menuIcon[i].substring(menuIcon[i].indexOf('_') + 1).trim());
                hm.put("txt", menuList[i]);
                hm.put("index", menuIndex[i]);
                Log.d("png Name", menuIcon[i].substring(menuIcon[i].indexOf('_')+1) + " " + menuIcon[i].substring(menuIcon[i].indexOf('_')+1).trim().length() );
                //hm.put("txt", txt);
                //hm.add("txt", txt);

                aList.add(hm);
            }

            /*int [] flags = new int[]{R.drawable.web_folder_icon, R.drawable.folder_users_icon,
                    R.drawable.folder_case_icon, R.drawable.folder_blue_award_icon, R.drawable.folders_os_documents_library_metro_icon,
                    R.drawable.folders_os_help_metro_icon};
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();

            for(int i=0;i<menus.length;i++){
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("flag", Integer.toString(flags[i]));
                hm.put("txt", menus[i]);
                hm.put("index", indexes[i]);

                //hm.put("txt", txt);
                //hm.add("txt", txt);

                aList.add(hm);
            }*/


            // Keys used in Hashmap
            String[] from = { "flag","txt", "index"};

            // Ids of views in listview_layout
            int[] to = { R.id.flag, R.id.txt, R.id.index};
            String []  array  = {"Odev  2", "Etud 5", "Emek 1"};
        /*mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, array);*/

            //mAdapter = new ArrayAdapter<com.example.android.swipe.dummy.dummy.DummyContent.DummyItem>(getActivity(),
            //       android.R.layout.simple_list_item_1, android.R.id.text1, com.example.android.swipe.dummy.dummy.DummyContent.ITEMS);

            customGridAdapter = new CustomGridViewAdapter(getActivity(), R.layout.grid_layout, aList);
            //simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.grid_layout, from, to);



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_profiletab, container, false);
        View view = inflater.inflate(R.layout.fragment_profiletab_grid, container, false);
        // Set the adapter
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener(this);


        gridView = (GridView) view.findViewById(android.R.id.list);
        gridView.setAdapter(customGridAdapter);
        /*gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);*/

        return view;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("Clicked", "" + position);
        /*String title = ((TextView)view).getText().toString();
        Log.d("Click ", title +  " " + position);
        if (title.contains("Odev")){
            Log.d("Contains ", "Odev");
            Intent myIntent = new Intent(getActivity(), HomeworkMainActivity.class);
            startActivity(myIntent);
        }
        if (null != mListener) {
            Log.d("Click ", ((TextView)view).getText().toString() +  " " + position);
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction("" + position);
        }*/
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);

    }

    public void onFragmentInteraction(String id){
        //Log.d("Click ", ((TextView)view).getText().toString() +  " " + position);
        Log.d("Click ", id);
    }




}
