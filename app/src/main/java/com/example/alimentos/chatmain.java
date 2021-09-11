package com.example.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class chatmain extends AppCompatActivity {


    private FirebaseListAdapter<ChatMessage> adapter;
    DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);




     /*   // User is already signed in. Therefore, display
        // a welcome Toast
        Toast.makeText(this,
                "Welcome " +  FirebaseDatabase.getInstance().getReference("users").child("name").get(),
                Toast.LENGTH_LONG)
                .show();
*/
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");




   /*     mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds :snapshot.getChildren()) {

                    String namme = "" + ds.child("name").getValue();

                    Toast.makeText(chatmain.this, "Welcome " + namme, Toast.LENGTH_LONG).show();

                }
              }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });   */


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
             userEmail = user.getEmail();
            Toast.makeText(chatmain.this, "Welcome " + userEmail, Toast.LENGTH_LONG).show();

        } else {
            // No user is signed in
        }







        // Load chat room contents
        displayChatMessages();


















        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("chats")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),user.getEmail())

                        );

                // Clear the input
                input.setText("");
            }
        });
    }

    private void displayChatMessages() {

        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("chats")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}