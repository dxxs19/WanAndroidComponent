package com.x.lib_base.bus

/**
 * @desc  普通的RxBus
 * @author wei
 * @date  2021/12/31
 **/
class Bus private constructor() : RxBus() {

    companion object {
        private var bus: Bus? = null
            get() {
                if (field == null) {
                    field = Bus()
                }
                return field
            }

        fun get(): Bus {
            return bus ?: Bus()
        }

        fun close() {
            bus?.release()
            bus = null
        }
    }
}

/**
 *  接收聊天消息专用的 RxBus
 */
class ChatBus private constructor() : RxBus() {
    companion object {
        private var bus: ChatBus? = null
            get() {
                if (field == null) {
                    field = ChatBus()
                }
                return field
            }

        fun get(): ChatBus {
            return bus ?: ChatBus()
        }

        fun close() {
            bus?.release()
            bus = null
        }
    }
}