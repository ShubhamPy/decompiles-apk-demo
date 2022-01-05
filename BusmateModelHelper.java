package in.linus.busmate.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BusmateModelHelper implements Parcelable {
    public static final Parcelable.Creator<BusmateModelHelper> CREATOR = new Parcelable.Creator<BusmateModelHelper>() {
        public BusmateModelHelper createFromParcel(Parcel parcel) {
            return new BusmateModelHelper(parcel);
        }

        public BusmateModelHelper[] newArray(int i) {
            return new BusmateModelHelper[i];
        }
    };
    String From;
    String To;
    String arriveTime;
    String busDetails;
    int busID;
    String busName;
    String bus_stop_latitude;
    String bus_stop_longitude;
    String bus_stop_name;
    String departTime;
    String waitingTime;

    public int describeContents() {
        return 0;
    }

    public BusmateModelHelper() {
    }

    protected BusmateModelHelper(Parcel parcel) {
        this.bus_stop_name = parcel.readString();
        this.bus_stop_latitude = parcel.readString();
        this.bus_stop_longitude = parcel.readString();
        this.busName = parcel.readString();
        this.busDetails = parcel.readString();
        this.From = parcel.readString();
        this.To = parcel.readString();
        this.busID = parcel.readInt();
        this.arriveTime = parcel.readString();
        this.departTime = parcel.readString();
        this.waitingTime = parcel.readString();
    }

    public String getBus_stop_name() {
        return this.bus_stop_name;
    }

    public void setBus_stop_name(String str) {
        this.bus_stop_name = str;
    }

    public String getBus_stop_latitude() {
        return this.bus_stop_latitude;
    }

    public void setBus_stop_latitude(String str) {
        this.bus_stop_latitude = str;
    }

    public String getBus_stop_longitude() {
        return this.bus_stop_longitude;
    }

    public void setBus_stop_longitude(String str) {
        this.bus_stop_longitude = str;
    }

    public String getBusName() {
        return this.busName;
    }

    public void setBusName(String str) {
        this.busName = str;
    }

    public String getBusDetails() {
        return this.busDetails;
    }

    public void setBusDetails(String str) {
        this.busDetails = str;
    }

    public String getFrom() {
        return this.From;
    }

    public void setFrom(String str) {
        this.From = str;
    }

    public String getTo() {
        return this.To;
    }

    public void setTo(String str) {
        this.To = str;
    }

    public int getBusID() {
        return this.busID;
    }

    public void setBusID(int i) {
        this.busID = i;
    }

    public String getArriveTime() {
        return this.arriveTime;
    }

    public void setArriveTime(String str) {
        this.arriveTime = str;
    }

    public String getDepartTime() {
        return this.departTime;
    }

    public void setDepartTime(String str) {
        this.departTime = str;
    }

    public String getWaitingTime() {
        return this.waitingTime;
    }

    public void setWaitingTime(String str) {
        this.waitingTime = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bus_stop_name);
        parcel.writeString(this.bus_stop_latitude);
        parcel.writeString(this.bus_stop_longitude);
        parcel.writeString(this.busName);
        parcel.writeString(this.busDetails);
        parcel.writeString(this.From);
        parcel.writeString(this.To);
        parcel.writeInt(this.busID);
        parcel.writeString(this.arriveTime);
        parcel.writeString(this.departTime);
        parcel.writeString(this.waitingTime);
    }
}
