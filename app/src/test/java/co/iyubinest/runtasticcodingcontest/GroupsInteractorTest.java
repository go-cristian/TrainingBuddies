package co.iyubinest.runtasticcodingcontest;
import co.iyubinest.runtasticcodingcontest.groups.Group;
import co.iyubinest.runtasticcodingcontest.groups.GroupsInteractor;
import co.iyubinest.runtasticcodingcontest.groups.HttpGroupsInteractor;
import co.iyubinest.runtasticcodingcontest.members.Member;
import co.iyubinest.runtasticcodingcontest.retrofit.AppRetrofit;
import io.reactivex.subscribers.TestSubscriber;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class GroupsInteractorTest {

  private MockWebServer server;
  private MockResponse groupsResponse = new MockResponse().setBody(fromFile("groups.json"));
  private MockResponse error = new MockResponse().setHttp2ErrorCode(500);
  private TestSubscriber<List<Group>> subscriber = new TestSubscriber<>();
  private GroupsInteractor interactor;

  private static String fromFile(String name) {
    try {
      InputStream resource = GroupsInteractorTest.class.getClassLoader().getResourceAsStream(name);
      BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
      StringBuilder result = new StringBuilder();
      String partial = reader.readLine();
      while (partial != null) {
        result.append(partial);
        partial = reader.readLine();
      }
      return result.toString();
    } catch (Exception ignored) {
      throw new IllegalArgumentException("File not found");
    }
  }

  @Before public void setup() throws Exception {
    server = new MockWebServer();
    interactor = new HttpGroupsInteractor(AppRetrofit.build(server.url("/").toString()));
  }

  @After public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void failOnError() throws Exception {
    server.enqueue(error);
    interactor.get().subscribe(subscriber);
    subscriber.assertError(Exception.class);
  }

  @Test public void biggestGroup() throws Exception {
    server.enqueue(groupsResponse);
    interactor.get().subscribe(subscriber);
    List<Group> groups = subscriber.values().get(0);
    Assert.assertThat(Group.biggestGroup(groups).name(), is("South Park"));
    Assert.assertThat(Group.biggestGroup(groups).members().size(), is(7));
  }

  @Test public void fastestMember() throws Exception {
    server.enqueue(groupsResponse);
    interactor.get().subscribe(subscriber);
    List<Group> groups = subscriber.values().get(0);
    Assert.assertThat(Group.fastestMember(groups).id(), is(8));
  }

  @Test public void trainingBuddies() throws Exception {
    server.enqueue(groupsResponse);
    interactor.get().subscribe(subscriber);
    List<Group> groups = subscriber.values().get(0);
    List<Member> simpsons = groups.get(0).members();
    List<Member> futurama = groups.get(1).members();
    List<Member> southPark = groups.get(2).members();
    List<List<Member>> simpsonsTrainingBuddies = Group.trainingBuddies(simpsons);
    List<List<Member>> futuramaTrainingBuddies = Group.trainingBuddies(futurama);
    List<List<Member>> southParkTrainingBuddies = Group.trainingBuddies(southPark);
    Assert.assertThat(Group.secondQuestion(southParkTrainingBuddies), is(18));
    Assert.assertThat(Group.secondQuestion(futuramaTrainingBuddies), is(-1));
    Assert.assertThat(Group.secondQuestion(simpsonsTrainingBuddies), is(3));
  }
}