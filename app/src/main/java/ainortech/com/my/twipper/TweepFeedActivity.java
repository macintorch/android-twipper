package ainortech.com.my.twipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweepFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweep_feed);

        setTitle("Twipper Feed");

        final ListView tweepListView = findViewById(R.id.tweepListView);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");

        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.setLimit(20);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        List<Map<String,String>> tweepData = new ArrayList<Map<String,String>>();

                        for (ParseObject tweep : objects) {

                                Map<String, String> tweepInfo = new HashMap<String, String>();

                                tweepInfo.put("content", tweep.getString("tweet"));

                                tweepInfo.put("username", "by " + tweep.getString("username"));

                                tweepData.add(tweepInfo);

                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(TweepFeedActivity.this, tweepData, android.R.layout.simple_list_item_2, new String[] {"content", "username"}, new int[] {android.R.id.text1, android.R.id.text2});

                        tweepListView.setAdapter(simpleAdapter);
                    }
                }
            }
        });
    }
}
