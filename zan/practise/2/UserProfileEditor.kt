package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.PlaygroundAppTheme

@Composable
fun UserProfileEditorScreen() {
    Scaffold(
        topBar = { UserProfileEditorAppBar() },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        UserProfileEditorContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileEditorAppBar() {
    TopAppBar(
        title = { Text(text = "Edit Profile") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.close_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Cancel",
                )
            }
        },

        actions = {
            Icon(
                painter = painterResource(R.drawable.done_outline_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                contentDescription = "Save"
            )
        }
    )
}


@Composable
fun UserProfileEditorContent(modifier: Modifier = Modifier) {
    val textFieldModifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.profile_placeholder),
            contentDescription = "User profile icon",
            modifier = Modifier
                .padding(top = 24.dp)
                .height(250.dp),
        )
        UserProfileEditorTextField(
            value = "John",
            onValueChange = {},
            label = "Name",
            icon = painterResource(R.drawable.outline_cancel_24),
            modifier = textFieldModifier,
        )

        UserProfileEditorTextField(
            value = "Doe",
            onValueChange = {},
            label = "Surname",
            icon = painterResource(R.drawable.outline_cancel_24),
            modifier = textFieldModifier,
        )

        UserProfileEditorTextField(
            value = "5",
            onValueChange = {},
            label = "Kids",
            icon = painterResource(R.drawable.outline_cancel_24),
            modifier = textFieldModifier,
        )
    }
}

@Composable
fun UserProfileEditorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    onTrailingIconClick: () -> Unit = {},
    label: String = "",
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        trailingIcon = {
            IconButton(onClick = onTrailingIconClick) {
                Icon(
                    painter = icon,
                    contentDescription = "Clear text",
                )
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfileEditorScreenPreview() {
    PlaygroundAppTheme {
        UserProfileEditorScreen()
    }
}