package taxiapp.safetrip.modal.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Leanberg on 7/16/2017.
 */

public class PojoLocation implements Parcelable{
    String placeAddress;
    LatLng latLng;

    public PojoLocation() {
    }

    protected PojoLocation(Parcel in) {
        placeAddress = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<PojoLocation> CREATOR = new Creator<PojoLocation>() {
        @Override
        public PojoLocation createFromParcel(Parcel in) {
            return new PojoLocation(in);
        }

        @Override
        public PojoLocation[] newArray(int size) {
            return new PojoLocation[size];
        }
    };

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeAddress);
        dest.writeParcelable(latLng, flags);
    }
}
