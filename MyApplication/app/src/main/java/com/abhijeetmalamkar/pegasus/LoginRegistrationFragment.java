package com.abhijeetmalamkar.pegasus;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class LoginRegistrationFragment extends Fragment {

    View view_login;
    View view_registor;
    Context mContext;
    GetLoginRegistation listner;

    public interface GetLoginRegistation{
        Boolean login(String name, String password);
        void registration(User _user);
        void close();
    }

    public LoginRegistrationFragment() {
        // Required empty public constructor
    }


    public static LoginRegistrationFragment newInstance() {
        LoginRegistrationFragment fragment = new LoginRegistrationFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        getView().findViewById(R.id.btn_login).setOnClickListener(login);
        getView().findViewById(R.id.btn_register).setOnClickListener(register);
        getView().findViewById(R.id.btn_login_).setOnClickListener(login_);
        getView().findViewById(R.id.btn_register_).setOnClickListener(register_);
        view_login = getView().findViewById(R.id.linear_login);
        view_registor = getView().findViewById(R.id.linear_registration);
    }

    private View.OnClickListener login_ = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = getView();
            EditText email = (EditText) view.findViewById(R.id.email_login);
            EditText password = (EditText) view.findViewById(R.id.password_login);
            if(listner.login(email.getText().toString(),password.getText().toString())){
                listner.close();
            }
        }
    };

    private View.OnClickListener register_ = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = getView();
            EditText name = (EditText) view.findViewById(R.id.name_registration);
            EditText address = (EditText) view.findViewById(R.id.address_registration);
            EditText email = (EditText) view.findViewById(R.id.email_registration);
            EditText password = (EditText) view.findViewById(R.id.password_registration);
            listner.registration(new User(email.getText().toString(),password.getText().toString(),
                    name.getText().toString(),address.getText().toString()));
        }
    };

    private View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            view_login.setVisibility(View.VISIBLE);
            view_registor.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            view_login.setVisibility(View.GONE);
            view_registor.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_registration, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(context instanceof GetLoginRegistation){
            listner = (GetLoginRegistation)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
