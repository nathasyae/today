package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today;
//
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.net.Uri;
//import android.provider.CalendarContract;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
//public class tets {
//    ContentResolver cr = this.getContentResolver();
//    ContentValues cv = new ContentValue();
//    cv.put(CalendarContract.Events.TITLE, taskTitleInput.text.toString());
//    cv.put(CalendarContract.Events.DESCRIPTION, taskDetailInput.text.toString());
//    cv.put(CalendarContract.Events.CALENDAR_ID, 1);
//    cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
//    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,cv);
//
//    Toast.makeText(this, "Successfully added to calendar", Toast.LENGTH_SHORT).show()
//}
