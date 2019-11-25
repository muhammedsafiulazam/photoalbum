package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.muhammedsafiulazam.photoalbum.network.model.Error

object ErrorUtils {
    /**
     * Create no connectivity error.
     * @return no connectivity error
     */
    fun noConnectivity() : Error {
        var code: Int? = 0
        var body: String? = ""

        val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGER) as IContextManager?
        val context: Context? = contextManager?.getContext()

        code = context?.resources?.getInteger(R.integer.error_code_no_connectivity)
        body = context?.resources?.getString(R.string.error_body_no_connectivity)

        return Error(code, body)
    }
}