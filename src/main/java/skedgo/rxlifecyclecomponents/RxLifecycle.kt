package skedgo.rxlifecyclecomponents

import com.trello.rxlifecycle.LifecycleProvider
import rx.Completable
import rx.Observable
import rx.Single

fun <T, E> Observable<T>.bindToLifecycle(provider: LifecycleProvider<E>): Observable<T>
    = this.compose<T>(provider.bindToLifecycle<T>())

fun <T, E> Observable<T>.bindUntilEvent(provider: LifecycleProvider<E>, event: E): Observable<T>
    = this.compose<T>(provider.bindUntilEvent(event))

fun <E> Completable.bindToLifecycle(provider: LifecycleProvider<E>): Completable
    = this.compose(provider.bindToLifecycle<Completable>().forCompletable())

fun <E> Completable.bindUntilEvent(provider: LifecycleProvider<E>, event: E): Completable
    = this.compose(provider.bindUntilEvent<Completable>(event).forCompletable())

fun <T, E> Single<T>.bindToLifecycle(provider: LifecycleProvider<E>): Single<T>
    = this.compose(provider.bindToLifecycle<T>().forSingle<T>())

fun <T, E> Single<T>.bindUntilEvent(provider: LifecycleProvider<E>, event: E): Single<T>
    = this.compose(provider.bindUntilEvent<T>(event).forSingle<T>())
