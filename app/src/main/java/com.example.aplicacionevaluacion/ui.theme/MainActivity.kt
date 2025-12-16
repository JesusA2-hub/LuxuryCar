package com.example.aplicacionevaluacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplicacionevaluacion.ui.theme.AplicacionEvaluacionTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplicacionEvaluacionTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF3F4F6)) {
                    AppWithLogin()
                }
            }
        }
    }
}


@Composable
fun AppWithLogin() {
    var loggedIn by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("login") }

    if (!loggedIn) {
        when (currentScreen) {
            "login" -> LoginScreen(
                onLogin = { loggedIn = true },
                onRegister = { currentScreen = "register" },
                onForgot = { currentScreen = "forgot" }
            )
            "register" -> RegisterScreen { currentScreen = "login" }
            "forgot" -> ForgotPasswordScreen { currentScreen = "login" }
        }
    } else {
        LuxuryCarApp()
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit, onForgot: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LuxuryCar", fontSize = 44.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1F2937))
        Text("Autos de lujo y subastas en vivo", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(40.dp))

        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (user == "Juan" && pass == "1234") onLogin() else error = "Datos incorrectos"
            },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) { Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp) }

        if (error.isNotEmpty()) Text(error, color = Color.Red, modifier = Modifier.padding(top = 12.dp))

        Spacer(Modifier.height(24.dp))
        Text("Crear nueva cuenta", color = Color(0xFF2563EB), modifier = Modifier.clickable { onRegister() })
        Spacer(Modifier.height(8.dp))
        Text("¿Olvidaste tu contraseña?", color = Color(0xFFDC2626), modifier = Modifier.clickable { onForgot() })
    }
}

@Composable
fun RegisterScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(32.dp))
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.fillMaxWidth()) {
            Text("Registrarme")
        }
        Spacer(Modifier.height(16.dp))
        Text("Volver", modifier = Modifier.clickable { onBack() })
    }
}

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Recuperar Contraseña", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(32.dp))
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.fillMaxWidth()) {
            Text("Enviar enlace")
        }
        Spacer(Modifier.height(24.dp))
        Text("Volver", modifier = Modifier.clickable { onBack() })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuxuryCarApp() {
    val vm: CarViewModel = viewModel()
    val cars by vm.cars.collectAsState()
    val loading by vm.isLoading.collectAsState()
    val usedCars = cars.filter { it.year < 2023 }
    val auctionCars = cars.filter { it.seller.contains("Subastas", ignoreCase = true) }

    var currentScreen by remember { mutableStateOf("Home") }
    val favorites = remember { mutableStateListOf<Car>() }
    var selectedCar by remember { mutableStateOf<Car?>(null) }
    var carToEdit by remember { mutableStateOf<Car?>(null) }
    var selectedAuction by remember { mutableStateOf<Car?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LuxuryCar", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black, titleContentColor = Color.White)
            )
        },
        bottomBar = {
            if (selectedCar == null && carToEdit == null && selectedAuction == null) {
                BottomNavigationBar(currentScreen) { currentScreen = it }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())

            when {
                carToEdit != null -> AutoFormScreen(carToEdit, { carToEdit = null; currentScreen = "Home" }) { carToEdit = null }
                selectedCar != null -> CarDetailScreen(selectedCar!!, vm, { selectedCar = null }) { carToEdit = it }
                selectedAuction != null -> AuctionDetailScreen(selectedAuction!!) { selectedAuction = null }
                currentScreen == "Alta" -> AutoFormScreen(onSaveSuccess = { currentScreen = "Home" }, onBack = { currentScreen = "Home" })
                currentScreen in listOf("Porsche", "Ferrari", "Cadillac") -> CarListSection(currentScreen, cars.filter { it.brand == currentScreen }, favorites) { selectedCar = it }
                currentScreen == "Usados" -> CarListSection("Usados", usedCars, favorites) { selectedCar = it }
                currentScreen == "Favoritos" -> FavoritesSection(favorites) { selectedCar = it }
                currentScreen == "Subastas" -> AuctionListSection(auctionCars) { selectedAuction = it }
                else -> HomeSection { currentScreen = it }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selected: String, onSelect: (String) -> Unit) {
    NavigationBar(containerColor = Color.Black) {
        listOf("Home", "Alta", "Usados", "Favoritos", "Subastas").forEach { item ->
            NavigationBarItem(
                selected = selected == item,
                onClick = { onSelect(item) },
                label = { Text(item, color = if (selected == item) Color.White else Color.Gray) },
                icon = {}
            )
        }
    }
}

@Composable
fun HomeSection(onBrandClick: (String) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Bienvenido a LuxuryCar", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        listOf("Porsche", "Ferrari", "Cadillac").forEach { brand ->
            Surface(
                color = when (brand) {
                    "Porsche" -> Color(0xFF1F2937)
                    "Ferrari" -> Color(0xFFB91C1C)
                    else -> Color(0xFF1E40AF)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onBrandClick(brand) }
            ) {
                Text(brand, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(32.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    }
}

@Composable
fun CarListSection(title: String, cars: List<Car>, favorites: MutableList<Car>, onCarClick: (Car) -> Unit) {
    Column {
        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cars) { car ->
                CarCard(car, car in favorites, { if (car in favorites) favorites.remove(car) else favorites.add(car) }) { onCarClick(car) }
            }
        }
    }
}

@Composable
fun CarCard(car: Car, isFavorite: Boolean, onFavoriteClick: () -> Unit, onClick: () -> Unit) {
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 8.dp, color = Color.White, modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(16.dp)) {
            Text("${car.brand} ${car.model}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Año: ${car.year} • $${car.price}", color = Color(0xFF16A34A), fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onFavoriteClick, colors = ButtonDefaults.buttonColors(if (isFavorite) Color.Red else Color.Black)) {
                Text(if (isFavorite) "Quitar" else "Favorito", color = Color.White)
            }
        }
    }
}

@Composable
fun FavoritesSection(favorites: List<Car>, onCarClick: (Car) -> Unit) {
    Column {
        Text("Favoritos", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        if (favorites.isEmpty()) Text("No tienes favoritos aún", color = Color.Gray)
        else LazyColumn { items(favorites) { CarCard(it, true, {}, { onCarClick(it) }) } }
    }
}

@Composable
fun AuctionListSection(auctions: List<Car>, onClick: (Car) -> Unit) {
    Column {
        Text("Subastas en Vivo", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
        LazyColumn {
            items(auctions) { car ->
                Surface(modifier = Modifier.padding(8.dp).clickable { onClick(car) }, color = Color(0xFFFFFBEA), shape = RoundedCornerShape(12.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${car.brand} ${car.model}", fontWeight = FontWeight.Bold)
                        Text("Precio actual: $${car.price}", color = Color(0xFFD97706))
                    }
                }
            }
        }
    }
}

@Composable
fun AuctionDetailScreen(car: Car, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("${car.brand} ${car.model}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("$${car.price}", fontSize = 28.sp, color = Color(0xFF16A34A))
        Text("Vendedor: ${car.seller}")
        Spacer(Modifier.height(32.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Regresar") }
    }
}


@Composable
fun AutoFormScreen(carToEdit: Car? = null, onSaveSuccess: () -> Unit, onBack: () -> Unit) {
    val vm: CarViewModel = viewModel()
    var brand by remember { mutableStateOf(carToEdit?.brand ?: "") }
    var model by remember { mutableStateOf(carToEdit?.model ?: "") }
    var year by remember { mutableStateOf(carToEdit?.year?.toString() ?: "") }
    var price by remember { mutableStateOf(carToEdit?.price?.toString() ?: "") }
    var seller by remember { mutableStateOf(carToEdit?.seller ?: "") }
    var legal by remember { mutableStateOf(carToEdit?.legalStatus ?: "Legalizado") }
    var warranty by remember { mutableStateOf(carToEdit?.warranty ?: "12 meses") }
    var isAuction by remember { mutableStateOf(carToEdit?.seller?.contains("Subastas", true) == true) }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Text(
            text = if (carToEdit != null) "Editar Auto" else "Nuevo Auto",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Marca") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Año") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())

        // NUEVO: Opción de subasta
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Este auto entra a Subastas?", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            Switch(
                checked = isAuction,
                onCheckedChange = { isAuction = it }
            )
        }

        if (isAuction) {
            seller = "Subastas LuxuryCar"
            Text("Vendedor: Subastas LuxuryCar", color = Color(0xFFD97706), fontWeight = FontWeight.Bold)
        } else {
            OutlinedTextField(value = seller, onValueChange = { seller = it }, label = { Text("Vendedor (opcional)") }, modifier = Modifier.fillMaxWidth())
        }

        OutlinedTextField(value = legal, onValueChange = { legal = it }, label = { Text("Estatus Legal") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = warranty, onValueChange = { warranty = it }, label = { Text("Garantía") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val y = year.toLongOrNull() ?: 0L
                val p = price.toLongOrNull() ?: 0L
                if (brand.isBlank() || model.isBlank() || y == 0L || p == 0L) {
                    message = "Completa marca, modelo, año y precio"
                } else {
                    val finalSeller = if (isAuction) "Subastas LuxuryCar" else if (seller.isBlank()) "Particular" else seller
                    val car = (carToEdit ?: Car()).copy(
                        brand = brand,
                        model = model,
                        year = y,
                        price = p,
                        seller = finalSeller,
                        legalStatus = legal,
                        warranty = warranty
                    )
                    vm.saveCar(car) {
                        message = if (it) "Guardado con éxito!" else "Error al guardar"
                        if (it) onSaveSuccess()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF16A34A)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar Auto") }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }

        message?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = if (it.contains("éxito")) Color(0xFF059669) else Color.Red, fontWeight = FontWeight.Medium)
        }
    }
}


@Composable
fun CarDetailScreen(car: Car, viewModel: CarViewModel, onBack: () -> Unit, onEdit: (Car) -> Unit) {
    var showPayment by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }

    if (showPayment) {
        PaymentScreen(car, viewModel, { showPayment = false; onBack() }) { showPayment = false }
    } else {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("${car.brand} ${car.model}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("$${car.price}", fontSize = 28.sp, color = Color(0xFF16A34A))
            Text("Año: ${car.year} • Vendedor: ${car.seller}")
            Text("Estatus: ${car.legalStatus} • Garantía: ${car.warranty}")

            Button(onClick = { showPayment = true }, colors = ButtonDefaults.buttonColors(Color(0xFF16A34A)), modifier = Modifier.fillMaxWidth()) {
                Text("Comprar con Tarjeta", color = Color.White, fontSize = 18.sp)
            }
            Row {
                Button(onClick = { onEdit(car) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFD97706))) { Text("Editar") }
                Button(onClick = { showDelete = true }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color.Red)) { Text("Eliminar") }
            }
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Regresar") }

            if (showDelete) {
                AlertDialog(
                    onDismissRequest = { showDelete = false },
                    title = { Text("Eliminar") },
                    text = { Text("¿Seguro?") },
                    confirmButton = {
                        Button(onClick = {
                            car.documentId?.let { id -> viewModel.deleteCar(id) { onBack() } }
                            showDelete = false
                        }) { Text("Sí") }
                    },
                    dismissButton = { OutlinedButton(onClick = { showDelete = false }) { Text("No") } }
                )
            }
        }
    }
}

@Composable
fun PaymentScreen(car: Car, viewModel: CarViewModel, onSuccess: () -> Unit, onBack: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var holder by remember { mutableStateOf("Juan Pérez") }
    var processing by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pago Seguro", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("${car.brand} ${car.model} - $${car.price}", fontSize = 20.sp, color = Color(0xFF16A34A))

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it.filter { it.isDigit() }.chunked(4).joinToString(" ").take(19) },
            label = { Text("Número de tarjeta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cvv,
            onValueChange = { if (it.length <= 3) cvv = it },
            label = { Text("CVV") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = holder, onValueChange = { holder = it }, label = { Text("Titular") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(32.dp))
        if (processing) CircularProgressIndicator()

        Button(
            onClick = {
                if (cardNumber.length < 19 || cvv.length < 3) {
                    message = "Completa los datos"
                    return@Button
                }
                processing = true
                scope.launch {
                    delay(3000)
                    val purchase = Purchase(
                        carId = car.documentId ?: "",
                        brand = car.brand,
                        model = car.model,
                        price = car.price,
                        buyerName = holder,
                        buyerEmail = "juan@example.com",
                        cardLast4 = cardNumber.takeLast(4)
                    )
                    viewModel.savePurchase(purchase) { saved ->
                        if (saved && car.documentId != null) {
                            viewModel.deleteCar(car.documentId!!) { deleted ->
                                processing = false
                                message = if (deleted) "¡Compra exitosa! El auto ya no está en venta" else "Error al eliminar"
                                if (deleted) {
                                    scope.launch {
                                        delay(1500)
                                        onSuccess()
                                    }
                                }
                            }
                        }
                    }
                }
            },
            enabled = !processing,
            colors = ButtonDefaults.buttonColors(Color(0xFF16A34A)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Pagar $${car.price}") }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
        message?.let { Text(it, color = if (it.contains("exitos")) Color.Green else Color.Red, modifier = Modifier.padding(top = 16.dp)) }
    }
}
