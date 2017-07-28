package co.iyubinest.runtasticcodingcontest.groups;
import co.iyubinest.runtasticcodingcontest.members.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Group {

  private final String id;
  private final String name;
  private final List<Member> members;

  Group(String id, String name, List<Member> members) {
    this.id = id;
    this.name = name;
    this.members = members;
  }

  public static Group biggestGroup(List<Group> groups) {
    if (groups == null || groups.size() == 0) throw new IllegalArgumentException();
    Group result = groups.get(0);
    for (Group group : groups) {
      if (group.members().size() > result.members().size()) result = group;
    }
    return result;
  }

  public static Member fastestMember(List<Group> groups) {
    if (groups == null || groups.size() == 0) throw new IllegalArgumentException();
    Member result = null;
    for (Group group : groups) {
      for (Member member : group.members()) {
        if (result == null && member != null) result = member;
        double paceMember = member != null ? member.averagePace() : 0;
        double paceResult = result != null ? result.averagePace() : 0;
        if (paceMember < paceResult) result = member;
      }
    }
    if (result == null) throw new IllegalArgumentException();
    return result;
  }

  public static List<List<Member>> trainingBuddies(List<Member> members) {
    Collections.sort(members, new Comparator<Member>() {
      @Override public int compare(Member o1, Member o2) {
        double v = o1.averagePace() - o2.averagePace();
        return v == 0 ? 0 : v > 0 ? 1 : -1;
      }
    });
    List<List<Member>> buddies = new ArrayList<>();
    for (int i = 0; i < members.size(); i++) {
      ArrayList<Member> buddiesGroup = new ArrayList<>();
      buddies.add(buddiesGroup);
      for (int j = i; j < members.size(); j++) {
        if (Math.abs(members.get(i).averagePace() - members.get(j).averagePace()) <= 1.0) {
          buddiesGroup.add(members.get(j));
        } else {
          break;
        }
      }
    }
    List<List<Member>> buddiesFinal = new ArrayList<>();
    for (int i = 0; i < buddies.size(); i++) {
      if (buddies.get(i).size() > 1) {
        if (i == 0) {
          buddiesFinal.add(buddies.get(i));
        } else {
          if (buddiesFinal.size() == 0) {
            buddiesFinal.add(buddies.get(i));
          } else if (buddiesFinal.size() > 0 && buddies.size() > 0) {
            if (!buddiesFinal.get(buddiesFinal.size() - 1).containsAll(buddies.get(i))) {
              buddiesFinal.add(buddies.get(i));
            }
          }
        }
      }
    }
    return buddiesFinal;
  }

  public static int secondQuestion(List<List<Member>> trainingBuddies) {
    if (trainingBuddies.size() == 0) return -1;
    return trainingBuddies.get(0).get(trainingBuddies.get(0).size() - 1).id();
  }

  public List<Member> members() {
    return members;
  }

  public String name() {
    return name;
  }

  public String id() {
    return id;
  }
}
