package com.example.nyuad;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	public static final int NOTIFICATION_ID = 42;
    private String notificationTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationTitle = this.getResources().getString(R.string.notification);
        Button addNotificationBtn = (Button) findViewById(R.id.add_notification);
        addNotificationBtn.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		createNotification("DUDE");}});
        
        Button deleteNotificationBtn = (Button) findViewById(R.id.delete_notification);
        deleteNotificationBtn.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		deleteNotification();}});    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        sup();
        return true;
    }   
   
    public void sup() {
    	Intent intent = new Intent(this, MyAlarmManager.class);
    	long scTime = 60*2000; //2mins
    	PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

    	AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scTime, pendingIntent);
    }
    
    public void translate(String annoucement) {
    	Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, annoucement);
        sendIntent.setType("mimix/mimix");
        startActivity(sendIntent);
    }
    
    public final void createNotification(String title){
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = new Notification(R.drawable.notification, notificationTitle, System.currentTimeMillis());  
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);        	
        final String notificationTitle = title;
        final String notificationDesc = getResources().getString(R.string.notification_desc);         
        	
        //Notification & Vibration
        notification.setLatestEventInfo(this, notificationTitle, notificationDesc, pendingIntent);
        notification.vibrate = new long[] {0,200,100,200,100,200};
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
        
    public void deleteNotification(){
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
   }
}