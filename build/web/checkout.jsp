<%-- 
    Document   : checkout
    Created on : May 30, 2017, 2:01:37 AM
    Author     : Thanh
--%>

<%@page import="product.*"%>
<%@page import="javax.ws.rs.core.UriBuilder"%>
<%@page import="javax.ws.rs.client.ClientBuilder"%>
<%@page import="javax.ws.rs.client.Client"%>
<%@page import="org.glassfish.jersey.client.ClientConfig"%>
<%@page import="javax.ws.rs.client.WebTarget"%>
<%@page import="javax.ws.rs.core.MediaType"%>
<%@page import="org.codehaus.jackson.type.TypeReference"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>

<!-- css for checkout page -->
<link rel="stylesheet" href="css/checkout-style.css" />
<!-- checkout-script.js -->
<script src="js/checkout-script.js"></script>
<script>
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

    var isValid = true;

    if(firstName.value === "")
    {
            firstName.classList.add("error");
            isValid = false;
    }
    else
    {
            firstName.classList.remove("error");
    }
    if(lastName.value === "")
    {
            lastName.classList.add("error");
            isValid = false;
    }
    else
    {
            lastName.classList.remove("error");
    }
    if(email.value === "" || email.validity.valid === false)
    {
            email.classList.add("error");
            isValid = false;
    }
    else
    {
            email.classList.remove("error");
    }
    if(telephone.value !== "")
    {
            telephone.classList.remove("error");
    }
    else
    {
            telephone.classList.add("error");
            isValid = false;
    }
    if(address.value === "")
    {
            address.classList.add("error");
            isValid = false;
    }
    else
    {
            address.classList.remove("error");
    }
    if(city.value === "")
    {
            city.classList.add("error");
            isValid = false;
    }
    else
    {
            city.classList.remove("error");
    }
    if(state.value === "")
    {
            state.classList.add("error");
            isValid = false;
    }
    else
    {
            state.classList.remove("error");
    }
    if(zip.value === "" || zip.value.length !== 5)
    {
            zip.classList.add("error");
            isValid = false;
    }
    else
    {
            zip.classList.remove("error");
    }
    if(creditCardNumber.value === "" || creditCardNumber.value.length !== 19)
    {
            creditCardNumber.classList.add("error");
            isValid = false;
    }
    else
    {
            creditCardNumber.classList.remove("error");
    }
    if(expirationMonth.value === "")
    {
            expirationMonth.classList.add("error");
            isValid = false;
    }
    else
    {
            expirationMonth.classList.remove("error");
    }
    if(expirationYear.value === "")
    {
            expirationYear.classList.add("error");
            isValid = false;
    }
    else
    {
            expirationYear.classList.remove("error");
    }
    if(ccv.value === "" || ccv.value.length !== 3)
    {
            ccv.classList.add("error");
            isValid = false;
    }
    else
    {
            ccv.classList.remove("error");
    }
    if(shippingAddress.value === "")
    {
            shippingAddress.classList.add("error");
            isValid = false;
    }
    else
    {
            shippingAddress.classList.remove("error");
    }
    if(shippingCity.value === "")
    {
            shippingCity.classList.add("error");
            isValid = false;
    }
    else
    {
            shippingCity.classList.remove("error");
    }
    if(shippingState.value === "")
    {
            shippingState.classList.add("error");
            isValid = false;
    }
    else
    {
            shippingState.classList.remove("error");
    }
    if(shippingCountry.value === "")
    {
            shippingCountry.classList.add("error");
            isValid = false;
    }
    else
    {
            shippingCountry.classList.remove("error");
    }
    if(isValid === false)
    {
            document.getElementById("error-message").style.display = "block";
            $('html, body').animate({ scrollTop: 0 }, 'fast');
    }
    if(shippingPostalCode.value === "")
    {
            shippingPostalCode.classList.add("error");
            isValid = false;
    }
    else
    {
            shippingPostalCode.classList.remove("error");
    }

    return isValid;
} 
</script>

		<br /><br />
		<a id="back-button" href="./products">
			<img id="back-arrow" src="img/back-arrow.png" />
		</a>

		<br>

		<img id="shopping-cart" class="float-left" src="img/shopping-cart.png" />
		<h1 id="checkout">CHECKOUT</h1>

		<hr align="left">

		<br />

		<span id="error-message">PLEASE FILL IN / CORRECT THE REQUIRED FIELDS.</span>

		<form id="order-checkout" name="order-checkout" onSubmit="return validateForm();" action="orderConfirmation" method="POST">

			<div class="section" id="product-order">
				<div class="section-header">
					<h2 class="section-title">YOUR ORDER</h2>
                                </div><div>
                                        <%
                                            ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
                                            ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity"); 
                                            WebTarget target = (WebTarget) request.getAttribute("target");
                                            for (int i=0; i<cart.size(); i++){
                                                int pid = cart.get(i);
                                                out.println("<div class='cartItem'>");
                                                out.println("<div class='cartProductText'>");
                                                
                                                String jsonResponse =
                                                    target.path("api").path("products").path(Integer.toString(pid)).
                                                    request(). //send a request
                                                    accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                                                    get(String.class); // use the get method and return the response as a string

//                                                System.out.println(jsonResponse);

                                                ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

                                                Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});
                                                
                                                out.println("<p>Item: <span style='font-weight:bold;background-color:transparent;color:black'>" + product.getName() + "</span></p>");
                                                out.println("<p>Quantity: <span style='font-weight:bold;background-color:transparent;color:black'>" + cartQuantity.get(i) + "</span></p>");
                                                out.println("</div>");
                                                out.println("<div class='cartProductImage'>");
                                                response.setContentType("image/jpg"); 
                                                if(pid == 1)
                                                {
                                                    out.println("<img class='cartImage' src='img/gtx1080.jpg'/>");
                                                }
                                                else if(pid == 2)
                                                {
                                                    out.println("<img class='cartImage' src='img/gtx1070.jpg'/>");
                                                }
                                                else if(pid == 3)
                                                {
                                                    out.println("<img class='cartImage' src='img/4690k.jpg'/>");
                                                }
                                                else if(pid == 4)
                                                {
                                                    out.println("<img class='cartImage' src='img/4790k.jpg'/>");
                                                }
                                                else if(pid == 5)
                                                {
                                                    out.println("<img class='cartImage' src='img/sabre.png'/>");
                                                }
                                                else if(pid == 6)
                                                {
                                                    out.println("<img class='cartImage' src='img/g502.jpg'/>");
                                                }
                                                else if(pid == 7)
                                                {
                                                    out.println("<img class='cartImage' src='img/blackwidow.png'/>");
                                                }
                                                else if(pid == 8)
                                                {
                                                    out.println("<img class='cartImage' src='img/strafe.png'/>");
                                                }
                                                else if(pid == 9)
                                                {
                                                    out.println("<img class='cartImage' src='img/h440.png'/>");
                                                }
                                                else
                                                {
                                                    out.println("<img class='cartImage' src='img/view27.jpg'/>");
                                                }                          
                                                out.println("</div>");
                                                response.setContentType("text/html;charset=UTF-8");
                                                out.println("</div>");
                                            }
                                                out.println("</div>");                            
                                        %>
                                        <%;
                                            //BigDecimal totalPrice = new BigDecimal((double) session.getAttribute("totalPrice"));
                                            String tax = String.format("%.2f", session.getAttribute("tax"));
                                            String shipping = String.format("%.2f", session.getAttribute("shipping"));
                                            String grandTotal = String.format("%.2f", session.getAttribute("grandTotal"));
                                            String totalPrice = String.format("%.2f", session.getAttribute("totalPrice"));

                                            out.println("<div class='left-element' id='calculate-order' style='display:block;margin:0 auto;width:500px;padding-left:250px;'>");
                                            out.println("<p id='order-total'>Order Total: $" + totalPrice + "</p>");
                                            out.println("<p id='tax'><b>Tax: $" + tax +  "</b></p>");
                                            out.println("<p id='shipping-rate'>Shipping: $" + shipping + "</p>");
                                            out.println("<b><p id='grand-total'>Grand Total: $" + grandTotal + "</p></b><br>");

                                            out.println("</div>");
                                            out.println("</div>");
                                            
                                        %>
                                        
				</div>
				
<!--				<div class="left-element" id="product-input">
					<p>Product ID: <input type="text" id="product-id" name="product-id" maxlength="2"></p>
					<button type="button" onclick="getProduct(document.getElementById('product-id').value)">Search</button>
				</div>-->
			</div>

			<br>

			<div class="section" id="personal-info">
				<div class="section-header">
					<h2 class="section-title">PERSONAL INFORMATION</h2>
				</div>
				<div class="display left-element">
					<p>First Name</p> 
					<input type="text" id="first-name" name="first-name" />
				</div>
				<div class="display">
					<p>Last Name</p> 
					<input type="text" id="last-name" name="last-name" />
				</div>
				<br />
				<div class="display left-element">
					<p>Email Address</p> 
					<input type="email" id="email" name="email" placeholder="peter@uci.edu" />
				</div>
				<div class="display">
					<p>Phone Number</p>
					<input type="tel" pattern="^\d{3}-\d{3}-\d{4}$" id="telephone" name="telephone" placeholder="XXX-XXX-XXXX" />
				</div>
				<br />
				<div id="user-address" class="left-element">
					<p>Address</p>
					<input type="text" id="address" name="address" />
				</div>
				<br />
				<div class="display left-element">
					<p>City</p>
					<input type="text" id="city" name="city" />
				</div>
				<div class="display">
					<p>State</p>
					<select id="state" name="state">
						<option disabled selected value></option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>
				</div>
				<br />
				<div style="padding-bottom: 5%;" class="display left-element">
					<p>Zip/Postal Code</p>
					<input type="number" id="postal-code" name="postal-code" maxlength="5">
				</div>
				<div class="display country">
					<p>Country</p>
					<select id="country" name="country">
						<option value="AF">Afghanistan</option>
						<option value="AX">Åland Islands</option>
						<option value="AL">Albania</option>
						<option value="DZ">Algeria</option>
						<option value="AS">American Samoa</option>
						<option value="AD">Andorra</option>
						<option value="AO">Angola</option>
						<option value="AI">Anguilla</option>
						<option value="AQ">Antarctica</option>
						<option value="AG">Antigua and Barbuda</option>
						<option value="AR">Argentina</option>
						<option value="AM">Armenia</option>
						<option value="AW">Aruba</option>
						<option value="AU">Australia</option>
						<option value="AT">Austria</option>
						<option value="AZ">Azerbaijan</option>
						<option value="BS">Bahamas</option>
						<option value="BH">Bahrain</option>
						<option value="BD">Bangladesh</option>
						<option value="BB">Barbados</option>
						<option value="BY">Belarus</option>
						<option value="BE">Belgium</option>
						<option value="BZ">Belize</option>
						<option value="BJ">Benin</option>
						<option value="BM">Bermuda</option>
						<option value="BT">Bhutan</option>
						<option value="BO">Bolivia, Plurinational State of</option>
						<option value="BQ">Bonaire, Sint Eustatius and Saba</option>
						<option value="BA">Bosnia and Herzegovina</option>
						<option value="BW">Botswana</option>
						<option value="BV">Bouvet Island</option>
						<option value="BR">Brazil</option>
						<option value="IO">British Indian Ocean Territory</option>
						<option value="BN">Brunei Darussalam</option>
						<option value="BG">Bulgaria</option>
						<option value="BF">Burkina Faso</option>
						<option value="BI">Burundi</option>
						<option value="KH">Cambodia</option>
						<option value="CM">Cameroon</option>
						<option value="CA">Canada</option>
						<option value="CV">Cape Verde</option>
						<option value="KY">Cayman Islands</option>
						<option value="CF">Central African Republic</option>
						<option value="TD">Chad</option>
						<option value="CL">Chile</option>
						<option value="CN">China</option>
						<option value="CX">Christmas Island</option>
						<option value="CC">Cocos (Keeling) Islands</option>
						<option value="CO">Colombia</option>
						<option value="KM">Comoros</option>
						<option value="CG">Congo</option>
						<option value="CD">Congo, the Democratic Republic of the</option>
						<option value="CK">Cook Islands</option>
						<option value="CR">Costa Rica</option>
						<option value="CI">Côte d'Ivoire</option>
						<option value="HR">Croatia</option>
						<option value="CU">Cuba</option>
						<option value="CW">Curaçao</option>
						<option value="CY">Cyprus</option>
						<option value="CZ">Czech Republic</option>
						<option value="DK">Denmark</option>
						<option value="DJ">Djibouti</option>
						<option value="DM">Dominica</option>
						<option value="DO">Dominican Republic</option>
						<option value="EC">Ecuador</option>
						<option value="EG">Egypt</option>
						<option value="SV">El Salvador</option>
						<option value="GQ">Equatorial Guinea</option>
						<option value="ER">Eritrea</option>
						<option value="EE">Estonia</option>
						<option value="ET">Ethiopia</option>
						<option value="FK">Falkland Islands (Malvinas)</option>
						<option value="FO">Faroe Islands</option>
						<option value="FJ">Fiji</option>
						<option value="FI">Finland</option>
						<option value="FR">France</option>
						<option value="GF">French Guiana</option>
						<option value="PF">French Polynesia</option>
						<option value="TF">French Southern Territories</option>
						<option value="GA">Gabon</option>
						<option value="GM">Gambia</option>
						<option value="GE">Georgia</option>
						<option value="DE">Germany</option>
						<option value="GH">Ghana</option>
						<option value="GI">Gibraltar</option>
						<option value="GR">Greece</option>
						<option value="GL">Greenland</option>
						<option value="GD">Grenada</option>
						<option value="GP">Guadeloupe</option>
						<option value="GU">Guam</option>
						<option value="GT">Guatemala</option>
						<option value="GG">Guernsey</option>
						<option value="GN">Guinea</option>
						<option value="GW">Guinea-Bissau</option>
						<option value="GY">Guyana</option>
						<option value="HT">Haiti</option>
						<option value="HM">Heard Island and McDonald Islands</option>
						<option value="VA">Holy See (Vatican City State)</option>
						<option value="HN">Honduras</option>
						<option value="HK">Hong Kong</option>
						<option value="HU">Hungary</option>
						<option value="IS">Iceland</option>
						<option value="IN">India</option>
						<option value="ID">Indonesia</option>
						<option value="IR">Iran, Islamic Republic of</option>
						<option value="IQ">Iraq</option>
						<option value="IE">Ireland</option>
						<option value="IM">Isle of Man</option>
						<option value="IL">Israel</option>
                                                <option value="IT">Italy</option>
                                                <option value="JM">Jamaica</option>
                                                <option value="JP">Japan</option>
                                                <option value="JE">Jersey</option>
                                                <option value="JO">Jordan</option>
                                                <option value="KZ">Kazakhstan</option>
                                                <option value="KE">Kenya</option>
						<option value="KI">Kiribati</option>
						<option value="KP">Korea, Democratic People's Republic of</option>
						<option value="KR">Korea, Republic of</option>
						<option value="KW">Kuwait</option>
						<option value="KG">Kyrgyzstan</option>
						<option value="LA">Lao People's Democratic Republic</option>
						<option value="LV">Latvia</option>
						<option value="LB">Lebanon</option>
						<option value="LS">Lesotho</option>
						<option value="LR">Liberia</option>
						<option value="LY">Libya</option>
						<option value="LI">Liechtenstein</option>
						<option value="LT">Lithuania</option>
						<option value="LU">Luxembourg</option>
						<option value="MO">Macao</option>
						<option value="MK">Macedonia, the former Yugoslav Republic of</option>
						<option value="MG">Madagascar</option>
						<option value="MW">Malawi</option>
						<option value="MY">Malaysia</option>
						<option value="MV">Maldives</option>
						<option value="ML">Mali</option>
						<option value="MT">Malta</option>
						<option value="MH">Marshall Islands</option>
						<option value="MQ">Martinique</option>
						<option value="MR">Mauritania</option>
						<option value="MU">Mauritius</option>
						<option value="YT">Mayotte</option>
						<option value="MX">Mexico</option>
						<option value="FM">Micronesia, Federated States of</option>
						<option value="MD">Moldova, Republic of</option>
						<option value="MC">Monaco</option>
						<option value="MN">Mongolia</option>
						<option value="ME">Montenegro</option>
						<option value="MS">Montserrat</option>
						<option value="MA">Morocco</option>
						<option value="MZ">Mozambique</option>
						<option value="MM">Myanmar</option>
						<option value="NA">Namibia</option>
						<option value="NR">Nauru</option>
						<option value="NP">Nepal</option>
						<option value="NL">Netherlands</option>
						<option value="NC">New Caledonia</option>
						<option value="NZ">New Zealand</option>
						<option value="NI">Nicaragua</option>
						<option value="NE">Niger</option>
						<option value="NG">Nigeria</option>
						<option value="NU">Niue</option>
						<option value="NF">Norfolk Island</option>
						<option value="MP">Northern Mariana Islands</option>
						<option value="NO">Norway</option>
						<option value="OM">Oman</option>
						<option value="PK">Pakistan</option>
						<option value="PW">Palau</option>
						<option value="PS">Palestinian Territory, Occupied</option>
						<option value="PA">Panama</option>
						<option value="PG">Papua New Guinea</option>
						<option value="PY">Paraguay</option>
						<option value="PE">Peru</option>
						<option value="PH">Philippines</option>
						<option value="PN">Pitcairn</option>
						<option value="PL">Poland</option>
						<option value="PT">Portugal</option>
						<option value="PR">Puerto Rico</option>
						<option value="QA">Qatar</option>
						<option value="RE">Réunion</option>
						<option value="RO">Romania</option>
						<option value="RU">Russian Federation</option>
						<option value="RW">Rwanda</option>
						<option value="BL">Saint Barthélemy</option>
						<option value="SH">Saint Helena, Ascension and Tristan da Cunha</option>
						<option value="KN">Saint Kitts and Nevis</option>
						<option value="LC">Saint Lucia</option>
						<option value="MF">Saint Martin (French part)</option>
						<option value="PM">Saint Pierre and Miquelon</option>
						<option value="VC">Saint Vincent and the Grenadines</option>
						<option value="WS">Samoa</option>
						<option value="SM">San Marino</option>
						<option value="ST">Sao Tome and Principe</option>
						<option value="SA">Saudi Arabia</option>
						<option value="SN">Senegal</option>
						<option value="RS">Serbia</option>
						<option value="SC">Seychelles</option>
						<option value="SL">Sierra Leone</option>
						<option value="SG">Singapore</option>
						<option value="SX">Sint Maarten (Dutch part)</option>
						<option value="SK">Slovakia</option>
						<option value="SI">Slovenia</option>
						<option value="SB">Solomon Islands</option>
						<option value="SO">Somalia</option>
						<option value="ZA">South Africa</option>
						<option value="GS">South Georgia and the South Sandwich Islands</option>
						<option value="SS">South Sudan</option>
						<option value="ES">Spain</option>
						<option value="LK">Sri Lanka</option>
						<option value="SD">Sudan</option>
						<option value="SR">Suriname</option>
						<option value="SJ">Svalbard and Jan Mayen</option>
						<option value="SZ">Swaziland</option>
						<option value="SE">Sweden</option>
						<option value="CH">Switzerland</option>
						<option value="SY">Syrian Arab Republic</option>
						<option value="TW">Taiwan, Province of China</option>
						<option value="TJ">Tajikistan</option>
						<option value="TZ">Tanzania, United Republic of</option>
						<option value="TH">Thailand</option>
						<option value="TL">Timor-Leste</option>
						<option value="TG">Togo</option>
						<option value="TK">Tokelau</option>
						<option value="TO">Tonga</option>
						<option value="TT">Trinidad and Tobago</option>
						<option value="TN">Tunisia</option>
						<option value="TR">Turkey</option>
						<option value="TM">Turkmenistan</option>
						<option value="TC">Turks and Caicos Islands</option>
						<option value="TV">Tuvalu</option>
						<option value="UG">Uganda</option>
						<option value="UA">Ukraine</option>
						<option value="AE">United Arab Emirates</option>
						<option value="GB">United Kingdom</option>
						<option selected value="US">United States</option>
						<option value="UM">United States Minor Outlying Islands</option>
						<option value="UY">Uruguay</option>
						<option value="UZ">Uzbekistan</option>
						<option value="VU">Vanuatu</option>
						<option value="VE">Venezuela, Bolivarian Republic of</option>
						<option value="VN">Viet Nam</option>
						<option value="VG">Virgin Islands, British</option>
						<option value="VI">Virgin Islands, U.S.</option>
						<option value="WF">Wallis and Futuna</option>
						<option value="EH">Western Sahara</option>
						<option value="YE">Yemen</option>
						<option value="ZM">Zambia</option>
						<option value="ZW">Zimbabwe</option>
					</select>
				<br>
			</div>
		</div>

			<br />

			<div class="section" id="card-info">
				<div class="section-header">
					<h2 class="section-title">PAYMENT DETAILS</h2>
				</div>
				<div class="display left-element">
					<p>Card Number</p>
					<input type="text" id="card-number" name="card-number" placeholder="XXXX-XXXX-XXXX-XXXX" />
				</div>
				<br />
				<div id="card-expiration" class="display left-element">
					<p>Expiration</p>
					<select id="expiration-month" name="expiration-month" class="card-menu">
						<option value="1" selected>01</option>
						<option value="2">02</option>
						<option value="3">03</option>
						<option value="4">04</option>
						<option value="5">05</option>
						<option value="6">06</option>
						<option value="7">07</option>
						<option value="8">08</option>
						<option value="9">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>
					<select id="expiration-year" name="expiration-year" class="card-menu">
						<option value="2017" selected>2017</option>
						<option value="2018">2018</option> 
						<option value="2019">2019</option> 
						<option value="2020">2020</option>
					</select>
				</div>
				<div style="margin-left: 4%;padding-bottom: 5%;" class="display">
					<p>CCV</p> 
					<input type="number" id="CCV" name="CCV" maxlength="3">
				</div>
			</div>

			<br>

			<div class="section" id="shipping-method">
				<div class="section-header">
					<h2 class="section-title">SHIPPING DETAILS</h2>
				</div>
				<div id="shipping-options" class="left-element">
					<p>Shipping Method</p>
					<input type="radio" name="shipping-option" value="10">Overnight ($10)<br>
					<input type="radio" name="shipping-option" value="5">2-days Expedited ($5)<br>
					<input type="radio" name="shipping-option" value="0">6-days Ground (FREE)
				</div>
				<br />
				<u><h2 class="left-element">Shipping Address</h2></u>
				<div id="user-address" class="left-element">
					<p>Address</p>
					<input type="text" id="shipping-address" name="shipping-address" />
				</div>
				<br />
				<div class="display left-element">
					<p>City</p>
					<input type="text" id="shipping-city" name="shipping-city" />
				</div>
				<div class="display">
					<p>State</p>
					<select id="shipping-state" name="shipping-state">
						<option disabled selected value></option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>
				</div>
				<br />
				<div style="padding-bottom: 5%;" class="display left-element">
					<p>Zip/Postal Code</p>
					<input type="number" id="shipping-postal-code" name="shipping-postal-code" maxlength="5">
				</div>
				<div class="country display">
					<p>Country</p>
					<select id="shipping-country" name="shipping-country">
						<option value="AF">Afghanistan</option>
						<option value="AX">Åland Islands</option>
						<option value="AL">Albania</option>
						<option value="DZ">Algeria</option>
						<option value="AS">American Samoa</option>
						<option value="AD">Andorra</option>
						<option value="AO">Angola</option>
						<option value="AI">Anguilla</option>
						<option value="AQ">Antarctica</option>
						<option value="AG">Antigua and Barbuda</option>
						<option value="AR">Argentina</option>
						<option value="AM">Armenia</option>
						<option value="AW">Aruba</option>
						<option value="AU">Australia</option>
						<option value="AT">Austria</option>
						<option value="AZ">Azerbaijan</option>
						<option value="BS">Bahamas</option>
						<option value="BH">Bahrain</option>
						<option value="BD">Bangladesh</option>
						<option value="BB">Barbados</option>
						<option value="BY">Belarus</option>
						<option value="BE">Belgium</option>
						<option value="BZ">Belize</option>
						<option value="BJ">Benin</option>
						<option value="BM">Bermuda</option>
						<option value="BT">Bhutan</option>
						<option value="BO">Bolivia, Plurinational State of</option>
						<option value="BQ">Bonaire, Sint Eustatius and Saba</option>
						<option value="BA">Bosnia and Herzegovina</option>
						<option value="BW">Botswana</option>
						<option value="BV">Bouvet Island</option>
						<option value="BR">Brazil</option>
						<option value="IO">British Indian Ocean Territory</option>
						<option value="BN">Brunei Darussalam</option>
						<option value="BG">Bulgaria</option>
						<option value="BF">Burkina Faso</option>
						<option value="BI">Burundi</option>
						<option value="KH">Cambodia</option>
						<option value="CM">Cameroon</option>
						<option value="CA">Canada</option>
						<option value="CV">Cape Verde</option>
						<option value="KY">Cayman Islands</option>
						<option value="CF">Central African Republic</option>
						<option value="TD">Chad</option>
						<option value="CL">Chile</option>
						<option value="CN">China</option>
						<option value="CX">Christmas Island</option>
						<option value="CC">Cocos (Keeling) Islands</option>
						<option value="CO">Colombia</option>
						<option value="KM">Comoros</option>
						<option value="CG">Congo</option>
						<option value="CD">Congo, the Democratic Republic of the</option>
						<option value="CK">Cook Islands</option>
						<option value="CR">Costa Rica</option>
						<option value="CI">Côte d'Ivoire</option>
						<option value="HR">Croatia</option>
						<option value="CU">Cuba</option>
						<option value="CW">Curaçao</option>
						<option value="CY">Cyprus</option>
						<option value="CZ">Czech Republic</option>
						<option value="DK">Denmark</option>
						<option value="DJ">Djibouti</option>
						<option value="DM">Dominica</option>
						<option value="DO">Dominican Republic</option>
						<option value="EC">Ecuador</option>
						<option value="EG">Egypt</option>
						<option value="SV">El Salvador</option>
						<option value="GQ">Equatorial Guinea</option>
						<option value="ER">Eritrea</option>
						<option value="EE">Estonia</option>
						<option value="ET">Ethiopia</option>
						<option value="FK">Falkland Islands (Malvinas)</option>
						<option value="FO">Faroe Islands</option>
						<option value="FJ">Fiji</option>
						<option value="FI">Finland</option>
						<option value="FR">France</option>
						<option value="GF">French Guiana</option>
						<option value="PF">French Polynesia</option>
						<option value="TF">French Southern Territories</option>
						<option value="GA">Gabon</option>
						<option value="GM">Gambia</option>
						<option value="GE">Georgia</option>
						<option value="DE">Germany</option>
						<option value="GH">Ghana</option>
						<option value="GI">Gibraltar</option>
						<option value="GR">Greece</option>
						<option value="GL">Greenland</option>
						<option value="GD">Grenada</option>
						<option value="GP">Guadeloupe</option>
						<option value="GU">Guam</option>
						<option value="GT">Guatemala</option>
						<option value="GG">Guernsey</option>
						<option value="GN">Guinea</option>
						<option value="GW">Guinea-Bissau</option>
						<option value="GY">Guyana</option>
						<option value="HT">Haiti</option>
						<option value="HM">Heard Island and McDonald Islands</option>
						<option value="VA">Holy See (Vatican City State)</option>
						<option value="HN">Honduras</option>
						<option value="HK">Hong Kong</option>
						<option value="HU">Hungary</option>
						<option value="IS">Iceland</option>
						<option value="IN">India</option>
						<option value="ID">Indonesia</option>
						<option value="IR">Iran, Islamic Republic of</option>
						<option value="IQ">Iraq</option>
						<option value="IE">Ireland</option>
						<option value="IM">Isle of Man</option>
						<option value="IL">Israel</option>
						<option value="IT">Italy</option>
						<option value="JM">Jamaica</option>
						<option value="JP">Japan</option>
						<option value="JE">Jersey</option>
						<option value="JO">Jordan</option>
						<option value="KZ">Kazakhstan</option>
						<option value="KE">Kenya</option>
						<option value="KI">Kiribati</option>
						<option value="KP">Korea, Democratic People's Republic of</option>
						<option value="KR">Korea, Republic of</option>
						<option value="KW">Kuwait</option>
						<option value="KG">Kyrgyzstan</option>
						<option value="LA">Lao People's Democratic Republic</option>
						<option value="LV">Latvia</option>
						<option value="LB">Lebanon</option>
						<option value="LS">Lesotho</option>
						<option value="LR">Liberia</option>
						<option value="LY">Libya</option>
						<option value="LI">Liechtenstein</option>
						<option value="LT">Lithuania</option>
						<option value="LU">Luxembourg</option>
						<option value="MO">Macao</option>
						<option value="MK">Macedonia, the former Yugoslav Republic of</option>
						<option value="MG">Madagascar</option>
						<option value="MW">Malawi</option>
						<option value="MY">Malaysia</option>
						<option value="MV">Maldives</option>
						<option value="ML">Mali</option>
						<option value="MT">Malta</option>
						<option value="MH">Marshall Islands</option>
						<option value="MQ">Martinique</option>
						<option value="MR">Mauritania</option>
						<option value="MU">Mauritius</option>
						<option value="YT">Mayotte</option>
						<option value="MX">Mexico</option>
						<option value="FM">Micronesia, Federated States of</option>
						<option value="MD">Moldova, Republic of</option>
						<option value="MC">Monaco</option>
						<option value="MN">Mongolia</option>
						<option value="ME">Montenegro</option>
						<option value="MS">Montserrat</option>
						<option value="MA">Morocco</option>
						<option value="MZ">Mozambique</option>
						<option value="MM">Myanmar</option>
						<option value="NA">Namibia</option>
						<option value="NR">Nauru</option>
						<option value="NP">Nepal</option>
						<option value="NL">Netherlands</option>
						<option value="NC">New Caledonia</option>
						<option value="NZ">New Zealand</option>
						<option value="NI">Nicaragua</option>
						<option value="NE">Niger</option>
						<option value="NG">Nigeria</option>
						<option value="NU">Niue</option>
						<option value="NF">Norfolk Island</option>
						<option value="MP">Northern Mariana Islands</option>
						<option value="NO">Norway</option>
						<option value="OM">Oman</option>
						<option value="PK">Pakistan</option>
						<option value="PW">Palau</option>
						<option value="PS">Palestinian Territory, Occupied</option>
						<option value="PA">Panama</option>
						<option value="PG">Papua New Guinea</option>
						<option value="PY">Paraguay</option>
						<option value="PE">Peru</option>
						<option value="PH">Philippines</option>
						<option value="PN">Pitcairn</option>
						<option value="PL">Poland</option>
						<option value="PT">Portugal</option>
						<option value="PR">Puerto Rico</option>
						<option value="QA">Qatar</option>
						<option value="RE">Réunion</option>
						<option value="RO">Romania</option>
						<option value="RU">Russian Federation</option>
						<option value="RW">Rwanda</option>
						<option value="BL">Saint Barthélemy</option>
						<option value="SH">Saint Helena, Ascension and Tristan da Cunha</option>
						<option value="KN">Saint Kitts and Nevis</option>
						<option value="LC">Saint Lucia</option>
						<option value="MF">Saint Martin (French part)</option>
						<option value="PM">Saint Pierre and Miquelon</option>
						<option value="VC">Saint Vincent and the Grenadines</option>
						<option value="WS">Samoa</option>
						<option value="SM">San Marino</option>
						<option value="ST">Sao Tome and Principe</option>
						<option value="SA">Saudi Arabia</option>
						<option value="SN">Senegal</option>
						<option value="RS">Serbia</option>
						<option value="SC">Seychelles</option>
						<option value="SL">Sierra Leone</option>
						<option value="SG">Singapore</option>
						<option value="SX">Sint Maarten (Dutch part)</option>
						<option value="SK">Slovakia</option>
						<option value="SI">Slovenia</option>
						<option value="SB">Solomon Islands</option>
						<option value="SO">Somalia</option>
						<option value="ZA">South Africa</option>
						<option value="GS">South Georgia and the South Sandwich Islands</option>
						<option value="SS">South Sudan</option>
						<option value="ES">Spain</option>
						<option value="LK">Sri Lanka</option>
						<option value="SD">Sudan</option>
						<option value="SR">Suriname</option>
						<option value="SJ">Svalbard and Jan Mayen</option>
						<option value="SZ">Swaziland</option>
						<option value="SE">Sweden</option>
						<option value="CH">Switzerland</option>
						<option value="SY">Syrian Arab Republic</option>
						<option value="TW">Taiwan, Province of China</option>
						<option value="TJ">Tajikistan</option>
						<option value="TZ">Tanzania, United Republic of</option>
						<option value="TH">Thailand</option>
						<option value="TL">Timor-Leste</option>
						<option value="TG">Togo</option>
						<option value="TK">Tokelau</option>
						<option value="TO">Tonga</option>
						<option value="TT">Trinidad and Tobago</option>
						<option value="TN">Tunisia</option>
						<option value="TR">Turkey</option>
						<option value="TM">Turkmenistan</option>
						<option value="TC">Turks and Caicos Islands</option>
						<option value="TV">Tuvalu</option>
						<option value="UG">Uganda</option>
						<option value="UA">Ukraine</option>
						<option value="AE">United Arab Emirates</option>
						<option value="GB">United Kingdom</option>
						<option selected value="US">United States</option>
						<option value="UM">United States Minor Outlying Islands</option>
						<option value="UY">Uruguay</option>
						<option value="UZ">Uzbekistan</option>
						<option value="VU">Vanuatu</option>
						<option value="VE">Venezuela, Bolivarian Republic of</option>
						<option value="VN">Viet Nam</option>
						<option value="VG">Virgin Islands, British</option>
						<option value="VI">Virgin Islands, U.S.</option>
						<option value="WF">Wallis and Futuna</option>
						<option value="EH">Western Sahara</option>
						<option value="YE">Yemen</option>
						<option value="ZM">Zambia</option>
						<option value="ZW">Zimbabwe</option>
					</select>
				<br>
			</div>
		</div>

			<br><br>

			<div id="confirmation">
                                <a id="cancel" class="float-left" href="/products">
					<button type="button" id="cancel-button">CANCEL</button>
				</a>
				<input id="purchase-button" class="float-right" type="submit" value="PURCHASE" />
                                <%
                                out.println("<input type='hidden' name='quantity' value='" + request.getAttribute("quantity") + "'>" + "</input>");
                                out.println("<input type='hidden' name='grandTotal' value='" + request.getAttribute("grandTotal") + "'>" + "</input>");
                                %>
				<input type="hidden" name="placeOrder" value="true">
				
			</div>

		</form>
		<br /><br />
                <p id="footer">&copy; EZPC Parts 2017</p>
	</body>
</html>
