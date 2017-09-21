package com.abhijeetmalamkar.pegasus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoginRegistrationFragment.GetLoginRegistation,
        DocumentsManagerFragment.AddDocument,TripListFragment.GetUser,TripDetailsFragment.CloseFragment {
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

        if(user != null) {
            Toast.makeText(MainActivity.this,user.getEmail()+" LoggedIn", Toast.LENGTH_SHORT).show();
            if(!user.getLoggedIn()) {
                presentLoginScreen();
            }
        } else {
            presentLoginScreen();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btn_logout) {
            if(user!=null){
                user.setLoggedIn(false);
                saveUser(user,user.getEmail());
                User _user = new User();
                _user.setEmail(NONE);
                _user.setLoggedIn(false);
                saveUser(_user,CURRENT_USER);
            }
            presentLoginScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    LoginRegistrationFragment loginRegistrationFragment;
    private void presentLoginScreen(){
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
                            .add(R.id.fragment_container, TripListFragment.newInstance())
                            .addToBackStack(TripListFragment.Tag).addToBackStack("trip").commit();
                    mainLayout.setVisibility(View.GONE);
                    break;
                case R.id.imageButton1:
                    documentsManagerFragment = DocumentsManagerFragment.newInstance();
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, documentsManagerFragment)
                            .addToBackStack(DocumentsManagerFragment.Tag).addToBackStack("document").commit();
                    mainLayout.setVisibility(View.GONE);
                    break;
                case R.id.imageButton2:
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, UserProfileFragment.newInstance())
                            .addToBackStack(UserProfileFragment.Tag).commit();
                    mainLayout.setVisibility(View.GONE);
                    break;
                case R.id.imageButton3:

                    Log.i("TAG", "onClick: ");
                    break;
                case R.id.imageButton4:
                    Log.i("TAG", "onClick: ");
                    break;
                case R.id.imageButton5:
                    Log.i("TAG", "onClick: ");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportActionBar() != null && !getSupportActionBar().isShowing()){
            getSupportActionBar().show();
        }
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void saveUser(User user,String filename){
        try {
            FileOutputStream fos = this.openFileOutput(filename + ".bin", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private User loadUser(String filename){
        User user = null;
        try {
            FileInputStream fis = this.openFileInput(filename + ".bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
            ois.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Boolean login(String name, String password) {
        user = loadUser(name);

        if(user != null && user.getPassword().equals(password)){
            registration(user);
            return true;
        }

        return false;
    }

    @Override
    public void registration(User _user) {
        _user.setLoggedIn(true);
        user = _user;
        saveUser(user,user.getEmail());
        saveUser(user,CURRENT_USER);
        close();
        Toast.makeText(MainActivity.this,user.getEmail()+" Loggin IN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        getFragmentManager().beginTransaction().remove(loginRegistrationFragment).commit();
        if(getSupportActionBar() != null && !getSupportActionBar().isShowing()){
            getSupportActionBar().show();
        }
        mainLayout.setVisibility(View.VISIBLE);
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
                .add(R.id.fragment_container, fragment,TripDetailsFragment.Tag)
                .addToBackStack(TripListFragment.Tag).commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            getImage.getImage(photo);
        }
    }

    @Override
    public void exit() {
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(TripDetailsFragment.Tag)).commit();
        update.update_();
    }
}

interface GetImage{
    void getImage(Bitmap image);
}



