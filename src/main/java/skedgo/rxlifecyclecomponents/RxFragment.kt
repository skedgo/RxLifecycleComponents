package skedgo.rxlifecyclecomponents

import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.View
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

abstract class RxFragment : Fragment(), LifecycleProvider<FragmentEvent> {
  private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

  @CheckResult
  override fun lifecycle(): Observable<FragmentEvent> {
    return lifecycleSubject.asObservable()
  }

  @CheckResult
  override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
    return RxLifecycle.bindUntilEvent<T, FragmentEvent>(lifecycleSubject, event)
  }

  @CheckResult
  override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
    return RxLifecycleAndroid.bindFragment(lifecycleSubject)
  }

  override fun onAttach(activity: android.app.Activity?) {
    super.onAttach(activity)
    lifecycleSubject.onNext(FragmentEvent.ATTACH)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleSubject.onNext(FragmentEvent.CREATE)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
  }

  override fun onStart() {
    super.onStart()
    lifecycleSubject.onNext(FragmentEvent.START)
  }

  override fun onResume() {
    super.onResume()
    lifecycleSubject.onNext(FragmentEvent.RESUME)
  }

  override fun onPause() {
    lifecycleSubject.onNext(FragmentEvent.PAUSE)
    super.onPause()
  }

  override fun onStop() {
    lifecycleSubject.onNext(FragmentEvent.STOP)
    super.onStop()
  }

  override fun onDestroyView() {
    lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    super.onDestroyView()
  }

  override fun onDestroy() {
    lifecycleSubject.onNext(FragmentEvent.DESTROY)
    super.onDestroy()
  }

  override fun onDetach() {
    lifecycleSubject.onNext(FragmentEvent.DETACH)
    super.onDetach()
  }
}
