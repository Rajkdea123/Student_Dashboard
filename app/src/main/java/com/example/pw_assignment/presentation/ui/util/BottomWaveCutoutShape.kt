package com.example.pw_assignment.presentation.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BottomWaveCutoutShape(// Bottom Curve shape
    private val cornerRadius: Float = 40f,
    private val waveWidth: Float = 600f,
    private val waveHeight: Float = 140f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val w = size.width
        val h = size.height
        val cX = w / 2f
        val halfWave = waveWidth / 2f
        val leftWaveX = cX - halfWave
        val rightWaveX = cX + halfWave
        val wavePeakY = h - waveHeight
        val r = cornerRadius

        val path = Path().apply {
            moveTo(0f, r)
            quadraticBezierTo(0f, 0f, r, 0f)        // <-- use quadraticBezierTo
            lineTo(w - r, 0f)
            quadraticBezierTo(w, 0f, w, r)
            lineTo(w, h - r)
            quadraticBezierTo(w, h, w - r, h)
            lineTo(rightWaveX, h)
            cubicTo(
                rightWaveX - waveWidth * 0.20f, h,
                cX + waveWidth * 0.20f, wavePeakY,
                cX, wavePeakY
            )
            cubicTo(
                cX - waveWidth * 0.20f, wavePeakY,
                leftWaveX + waveWidth * 0.20f, h,
                leftWaveX, h
            )
            lineTo(r, h)
            quadraticBezierTo(0f, h, 0f, h - r)
            close()
        }

        return Outline.Generic(path)
    }
}


