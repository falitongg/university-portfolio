package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.PlaygroundAppTheme

@Composable
fun UserProfileScreen(){
    Scaffold(
        topBar = {UserProfileAppBar() },
        bottomBar = {UserProfileBottomNavigation() },
        modifier = Modifier.fillMaxSize()
    ) {innerPadding ->
        UserProfileContent(
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileAppBar() {
    TopAppBar(
        title = {Text ("User Profile")},
        actions = {
            IconButton(onClick = { TODO() }) {
                Icon(
                    painter = painterResource(R.drawable.edit_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Edit user profile"
                )
            }
            IconButton(onClick = { TODO()}) {
                Icon(
                    painter = painterResource(R.drawable.logout_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Logout"
                )
            }
        }
    )
}

@Composable
fun UserProfileBottomNavigation(){
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.attractions_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Playgrounds",
                    modifier = Modifier.size(32.dp)
                )
            },
            label = {Text("Playgrounds")},
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.person_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            },
            label = {Text("Profile")},
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun UserProfileContent(modifier: Modifier = Modifier){
    val scrollableColumnState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollableColumnState)
    ) {
        Image(
            painter = painterResource(R.drawable.profile_placeholder),
            contentDescription = "User profile icon",
            modifier = Modifier
                .padding(top=24.dp)
                .height(250.dp)
        )
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top=24.dp),
        )
        Text(
            text = "6 kids"
        )
        Text(
            text = "Last visited playgrounds",
            Modifier.fillMaxSize(),
            textAlign = TextAlign.Start // Выравниваем сам текст внутри рамки влево
        )

        PlaygroundItem(modifier = Modifier.padding(top = 16.dp))

    }
}

@Composable
fun PlaygroundItem(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.na_krejcarku),
            contentDescription = "Playground Na Krejcarku",
            modifier = Modifier.height(80.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            SingleLineText(
                text = "Na Krejcarku",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp),
            )

            SingleLineText(
                text = "Za Žižkovskou vozovnou 2716/19",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            SingleLineText(
                text = "Skluzavka, houpačka, pískoviště",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(top=16.dp, start = 16.dp, end=16.dp)
            )
        }
    }
}

@Composable
fun SingleLineText(text: String, style: TextStyle, modifier: Modifier = Modifier) {
    Text(
        text = text, style = style, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfileScreenPreview() {
    PlaygroundAppTheme {
        UserProfileScreen()
    }
}