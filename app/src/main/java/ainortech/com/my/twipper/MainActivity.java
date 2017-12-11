package ainortech.com.my.twipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;

    EditText passwordEditText;

    public void signIn(View view) {

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.i("Info", "Logged In");
                } else  {
                    Log.i("Info", "Please Sign Up");
                    Toast.makeText(MainActivity.this, e.getMessage() ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void signUp(View view) {

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        ParseUser parseUser = new ParseUser();

        parseUser.setUsername(usernameEditText.getText().toString());
        parseUser.setPassword(passwordEditText.getText().toString());

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Info", "Signed up");
                } else {
                    Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")) ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
