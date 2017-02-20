package skedgo.rxlifecyclecomponents

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

abstract class RxAppCompatActivity : AppCompatActivity(), LifecycleProvider<ActivityEvent> {
  private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

  @CheckResult
  override fun lifecycle(): Observable<ActivityEvent> {
    return lifecycleSubject.asObservable()
  }

  @CheckResult
  override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
    return RxLifecycle.bindUntilEvent<T, ActivityEvent>(lifecycleSubject, event)
  }

  @CheckResult
  override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
    return RxLifecycleAndroid.bindActivity(lifecycleSubject)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleSubject.onNext(ActivityEvent.CREATE)
  }

  @CallSuper
  override fun onStart() {
    super.onStart()
    lifecycleSubject.onNext(ActivityEvent.START)
  }

  @CallSuper
  override fun onResume() {
    super.onResume()
    lifecycleSubject.onNext(ActivityEvent.RESUME)
  }

  @CallSuper
  override fun onPause() {
    lifecycleSubject.onNext(ActivityEvent.PAUSE)
    super.onPause()
  }

  @CallSuper
  override fun onStop() {
    lifecycleSubject.onNext(ActivityEvent.STOP)
    super.onStop()
  }

  @CallSuper
  override fun onDestroy() {
    lifecycleSubject.onNext(ActivityEvent.DESTROY)
    super.onDestroy()
  }
}
