package com.example.newyorkschoolsassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newyorkschoolsassessment.Destinations.SCHOOL_DETAILS
import com.example.newyorkschoolsassessment.Destinations.SCHOOL_LIST
import com.example.newyorkschoolsassessment.Destinations.SELECTED_SCHOOL
import com.example.newyorkschoolsassessment.model.data.School
import com.example.newyorkschoolsassessment.model.data.SchoolsRepository
import com.example.newyorkschoolsassessment.model.datasource.SchoolsDataSourceImpl
import com.example.newyorkschoolsassessment.model.getSchoolsAPI
import com.example.newyorkschoolsassessment.ui.theme.NewyorkSchoolsAssessmentTheme
import com.example.newyorkschoolsassessment.viewmodel.SchoolsViewModel
import com.example.newyorkschoolsassessment.viewmodel.SchoolsViewModelProviderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = getSchoolsAPI()
        val dataSource = SchoolsDataSourceImpl(api)
        val repository = SchoolsRepository(dataSource)
        val viewModel = SchoolsViewModelProviderFactory(repository).create(SchoolsViewModel::class.java)
        setContent {
            NewyorkSchoolsAssessmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

object Destinations {
    const val SCHOOL_LIST = "schools_list"
    const val SCHOOL_DETAILS = "school_details"
    const val SELECTED_SCHOOL = "selected_school"
}

@Composable
fun AppNavigation(viewModel: SchoolsViewModel) {
    val navController = rememberNavController()
    val startDestination = SCHOOL_LIST
    NavHost(navController, startDestination) {
        composable(SCHOOL_LIST) {
            SchoolsList(viewModel = viewModel) {
                navController.navigate("${SCHOOL_DETAILS}/$it")
            }
        }
        composable("${SCHOOL_DETAILS}/{$SELECTED_SCHOOL}", arguments = listOf(
            navArgument(SELECTED_SCHOOL) {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val school = viewModel.schools.firstOrNull { it.dbn ==  arguments.getString(SELECTED_SCHOOL) }
            SchoolDetails(school = school) {
                navController.navigateUp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolsList(
    viewModel: SchoolsViewModel,
    selectSchool: (school: String) -> Unit
) {
    LaunchedEffect(key1 = "FetchData", block = {
        viewModel.getSchools()
    })
    Scaffold (
        topBar = {
            TopAppBar(title = {
                Row {
                    Text(text = "Schools")
                }
            })
        }) { _ ->
            if(viewModel.exception.isEmpty()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(viewModel.schools) { school ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                        .clickable {
                                            selectSchool(school.dbn)
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Column {
                                        Text(text = school.schoolName)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(text = "Contact Us at, ${school.phoneNumber}")
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(text = "Know us more about here, ${school.website}")
                                        Spacer(modifier = Modifier.height(6.dp))
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            } else {
                Text(text = viewModel.exception)
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolDetails(
    school: School?,
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back Button")
                    }
                    Text(text = "School Details")
                }
            })
        }) { _ ->
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Column {
                Text(text = school?.schoolName.orEmpty())
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = school?.overview.orEmpty())
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewyorkSchoolsAssessmentTheme {
        //Greeting("Android")
    }
}