package co.iyubinest.runtasticcodingcontest.members;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.iyubinest.runtasticcodingcontest.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MembersWidget extends RecyclerView {

  private MemberAdapter adapter;

  public MembersWidget(Context context) {
    this(context, null);
  }

  public MembersWidget(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setLayoutManager(new LinearLayoutManager(getContext()));
    setHasFixedSize(true);
    adapter = new MemberAdapter();
  }

  public void add(List<Member> members) {
    adapter.add(members);
    setAdapter(adapter);
  }

  private static class MemberAdapter extends RecyclerView.Adapter<MemberHolder> {

    private final List<Member> members = new ArrayList<>();

    @Override public MemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new MemberHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.members_item, parent, false));
    }

    @Override public void onBindViewHolder(MemberHolder holder, int position) {
      holder.group(members.get(position));
    }

    @Override public int getItemCount() {
      return members.size();
    }

    public void add(List<Member> members) {
      this.members.addAll(members);
    }
  }

  static class MemberHolder extends RecyclerView.ViewHolder {

    private static final String IMAGE_FORMAT =
        "http://download.runtastic.com/meetandcode/mobile_and_web_2016/images/members/%s.png";
    @BindView(R.id.member_image) ImageView pictureView;
    @BindView(R.id.member_name) TextView nameView;
    @BindView(R.id.member_pace) TextView paceView;
    @BindString(R.string.member_pace_format) String paceFormat;

    MemberHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    void group(Member member) {
      nameView.setText(member.firstName() + " " + member.lastName());
      paceView.setText(String.format(Locale.getDefault(), paceFormat, member.averagePace()));
      Picasso.with(itemView.getContext())
          .load(String.format(IMAGE_FORMAT, member.id()))
          .into(pictureView);
    }
  }
}
