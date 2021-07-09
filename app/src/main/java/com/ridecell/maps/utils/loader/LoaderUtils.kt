package com.ridecell.maps.utils.loader

import android.content.Context

open class LoaderUtils {

    companion object {
        private var jarvisLoader: Loader? = null
        fun showLoader(
            context: Context?,
            isCancelable: Boolean) {
            hideLoader()
            if (context != null) {
                try {
                    jarvisLoader = Loader(context)
                    jarvisLoader?.let { jarvisLoader->
                        jarvisLoader.setCanceledOnTouchOutside(true)
                        jarvisLoader.setCancelable(isCancelable)
                        jarvisLoader.show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun hideLoader() {
            if (jarvisLoader!=null && jarvisLoader?.isShowing!!) {
                jarvisLoader = try {
                    jarvisLoader?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }

    }


}