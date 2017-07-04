package com.adilsal33m.menucard;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class Login extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Code for fullscreen

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        topToolBar.setTitle(R.string.app_name);
        topToolBar.setOverflowIcon(getResources().getDrawable(R.drawable.overflow_icon));
        setSupportActionBar(topToolBar);


        // Check that the activity_main is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity_main layout
             LoginFragment firstFragment = LoginFragment.newInstance();

            // In case this activity_main was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.login_exit:
                finish();
                break;
            case R.id.login_about:
                break;
            case R.id.register:
                break;
            case R.id.login_pass:
                onPass();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLogin(String account, String pass) {

    }

    @Override
    public void onPass() {
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void setTitleLogin() {

    }
}
