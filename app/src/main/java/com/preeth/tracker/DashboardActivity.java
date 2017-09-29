package com.preeth.tracker;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import com.preeth.tracker.CircularLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG="Dashboard";
    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        CircularLayout circularLayout=(CircularLayout) findViewById(R.id.circular_layout);
        if(circularLayout != null) {
            circularLayout.setOnCircularItemClickListener(new CircularLayout.OnCircularItemClickListener() {
                @Override
                public void onCircularItemClick(int index) {

                    switch (index) {
                        case -1:
                            Toast.makeText(getApplicationContext(), index + " User Profile ", Toast.LENGTH_SHORT).show() ;
                            startActivity(new Intent(getApplicationContext(),UserProfileActivity.class).putExtra("userEmail",mAuth.getCurrentUser().getEmail()).putExtra("userName",mAuth.getCurrentUser().getDisplayName()));
                            break;
                        case 0:
                            Toast.makeText(getApplicationContext(), index + " Edit TimeSheet ", Toast.LENGTH_SHORT).show() ;
                            startActivity(new Intent(getApplicationContext(),EditTimeSheetActivity.class));
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), index + " BulletIn ", Toast.LENGTH_SHORT).show() ;
                            startActivity(new Intent(getApplicationContext(),BulletinActivity.class));
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), index + " Leave App ", Toast.LENGTH_SHORT).show() ;
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), index + " HR request ", Toast.LENGTH_SHORT).show() ;
                            break;
                    }
                }
            });
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView emailTextView=(TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
        TextView userTextView=(TextView) navigationView.getHeaderView(0).findViewById(R.id.userNameTextView);
        Intent intent=getIntent();
        emailTextView.setText(intent.getStringExtra("email"));
        userTextView.setText(intent.getStringExtra("user"));



    }

   /* public void initializeCalendar() {

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        calendar.setBackgroundColor(getResources().getColor(R.color.background_colorpurple));
        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.gray));
        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.white_greyish));
        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.navy));
        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.white_greyish);
        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }

        });

    } */



    public void goToActivity(FirebaseUser user){
        String userEmail=user.getEmail();
        Intent intent=new Intent(this,LoginActivity.class);
        intent.putExtra("user",userEmail);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
