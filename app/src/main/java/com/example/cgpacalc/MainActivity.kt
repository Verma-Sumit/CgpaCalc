package com.example.cgpacalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpacalc.ui.theme.CgpaCalcTheme
data class Semester (val grade: String, val credit: Int)

class MainActivity : ComponentActivity() {
    private var semesters:MutableList<Semester> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CgpaCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CGPA(semesters)

                }
            }
        }
    }
}

@Composable
fun CGPA(semesters: MutableList<Semester>){
    var grade1 by remember { mutableStateOf("") }
    var credit1 by remember { mutableStateOf<Int?>(null) }
    var grade2 by remember { mutableStateOf("") }
    var credit2 by remember { mutableStateOf<Int?>(null) }
    var grade3 by remember { mutableStateOf("") }
    var credit3 by remember { mutableStateOf<Int?>(null) }
    var grade4 by remember { mutableStateOf("") }
    var credit4 by remember { mutableStateOf<Int?>(null) }
    var cgpa by remember { mutableStateOf(0.0) }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Text(text = "CGPA Calculator \nA Debut app by Sumit", modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 25.sp, textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color(0xFF000000)
                )
            )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SubjectText(subject = "Year/Semester 1")
        GradeTextField(grade1) {grade1 = it}
        Spacer8dp()
        CreditTextField(credit1) {credit1 = it}
        Spacer8dp()
        SubjectText(subject = "Year/Semester 2")
        GradeTextField(grade2) {grade2 = it}
        Spacer8dp()
        CreditTextField(credit2) {credit2 = it}
        Spacer8dp()
        SubjectText(subject = "Year/Semester 3")
        GradeTextField(grade3) {grade3 = it}
        Spacer8dp()
        CreditTextField(credit3) {credit3 = it}
        Spacer8dp()
        SubjectText(subject = "Year/Semester 4")
        GradeTextField(grade4) {grade4 = it}
        Spacer8dp()
        CreditTextField(credit4) {credit4 = it}
        Spacer8dp()



        Row {
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /*TODO*/
                                 val semester = Semester(grade1, credit1 ?: 0)
                    semesters.add(semester)
                    val totalCredit = semesters.sumOf { it.credit }
                    val totalGradePoint = semesters.sumOf { calculateGradePoints(it.grade , it.credit) }
                    if (totalCredit >0){
                        cgpa = totalGradePoint / totalCredit.toDouble()
                    } else {
                        cgpa = 0.0
                    }

                    grade1 = ""
                    credit1 = null
                    grade2 = ""
                    credit2 = null
                    grade3 = ""
                    credit3 = null
                    grade4 = ""
                    credit4 = null



                                 }, colors = ButtonDefaults.buttonColors(
                    Color(0xFFA4193D),
                ), shape = RoundedCornerShape(15.dp)
                ){
                    Text(

                    text = "Calculate CGPA", fontSize = 16.sp, color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                        )

                }
                Surface (
                    modifier = Modifier
                        .width(175.dp)
                        .padding(10.dp)
                        .wrapContentHeight(), color = Color(0xFFFFDFB9), shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        modifier = Modifier .padding(start = 10.dp),
                        text = "Your All Time \nCGPA : $cgpa",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 16.sp,
                            color = Color( 0xFF000000)
                        )
                        )
                }


            }
            Surface (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), color = Color(0xFFFFDFB9), shape = RoundedCornerShape(15.dp)
            ) {
                Column {
                    Text(
                        modifier = Modifier .fillMaxWidth(), textAlign = TextAlign.Center,
                        text = "Previous Semester",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 16.sp,
                            color = Color( 0xFF000000)
                        )
                    )
                    if (semesters.isNotEmpty()){
                        for (semester in semesters){
                            Text(text = "Grade: ${semester.grade} , Credit: ${semester.credit}" , color = Color.Black, fontFamily = FontFamily(Font(R.font.poppins_medium)), fontSize = 16.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                        }

                    }
                }

            }


        }


        }

        }

fun calculateGradePoints(grade: String, credit: Int) : Double {
    return  when (grade.uppercase()) {
        "A" -> 4.0
        "B" -> 3.0
        "C" -> 2.0
        "D" -> 1.0
        else -> 0.0
    } * credit


}

@Composable
fun Spacer8dp(){
    Spacer(modifier = Modifier.padding(top = 8.dp))
}

//@Preview(showBackground = true)
//@Composable
//fun CGPAPreview(){
//    CgpaCalcTheme {
//        CGPA()
//    }
//}
@Composable
fun SubjectText(subject :String){
    Text(text = subject, modifier = Modifier.fillMaxWidth(),
        style = TextStyle(fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            color = Color(0xFF000000)
        )
    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeTextField(grade: String, onValueChange: (String) -> Unit){
    TextField(value = grade, onValueChange = { text ->
        onValueChange(text)
    }, modifier = Modifier
        .fillMaxWidth()
        .height(47.dp), label = {Text(text = "Enter Grade", color = Color.White, fontSize = 12.sp)},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
//            textColor = Color.White,
            containerColor = Color(0xFF50586C),


        ),shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(fontSize = 12.sp, color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditTextField(credit: Int?, onValueChange: (Int?) -> Unit){
    TextField(value = credit?.toString() ?: "", onValueChange = { text ->
        onValueChange(text.toIntOrNull())
    }, modifier = Modifier
        .fillMaxWidth()
        .height(47.dp), label = {Text(text = "Enter Credit", color = Color.Black, fontSize = 12.sp)},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
//            textColor = Color.White,
            containerColor = Color(0xFFDCE2F0),


        ),shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(fontSize = 12.sp, color = Color.Black,
//            fontFamily = FontFamily(Font(R.font.poppins_medium))
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}