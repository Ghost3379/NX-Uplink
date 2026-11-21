package com.nx.uplink.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nx.uplink.ui.components.DeviceQuickViewDialog
import com.nx.uplink.ui.components.TacticalBackgroundGrid
import com.nx.uplink.ui.components.TacticalHamburgerButton
import com.nx.uplink.ui.submenu.SubMenuScreen
import com.nx.uplink.ui.theme.*

@Composable
fun HomeScreen(
    onOpenDrawer: () -> Unit = {},
    onNavigateFleet: () -> Unit = {}
) {
    val context = LocalContext.current
    var activeSubMenu by remember { mutableStateOf<String?>(null) }
    var statusTitle by remember { mutableStateOf("Good") }
    var statusSubtitle by remember { mutableStateOf("No battery or sensor issues") }
    var showQuickView by remember { mutableStateOf(false) }

    // Intercept physical/gesture back button:
    // If in sub-menu -> exit to hub
    // If in hub -> exit to Device Fleet start screen
    BackHandler(enabled = true) {
        if (activeSubMenu != null) {
            activeSubMenu = null
        } else {
            onNavigateFleet()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = OledBlack
    ) {
        Crossfade(
            targetState = activeSubMenu,
            animationSpec = tween(350),
            label = "SubMenuNavigation"
        ) { currentSubMenu ->
            if (currentSubMenu != null) {
                // Dedicated Tactical Sub-Menu View (No popup dialog!)
                SubMenuScreen(
                    nodeId = currentSubMenu,
                    onBackClicked = { activeSubMenu = null }
                )
            } else {
                // Main WayneTech Hub Screen
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Subtle Arkham Origins Ambient Grid Layer
                    TacticalBackgroundGrid()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // TOP BAR: Hamburger button (3 lines) on left, Title center, Fleet switcher on right
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TacticalHamburgerButton(
                                onClick = onOpenDrawer
                            )

                            Text(
                                text = "NX-ISD",
                                fontFamily = CircuitForem,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AmberBright,
                                letterSpacing = 2.sp
                            )

                            // Device quick-view pill button at top right
                            Box(
                                modifier = Modifier
                                    .background(TacticalSurfaceElevated, shape = RoundedCornerShape(8.dp))
                                    .border(1.dp, AmberMid.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                                    .clickable { showQuickView = true }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .background(NominalGreen, CircleShape)
                                    )
                                    Text(
                                        text = "94%",
                                        fontFamily = TypewriterFont,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "INFO",
                                        fontFamily = CircuitForem,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AmberBright,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // ZONE 1: Minimalist Health Care Overview (Monochrome Vector Smiley + Verdict + Optimise)
                        DeviceCareCard(
                            statusTitle = statusTitle,
                            statusSubtitle = statusSubtitle,
                            onOptimiseClicked = {
                                statusTitle = "Optimized"
                                statusSubtitle = "NX-SDS audit passed • RTC synced"
                                Toast.makeText(context, "Optimised watch state", Toast.LENGTH_SHORT).show()
                            }
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        // ZONE 2: Arkham Knight WayneTech 5-Hexagon Cluster
                        HexSubsystemHub(
                            onNodeSelected = { nodeKey ->
                                activeSubMenu = nodeKey // Open dedicated sub-menu view
                            }
                        )

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }

    if (showQuickView) {
        DeviceQuickViewDialog(
            deviceName = "NX-ISD",
            deviceType = "Tactical Biometric Watch",
            batteryPercent = 94,
            batteryVoltage = "4.12 V",
            softwareVersion = "ISD-Core v1.4.2",
            deviceId = "NX-ISD-0042",
            macAddress = "SIM:E4:65:B8:21:40",
            hardwareRev = "v1.4 (Production)",
            onDismiss = { showQuickView = false }
        )
    }
}
