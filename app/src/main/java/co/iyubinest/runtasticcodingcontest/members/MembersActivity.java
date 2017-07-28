package co.iyubinest.runtasticcodingcontest.members;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.iyubinest.runtasticcodingcontest.R;
import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity {

  private static final String MEMBERS = "MEMBERS";
  @BindView(R.id.members) MembersWidget membersWidget;

  public static Intent intent(Activity activity, ArrayList<Member> members) {
    Intent intent = new Intent(activity, MembersActivity.class);
    intent.putParcelableArrayListExtra(MEMBERS, members);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.members_activity);
    ButterKnife.bind(this);
    membersWidget.add(members());
  }

  private List<Member> members() {
    return getIntent().getParcelableArrayListExtra(MEMBERS);
  }
}
