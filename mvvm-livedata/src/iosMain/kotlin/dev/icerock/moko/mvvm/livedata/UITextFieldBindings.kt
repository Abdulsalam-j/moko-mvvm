/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField

fun <T : String?> LiveData<T>.bindStringToTextFieldText(
    textField: UITextField
): Closeable {
    return bind(textField) { value ->
        if (this.text == value) return@bind

        this.text = value
    }
}

fun <T : StringDesc?> LiveData<T>.bindStringDescToTextFieldText(
    textField: UITextField
): Closeable {
    return map { it?.localized() }.bindStringToTextFieldText(textField)
}

fun MutableLiveData<String>.bindStringTwoWayToTextFieldText(
    textField: UITextField
): Closeable {
    val readCloseable = bindStringToTextFieldText(textField)

    val writeCloseable = textField.setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()

        if (value == newText) return@setEventHandler

        value = newText
    }

    return readCloseable + writeCloseable
}
