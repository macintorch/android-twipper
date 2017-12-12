package ainortech.com.my.twipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweepFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweep_feed);

        ListView tweepListView = findViewById(R.id.tweepListView);

        List<Map<String,String>> tweepData = new ArrayList<Map<String,String>>();

        for (int i = 1; i <= 5; i++) {
            Map<String, String> tweepInfo = new HashMap<String, String>();

            tweepInfo.put("content","Tweep content " + Integer.toString(i));

            tweepInfo.put("username", "User " + Integer.toString(i));

            tweepData.add(tweepInfo);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, tweepData, android.R.layout.simple_list_item_2, new String[] {"content", "username"}, new int[] {android.R.id.text1, android.R.id.text2});

        tweepListView.setAdapter(simpleAdapter);
    }
}
