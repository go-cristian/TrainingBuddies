package co.iyubinest.runtasticcodingcontest.members;
import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {

  public static final Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>() {
    @Override public Member createFromParcel(Parcel source) {
      return new Member(source);
    }

    @Override public Member[] newArray(int size) {
      return new Member[size];
    }
  };
  private final int id;
  private final String firstName, lastName;
  private final double distance;
  private final double minutes;

  public Member(int id, String firstName, String lastName, double distance, double minutes) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.distance = distance;
    this.minutes = minutes;
  }

  protected Member(Parcel in) {
    this.id = in.readInt();
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.distance = in.readDouble();
    this.minutes = in.readDouble();
  }

  public int id() {
    return id;
  }

  String firstName() {
    return firstName;
  }

  String lastName() {
    return lastName;
  }

  public double averagePace() {
    return minutes / distance;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.firstName);
    dest.writeString(this.lastName);
    dest.writeDouble(this.distance);
    dest.writeDouble(this.minutes);
  }
}
