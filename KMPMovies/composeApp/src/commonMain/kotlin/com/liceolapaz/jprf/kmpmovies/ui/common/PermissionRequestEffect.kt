package com.liceolapaz.jprf.kmpmovies.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
fun PermissionRequestEffect(permission: Permission, onResult: (Boolean) -> Unit) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)
    LaunchedEffect(controller) {
        controller.providePermission(permission)
        onResult(controller.isPermissionGranted(permission))
    }
}