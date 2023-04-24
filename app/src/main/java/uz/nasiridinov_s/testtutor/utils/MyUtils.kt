package uz.nasiridinov_s.testtutor.utils

import androidx.viewbinding.ViewBinding
import timber.log.Timber


fun <T : ViewBinding> T.scope(block: T.() -> Unit) {
    block(this)
}

fun timber(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}