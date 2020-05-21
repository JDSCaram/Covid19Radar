package br.com.jdscaram.covid19radar.util

import android.animation.Animator

import android.animation.AnimatorListenerAdapter
import android.view.View


class AnimationUtils {

    companion object {
        fun rotateFab(v: View, rotate: Boolean): Boolean {
            v.animate().setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .rotation(if (rotate) 135f else 0f)
            return rotate
        }
    }
}