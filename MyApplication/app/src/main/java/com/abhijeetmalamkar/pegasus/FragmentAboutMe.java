package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentAboutMe extends Fragment {

    public static String Tag = "FragmentAboutMe";

    Context context;
    ArrayList<String> headers = new ArrayList<>();

    public FragmentAboutMe() {
        // Required empty public constructor
    }

    public static FragmentAboutMe newInstance() {
        FragmentAboutMe fragment = new FragmentAboutMe();
        Bundle args = new Bundle();

        return fragment;
    }

    ArrayList<ArrayList<Service>> allServices;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/gameofchats-762ca.appspot.com/o/message_movies%2F12323439-9729-4941-BA07-2BAE970967C7.mov?alt=media&token=3e37a093-3bc8-410f-84d3-38332af9c726");
        VideoView videoView = view.findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.start();

        allServices = new ArrayList<>();

        ListView list = view.findViewById(R.id.listView);
        try {
            JSONObject bookkeeping = new JSONObject(JSONData.BOOKKEEPING_PRICES);
            JSONArray array = bookkeeping.getJSONArray("services");
            ArrayList<Service> bookkeeping_array = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                bookkeeping_array.add(new Service(object.getString("plan_title"), object.getString("additional"), "$" + object.getString("cost")));
            }
            allServices.add(bookkeeping_array);

            JSONObject services = new JSONObject(JSONData.SERVICES);
            JSONArray section_array = services.getJSONArray("sections");

            for (int i = 0; i < section_array.length(); i++) {
                ArrayList<Service> section = new ArrayList<>();
                JSONArray array1 = ((JSONObject) section_array.get(i)).getJSONArray("service");

                for (int j = 0; j < array1.length(); j++) {
                    JSONObject object = (JSONObject) array1.get(j);
                    section.add(new Service(object.getString("form"), "", "$" + object.getString("cost")));
                }

                allServices.add(section);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        headers.add("BookKeeping Prices");
        headers.add("Business Returns");
        headers.add("Individual Returns");
        headers.add("Other Returns");

        list.setAdapter(new ServicesAdapter(context, headers));
        list.setOnItemClickListener(listener);
        ((MainActivity) context).setTitle("About Pegasus");
    }

    String to = "Services";

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListView list = getView().findViewById(R.id.listView);
            list.setAdapter(new CustomAdapter(context, allServices.get(i)));
            list.setOnItemClickListener(null);
            to = "Sections";
        }
    };

    public void setSections(){
        ListView list = getView().findViewById(R.id.listView);
        list.setAdapter(new ServicesAdapter(context, headers));
        list.setOnItemClickListener(listener);
        to = "Services";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_about_me, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
