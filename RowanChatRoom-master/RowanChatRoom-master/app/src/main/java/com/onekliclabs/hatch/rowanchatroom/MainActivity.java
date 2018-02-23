package com.onekliclabs.hatch.rowanchatroom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Harold Hatch on 7/8/15.
 */
public class MainActivity extends Activity implements View.OnClickListener
{
    /**
     *
     */
    private static final String FILE_REGISTER = "register";

    //Protocr
    static final String ADD_LIKE = "/*";
    static final String REMOVE_LIKE = "/**";

    //widgets
    private ChatArrayAdapter adp;
    private ListView list;
    private EditText chatText;
    private Button send;
    private RadioButton rbutton;

    //private Client mClient;

    public static String userName;
    public static Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load profile info from file
       // try
       // {
       //     loadProfileInfo();
       // } catch (FileNotFoundException e)
       // {
       //     startActivity(new Intent(this, LoginActivity.class));
       // }

        //initialize widgets an
        initWidgets();

        // connect to the server
        //new AsyncTask().execute("");

        //new Handler().postDelayed(new Runnable() {
        //    @Override
         //   public void run() {
        //        /* Create an Intent that will start the Menu-Activity. */
        //        mClient.sendMessage(userName);
        //    }
        //}, 100);



        // listener if user presses send
        send.setOnClickListener(this);

        //keep scrolled to the bottom
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);


        System.out.println("here 1");
        adp.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adp.getCount() - 1);
            }
        });

    }

    /**
     * When user presses the radio button this method is called
     * @param view

    public void onRadioButtonClicked(View view)
    {
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        if (checked)
        {
            mClient.sendMessage(ADD_LIKE + (adp.getCount()));
        }
        else
        {
            mClient.sendMessage(REMOVE_LIKE +(adp.getCount()));
        }
    }*/



    /**
     * Loads information from file
     * @throws FileNotFoundException
     */
    public void loadProfileInfo() throws FileNotFoundException
    {
        File profile = new File(getFilesDir(), FILE_REGISTER);

        //Throw exception if file doesn't exist
        if (!profile.exists())
            throw new FileNotFoundException("File Does't Exist");

        BufferedReader profileReader;

        try
        {
            profileReader = new BufferedReader(new FileReader(profile));

            userName = profileReader.readLine();

            profileReader.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Initialize all widgets and classes
     */
    public void initWidgets()
    {
        chatText = findViewById(R.id.chat_editText);
        rbutton =  findViewById(R.id.radioButton_like);
        send =  findViewById(R.id.button_send);
        list = findViewById(R.id.listView_1);
        list.setDivider(null);
        list.setDividerHeight(0);
        adp = new ChatArrayAdapter(this, R.layout.bubble_layout, userName);

    }

    @Override
    public void onClick(View v)
    {
        // Start background thread to send message to socket
        String message = chatText.getText().toString();

        // sends the message to the server
        //if (mClient != null)
        //{
        //    mClient.sendMessage(message);
        //}

        adp.add(new ChatBox(message));

        //refresh the list
        adp.notifyDataSetChanged();
        chatText.setText("");
        hideSoftKeyboard();



    }


    /**
     * Hides software keyboard from view
     */
    public void hideSoftKeyboard()
    {
        View view = getCurrentFocus();

        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        // resets username and picture if they have been changed
        if (pictureUri != null)
        {
            adp = new ChatArrayAdapter(this, R.layout.bubble_layout, userName, pictureUri);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }


    /**
     * ASychronise task that operates in the background waiting to
     * recieve a message from the server. Once a message is a recived it
     * updates the chat list with the new message.
     *
    private class AsyncTask extends android.os.AsyncTask<String,String, Client>
    {
         protected Client doInBackground(String... message)
         {

             //create a Client object
             mClient = new Client(new Client.OnMessageReceived()

             {
                @Override
                //here the messageReceived method is implemented
                public void onMessageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }

                @Override
                public void onErrorMessageReceived()
                {
                    publishProgress("Server Error");
                }
             });

             mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);


            if(values[0].equals("Server Error"))
                postErrorMessage();
            else
                interpretMessage(values[0]);

        }
    }

    private void postErrorMessage()
    {
        Toast.makeText(getApplicationContext(),"Couldn't connect to server",Toast.LENGTH_LONG).show();
    }

    private void interpretMessage(String value)
    {
        if (value.contains(ADD_LIKE))
        {
            String subString = value.replace(ADD_LIKE,"");
            int position = Integer.parseInt(subString);
            ChatBox chb = adp.getItem(position);
            chb.addLike();
            adp.notifyDataSetChanged();

        }else if(value.contains(REMOVE_LIKE))
        {
            String subString = value.replace(REMOVE_LIKE,"");
            int position = Integer.parseInt(subString);
            ChatBox chb = adp.getItem(position);
            chb.removeLike();
            adp.notifyDataSetChanged();

        }else
        {
            adp.add(new ChatBox(value));
        }
    }**/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Only show items in the action bar relevant to this screen
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        startActivity(new Intent(this, EditProfileActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
}


