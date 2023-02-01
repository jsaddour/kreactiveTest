package com.jsaddour.kreactivetest.ui.inputs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jsaddour.kreactivetest.R
import com.jsaddour.kreactivetest.ui.inputs.InputsViewState.Companion.FIRST_INTEGER_DEFAULT_VALUE
import com.jsaddour.kreactivetest.ui.inputs.InputsViewState.Companion.FIRST_WORD_DEFAULT_VALUE
import com.jsaddour.kreactivetest.ui.inputs.InputsViewState.Companion.LIMIT_DEFAULT_VALUE
import com.jsaddour.kreactivetest.ui.inputs.InputsViewState.Companion.SECOND_INTEGER_DEFAULT_VALUE
import com.jsaddour.kreactivetest.ui.inputs.InputsViewState.Companion.SECOND_WORD_DEFAULT_VALUE
import com.jsaddour.kreactivetest.ui.results.ResultActivity
import com.jsaddour.kreactivetest.ui.results.ResultActivity.Companion.RESULT_EXTRA_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : ComponentActivity() {
    private val viewModel by viewModels<InputViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorResource(id = R.color.purple_700),
                            titleContentColor = Color.White
                        ),
                        title = {
                            Text(
                                color = colorResource(id = R.color.white),
                                text = stringResource(R.string.app_name),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                    )
                }, content = { Form() })
        }
    }

    @Composable
    fun Form() {
        val context = LocalContext.current
        val state = viewModel.inputsViewState.observeAsState()
        val isLoading = state.value?.loading == true

        state.value?.result?.getContent()?.let { result ->
            val intent = Intent(context, ResultActivity::class.java)
            intent.putStringArrayListExtra(RESULT_EXTRA_KEY, ArrayList(result))
            context.startActivity(intent)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

            Field(
                label = stringResource(R.string.first_integer),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = state.value?.onFirstIntegerChange,
                error = state.value?.firstFielderror?.getContent()?.let { stringResource(it) },
                defaultValue = FIRST_INTEGER_DEFAULT_VALUE
            )
            Field(
                label = stringResource(R.string.second_integer),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = state.value?.onSecondIntegerChange,
                error = state.value?.secondFielderror?.getContent()?.let { stringResource(it) },
                defaultValue = SECOND_INTEGER_DEFAULT_VALUE
            )
            Field(
                label = stringResource(R.string.first_word),
                onValueChange = state.value?.onFirstWordChange,
                defaultValue = FIRST_WORD_DEFAULT_VALUE
            )
            Field(
                label = stringResource(R.string.second_word),
                onValueChange = state.value?.onSecondWordChange,
                defaultValue = SECOND_WORD_DEFAULT_VALUE
            )
            Field(
                label = stringResource(R.string.limit),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = state.value?.onLimitChange,
                defaultValue = LIMIT_DEFAULT_VALUE
            )

            Button(
                enabled = state.value?.validateButtonEnabled ?: false,
                onClick = {
                    state.value?.onValidate?.invoke()
                }) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                } else {
                    Text(text = stringResource(R.string.fizz_buzz))
                }
            }


        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Field(
        label: String,
        error: String? = null,
        keyboardOptions: KeyboardOptions? = null,
        defaultValue: String? = null,
        onValueChange: ((value: String) -> Unit)?
    ) {
        var value by remember {
            mutableStateOf(defaultValue ?: "")
        }

        val isError = error != null

        OutlinedTextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = value,
            keyboardOptions = keyboardOptions ?: KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = isError,
            supportingText = {
                error?.let {
                    Text(
                        color = Color.Red,
                        text = it,
                    )
                }
            },
            onValueChange = { newText ->
                if (newText.length < 5) {
                    value = if (keyboardOptions?.keyboardType == KeyboardType.Number) {
                        newText.filter { it.isDigit() }
                    } else {
                        newText
                    }
                    onValueChange?.invoke(value)
                }
            },
            label = { Text(text = label) },
        )
    }
}




