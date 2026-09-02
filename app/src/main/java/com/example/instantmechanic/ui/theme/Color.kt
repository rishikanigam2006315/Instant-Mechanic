package com.example.instantmechanic.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Background and Surface Colors (Deep Midnight Slate)
val BackgroundDark = Color(0xFF090E17)
val SurfaceDark = Color(0xFF121B2B)
val SurfaceVariantDark = Color(0xFF1A263B)
val SurfaceHoverDark = Color(0xFF22324C)
val CardBorderStroke = Color(0xFF2A3C58)
val DividerDark = Color(0xFF1F2D42)

// Primary Brand Colors (Electric Automotive Blue & Cyan)
val PrimaryBlue = Color(0xFF0284C7)
val PrimaryBlueLight = Color(0xFF38BDF8)
val PrimaryBlueDark = Color(0xFF0369A1)
val PrimaryContainerDark = Color(0xFF0C4A6E)
val OnPrimaryDark = Color(0xFFFFFFFF)

// Accent & Emergency Roadside Colors (Vibrant Amber & Flare Orange)
val AccentAmber = Color(0xFFF59E0B)
val AccentOrange = Color(0xFFFF6B00)
val AccentOrangeLight = Color(0xFFFF8533)
val StarGold = Color(0xFFFBBF24)

// Semantic & Status Colors
val SuccessGreen = Color(0xFF10B981)
val SuccessGreenLight = Color(0xFF34D399)
val SuccessContainer = Color(0xFF064E3B)

val ErrorRed = Color(0xFFEF4444)
val ErrorRedLight = Color(0xFFF87171)
val ErrorContainer = Color(0xFF450A0A)

val InfoBlue = Color(0xFF3B82F6)

// Text Colors
val TextPrimary = Color(0xFFF8FAFC)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)

// Gradients
val PrimaryGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF0284C7), Color(0xFF06B6D4))
)

val EmergencyGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFDC2626), Color(0xFFEA580C))
)

val PremiumCardGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF172338), Color(0xFF0E1726))
)

val AccentCardGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF1E3A8A), Color(0xFF0284C7))
)