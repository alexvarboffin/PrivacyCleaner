package aa.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class GmailMessageViewModel implements ViewModel, Parcelable, Serializable {

    public String threadId;
    public String id;
    public int icon;

    public GmailMessageViewModel(String id, String threadId, int icon) {
        this.threadId = threadId;
        this.id = id;
        this.icon = icon;
    }

    private GmailMessageViewModel(Parcel in) {
        threadId = in.readString();
        id = in.readString();
        icon = in.readInt();
    }

    public static final Creator<GmailMessageViewModel> CREATOR = new Creator<GmailMessageViewModel>() {
        @Override
        public GmailMessageViewModel createFromParcel(Parcel in) {
            return new GmailMessageViewModel(in);
        }

        @Override
        public GmailMessageViewModel[] newArray(int size) {
            return new GmailMessageViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(threadId);
        dest.writeString(id);
        dest.writeInt(icon);
    }
}
