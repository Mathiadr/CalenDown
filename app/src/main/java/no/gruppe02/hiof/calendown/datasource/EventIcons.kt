package no.gruppe02.hiof.calendown.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

class EventIcons {
    object DefaultIcons {
        val defaultIcons: Map<String, ImageVector> = mapOf(
            Pair(Icons.Default.Home.name, Icons.Default.Home),
            Pair(Icons.Default.Warning.name, Icons.Default.Warning),
            Pair(Icons.Default.Star.name, Icons.Default.Star),
            Pair(Icons.Default.Favorite.name, Icons.Default.Favorite),
            Pair(Icons.Default.Info.name, Icons.Default.Info),
            Pair(Icons.Default.Edit.name, Icons.Default.Edit),
            Pair(Icons.Default.Face.name, Icons.Default.Face),
            Pair(Icons.Default.FavoriteBorder.name, Icons.Default.FavoriteBorder),
            Pair(Icons.Default.Build.name, Icons.Default.Build),
            Pair(Icons.Default.DateRange.name, Icons.Default.DateRange),
            Pair(Icons.Default.Call.name, Icons.Default.Call),
            Pair(Icons.Default.Create.name, Icons.Default.Create),
            Pair(Icons.Default.Email.name, Icons.Default.Email),
            Pair(Icons.Default.LocationOn.name, Icons.Default.LocationOn),
            Pair(Icons.Default.ShoppingCart.name, Icons.Default.ShoppingCart),
            Pair(Icons.Default.ThumbUp.name, Icons.Default.ThumbUp)
        )
    }
}
