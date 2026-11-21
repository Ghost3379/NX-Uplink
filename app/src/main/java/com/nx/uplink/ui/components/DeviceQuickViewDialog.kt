package com.nx.uplink.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nx.uplink.ui.theme.*

/**
 * Tactical device quick-view popup dialog.
 * Displays live battery status, firmware version, device ID, and hardware telemetry.
 */
@Composable
fun DeviceQuickViewDialog(
    deviceName: String = "NX-ISD",
    deviceType: String = "Tactical Biometric Watch",
    batteryPercent: Int = 94,
    batteryVoltage: String = "4.12 V",
    softwareVersion: String = "ISD-Core v1.4.2",
    deviceId: String = "NX-ISD-0042",
    macAddress: String = "SIM:E4:65:B8:21:40",
    hardwareRev: String = "v1.4 (Production)",
    onDismiss: () -> Unit,
    onSwitchDevice: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(TacticalSurfaceElevated)
                .border(1.2.dp, AmberMid.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header: Device Name + Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = deviceName,
                            fontFamily = CircuitForem,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberBright,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = deviceType,
                            fontFamily = TypewriterFont,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    // Connected Status Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .background(OledBlack, RoundedCornerShape(6.dp))
                            .border(1.dp, NominalGreen.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(NominalGreen, CircleShape)
                        )
                        Text(
                            text = "CONNECTED",
                            fontFamily = TypewriterFont,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = NominalGreen
                        )
                    }
                }

                HorizontalDivider(color = TacticalBorder, thickness = 1.dp)

                // Battery Section Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(OledBlack)
                        .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "BATTERY STATUS",
                                fontFamily = TypewriterFont,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDim
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "$batteryPercent%",
                                    fontFamily = CircuitForem,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberBright
                                )
                                Text(
                                    text = "• $batteryVoltage",
                                    fontFamily = TypewriterFont,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            Text(
                                text = "MAX17048 Fuel Gauge • Normal Drain",
                                fontFamily = TypewriterFont,
                                fontSize = 10.sp,
                                color = TextDim
                            )
                        }

                        // Battery Level Indicator Graphic
                        Box(
                            modifier = Modifier.size(width = 44.dp, height = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val stroke = 1.5.dp.toPx()
                                val bodyWidth = size.width - 4.dp.toPx()
                                val bodyHeight = size.height

                                // Battery outline
                                drawRoundRect(
                                    color = AmberBright,
                                    topLeft = Offset(0f, 0f),
                                    size = Size(bodyWidth, bodyHeight),
                                    cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
                                    style = Stroke(stroke)
                                )
                                // Battery terminal bump
                                drawRoundRect(
                                    color = AmberBright,
                                    topLeft = Offset(bodyWidth, bodyHeight * 0.28f),
                                    size = Size(3.dp.toPx(), bodyHeight * 0.44f),
                                    cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
                                )
                                // Battery fill level
                                val fillPadding = 2.5.dp.toPx()
                                val fillWidth = (bodyWidth - fillPadding * 2) * (batteryPercent / 100f)
                                drawRoundRect(
                                    color = NominalGreen,
                                    topLeft = Offset(fillPadding, fillPadding),
                                    size = Size(fillWidth, bodyHeight - fillPadding * 2),
                                    cornerRadius = CornerRadius(1.5.dp.toPx(), 1.5.dp.toPx())
                                )
                            }
                        }
                    }
                }

                // General Device Information Table
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(OledBlack)
                        .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickViewInfoRow(label = "Software", value = softwareVersion)
                    QuickViewInfoRow(label = "Device ID", value = deviceId)
                    QuickViewInfoRow(label = "Hardware Rev", value = hardwareRev)
                    QuickViewInfoRow(label = "MAC / Link", value = macAddress)
                    QuickViewInfoRow(label = "RTC Timekeeper", value = "RV-3028 (Synced)")
                    QuickViewInfoRow(label = "Storage", value = "MicroSD (4-bit SDMMC)")
                }

                // Action Buttons: Switch Device & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Switch Device
                    Button(
                        onClick = onSwitchDevice,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TacticalSurface,
                            contentColor = AmberBright
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .border(1.dp, AmberMid, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "Switch Device",
                            fontFamily = TypewriterFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Close
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TacticalSurfaceElevated,
                            contentColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .border(1.dp, TacticalBorder, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "Dismiss",
                            fontFamily = TypewriterFont,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickViewInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = TypewriterFont,
            fontSize = 11.sp,
            color = TextDim
        )
        Text(
            text = value,
            fontFamily = TypewriterFont,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}
