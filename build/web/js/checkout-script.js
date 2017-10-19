$(document).ready(function() {
	$("#product-id").keyup(function(){
		var data = {input:$(this).val(), getProductImage: 1};
		console.log($(this).val());
		$.post("orderconfirmation", data, function(msg){
			var productDiv = document.getElementById("product");
			if (msg.trim() !== "/"){
				productDiv.innerHTML = ('<img id="product-image" src="' + msg + '" /><h3 id="product-name">');
				productDiv.style.display = "block";
			} else{
				productDiv.style.display = "none";
			}
		});
	});
});

function getProduct(productID)
{

	var productArray = {
	1: ["GeForce GTX 1080 FTW GAMING", 559.99],
	2: ["GeForce GTX 1070 FTW GAMING", 439.99],
	3: ["Intel Core i5-4690K Processor", 271.12],
	4: ["Intel Core i7-4790K Processor", 397.19],
	5: ["Sabre RGB", 49.99],
	6: ["G502 Proteus Spectrum", 59.99],
	7: ["Razer Blackwidow Chroma V2", 169.99],
	8: ["Corsair Strafe", 89.99],
	9: ["H440", 114.99],
	10:["View 27", 69.99]
	};

	for(var pid in productArray)
	{
		if(pid == productID && productID == 1)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/gtx1080.jpg" /><h3 id="product-name">' + productArray[1][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[1][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 559.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 2)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/gtx1070.jpg" /><h3 id="product-name">' + productArray[2][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[2][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 439.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 3)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/4690k.jpg" /><h3 id="product-name">' + productArray[3][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[3][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 271.12)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 4)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/4790k.jpg" /><h3 id="product-name">' + productArray[4][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[4][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 397.19)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 5)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/sabre.png" /><h3 id="product-name">' + productArray[5][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[5][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 49.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 6)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/g502.jpg" /><h3 id="product-name">' + productArray[6][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[6][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 59.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 7)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/blackwidow.png" /><h3 id="product-name">' + productArray[7][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[7][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 169.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 8)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/strafe.png" /><h3 id="product-name">' + productArray[8][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[8][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 89.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 9)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/h440.png" /><h3 id="product-name">' + productArray[9][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[9][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 114.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
		if(pid == productID && productID == 10)
		{
			var productDiv = document.getElementById("product");
			productDiv.innerHTML = '<img id="product-image" src="img/view27.jpg" /><h3 id="product-name">' + productArray[10][0] + '</h3><p id="product-price"><b>Price: </b>$' + productArray[10][1] + '</p><p class="left-element">Quantity: <input type="number" id="quantity" name="quantity" maxlength="2" value="1"></p><button type="button" class="left-element" onclick="calculateTotal(document.getElementById(\'quantity\').value, 69.99)">Calculate Price</button>';
			document.getElementById("product-break").style.display = "block";
		}
	}
	document.getElementById("product").style.display = "block";
}																																																				

function calculateTotal(quantity, price)
{
	var shippingOption = "";
	var zipCode = document.getElementById("shipping-postal-code").value;
	var radioButtons = document.getElementsByName("shipping-option");
	var tax;
	var total;
	for(var i = 0; i < radioButtons.length; i++)
	{
		if(radioButtons[i].checked)
		{
			shippingOption = radioButtons[i].value;
			break;
		}
	}
	if(quantity < 1)
	{
		window.alert("Please specify a quantity greater than or equal to 1.");
	}
	if(shippingOption == "")
	{
		window.alert("Please specify a shipping rate.");
		return;
	}
	if (zipCode == ""){
		window.alert("Please enter in the shipping zip code.");
	}
	//var tax = (quantity * price) * 0.15;
	//var total = (quantity * price) + tax + parseInt(shippingOption);
	if (zipCode != "" && shippingOption != "" && quantity >= 1){
		var data = {shippingZip: zipCode, calculatePrice: 1};
		$.post("order-confirmation", data, function(taxRate){
			var total = (quantity * price);
			var tax = 0.08;
			var grandTotal = (total + (total*tax) + parseFloat(shippingOption));
			document.getElementById("order-total").innerHTML = "Order Total: $".concat((total).toFixed(2));
			document.getElementById("shipping-rate").innerHTML = "Shipping: $".concat(parseInt(shippingOption));		
			document.getElementById("tax").innerHTML = "Tax: ".concat((total * tax).toFixed(2));
			document.getElementById("grand-total").innerHTML = "Grand Total: $".concat((grandTotal).toFixed(2));
		});
	}
	else{
		document.getElementById("tax").innerHTML = "Tax: N/A";
		document.getElementById("grand-total").innerHTML = "Grand Total: N/A";
	}
	document.getElementById("calculate-order").style.display = "block";
}

function validateForm()
{
	var firstName = document.getElementById("first-name");
	var lastName = document.getElementById("last-name");
	var email = document.getElementById("email");
	var telephone = document.getElementById("telephone");
	var address = document.getElementById("address");
	var city = document.getElementById("city");
	var state = document.getElementById("state");
	var zip = document.getElementById("postal-code")
	var country = document.getElementById("country");
	var creditCardNumber = document.getElementById("card-number");
	var expirationMonth = document.getElementById("expiration-month");
	var expirationYear = document.getElementById("expiration-year");
	var ccv = document.getElementById("CCV");
	var shippingMethod = document.getElementById("shipping-method");
	var shippingAddress = document.getElementById("shipping-address");
	var shippingCity = document.getElementById("shipping-city");
	var shippingState = document.getElementById("shipping-state");
	var shippingCountry = document.getElementById("shipping-country");
	var shippingPostalCode = document.getElementById("shipping-postal-code");
	var productID = document.getElementById("product-id");
        
	if(productID.value != "")
	{
		var productQuantity = document.getElementById("quantity");
		if(productQuantity.value == "")
		{
			productQuantity.classList.add("error");
		}
		else
		{
			productQuantity.classList.remove("error");
		}
	}

	var isValid = true;
        
	if(firstName.value == "")
	{
		firstName.classList.add("error");
		isValid = false;
	}
	else
	{
		firstName.classList.remove("error");
	}
	if(lastName.value == "")
	{
		lastName.classList.add("error");
		isValid = false;
	}
	else
	{
		lastName.classList.remove("error");
	}
	if(email.value == "" || email.validity.valid == false)
	{
		email.classList.add("error");
		isValid = false;
	}
	else
	{
		email.classList.remove("error");
	}
	if(telephone.value != "")
	{
		telephone.classList.remove("error");
	}
	else
	{
		telephone.classList.add("error");
		isValid = false;
	}
	if(address.value == "")
	{
		address.classList.add("error");
		isValid = false;
	}
	else
	{
		address.classList.remove("error");
	}
	if(city.value == "")
	{
		city.classList.add("error");
		isValid = false;
	}
	else
	{
		city.classList.remove("error");
	}
	if(state.value == "")
	{
		state.classList.add("error");
		isValid = false;
	}
	else
	{
		state.classList.remove("error");
	}
	if(zip.value == "" || zip.value.length != 5)
	{
		zip.classList.add("error");
		isValid = false;
	}
	else
	{
		zip.classList.remove("error");
	}
	if(creditCardNumber.value == "" || creditCardNumber.value.length != 19)
	{
		creditCardNumber.classList.add("error");
		isValid = false;
	}
	else
	{
		creditCardNumber.classList.remove("error");
	}
	if(expirationMonth.value == "")
	{
		expirationMonth.classList.add("error");
		isValid = false;
	}
	else
	{
		expirationMonth.classList.remove("error");
	}
	if(expirationYear.value == "")
	{
		expirationYear.classList.add("error");
		isValid = false;
	}
        
	else
	{
		expirationYear.classList.remove("error");
	}

	if(ccv.value == "" || ccv.value.length != 3)
	{
		ccv.classList.add("error");
		isValid = false;
	}
	else
	{
		ccv.classList.remove("error");
	}
	if(shippingAddress.value == "")
	{
		shippingAddress.classList.add("error");
		isValid = false;
	}
	else
	{
		shippingAddress.classList.remove("error");
	}
	if(shippingCity.value == "")
	{
		shippingCity.classList.add("error");
		isValid = false;
	}
	else
	{
		shippingCity.classList.remove("error");
	}
	if(shippingState.value == "")
	{
		shippingState.classList.add("error");
		isValid = false;
	}
	else
	{
		shippingState.classList.remove("error");
	}
	if(shippingCountry.value == "")
	{
		shippingCountry.classList.add("error");
		isValid = false;
	}
	else
	{
		shippingCountry.classList.remove("error");
	}
	if(isValid == false)
	{
		document.getElementById("error-message").style.display = "block";
		$('html, body').animate({ scrollTop: 0 }, 'fast');
	}
	if(shippingPostalCode.value == "")
	{
		shippingPostalCode.classList.add("error");
		isValid = false;
	}
	else
	{
		shippingPostalCode.classList.remove("error");
	}
	if(productID.value == "")
	{
		productID.classList.add("error");
	}
	else
	{
		productID.classList.remove("error");
	}

	if(isValid == true)
	{

	}
	return isValid;
}



