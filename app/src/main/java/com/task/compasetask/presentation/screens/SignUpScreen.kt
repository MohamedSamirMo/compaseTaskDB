package com.task.compasetask.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.compasetask.R
import com.task.compasetask.presentation.TabButton
import com.task.compasetask.presentation.nav.Screen
import com.task.compasetask.presentation.viewModel.AuthViewModel
import com.task.compasetask.presentation.viewModel.SignUpEvent
import com.task.compasetask.presentation.viewModel.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()   // <-- إضافة AuthViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onEvent(SignUpEvent.ResetError)
        }
    }

    LaunchedEffect(uiState.isSignUpSuccess) {
        if (uiState.isSignUpSuccess) {
            // بعد نجاح التسجيل، ننتقل إلى شاشة تسجيل الدخول (لم يتم تسجيل دخول تلقائي)
            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.SignUp.route) { inclusive = true }
            }
        }
    }

    // دوال مساعدة لتسجيل الدخول الوهمي عبر فيسبوك / جوجل
    fun mockLoginWithFacebook() {
        val mockEmail = "user@facebook.com"
        authViewModel.login(mockEmail)
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.SignUp.route) { inclusive = true }
        }
    }

    fun mockLoginWithGoogle() {
        val mockEmail = "user@gmail.com"
        authViewModel.login(mockEmail)
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.SignUp.route) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFF6F2), Color(0xFFFFE0B2))
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_background_top),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.ic_mask_top),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Fit
            )
        }

        // الصورة السفلية
        Image(
            painter = painterResource(id = R.drawable.ic_background_bottom),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets(
                        top = 16.dp,
                        bottom = 16.dp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_logo_dounts),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )

                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(40.dp)
                                .align(Alignment.CenterHorizontally)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFA8A7A780),
                                    shape = RoundedCornerShape(50.dp)
                                ),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TabButton(
                                text = "Sign In",
                                isSelected = false,
                                onClick = { navController.navigate(Screen.SignIn.route) }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            TabButton(
                                text = "Sign Up",
                                isSelected = true,
                                onClick = { }
                            )
                        }

                        var showPassword by remember { mutableStateOf(false) }
                        var showConfirmPassword by remember { mutableStateOf(false) }

                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            TextField(
                                value = uiState.email,
                                onValueChange = { viewModel.onEvent(SignUpEvent.EmailChanged(it)) },
                                label = { Text("Enter Email") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                enabled = !uiState.isLoading,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Gray,
                                    unfocusedIndicatorColor = Color.LightGray,
                                    disabledIndicatorColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFFFF9666),
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color(0xFFFF9666)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            TextField(
                                value = uiState.password,
                                onValueChange = { viewModel.onEvent(SignUpEvent.PasswordChanged(it)) },
                                label = { Text("Password") },
                                trailingIcon = {
                                    IconButton(onClick = { showPassword = !showPassword }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_view),
                                            contentDescription = if (showPassword) "Hide password" else "Show password"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                enabled = !uiState.isLoading,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Gray,
                                    unfocusedIndicatorColor = Color.LightGray,
                                    disabledIndicatorColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFFFF9666),
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color(0xFFFF9666)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            TextField(
                                value = uiState.confirmPassword,
                                onValueChange = { viewModel.onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
                                label = { Text("Confirm Password") },
                                trailingIcon = {
                                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_view),
                                            contentDescription = if (showConfirmPassword) "Hide password" else "Show password"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                enabled = !uiState.isLoading,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Gray,
                                    unfocusedIndicatorColor = Color.LightGray,
                                    disabledIndicatorColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFFFF9666),
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color(0xFFFF9666)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.onEvent(SignUpEvent.SignUpClicked) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9666)),
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Sign Up", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_line_left),
                                contentDescription = null,
                                modifier = Modifier.weight(1f),
                                tint = Color.Gray
                            )
                            Text(
                                text = "Or",
                                color = Color(0xFFA8A7A7),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_line_right),
                                contentDescription = null,
                                modifier = Modifier.weight(1f),
                                tint = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { mockLoginWithFacebook() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_facebook),
                                    contentDescription = "Facebook login",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.Unspecified
                                )
                            }
                            Spacer(modifier = Modifier.width(2.dp))
                            IconButton(onClick = { mockLoginWithGoogle() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_google),
                                    contentDescription = "Google login",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(enabled = false) { }
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center),
                    color = Color(0xFFFF9666),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}