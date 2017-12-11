package ainortech.com.my.twipper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {


    ArrayList<String> users = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.tweet_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.tweet) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Send a Tweet");

            final EditText tweetEditText = new EditText(this);

            builder.setView(tweetEditText);

            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Info", tweetEditText.getText().toString());

                    ParseObject tweet = new ParseObject("Tweet");

                    tweet.put("username", ParseUser.getCurrentUser().getUsername());
                    tweet.put("tweet", tweetEditText.getText().toString());

                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(UsersActivity.this,"Tweet sent successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(UsersActivity.this,"Tweet failed to send.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();

        } else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        setTitle("Twipper User List");

        if (ParseUser.getCurrentUser().get("isFollowing") == null) {

            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing", emptyList);
        }

        final ListView usersListView = findViewById(R.id.usersListView);

        usersListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);

        usersListView.setAdapter(arrayAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView) view;

                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Row is checked");

                    //ParseUser.getCurrentUser().getList("isFollowing").add(users.get(i));
                    ParseUser.getCurrentUser().add("isFollowing", users.get(i));

                    ParseUser.getCurrentUser().saveInBackground();
                } else {
                    Log.i("Info", "Row is not checked");

                   // ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(i));

                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(i));
                    List newList = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", newList);

                    ParseUser.getCurrentUser().saveInBackground();
                }
            }
        });

        users.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        for (ParseUser user: objects) {
                            users.add(user.getUsername());

                        }

                        arrayAdapter.notifyDataSetChanged();

                        // check if user already follow another user or not

                        for (String username : users) {
                            if (ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {
                                usersListView.setItemChecked(users.indexOf(username), true);
                            }
                        }
                    }
                }
            }
        });
    }
}
