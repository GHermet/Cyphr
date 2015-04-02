package hermax_Lab.cyphr.app;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class LoginFragment extends Fragment {
    EditText username;
    EditText password;
    Button Login;
    Button Register;
    Fragment fragment;
    TextView forgotPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.login_layout, container,
                false);
        ActionBar actionBar= getActivity().getActionBar();
        actionBar.setIcon(R.drawable.cyphr_logo);
        actionBar.setTitle("");
       username = (EditText) view.findViewById(R.id.username_text);
       password= (EditText) view.findViewById(R.id.password_text);
        Login = (Button) view.findViewById(R.id.login_button);
        Register = (Button) view.findViewById(R.id.signup_button);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password_text);
        fragment = null;


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment = new RegisterFragment();

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_right, R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();




            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(true);

                fragment = new ForgotPasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_right, R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();
            }
        });


        return view;


    }






}
