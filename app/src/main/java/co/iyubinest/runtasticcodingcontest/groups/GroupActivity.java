package co.iyubinest.runtasticcodingcontest.groups;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.iyubinest.runtasticcodingcontest.R;
import co.iyubinest.runtasticcodingcontest.members.Member;
import co.iyubinest.runtasticcodingcontest.members.MembersActivity;
import co.iyubinest.runtasticcodingcontest.retrofit.AppRetrofit;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

  @BindView(R.id.groups) GroupWidget groupsWidget;
  private static final String BASE_URL = "http://codingcontest.runtastic.com/";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.group_activity);
    ButterKnife.bind(this);
    GroupsInteractor interactor =
        new AndroidGroupInteractor(new HttpGroupsInteractor(AppRetrofit.build(BASE_URL)));
    interactor.get().subscribe(new Consumer<List<Group>>() {
      @Override public void accept(List<Group> groups) throws Exception {
        groupsWidget.add(groups);
        groupsWidget.addGroupSelectedListener(new GroupWidget.OnGroupSelected() {
          @Override public void onSelected(Group group) {
            showMembers(group.members());
          }
        });
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        showError();
      }
    });
  }

  private void showError() {
    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
  }

  private void showMembers(List<Member> members) {
    startActivity(MembersActivity.intent(this, new ArrayList<>(members)));
  }
}
