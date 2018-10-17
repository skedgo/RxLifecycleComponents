package skedgo.rxlifecyclecomponents

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

abstract class RxAppCompatDialogFragment : AppCompatDialogFragment(), LifecycleProvider<FragmentEvent> {
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

  @CallSuper
  override fun onAttach(context: Context?) {
    super.onAttach(context)
    lifecycleSubject.onNext(FragmentEvent.ATTACH)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleSubject.onNext(FragmentEvent.CREATE)
  }

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
  }

  @CallSuper
  override fun onStart() {
    super.onStart()
    lifecycleSubject.onNext(FragmentEvent.START)
  }

  @CallSuper
  override fun onResume() {
    super.onResume()
    lifecycleSubject.onNext(FragmentEvent.RESUME)
  }

  @CallSuper
  override fun onPause() {
    lifecycleSubject.onNext(FragmentEvent.PAUSE)
    super.onPause()
  }

  @CallSuper
  override fun onStop() {
    lifecycleSubject.onNext(FragmentEvent.STOP)
    super.onStop()
  }

  @CallSuper
  override fun onDestroyView() {
    lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    super.onDestroyView()
  }

  @CallSuper
  override fun onDestroy() {
    lifecycleSubject.onNext(FragmentEvent.DESTROY)
    super.onDestroy()
  }

  @CallSuper
  override fun onDetach() {
    lifecycleSubject.onNext(FragmentEvent.DETACH)
    super.onDetach()
  }
}
