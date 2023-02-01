package com.jsaddour.kreactivetest.ui.results

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jsaddour.kreactivetest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : ComponentActivity() {

    private val viewModel by viewModels<ResultViewModel>()


    companion object {
        const val RESULT_EXTRA_KEY = "RESULT_EXTRA_KEY"
    }

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
                                text = stringResource(R.string.result),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                    )
                }, content = { Content() })
        }
    }

    @Composable
    fun Content() {
        val state = viewModel.resultViewState.observeAsState()

        LazyColumn {
            state.value?.let { result ->
                items(result) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(thickness = 1.dp, color = Color.Gray)
                }
            }
        }
    }


}
