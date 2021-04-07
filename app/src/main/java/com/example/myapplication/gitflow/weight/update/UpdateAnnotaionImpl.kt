package com.example.myapplication.gitflow.weight.update

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import com.example.myapplication.R

class UpdateAnnotaionImpl(refreshRoot: View) : UpdateViewGroup.UpdataAnnotation {
    var iv: ImageView = refreshRoot.findViewById(R.id.iv)
    var apply: ValueAnimator? = null
    override fun scrollIng(distenceY: Int, unit: Float) {
        iv.rotation = (distenceY % unit / unit * 360F)
    }

    override fun scrollBack(unit: Float, over: () -> Unit) {
        apply = ValueAnimator.ofFloat(0F, unit).apply {
            addUpdateListener {
                val fl = it.animatedValue as Float
                iv.rotation = (fl % unit / unit * 360F)
            }
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            startDelay = 1000
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    over()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
            start()
        }
    }

    override fun scrollOver() {
        apply?.cancel()
    }

}