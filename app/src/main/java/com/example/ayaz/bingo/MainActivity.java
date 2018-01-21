package com.example.ayaz.bingo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<Button> blocks;
    GridLayout G1;
    ArrayList<Integer> RowsGone;
    ArrayList<Integer> ColumnGone;
    ArrayList<Integer> CrossGone;
    Integer BingoCounter=0;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blocks=new ArrayList<>();
        G1=findViewById(R.id.grid_layout);
        ColumnGone=new ArrayList<>();
        RowsGone =new ArrayList<>();
        BingoText=new ArrayList<>();
        CrossGone=new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        reference= FirebaseDatabase.getInstance().getReference();
        else {
            Toast.makeText(this,"Auth token Expired",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Login.class));
            finish();
        }
        Toolbar myToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        BindView();
        Populate();
        AssignOnListener();


    }

    private void toFirebase(String number) {
        DatabaseReference ref;
        if(reference!=null) {
            ref = reference.child("BINGO");
            ref.child(mAuth.getCurrentUser().getDisplayName());



        }
    }

    private void AssignOnListener() {
        for(int i=0;i<25;i++)
        {
            View.OnClickListener listener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OnClick(v);
                }
            };
            blocks.get(i).setOnClickListener(listener);

        }



    }

    private void OnClick(View v) {
      int i= blocks.indexOf(v);
      blocks.get(i).setEnabled(false);
      toFirebase(blocks.get(i).getText().toString());
      int row=RowChecker();
      int col=ColumnChecker();
      int cros=CrossChecker();
      if(row==0)
      ;
      else
      {
          BingoCounter++;
          RowsGone.add(row);
      }
       if(col==0)
            ;
       else {
           ColumnGone.add(col);
           BingoCounter++;
       }
       if(cros==0)
       ;
       else
       {
           CrossGone.add(cros);
           BingoCounter++;

       }

       //
    for(i=0;i<BingoCounter;i++)
    {
     BingoText.get(i).setBackgroundColor(Color.RED);
    }


    }
    ArrayList<TextView> BingoText;
    private void BindView()
    {
        BingoText.add((TextView)findViewById(R.id.bingo_text_b));
        BingoText.add((TextView)findViewById(R.id.bingo_text_i));
        BingoText.add((TextView)findViewById(R.id.bingo_text_n));
        BingoText.add((TextView)findViewById(R.id.bingo_text_g));
        BingoText.add((TextView)findViewById(R.id.bingo_text_o));




        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<25;i++) {

            Button b=new Button(this);
            b.setLayoutParams(params);
            blocks.add(b);
            }
       int i;
        for(i=0;i<25;i++)
        G1.addView(blocks.get(i));




    }

    void Populate()
    {
        ArrayList<Integer> numbers=new ArrayList<>();
        for(int i=1;i<=25;i++)
            numbers.add(i);
        Collections.shuffle(numbers);

        for(int i=0;i<25;i++)
            blocks.get(i).setText(""+numbers.get(i));
    }


    int RowChecker() {

        boolean isOkay = true;
        for (int i = 0; i < 5; i++)
            if(blocks.get(i).isEnabled())
                isOkay=false;
        if(isOkay&&!RowsGone.contains(1))
            return 1;


        isOkay=true;
        for (int i = 5; i < 10; i++) {
            if(blocks.get(i).isEnabled())
                isOkay=false;
        }
        if(isOkay&&!RowsGone.contains(2))
            return 2;

        isOkay=true;
        for (int i = 10; i < 15; i++) {
            if(blocks.get(i).isEnabled())
                isOkay=false;

        }
        if(isOkay&&!RowsGone.contains(3))
            return 3;


        isOkay=true;
        for (int i = 15; i < 20; i++) {
            if(blocks.get(i).isEnabled())
                isOkay=false;
        }
        if(isOkay&&!RowsGone.contains(4))
            return 4;

        isOkay=true;
        for (int i = 20; i < 25; i++) {
            if(blocks.get(i).isEnabled())
                isOkay=false;

        }
        if(isOkay&&!RowsGone.contains(5))
            return 5;

return 0;
    }




    int ColumnChecker()
    {
        boolean isOkay = true;
        for (int i = 0; i < 21; i=i+5)
            if(blocks.get(i).isEnabled())
                isOkay=false;
        if(isOkay&&!ColumnGone.contains(1))
            return 1;

        isOkay=true;
        for (int i = 1; i < 22; i=i+5) {
            if(blocks.get(i).isEnabled())
                isOkay=false;
        }
        if(isOkay&&!ColumnGone.contains(2))
            return 2;

        isOkay=true;
        for (int i = 2; i < 23; i=i+5) {
            if(blocks.get(i).isEnabled())
                isOkay=false;

        }
        if(isOkay&&!ColumnGone.contains(3))
            return 3;


        isOkay=true;
        for (int i = 3; i < 24; i=i+5) {
            if(blocks.get(i).isEnabled())
                isOkay=false;
        }
        if(isOkay&&!ColumnGone.contains(4))
            return 4;

        isOkay=true;
        for (int i = 4; i < 25; i+=5) {
            if(blocks.get(i).isEnabled())
                isOkay=false;

        }
        if(isOkay&&!ColumnGone.contains(5))
            return 5;

        return 0;

    }
    int CrossChecker()
    {
        boolean isOkay = true;
        for (int i = 0; i < 25; i=i+6)
            if(blocks.get(i).isEnabled())
                isOkay=false;
        if(isOkay&&!CrossGone.contains(1))
            return 1;

        isOkay=true;
        for (int i = 4; i < 21; i=i+4) {
            if(blocks.get(i).isEnabled())
                isOkay=false;
        }
        if(isOkay&&!CrossGone.contains(2))
            return 2;

        return 0;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                CreateDialogueForLogOut();
                return true;

            case R.id.action_favorite:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
    private void CreateDialogueForLogOut() {
        AlertDialog.Builder LogoutDialogue = new AlertDialog.Builder(this);
        LogoutDialogue.setTitle(getResources().getString(R.string.connfirm_logout));
        LogoutDialogue.setMessage(getResources().getString(R.string.logout_mesg));
        LogoutDialogue.setIcon(R.drawable.ic_error_outline_black_24dp);
        LogoutDialogue.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               mAuth.signOut();
               startActivity(new Intent(getBaseContext(),Login.class));
               finish();
            }
        });

        LogoutDialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        LogoutDialogue.create();
        LogoutDialogue.show();



    }

}
