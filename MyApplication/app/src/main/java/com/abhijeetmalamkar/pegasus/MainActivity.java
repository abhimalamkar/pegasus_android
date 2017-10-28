package com.abhijeetmalamkar.pegasus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements LoginRegistrationFragment.GetLoginRegistation,
        DocumentsManagerFragment.AddDocument, TripListFragment.GetUser, TripDetailsFragment.CloseFragment,
        DatePickerFragment.Close, RecyclerViewDataAdapter.Open, SingleDocumentFragment.Mail, ProfileListFragment.ClickYear {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    ArrayList<View> buttons;
    View mainLayout;
    User user;

    private static String CURRENT_USER = "CURRENT_USER";
    private static String NONE = "none";
    private static final int CAMERA_REQUEST = 1888;
    DocumentsManagerFragment documentsManagerFragment;
    GetImage getImage;
    public Update update;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");

    private SlideDateTimeListener simpleListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            Toast.makeText(MainActivity.this,
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(MainActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        buttons = mainLayout.getTouchables();
        for (View button : buttons) {
            button.setOnClickListener(listener);
        }

        user = loadUser(CURRENT_USER);

        if (user != null) {
            Toast.makeText(MainActivity.this, user.getEmail() + " LoggedIn", Toast.LENGTH_SHORT).show();
            if (!user.getLoggedIn()) {
                presentLoginScreen();
            }
        } else {
            presentLoginScreen();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_logout) {
            if (user != null) {
                user.setLoggedIn(false);
                saveUser(user, user.getEmail());
                User _user = new User();
                _user.setEmail(NONE);
                _user.setLoggedIn(false);
                saveUser(_user, CURRENT_USER);
            }

            presentLoginScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    LoginRegistrationFragment loginRegistrationFragment;

    private void presentLoginScreen() {
        loginRegistrationFragment = LoginRegistrationFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginRegistrationFragment)
                .commit();
        mainLayout.setVisibility(View.GONE);
        Intent intent = new Intent(MainActivity.this, PagerActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton:

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, TripListFragment.newInstance())
                            .addToBackStack(TripListFragment.Tag).addToBackStack("trip").commit();

                    break;
                case R.id.imageButton1:
                    documentsManagerFragment = DocumentsManagerFragment.newInstance();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, documentsManagerFragment)
                            .addToBackStack(DocumentsManagerFragment.Tag).addToBackStack("document").commit();
                    break;
                case R.id.imageButton2:
                    for (int i = 0;i<2;i++) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 2);
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (i == 0 ? "5614458551" : "5618091288")));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }

                    break;
                case R.id.imageButton3:
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, DatePickerFragment.newInstance(), DatePickerFragment.Tag)
                            .addToBackStack(DatePickerFragment.Tag).commit();
                    break;
                case R.id.imageButton4:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ProfileListFragment.newInstance(user), ProfileListFragment.Tag)
                            .addToBackStack(ProfileListFragment.Tag).commit();
                    break;
                case R.id.imageButton5:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, FragmentAboutMe.newInstance(),FragmentAboutMe.Tag)
                            .addToBackStack(FragmentAboutMe.Tag).commit();
                    Log.i("TAG", "onClick: ");
                    break;
                default:
                    break;
            }

            mainLayout.setVisibility(View.GONE);
        }
    };

    @Override
    public void onBackPressed() {
        ProfileListFragment fragment = (ProfileListFragment) getFragmentManager().findFragmentByTag(ProfileListFragment.Tag);
        FragmentAboutMe fragmentAboutMe = (FragmentAboutMe) getFragmentManager().findFragmentByTag(FragmentAboutMe.Tag);
        if (fragment != null) {
            if (fragment.isYear.equals("goToSection")) {
                fragment.setScrollGone();

            } else if (fragment.isYear.equals("goToYear")) {
                fragment.setYear();
            } else if (fragment.isYear.equals("goToMain")) {
                setMain();
            }

        } else if (fragmentAboutMe != null) {
            if (fragmentAboutMe.to.equals("Sections")) {
                fragmentAboutMe.setSections();
            } else {
                setMain();
            }
        } else {
            setMain();
        }
    }

    void setMain(){
        super.onBackPressed();
        if (getSupportActionBar() != null && !getSupportActionBar().isShowing()) {
            getSupportActionBar().show();
        }
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void saveUser(User user, String filename) {
        try {
            FileOutputStream fos = this.openFileOutput(filename + ".bin", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User loadUser(String filename) {
        User user = null;
        try {
            FileInputStream fis = this.openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Boolean login(String name, String password) {
        user = loadUser(name);

        if (user != null && user.getPassword().equals(password)) {
            registration(user);
            return true;
        }

        return false;
    }

    @Override
    public void registration(User _user) {
        _user.setLoggedIn(true);
        user = _user;
        saveUser(user, user.getEmail());
        saveUser(user, CURRENT_USER);
        close();
        Toast.makeText(MainActivity.this, user.getEmail() + " Loggin IN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        getFragmentManager().beginTransaction().remove(loginRegistrationFragment).commit();
        if (getSupportActionBar() != null && !getSupportActionBar().isShowing()) {
            getSupportActionBar().show();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public User getUserDocuments() {
        return user;
    }

    @Override
    public void details(Trip trip, int position) {
        TripDetailsFragment fragment = TripDetailsFragment.newInstance(position);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, TripDetailsFragment.Tag).commit();
        fragment.trip = trip;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            getImage.getImage(photo);
        }
    }

    @Override
    public void exit() {
        TripDetailsFragment fragment = (TripDetailsFragment) getFragmentManager().findFragmentByTag(TripDetailsFragment.Tag);
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .remove(fragment).commit();
        }
    }

    @Override
    public void getDate() {

    }

    @Override
    public void closeDatePicker() {
        DatePickerFragment fragment = (DatePickerFragment) getFragmentManager().findFragmentByTag(DatePickerFragment.Tag);
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    @Override
    public void openSingle(SingleDocument document,int position) {
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, SingleDocumentFragment.newInstance(document,position), SingleDocumentFragment.Tag)
                .addToBackStack(SingleDocumentFragment.Tag).commit();
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void openMonth(DocumentsCollection collection,int position) {
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, DocumentCollectionFragment.newInstance(collection), DocumentCollectionFragment.Tag)
                .addToBackStack(DocumentCollectionFragment.Tag).commit();
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void singleDocument(SingleDocument document,int position) {
        sendEmail();
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"abhijeetmalamkar@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void selectedYear(ArrayList<Year> year, int position, String name) {

    }
}

interface GetImage {
    void getImage(Bitmap image);
}



