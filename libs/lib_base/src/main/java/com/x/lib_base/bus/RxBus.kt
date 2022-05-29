package com.x.lib_base.bus

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * @desc
 * @author wei
 * @date  2021/12/31
 **/
abstract class RxBus {

    private val subject = PublishSubject.create<Event>().toSerialized()

    fun send(event: Event) {
        subject.onNext(event)
    }

    fun send(id: Int, any: Any? = null) {
        subject.onNext(object : Event {
            override val id: Int
                get() = id
            override val any: Any?
                get() = any
        })
    }

    fun with(owner: LifecycleOwner) = RxSender(subject, owner)

    fun release() {
        subject.onComplete()
    }

    class RxSender constructor(private val subject: Subject<Event>, private val owner: LifecycleOwner) {
        private var flag = HashSet<Int>()
        private var event: Lifecycle.Event? = null
        private var acceptAll = true

        /**
         * 接收指定消息，默认接收所有消息
         */
        fun accept(vararg id: Int): RxSender {
            id.forEach { flag.add(it) }
            acceptAll = false
            return this
        }

        fun bindUtil(event: Lifecycle.Event): RxSender {
            this.event = event
            return this
        }

        /**
        * 我们经常会在onNext中执行一些ui操作，当执行完耗时操作触发onNext的时候，
        * Activity有可能已经destory了，我们期望是就不去执行onNext中的内容了。
        * 这种操作使用rxlifecycle来实现非常容易，只需一行代码即可：
        * observable.compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
        **/
        @SuppressLint("CheckResult")
        fun subscribe(block: (ev: Event) -> Unit) {
            subject.compose { o ->
                event?.let { o.bindUntilEvent(owner, it) } ?: o.bindToLifecycle(owner)
            }.filter {
                if (!acceptAll) {
                    flag.contains(it.id)
                } else true
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    block(it)
                }
        }
    }

    interface Event {
        val id: Int
        val any: Any?
    }
}