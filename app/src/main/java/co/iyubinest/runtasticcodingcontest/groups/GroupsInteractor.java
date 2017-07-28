package co.iyubinest.runtasticcodingcontest.groups;
import io.reactivex.Flowable;
import java.util.List;

public interface GroupsInteractor {

  Flowable<List<Group>> get();
}
