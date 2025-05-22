package com.example.lostfoundupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RemoveActivity extends AppCompatActivity {
    Post post;
    TextView TextView;
    Button RemoveBTN;
    ManagerDB managerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        post = (Post) getIntent().getSerializableExtra("Post");
        managerDB = new ManagerDB(this);

        TextView = findViewById(R.id.TextView);
        RemoveBTN = findViewById(R.id.RemoveButton);

        StringBuilder st = new StringBuilder();
        st.append(post.getType()).append(" ").append(post.getDescription()).append("\n\n");
        st.append(getRelativeDate(post.getDate())).append("\n\n");
        st.append("At: ").append(post.getLocation()).append("\n\n");
        TextView.setText(st);

        RemoveBTN.setEnabled(true);
        RemoveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerDB.deletePost((int) post.getId());
                TextView.setText("This post has been deleted");
                RemoveBTN.setEnabled(false);
            }
        });
    }

    private String getRelativeDate(String postDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
        try {
            Date postDate = sdf.parse(postDateStr);
            Date currentDate = new Date();

            long diffInMillis = currentDate.getTime() - postDate.getTime();
            long daysAgo = TimeUnit.MILLISECONDS.toDays(diffInMillis);

            if (daysAgo == 0) {
                return "Today";
            } else if (daysAgo == 1) {
                return "1 day ago";
            } else {
                return daysAgo + " days ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }
}