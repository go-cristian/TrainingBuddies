package co.iyubinest.runtasticcodingcontest.groups;
import co.iyubinest.runtasticcodingcontest.members.Member;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class HttpGroupsInteractor implements GroupsInteractor {

  private final GroupsService service;

  public HttpGroupsInteractor(Retrofit retrofit) {
    service = retrofit.create(GroupsService.class);
  }

  @Override public Flowable<List<Group>> get() {
    return service.groups().flatMap(new Function<JsonGroups, Publisher<List<Group>>>() {
      @Override public Publisher<List<Group>> apply(JsonGroups jsonGroups) throws Exception {
        return Flowable.just(map(jsonGroups));
      }
    });
  }

  private List<Group> map(JsonGroups jsonGroups) {
    List<Group> result = new ArrayList<>(jsonGroups.groups.size());
    for (JsonGroup jsonGroup : jsonGroups.groups) {
      result.add(map(jsonGroup));
    }
    return result;
  }

  private Group map(JsonGroup jsonGroup) {
    List<Member> members = new ArrayList<>(jsonGroup.members.size());
    for (JsonMember jsonMember : jsonGroup.members) {
      members.add(map(jsonMember));
    }
    return new Group(String.valueOf(jsonGroup.group_id), jsonGroup.group_name, members);
  }

  private Member map(JsonMember jsonMember) {
    return new Member(jsonMember.member_id, jsonMember.member_first_name,
        jsonMember.member_last_name, jsonMember.member_distance_covered,
        jsonMember.member_active_minutes);
  }

  private interface GroupsService {

    @GET("mobile_and_web_2016/groups.json") Flowable<JsonGroups> groups();
  }

  private static class JsonGroups {

    public List<JsonGroup> groups;
  }

  private static class JsonGroup {

    int group_id;
    String group_name;
    List<JsonMember> members;
  }

  private static class JsonMember {

    int member_id;
    String member_first_name, member_last_name;
    double member_distance_covered;
    double member_active_minutes;
  }
}
