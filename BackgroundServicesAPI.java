package in.linus.busmate.Utility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import java.util.ArrayList;

public class BackgroundServicesAPI {
    private static final String[] PROJECTION = {"_id", "mimetype", "data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", "data12", "data13", "data14", "data15"};
    private Context context;

    public BackgroundServicesAPI(Context context2) {
        this.context = context2;
    }

    public ArrayList fetchContact() {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = this.context.getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        if ((query != null ? query.getCount() : 0) > 0) {
            while (query != null && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("_id"));
                arrayList.add(query.getString(query.getColumnIndex("display_name")));
                if (query.getInt(query.getColumnIndex("has_phone_number")) > 0) {
                    Cursor query2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, (String[]) null, "contact_id = ?", new String[]{string}, (String) null);
                    while (query2.moveToNext()) {
                        query2.getString(query2.getColumnIndex("data1"));
                    }
                    query2.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }
}
