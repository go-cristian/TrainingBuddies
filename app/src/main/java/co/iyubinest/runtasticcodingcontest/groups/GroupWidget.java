package co.iyubinest.runtasticcodingcontest.groups;
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

public class GroupWidget extends RecyclerView {

  private static final String IMAGE_FORMAT =
      "http://download.runtastic.com/meetandcode/mobile_and_web_2016/images/groups/%s.png";
  private GroupAdapter adapter;

  interface OnGroupSelected {

    void onSelected(Group group);
  }

  public GroupWidget(Context context) {
    this(context, null);
  }

  public GroupWidget(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setLayoutManager(new LinearLayoutManager(getContext()));
    setHasFixedSize(true);
    adapter = new GroupAdapter();
  }

  public void add(List<Group> groups) {
    adapter.add(groups);
    setAdapter(adapter);
  }

  public void addGroupSelectedListener(OnGroupSelected onGroupSelected) {
    adapter.setOnGroupSelected(onGroupSelected);
  }

  static class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {

    private final List<Group> groups = new ArrayList<>();
    private OnGroupSelected onGroupSelected;

    @Override public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      GroupHolder holder = new GroupHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false));
      holder.onPositionSelected(new GroupHolder.OnPositionSelected() {
        @Override public void onSelected(int position) {
          onGroupSelected.onSelected(groups.get(position));
        }
      });
      return holder;
    }

    @Override public void onBindViewHolder(GroupHolder holder, int position) {
      holder.group(groups.get(position));
    }

    @Override public int getItemCount() {
      return groups.size();
    }

    public void add(List<Group> groups) {
      this.groups.addAll(groups);
    }

    void setOnGroupSelected(OnGroupSelected onGroupSelected) {
      this.onGroupSelected = onGroupSelected;
    }
  }

  static class GroupHolder extends RecyclerView.ViewHolder {

    interface OnPositionSelected {

      void onSelected(int position);
    }

    @BindView(R.id.group_image) ImageView pictureView;
    @BindView(R.id.group_name) TextView nameView;
    @BindView(R.id.group_count) TextView countView;
    @BindString(R.string.group_count_format) String countFormat;
    private OnPositionSelected listener;

    GroupHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          listener.onSelected(getAdapterPosition());
        }
      });
    }

    void group(Group group) {
      nameView.setText(group.name());
      countView.setText(String.format(Locale.getDefault(), countFormat, group.members().size()));
      Picasso.with(itemView.getContext())
          .load(String.format(IMAGE_FORMAT, group.id()))
          .into(pictureView);
    }

    void onPositionSelected(OnPositionSelected listener) {
      this.listener = listener;
    }
  }
}
