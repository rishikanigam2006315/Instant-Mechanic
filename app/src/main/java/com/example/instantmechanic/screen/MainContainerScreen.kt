package com.example.instantmechanic.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.ui.UserAvatar
import com.example.instantmechanic.ui.theme.AccentAmber
import com.example.instantmechanic.ui.theme.BackgroundDark
import com.example.instantmechanic.ui.theme.CardBorderStroke
import com.example.instantmechanic.ui.theme.ErrorRed
import com.example.instantmechanic.ui.theme.PrimaryBlue
import com.example.instantmechanic.ui.theme.PrimaryBlueLight
import com.example.instantmechanic.ui.theme.SuccessGreen
import com.example.instantmechanic.ui.theme.SurfaceDark
import com.example.instantmechanic.ui.theme.SurfaceVariantDark
import com.example.instantmechanic.ui.theme.TextMuted
import com.example.instantmechanic.ui.theme.TextPrimary
import com.example.instantmechanic.ui.theme.TextSecondary
import com.example.instantmechanic.viewmodel.ServiceRequestViewModel
import com.example.instantmechanic.viewmodel.UserSession
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainerScreen(
    mechanics: List<Mechanic>,
    isLoading: Boolean,
    error: String?,
    serviceRequestViewModel: ServiceRequestViewModel,
    onMechanicClick: (Long) -> Unit,
    onEmergencySosClick: (Long) -> Unit,
    onLogout: () -> Unit = {},
    currentUser: UserSession? = null,
    onUpdateAvatar: (String) -> Unit = {},
    onUpdateVehicle: (String, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Modals state
    var showSosConfirmation by remember { mutableStateOf(false) }
    var showLocationSelector by remember { mutableStateOf(false) }
    var showVehiclesDialog by remember { mutableStateOf(false) }
    var showInsuranceDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }

    var currentLocation by remember { mutableStateOf("Indiranagar, BLR") }

    val bottomNavItems = listOf(
        Triple("Home", Icons.Filled.Home, 0),
        Triple("Explore", Icons.Filled.Search, 1),
        Triple("Bookings", Icons.Filled.Build, 2),
        Triple("Profile", Icons.Filled.Person, 3)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = SurfaceDark,
                modifier = Modifier
                    .width(320.dp)
                    .fillMaxHeight()
            ) {
                // Polished Luxury Automotive Drawer Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF0F172A),
                                    Color(0xFF1E293B)
                                )
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // User Avatar with Camera Badge for Photo Upload
                        UserAvatar(
                            avatarUri = currentUser?.avatarUri,
                            size = 76.dp,
                            iconSize = 42.dp,
                            cameraButtonSize = 28.dp,
                            cameraIconSize = 14.dp,
                            showCameraBadge = true,
                            onImagePicked = onUpdateAvatar
                        )

                        // Active Roadside Shield Pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SuccessGreen.copy(alpha = 0.15f))
                                .border(1.dp, SuccessGreen.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(SuccessGreen)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "SHIELD ACTIVE",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = SuccessGreen,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val displayName = currentUser?.name ?: "Guest User"
                    val displayPhone = currentUser?.phone ?: "No contact saved"
                    val vehicleType = currentUser?.vehicleType ?: "Car"
                    val vehicleNumber = currentUser?.vehicleNumber ?: "KA 01 MJ 4521"
                    val isTwoWheeler = vehicleType.contains("Two", ignoreCase = true) || vehicleType.contains("Bike", ignoreCase = true)

                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = displayPhone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Vehicle registration badge (Clickable to edit)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(PrimaryBlue.copy(alpha = 0.15f))
                            .border(1.dp, PrimaryBlueLight.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                            .clickable {
                                coroutineScope.launch { drawerState.close() }
                                showVehiclesDialog = true
                            }
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isTwoWheeler) Icons.Filled.TwoWheeler else Icons.Filled.DirectionsCar,
                            contentDescription = null,
                            tint = PrimaryBlueLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$vehicleType • $vehicleNumber",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = PrimaryBlueLight
                        )
                    }
                }

                HorizontalDivider(color = CardBorderStroke)

                // Scrollable Navigation Items inside Drawer
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "EXPLORE & BOOKINGS",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextMuted,
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp)
                    )

                    DrawerItem(
                        icon = Icons.Filled.Home,
                        label = "Home Feed",
                        isSelected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            coroutineScope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        icon = Icons.Filled.Search,
                        label = "Find Garages & Mechanics",
                        isSelected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            coroutineScope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        icon = Icons.Filled.Build,
                        label = "My Service Requests",
                        isSelected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            coroutineScope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        icon = Icons.Filled.Emergency,
                        label = "Emergency 24x7 SOS",
                        isSelected = false,
                        tint = ErrorRed,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            showSosConfirmation = true
                        }
                    )

                    HorizontalDivider(
                        color = CardBorderStroke,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "ACCOUNT & ASSISTANCE",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextMuted,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
                    )

                    // Interactive: My Registered Vehicles
                    DrawerItem(
                        icon = Icons.Filled.DirectionsCar,
                        label = "My Registered Vehicles",
                        isSelected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            showVehiclesDialog = true
                        }
                    )

                    // Interactive: Roadside Insurance Policy
                    DrawerItem(
                        icon = Icons.Filled.Security,
                        label = "Roadside Insurance Policy",
                        isSelected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            showInsuranceDialog = true
                        }
                    )

                    // Interactive: Settings & Preferences
                    DrawerItem(
                        icon = Icons.Filled.Settings,
                        label = "Settings & Preferences",
                        isSelected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            showSettingsDialog = true
                        }
                    )

                    // Interactive: Customer Support & FAQs
                    DrawerItem(
                        icon = Icons.Filled.HelpOutline,
                        label = "Customer Support & FAQs",
                        isSelected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            showSupportDialog = true
                        }
                    )

                    DrawerItem(
                        icon = Icons.Filled.ExitToApp,
                        label = "Sign Out",
                        isSelected = false,
                        tint = ErrorRed,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            onLogout()
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Bottom Version text in Drawer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Instant Mechanic • v1.0 Production",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(SurfaceDark)
                                .border(1.dp, CardBorderStroke, RoundedCornerShape(20.dp))
                                .clickable { showLocationSelector = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location",
                                tint = PrimaryBlueLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentLocation,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "▾",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextSecondary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Sidebar Menu",
                                tint = TextPrimary
                            )
                        }
                    },
                    actions = {
                        // Quick Emergency SOS Icon
                        IconButton(onClick = { showSosConfirmation = true }) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF7F1D1D)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Emergency,
                                    contentDescription = "Quick SOS",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Notification Icon with badge
                        IconButton(onClick = {
                            showSettingsDialog = true
                        }) {
                            Box(contentAlignment = Alignment.TopEnd) {
                                Icon(
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = "Notifications",
                                    tint = TextPrimary
                                )
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(AccentAmber)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundDark,
                        titleContentColor = TextPrimary,
                        navigationIconContentColor = TextPrimary
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = SurfaceDark,
                    tonalElevation = 8.dp,
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = CardBorderStroke,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                ) {
                    bottomNavItems.forEach { (title, icon, index) ->
                        val isSelected = selectedTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = index },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryBlueLight,
                                selectedTextColor = PrimaryBlueLight,
                                indicatorColor = PrimaryBlue.copy(alpha = 0.2f),
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> HomeScreen(
                        mechanics = mechanics,
                        isLoading = isLoading,
                        error = error,
                        onMechanicClick = onMechanicClick,
                        onEmergencySosClick = { showSosConfirmation = true },
                        onCategoryClick = { category ->
                            selectedTab = 1
                        },
                        currentUser = currentUser
                    )
                    1 -> ExploreScreen(
                        mechanics = mechanics,
                        isLoading = isLoading,
                        onMechanicClick = onMechanicClick
                    )
                    2 -> BookingsScreen(
                        serviceRequestViewModel = serviceRequestViewModel,
                        onRequestNewService = { selectedTab = 1 }
                    )
                    3 -> ProfileScreen(
                        currentUser = currentUser,
                        onUpdateAvatar = onUpdateAvatar,
                        onUpdateVehicle = onUpdateVehicle,
                        onLogoutClick = onLogout
                    )
                }
            }
        }
    }

    // Interactive Dialog: My Registered Vehicles
    if (showVehiclesDialog) {
        var inputType by remember { mutableStateOf(currentUser?.vehicleType ?: "Car") }
        var inputNumber by remember { mutableStateOf(currentUser?.vehicleNumber ?: "KA 01 MJ 4521") }

        AlertDialog(
            onDismissRequest = { showVehiclesDialog = false },
            containerColor = SurfaceDark,
            title = {
                Text(
                    text = "My Registered Vehicles",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "Select your default vehicle for 1-tap roadside dispatch:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Car 🚗", "Two-Wheeler 🏍️", "Commercial 🚐").forEach { type ->
                            val cleanType = type.split(" ")[0]
                            val isSelected = inputType.contains(cleanType, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) PrimaryBlue else SurfaceVariantDark)
                                    .clickable { inputType = cleanType }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = type,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) TextPrimary else TextMuted
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = inputNumber,
                        onValueChange = { inputNumber = it.uppercase() },
                        label = { Text("Plate Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceVariantDark,
                            unfocusedContainerColor = SurfaceVariantDark,
                            focusedBorderColor = PrimaryBlueLight,
                            unfocusedBorderColor = CardBorderStroke,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (inputNumber.isNotBlank()) {
                            onUpdateVehicle(inputType, inputNumber)
                            showVehiclesDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Update Vehicle")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showVehiclesDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close", color = TextSecondary)
                }
            }
        )
    }

    // Interactive Dialog: Roadside Insurance Policy
    if (showInsuranceDialog) {
        AlertDialog(
            onDismissRequest = { showInsuranceDialog = false },
            containerColor = SurfaceDark,
            icon = {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(SuccessGreen.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Security,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "Roadside Assistance Shield",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceVariantDark)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(text = "POLICY NUMBER", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            Text(text = "IM-RSA-BLR-2026-9901", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = PrimaryBlueLight)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "STATUS: ACTIVE • UNLIMITED CLAIMS", style = MaterialTheme.typography.labelSmall, color = SuccessGreen)
                        }
                    }

                    Text(text = "Covered Benefits Included:", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                    Text(text = "• Free Emergency Flatbed Towing up to 50 km", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(text = "• 24x7 Battery Jumpstart & Mobile Charging", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(text = "• Emergency On-Spot Fuel Delivery (up to 5L)", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(text = "• Cashless Settlement at 250+ Certified Garages", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showInsuranceDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Got It")
                }
            }
        )
    }

    // Interactive Dialog: Settings & Preferences
    if (showSettingsDialog) {
        var pushNotifications by remember { mutableStateOf(true) }
        var highContrastMap by remember { mutableStateOf(true) }
        var autoSosGps by remember { mutableStateOf(true) }

        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            containerColor = SurfaceDark,
            title = {
                Text(
                    text = "Settings & Preferences",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Dispatch Notifications", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text(text = "Mechanic arrival time alerts", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                        Switch(
                            checked = pushNotifications,
                            onCheckedChange = { pushNotifications = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = TextPrimary, checkedTrackColor = PrimaryBlue)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Auto-Share Breakdown GPS", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text(text = "Enables pinpoint van dispatch", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                        Switch(
                            checked = autoSosGps,
                            onCheckedChange = { autoSosGps = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = TextPrimary, checkedTrackColor = PrimaryBlue)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Automotive Dark Theme", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text(text = "Optimized for night roadside visibility", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                        Switch(
                            checked = highContrastMap,
                            onCheckedChange = { highContrastMap = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = TextPrimary, checkedTrackColor = PrimaryBlue)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSettingsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Save Preferences")
                }
            }
        )
    }

    // Interactive Dialog: Customer Support & FAQs
    if (showSupportDialog) {
        AlertDialog(
            onDismissRequest = { showSupportDialog = false },
            containerColor = SurfaceDark,
            icon = {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.HelpOutline,
                        contentDescription = null,
                        tint = PrimaryBlueLight,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "24x7 Customer Support",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Need immediate assistance or have a question about your repair?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18001021234"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Call Helpline: 1800-102-1234")
                    }

                    Text(text = "Frequently Asked Questions:", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                    Text(text = "Q: How long does mechanic dispatch take?\nA: Average response time is 15-20 minutes in Bangalore metro.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(text = "Q: What payment methods are supported?\nA: UPI, Credit/Debit Cards, Net Banking & Cash on completion.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSupportDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close", color = TextPrimary)
                }
            }
        )
    }

    // Emergency SOS Confirmation Modal
    if (showSosConfirmation) {
        val topEmergencyGarage = mechanics.firstOrNull { it.isOpen } ?: mechanics.firstOrNull()
        AlertDialog(
            onDismissRequest = { showSosConfirmation = false },
            containerColor = SurfaceDark,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary,
            icon = {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF7F1D1D)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Emergency,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            title = {
                Text(
                    text = "Request Emergency Dispatch?",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Text(
                        text = "We will immediately alert the nearest roadside patrol and mechanics in $currentLocation.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "• Average ETA: 12-18 minutes\n• Real-time GPS tracking\n• 24/7 Helpline backed",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryBlueLight
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSosConfirmation = false
                        if (topEmergencyGarage != null) {
                            onEmergencySosClick(topEmergencyGarage.id)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Confirm SOS Dispatch", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSosConfirmation = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Cancel", color = TextSecondary)
                }
            }
        )
    }

    // Location Selector Dialog
    if (showLocationSelector) {
        val locations = listOf(
            "Indiranagar, BLR",
            "Koramangala, BLR",
            "HSR Layout, BLR",
            "Whitefield, BLR",
            "Outer Ring Road, BLR",
            "BTM Layout, BLR"
        )
        AlertDialog(
            onDismissRequest = { showLocationSelector = false },
            containerColor = SurfaceDark,
            title = {
                Text(
                    text = "Select Breakdown Location",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    locations.forEach { loc ->
                        val isSelected = loc == currentLocation
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) PrimaryBlue.copy(alpha = 0.2f) else SurfaceVariantDark)
                                .clickable {
                                    currentLocation = loc
                                    showLocationSelector = false
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = if (isSelected) PrimaryBlueLight else TextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = loc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isSelected) PrimaryBlueLight else TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showLocationSelector = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    tint: Color = PrimaryBlueLight,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) PrimaryBlueLight else tint,
                modifier = Modifier.size(20.dp)
            )
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                ),
                color = if (isSelected) PrimaryBlueLight else TextPrimary
            )
        },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = PrimaryBlue.copy(alpha = 0.15f),
            unselectedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}
