package co.iyubinest.runtasticcodingcontest.groups;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

class AndroidGroupInteractor implements GroupsInteractor {

  private final HttpGroupsInteractor interactor;

  AndroidGroupInteractor(HttpGroupsInteractor interactor) {
    this.interactor = interactor;
  }

  @Override public Flowable<List<Group>> get() {
    return interactor.get().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }
}
