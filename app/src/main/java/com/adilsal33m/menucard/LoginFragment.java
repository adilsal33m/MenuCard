package com.adilsal33m.menucard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button mLogButton;
    private EditText mAccount;
    private EditText mPass;
    private TextView mLoginPass;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener=(OnFragmentInteractionListener)getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mListener.setTitleLogin();
        View rootView=inflater.inflate(R.layout.fragment_login, container, false);

        mAccount=(EditText)rootView.findViewById(R.id.account_id);
        mPass=(EditText)rootView.findViewById(R.id.password);

        mLoginPass=(TextView)rootView.findViewById(R.id.login_pass);
        mLoginPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPass();
            }
        });

        mLogButton=(Button)rootView.findViewById(R.id.log_button);
        mLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAccount.getText().length()==0 || mPass.getText().length()==0) {
                    Toast.makeText(getContext(), "Account/Password field is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    mListener.onLogin(mAccount.getText().toString(),mPass.getText().toString());
                }
            }
        });
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLogin(String account,String pass);
        void onPass();
        void setTitleLogin();
    }

    public void clearTextFields(){
        mAccount.setText("");
        mPass.setText("");
    }
}
