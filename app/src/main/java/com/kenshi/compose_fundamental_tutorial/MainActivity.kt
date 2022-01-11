package com.kenshi.compose_fundamental_tutorial

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kenshi.compose_fundamental_tutorial.ui.theme.Compose_fundamental_tutorialTheme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_fundamental_tutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //BoxWithConstraintContainer()
                    //TextContainer()
                    //ShapeContainer()
                    //ButtonsContainer()
                    //checkBoxContainer()
                    //MySnackBar()
                    //TextFieldTest()
                    NavigationGraph()
                }
            }
        }
    }
}

enum class NAV_ROUTE(val routeName: String, val description: String, val btnColor: Color) {
    MAIN("MAIN", "메인화면", Color(0xFF3949AB)),
    LOGIN("LOGIN", "로그인 화면", Color(0xFFE91E63)),
    REGISTER("REGISTER", "회원가입 화면", Color(0xFF9C27B0)),
    USER_PROFILE("USER_PROFILE", "유저 프로필 화면", Color(0xFF4CAF50)),
    SETTING("SETTING", "설정 화면", Color(0xFFFF5722)),
}

//네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController) {
    //lambda 로 이동(이벤트 발생) -> 특정 라우트로 이동
    val navTo: (NAV_ROUTE) -> Unit = { route ->
        navHostController.navigate(route.routeName)
    }

    // 뒤로 가기 이동 (NavHost 를 이용해서 뒤로 보냄)
    val goBack: () -> Unit = {
        //뒤로 보내는 코드
        navHostController.navigateUp()
    }
}

@Composable
fun NavigationGraph(startRoute: NAV_ROUTE = NAV_ROUTE.MAIN) {
    // 네비게이션 컨트롤러
    val navController = rememberNavController()

    // 네비게이션 라우트 액션
    val routeAction = remember(navController) { RouteAction(navController) }

    // NavHost 로 네비게이션 결정
    // 네비게이션 연결할 녀석들을 설정한다. (이동시킬 화면들을 세팅)
    NavHost(navController, startRoute.routeName) {

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.MAIN.routeName){
            // 화면 = 값
            MainScreen(routeAction = routeAction)
        }

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.LOGIN.routeName){
            // 화면 = 값
            LoginScreen(routeAction = routeAction)
        }

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.REGISTER.routeName){
            // 화면 = 값
            RegisterScreen(routeAction = routeAction)
        }

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.USER_PROFILE.routeName){
            // 화면 = 값
            UserProfileScreen(routeAction = routeAction)
        }

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.SETTING.routeName){
            // 화면 = 값
            SettingScreen(routeAction = routeAction)
        }
    }
}

// 메인 화면
// RouteAction 으로 화면을 이동시켜야 하기 때문에 routeAction 을 가지고 있음
@Composable
fun MainScreen(routeAction: RouteAction) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            NavButton(route = NAV_ROUTE.MAIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.LOGIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.REGISTER, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.USER_PROFILE, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.SETTING, routeAction = routeAction)
        }
    }
}

// 로그인 화면
@Composable
fun LoginScreen(routeAction: RouteAction) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "로그인 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(
                onClick = routeAction.goBack,
                modifier = Modifier.padding(16.dp)
                                    //offset : 버튼 이동
                                    .offset(y = 100.dp)
            ) {
                Text("뒤로가기")
            }
        }
    }
}

// 로그인 화면
@Composable
fun RegisterScreen(routeAction: RouteAction) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "회원가입 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(
                onClick = routeAction.goBack,
                modifier = Modifier.padding(16.dp)
                    //offset : 버튼 이동
                    .offset(y = 100.dp)
            ) {
                Text("뒤로가기")
            }
        }
    }
}

// 로그인 화면
@Composable
fun UserProfileScreen(routeAction: RouteAction) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "유저 프로필 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(
                onClick = routeAction.goBack,
                modifier = Modifier.padding(16.dp)
                    //offset : 버튼 이동
                    .offset(y = 100.dp)
            ) {
                Text("뒤로가기")
            }
        }
    }
}

// 로그인 화면
@Composable
fun SettingScreen(routeAction: RouteAction) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "설정 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(
                onClick = routeAction.goBack,
                modifier = Modifier.padding(16.dp)
                    //offset : 버튼 이동
                    .offset(y = 100.dp)
            ) {
                Text("뒤로가기")
            }
        }
    }
}




// column 에 있는 네비게이션 버튼
@Composable
fun ColumnScope.NavButton(route: NAV_ROUTE, routeAction: RouteAction) {
    //버튼 클릭시 이동하도록
    Button(
        onClick = { routeAction.navTo(route) },
        colors = ButtonDefaults.buttonColors(backgroundColor = route.btnColor),
        modifier = Modifier.weight(1f).padding(8.dp).fillMaxSize()
    ) {
        Text(
            text = route.description,
            style = TextStyle(Color.White, 22.sp, FontWeight.Medium)
        )
    }
}


@Composable
fun TextFieldTest() {
    var userInput by remember { mutableStateOf(TextFieldValue()) }

    var phoneNumberInput by remember { mutableStateOf(TextFieldValue()) }

    var emailInput by remember { mutableStateOf(TextFieldValue()) }

    val shouldShowPassword = remember { mutableStateOf(false) }

    var passwordInput by remember { mutableStateOf(TextFieldValue()) }

    val passwordResource: (Boolean) -> Int = {
        if (it) {
            R.drawable.ic_visibility
        } else {
            R.drawable.ic_visibility_off
        }
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            singleLine = false,
            maxLines = 2,
            // 입력값을 textField 에 넣어줌
            onValueChange = { newValue -> userInput = newValue },
            label = { Text("사용자 입력") },
            placeholder = { Text("작성해 주세요") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumberInput,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { newValue -> phoneNumberInput = newValue },
            label = { Text("전화번호") },
            placeholder = { Text("010-1234-5678") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailInput,
            singleLine = true,
            //왼쪽 아이콘
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
//            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null ) },
            trailingIcon = {
                IconButton(onClick = { Log.d(TAG, "TextFieldTest: 체크버튼 클릭") }) {
                    Icon(imageVector = Icons.Default.CheckCircle, null)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { newValue -> emailInput = newValue },
            label = { Text(text = "전화번호") },
            placeholder = { Text(text = "010-1234-5678") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            singleLine = true,
            //왼쪽 아이콘
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = {
                    Log.d(TAG, "TextFieldTest: 비밀번호 visible 버튼 클릭")
                    shouldShowPassword.value = !shouldShowPassword.value
                }) {
                    //shouldShowPassword.value -> state 이기 때문에, 값 까지 들어가야함
                    Icon(painter = painterResource(id = passwordResource(shouldShowPassword.value)),
                        null)
                }
            },
            //password 를 보여준다는 뜻
            visualTransformation = if (shouldShowPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { newValue -> passwordInput = newValue },
            label = { Text("비밀번호") },
            placeholder = { Text("비밀번호를 입력해주세요") }
        )
    }
}

//fun TextField(
//    value: TextFieldValue,
//    onValueChange: (TextFieldValue) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    label: @Composable (() -> Unit)? = null,
//    placeholder: @Composable (() -> Unit)? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions(),
//    singleLine: Boolean = false,
//    maxLines: Int = Int.MAX_VALUE,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    shape: Shape =
//        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
//    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
//)

@Composable
fun MySnackBar() {

    // remember : 변경이 되는 것을 감지, 상태를 가지고 있음 
    // snackBar 가 닫힐때 buttonTitle 이 변경됨
    val snackBarHostState = remember { SnackbarHostState() }

    //Composable 안에서 비동기 로직 처리시 코루틴 스코프내부에서 수행
    val coroutineScope = rememberCoroutineScope()

    val buttonTitle: (SnackbarData?) -> String = { snackBarData ->
        if (snackBarData != null) {
            "스낵바 숨기기"
        } else {
            "스낵바 보여주기"
        }
    }

    val buttonColor: (SnackbarData?) -> Color = { snackBarData ->
        if (snackBarData != null) {
            Color.Black
        } else {
            Color.Blue
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                buttonColor(snackBarHostState.currentSnackbarData)
            ),
            onClick = {
                Log.d(TAG, "MySnackBar: 스낵바 버튼 클릭")
                if (snackBarHostState.currentSnackbarData != null) {
                    Log.d(TAG, "MySnackBar: 이미 스낵바가 있다.")
                    //snackBar 닫기
                    snackBarHostState.currentSnackbarData?.dismiss()
                    return@Button //Button Scope 이므로
                }
                coroutineScope.launch {
                    val result = snackBarHostState.showSnackbar(
                        "오늘도 빡코딩?! 👍👏",
                        "확인",
                        SnackbarDuration.Short
                    ).let { result ->
                        when (result) {
                            SnackbarResult.Dismissed -> Log.d(TAG, "MySnackBar: 스낵바 닫아짐")
                            SnackbarResult.ActionPerformed -> Log.d(TAG, "MySnackBar: 스낵바 확인 버튼 클릭")
                        }
                    }
                } // coroutineScope
            }) {
            Text(
                color = Color.White,
                text = buttonTitle(snackBarHostState.currentSnackbarData)
            )
        }

        // 스낵바가 보여지는 부분
        SnackbarHost(hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun Container() {
    //아이템을 가로로 나열: Row
    //핵심 두가지 arrangement, alignment
    // arrangement 요소를 어떤식으로 배열할지
    // arrangement 는 Row, Column 같은 요소들이 들어가는
    // 컨테이너 성격의 Composable 에서 요소들의 아이템을 정렬할 때 사용됨
    // 웹 개발 css 에서 flex 와 비슷하다고 보면 됨

    //alignment
    //gravity 와 성격이 유사
    //ConstrainLayout 의 start, end 와 비슷하다고 보면 됨
    //horizontal Arrangement 이니까 Start, End, Center 만 존재
    //Arrangement.Start : 왼쪽으로
    //Arrangement.End: 오른쪽으로
    //Arrangement.SpaceAround: 요소들 사이에 공간을 넣음
    //Arrangement.Center: 요소들을 가운데 정렬
    //Arrangement.SpaceBetween: 사이에 공간을 밀어넣기 좌우로 밀려남
    //Arrangement.SpaceEvenly: 요소들 사이의 공간을 똑같이 하기, 공간의 간격을 모두 같게
    Row(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Row 기 때문에 horizontalArrangement, verticalAlignment
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun VerticalContainer() {
    Column(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

//contentPadding: 내용물을 밀어넣는 공간
//interactionSource: 사용자의 인터렉션 처리, 인터렉션(상태)을 감지

@Composable
fun ButtonsContainer() {

    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))
    //interactionSource instance 생성
    // remember - 변경이 일어나면 뷰를 다시 그림
    val interactionSource = remember { MutableInteractionSource() }

    // 눌러졌는지 여부, 상태를 변수에 담음
    // 연결(장착)
    val isPressed by interactionSource.collectIsPressedAsState()

    val interactionSourceForSecondBtn = remember { MutableInteractionSource() }

    val pressStatusTitle = if (isPressed) "버튼을 누르고 잇다" else "버튼에서 손을 뗐다"

    // 눌러졌는지 여부, 상태를 변수에 담음
    // 연결 장착
    val isPressedForSecondBtn by interactionSourceForSecondBtn.collectIsPressedAsState()

    val pressedBtnRadius = if (isPressedForSecondBtn) 20.dp else 0.dp

    //기본 애니메이션 적용
    val pressedBtnRadiusAnim: Dp by animateDpAsState(
        targetValue = if (isPressedForSecondBtn) 20.dp else 0.dp)


    Column(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp
            ),
            enabled = true,
            onClick = {
                Log.d(TAG, "ButtonsContainer:  버튼 1 클릭")
            }) {
            Text(text = "버튼 1")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                //enabled 가 false 일때 적용되는 elevation
                disabledElevation = 0.dp
            ),
            enabled = false,
            onClick = {
                Log.d(TAG, "ButtonsContainer: 버튼 2 클릭")
            }) {
            Text(text = "버튼 2")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                //enabled 가 false 일때 적용되는 elevation
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = CircleShape,
            onClick = {
                Log.d(TAG, "ButtonsContainer: 버튼 3 클릭")
            }) {
            Text(text = "버튼 3")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                //enabled 가 false 일때 적용되는 elevation
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, Color.Yellow),
            //버튼의 내용(글자) 사이의 간격, 패딩을 많이줄 수록 버튼이 커지게됨
            //패딩 방향은 내 맘대로
            contentPadding = PaddingValues(40.dp),
            onClick = {
                Log.d(TAG, "ButtonsContainer: 버튼 4 클릭")
            }) {
            Text(text = "버튼 4")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                //enabled 가 false 일때 적용되는 elevation
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            //클릭을 감지할 수 있게 됨
            interactionSource = interactionSource,
            border = BorderStroke(4.dp, buttonBorderGradient),
            onClick = {
                Log.d(TAG, "ButtonsContainer: 버튼 5 클릭")
            }) {
            Text(text = "버튼 5", color = Color.White)
        }

//        if (isPressed) {
//            Text(text = "버튼을 누르고 있다.")
//        } else {
//            Text(text = "버튼에서 손을 뗐다.")
//        }
        Text(text = pressStatusTitle)

        //피그마 커스텀 버튼 제작 (색깔있는 그림자)
        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                //enabled 가 false 일때 적용되는 elevation
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            //클릭을 감지할 수 있게 됨
            interactionSource = interactionSourceForSecondBtn,
            border = BorderStroke(4.dp, buttonBorderGradient),
            modifier = Modifier.drawColoredShadow(
                color = Color.Red,
                alpha = 0.5f,
                //corner radius 값과 일치하게
                borderRadius = 10.dp,
                //가변적으로 radius 를 설정 (애니메이션은 별도로 추가해야)
                //shadowRadius = pressedBtnRadius,
                pressedBtnRadiusAnim,
                offsetY = 0.dp,
                offsetX = 0.dp
            ),
            onClick = {
                Log.d(TAG, "ButtonsContainer: 버튼 5 클릭")
            }) {
            Text(text = "버튼 5", color = Color.White)
        }
    }
}

//text: String,
//modifier: Modifier = Modifier,
//color: Color = Color.Unspecified,
//fontSize: TextUnit = TextUnit.Unspecified,
//fontStyle: FontStyle? = null,
//fontWeight: FontWeight? = null,
//fontFamily: FontFamily? = null,
//letterSpacing: TextUnit = TextUnit.Unspecified,
//textDecoration: TextDecoration? = null,
//textAlign: TextAlign? = null,
//lineHeight: TextUnit = TextUnit.Unspecified,
//overflow: TextOverflow = TextOverflow.Clip,
//softWrap: Boolean = true,
//maxLines: Int = Int.MAX_VALUE,
//onTextLayout: (TextLayoutResult) -> Unit = {},
//style: TextStyle = LocalTextStyle.current

@Composable
fun TextContainer() {
    val name = "쩡대리"

    var words = stringResource(id = R.string.dummy_short_text)
    var wordsArray = words.split(" ")

    val scrollState = rememberScrollState()

    //스크롤을 하려면 스크롤 상태를 기억해야함
    Column(
//        verticalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            //스크롤 기능은 modifier 내에서 장착
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "안녕하세요? ! $name",
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = "안녕하세요?  $name",
            style = TextStyle(
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = "안녕하세요?  $name",
            style = TextStyle(
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        //한글 입숨
        Text(
            text = stringResource(id = R.string.dummy_short_text),
            maxLines = 3,
            //텍스트가 짤려서 세줄까지만 보이고 ...
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Justify,
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.LineThrough,
                        TextDecoration.Underline
                    )
                )
            ),
            fontWeight = FontWeight.W200,
            fontSize = 20.sp,
            //폰트 적용
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        //특정한 문자만 속성을 바꾸는 방법
        Text(text = buildAnnotatedString {
            append("안녕하세요?")

            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append("개발하는 정대리 입니다!")
            }
            withStyle(
                style = SpanStyle(color = Color.Red)
            ) {
                append("빡! 코딩")
            }
        })

        Text(text = buildAnnotatedString {
            wordsArray.forEach {
                if (it.contains("사막")) {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append("$it ")
                    }
                } else {
                    append("$it ")
                }
            }
        })

        ClickableText(text = AnnotatedString("클릭미!"), onClick = {
            Log.d("TAG", "TextContainer: 클릭미가 클릭되었다!")
        })

        Text(
            text = stringResource(id = R.string.dummy_long_text),
            style = TextStyle(lineHeight = 20.sp)
        )
    }
}

// Box 는 겹칠 수 있다 (기존 relative layout, constraint layout, frameLayout 과 같이)
// 아내로 내려갈수록 뷰를 올리는 방식

// alignment 는 row, column 보다 다양하게 지원

// Alignment.BottomCenter : 컨테이너의 중앙 아래
// Alignment.BottomEnd : 컨테이너의 아래 오른쪽
// Alignment.BottomStart : 컨테이너의 아래 왼쪽

// Alignment.Center : 컨테이너의 정중앙
// Alignment.CenterStart : 컨테이너의 중앙 왼쪽
// Alignment.CenterEnd : 컨테이너으 중앙 오른쪽

// Alignment. TopCenter : 컨테이너의 위 중앙
// Alignment. TopEnd : 컨테이너의 위 오른쪽
// Alignment. TopStart : 컨테이너의 위 왼쪽

// propagateMinConstraints 해당 옵션을 true 로 하면
// 박스 안에 있는 제일 작은 크기의 뷰를 컨테이너의 박스 크기 만큼 Constraint 를 검
// -> 제일 작은 애로 뷰를 꽉 채운다는 의미

@Composable
fun BoxContainer() {
    Box(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        contentAlignment = Alignment.Center,
        //propagateMinConstraints = true
    ) {
        //박스가 겹처 하나밖에 보이지 않음
        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
        DummyBox(color = Color.Blue)

    }
}


//화면 전환에 따른 레이아웃의 변경을 필요로 할때 사용
@Composable
fun BoxWithConstraintContainer() {
    BoxWithConstraints(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        if (this.minHeight > 400.dp) {
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        } else {
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Yellow)
        }
        Text(text = "minHeight: ${this.minHeight}")

//        Column {
//            BoxWithConstraintItem(modifier = Modifier
//                .size(200.dp)
//                .background(Color.Yellow)
//            )
//
//            BoxWithConstraintItem(modifier = Modifier
//                .size(300.dp)
//                .background(Color.Green)
//            )
//        }
    }
}

@Composable
fun BoxWithConstraintItem(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier,
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
        //propagateMinConstraints = true
    ) {
        //크기 변화에 대한 처리 가능
        if (this.minWidth > 200.dp) {
            Text(text = "이것은 큰 상자이다.")
        } else {
            Text(text = "이것은 작은 상자이다.")
        }
        //BoxWithConstraintScope 라는 것이 생김, this 로 속성에 접근 가능

//        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
//        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
//        DummyBox(color = Color.Blue)

    }
}

@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color? = null) {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    // color 값이 있으면 해당 값을 넣어주고 값이 없다면 랜덤 값을 넣어주기
    val randomColor = color?.let { it } ?: Color(red, green, blue)
    Box(
        modifier = modifier
            .size(100.dp)
            .background(randomColor)
    )
}

@Composable
fun ShapeContainer() {

    //Compose 에서는 특정 변수의 대한 상태를 가지고 있어야 함
    //state value 가 바뀌면 화면이 다시 rendering
    var polySides by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .background(Color.White)
            //전체 사이즈 적용
            .fillMaxSize(),
        //Column 이기 때문에 verticalArrangement, horizontalAlignment
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // clip -> 원하는 모양으로 깍을 수 있음
        //DummyBox(modifier = Modifier.clip(RectangleShape))
        DummyBox(modifier = Modifier.clip(CircleShape))
        DummyBox(modifier = Modifier.clip(RoundedCornerShape(10.dp)))
        // Custom shape
        DummyBox(modifier = Modifier.clip(TriangleShape()))
        //여기선 radius 가 도형의 크기라고 이해
        DummyBox(modifier = Modifier.clip(PolyShape(polySides, 100f)))

        Text(text = "polySides: $polySides")

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                polySides += 1
            }) {
                Text(text = "polySides + 1")
            }
            Button(onClick = {
                polySides = 3
            }) {
                Text(text = "초기화")
            }

        }
    }
}

class TriangleShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(path = Path().apply { this.polygon(sides, radius, size.center) })
    }
}

//도형을 만드는 메소드
fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides

    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}


//@Composable
//fun Checkbox(

//    checked: Boolean,
//    onCheckedChange: ((Boolean) -> Unit)?, <- 콜백 (클릭 이벤트)
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    colors: CheckboxColors = CheckboxDefaults.colors()

// Composable 에서 MutableState 객체를 선언하는 데는 세가지 방법이 있다.
// - val mutableState = remember { mutableStateOf(default) }
// - var value by remember { mutableStateOf(default) }
// - val (value, setValue) = remember { mutableStateOf(default) }
// 정대리 피셜 값을 넣는 측면에서 2번째 방법이 젤 간편함

@Composable
fun checkBoxContainer() {
    //상태 체크 mutable state
    val checkedStatusForFirst = remember { mutableStateOf(false) }

    val checkedStatusForSecond = remember { mutableStateOf(false) }

    val checkedStatusForThird = remember { mutableStateOf(false) }

    //위에 1~3번이 모두 체크가 되면 모두 동의(4번) 이 체크되도록
    // val checkedStatusForForth = remember { mutableStateOf(false) }

    val checkedStatesArray = listOf(
        checkedStatusForFirst,
        checkedStatusForSecond,
        checkedStatusForThird,
    )

    val allBoxChecked: (Boolean) -> Unit = { isAllBoxChecked ->
        //isAllBoxChecked -> Boolean
        Log.d(TAG, "checkBoxContainer: isAllBoxChecked : $isAllBoxChecked")
        checkedStatesArray.forEach { it.value == isAllBoxChecked }
        //forEach 를 통한 반복문
        //checkedStatesArray.forEach { it.value = isAllBoxChecked }
        //Array 의 요소들이 하나씩 다 들어옴
        // 이 콜백을 호출하면 모든 요소의 상태가 다 변함
    }

//익숙해져야할 표현법들
//    var checkedStatusForSecond by remember { mutableStateOf(false) }
//
//    // 데이터를 가져올 때, 데이터 값을 설정할 때
//    val (checkedStatusForThird, setCheckedStatusForThird) = remember { mutableStateOf(false) }

    // 전부 조건이 만족되어야 true 로 값이 설정됨 (고차함수)
    val checkedStatusForForth: Boolean = checkedStatesArray.all { it.value == true }
    //간단하게
    //val checkedStatusForForth : Boolean = checkedStatesArray.all { it.value }

    var (checkedStatusForFourth, setCheckedStatusForFourth) = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CheckBoxWithTitle("1번 확인사항", checkedStatusForFirst)
        CheckBoxWithTitle("2번 확인사항", checkedStatusForSecond)
        CheckBoxWithTitle("3번 확인사항", checkedStatusForThird)

//        Checkbox(
//            //enabled = false,
//            enabled = true,
//            checked = checkedStatusForFirst.value,
//            onCheckedChange = { isChecked ->
//            Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
//            checkedStatusForFirst.value = isChecked
//        })
//
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForSecond,
//            onCheckedChange = { isChecked ->
//            Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
//            checkedStatusForSecond = isChecked
//        })

//        Spacer(modifier = Modifier.height(30.dp))
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForThird,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red
//            ),
//            onCheckedChange = { isChecked ->
//                Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
//                setCheckedStatusForThird.invoke(isChecked)
//        })
        Spacer(modifier = Modifier.height(10.dp))

        //얘를 클릭하면 1번~3번이 모두 체크되도록
        AllAgreeCheckBox("모두 동의하십니까?", checkedStatusForForth, allBoxChecked)

        MyCustomCheckBox(title = "커스텀 체크박스 리플 O", withRipple = true)
        MyCustomCheckBox(title = "커스텀 체크박스 리플 X", withRipple = false)
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForFourth,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red,
//                uncheckedColor = Color(0xFFEF9A9A),
//                checkmarkColor = Color.Black,
//                disabledColor = Color(0xFF90CAF9)
//            ),
//            onCheckedChange = { isChecked ->
//                Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
//                setCheckedStatusForFourth.invoke(isChecked)
//            })
    }
}

@Composable
fun CheckBoxWithTitle(title: String, isCheckedState: MutableState<Boolean>) {

    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            //enabled = false,
            enabled = true,
            checked = isCheckedState.value,
            onCheckedChange = { isChecked ->
                Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
                isCheckedState.value = isChecked
            })
        Text(text = title)
    }
}

@Composable
fun AllAgreeCheckBox(
    title: String,
    shouldChecked: Boolean,
    allBoxChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            //enabled = false,
            enabled = true,
            checked = shouldChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color(0xFFEF9A9A),
                checkmarkColor = Color.White,
                disabledColor = Color(0xFF90CAF9)
            ),
            onCheckedChange = { isChecked ->
                Log.d(TAG, "checkBoxContainer: isChecked: $isChecked")
//                isCheckedState.value = isChecked
                allBoxChecked(isChecked)
            })
        Text(text = title)
    }
}


//fun colors()

//checkedColor: Color = MaterialTheme.colors.secondary, //선택된 color
//uncheckedColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
//checkmarkColor: Color = MaterialTheme.colors.surface,
//disabledColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
//disabledIndeterminateColor: Color = checkedColor.copy(alpha = ContentAlpha.disabled)

@Composable
//Ripplen: 클릭 시 물결표시
fun MyCustomCheckBox(title: String, withRipple: Boolean = false) {

    //by 로 하면 value 세팅하지않아도 됨
//    var isCheckedState by remember { mutableStateOf(false) }
//    var isChecke =  remember { mutableStateOf(false) }
    // isChecked => get, setIsChecked => set 이라 생각
    var (isChecked, setIsChecked) = remember { mutableStateOf(false) }

    var togglePainter = if (isChecked) R.drawable.ic_checked else R.drawable.ic_unchecked

    var checkedInfoString = if (isChecked == true) "체크됨" else "체크안됨"

    var rippleEffect = if (withRipple) rememberRipple(
        radius = 30.dp,
        bounded = false,
        color = Color.Blue
    ) else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //이미지 자체를 바꾸는 것이기 때문에 체크박스를 넣을 수 없음
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
//            .background(Color.Yellow)
                .clickable(
                    //indication 이 리플인데 (클릭할때의 애니메이션) 물결표시가 발생하지 않음
                    indication = rippleEffect,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    //이벤트 처라
                    setIsChecked.invoke(!isChecked)
                    Log.d(TAG, "MyCustomCheckBox: Clicked! / $isChecked")
                }) {
            Image(
                painter = painterResource(id = togglePainter),
                contentDescription = null,
            )
        }
//        rememberRipple(
//            bounded: Boolean = true,
//        radius: Dp = Dp.Unspecified,
//        color: Color = Color.Unspecified
//        )

//        Modifier.clickable(
//            enabled = enabled,
//            onClickLabel = onClickLabel,
//            onClick = onClick,
//            role = role,
//            indication = LocalIndication.current,
//            interactionSource = remember { MutableInteractionSource() }
//        )

        Text(text = "$title / $checkedInfoString")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {
        //BoxWithConstraintContainer()
        //TextContainer()
        //ShapeContainer()
        //ButtonsContainer()
        checkBoxContainer()
    }
}