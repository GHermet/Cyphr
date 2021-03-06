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


public class RegisterFragment extends Fragment {
    EditText username;
    EditText email;
    EditText password;
    EditText confpassword;
    Button  register;
    Button cancel;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_layout, container,
                false);
        ActionBar actionBar= getActivity().getActionBar();
        actionBar.setIcon(R.drawable.cyphr_logo);
        actionBar.setTitle("");

        username =(EditText) view.findViewById(R.id.registration_username_text);
        email = (EditText) view.findViewById(R.id.registration_email_text);
        password =(EditText) view.findViewById(R.id.registration_password_text);
        confpassword = (EditText) view.findViewById(R.id.registration_confpassword_text);
        register = (Button) view.findViewById(R.id.register_button);
        cancel = (Button) view.findViewById(R.id.cancel_registration_button);

        fragment =null;


        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fragment =  new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_left,R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();
            }
        });







        return view;

    }
}