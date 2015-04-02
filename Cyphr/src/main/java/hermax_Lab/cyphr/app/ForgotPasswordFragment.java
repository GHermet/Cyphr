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
import android.widget.ImageButton;

/**
 * Created by macbook on 5/9/14.
 */
public class ForgotPasswordFragment extends Fragment {
    ImageButton goback;
    Fragment fragment = null;
    EditText email;
    Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgot_password_layout, container,
                false);
        ActionBar actionBar = getActivity().getActionBar();

        goback = (ImageButton) view.findViewById(R.id.forgotpassword_return_buttton);
        email = (EditText) view.findViewById(R.id.forgotpassword_email_text);
        send = (Button) view.findViewById(R.id.forgotpassword_email_send_button);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null).setCustomAnimations(R.anim.slide_in_left, R.anim.do_nothing).replace(R.id.content_frame, fragment).commit();
            }
        });


        return view;
    }
}
