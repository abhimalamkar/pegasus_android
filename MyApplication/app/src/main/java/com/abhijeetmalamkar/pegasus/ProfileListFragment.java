package com.abhijeetmalamkar.pegasus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.ListFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static java.lang.Integer.*;


public class ProfileListFragment extends ListFragment {

    Context context;
    User user;
    YearAdapter adapter;
    ArrayList<Year> years;
    public static String Tag = "ProfileListFragment";

    String[] categories = new String[]{"General Information","Child Care Provider Information","Part I - Income","Part II - Expenses","Part III - Cost of Goods Sold"};
    String[][] titles = new String[][]{new String[] {"SSN","First name","Last name","Occupation","Phone number","Email","Occupation","Dependent of Other","Full-Time Student","Presidencial Candidate","Blind","Street Address","City","State","Zip","Country"},
            new String[] {"Amount paid this year?","Name","Address","City, State, Zip","SSN or EIN","Phone"},
    new String[]{"Gross reciepts/sales","Returns and allowances","Other income"},new String[]{"Advertising","Car and truck expences","Commisions and fees","Contract labor","Depletion","Depreciation adjustment","Employee benifits","Insurance","Interest - mortgage","Interest - other","Legal and professional services","Office expense"},
    new String[]{"Inventory valuation method","Change in method","Beginning inventory","Purchases less personal","Cost of labor","Materials and supplies","Other costs","Ending inventory"}};

    ArrayList<TextView> title = new ArrayList<>();
    ArrayList<EditText> fields = new ArrayList<>();

    interface ClickYear{
        void selectedYear(ArrayList<Year> year,int position,String name);
    }

    public ProfileListFragment() {
    }


    public static ProfileListFragment newInstance(User user) {
        ProfileListFragment fragment = new ProfileListFragment();
        fragment.user = user;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.btn_logout);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_share,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btn_add) {
            getYear(true);
        }
        if(item.getItemId()==R.id.btn_share) {

        }
        return super.onOptionsItemSelected(item);
    }

    void getYear(Boolean wrong) {

        final EditText taskEditText = new EditText(context);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Add a Year")
                .setMessage(wrong ? "What Year you wants to Add?" : "Please Enter a Valid Year")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        if (task.toCharArray().length > 4) {
                            getYear(false);
                        }
                        saveTrips(user.email + "_years",years);
                        years.add(new Year(valueOf(task)));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        years = loadTrips(user.email + "_years");
        adapter = new YearAdapter(context,years);

        setListAdapter(adapter);
        setHasOptionsMenu(true);
        ((MainActivity) context).setTitle("Year Profile");
    }

    private void saveTrips(String filename, ArrayList<Year> years){
        try {
            FileOutputStream fos = context.openFileOutput(filename + ".bin", context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(years);
            oos.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Year> loadTrips(String filename){
        ArrayList<Year> list = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Year>) ois.readObject();
            ois.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    int selectedYear = 0;
    int selectedSection = 0;
    boolean yearCell = true;

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(context instanceof ClickYear) {
//            ((ClickYear) context).selectedYear(years,position,user.email + "_years");

           if (yearCell) {
               selectedYear = position;
               setListAdapter(new OneYearAdapter(context, categories));
               yearCell = false;
           } else {
               isYear = "goToSection";
               selectedSection = position;
               ScrollView view = getView().findViewById(R.id.scrollview);
               view.setVisibility(View.VISIBLE);

               for(int i = 0;i<16;i++) {
                   TextView text = view.findViewById(getResources().getIdentifier("textView_" + i, "id", context.getPackageName()));
                   final EditText editText = view.findViewById(getResources().getIdentifier("editText_" + i, "id", context.getPackageName()));
                   if (i < titles[position].length) {
                       text.setText(titles[position][i]);
                       editText.setText(years.get(selectedYear).data.get(titles[position][i]));
                       text.setVisibility(View.VISIBLE);
                       editText.setVisibility(View.VISIBLE);
                       editText.addTextChangedListener(textWatcher);

                       title.add(text);
                   } else {
                       text.setVisibility(View.GONE);
                       editText.setVisibility(View.GONE);
                   }
               }
               //setEditFields((LinearLayout) view.findViewById(R.id.inside_Listview),150,32,10,32);
            }
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            View view = getView();
            if (view != null) {
                if (!String.valueOf(s).equals("")) {
                    for (int j = 0; j < 16; j++) {
                        TextView text = view.findViewById(getResources().getIdentifier("textView_" + j, "id", context.getPackageName()));
                        final EditText editText = view.findViewById(getResources().getIdentifier("editText_" + j, "id", context.getPackageName()));

                        if(editText.getText().hashCode() == s.hashCode()) {
                            //add special checks
                            if (titles[selectedYear][j].equals("SSN")) {
                                Integer number = null;

                                try {
                                    number = parseInt(String.valueOf(s));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (s.length() <= 10 && number != null) {
                                    years.get(selectedYear).data.put(titles[selectedYear][j], String.valueOf(s));
                                } else {
                                    Toast.makeText(context, "Enter a Valid SSN", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                years.get(selectedYear).data.put(titles[selectedSection][j], String.valueOf(s));
                            }
                        }
                    }
                }
            }

            saveTrips(user.email + "_years",years);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    String isYear = "goToYear";

    void setYear(){
        yearCell = true;
        adapter = new YearAdapter(context,years);
        setListAdapter(adapter);
        isYear = "goToMain";
    }

    void setScrollGone(){
        isYear = "goToYear";
        ScrollView view = getView().findViewById(R.id.scrollview);
        view.setVisibility(View.GONE);
    }

//    void setEditFields(LinearLayout layout,int height,int gap,int numberOfFields,int leftMargin){
//        for (int i = 0;i<numberOfFields;i++) {
//            EditText lEditText = new EditText(context);
//            lEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT));
//            lEditText.setText("Text Here");
//            lEditText.setWidth(layout.getWidth()- (2*leftMargin));     // change width
//            lEditText.setHeight(height);   // change height
//            lEditText.setX(leftMargin);     // set absolute position of x
//            lEditText.setY(i * (height+gap) + layout.getTop());     // set absolute position of y
//            lEditText.setBackgroundColor(getResources().getColor(R.color.amber_50));
//            layout.addView(lEditText);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
