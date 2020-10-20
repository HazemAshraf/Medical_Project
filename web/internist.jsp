<%@page import="com.aman.medical.db.getcon"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html  lang="en-GB">
    <head>

        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


        <link rel="stylesheet" href="temp1/fonts/icomoon/style.css">

        <link rel="stylesheet" href="temp1/css/bootstrap.min.css">
        <link rel="stylesheet" href="temp1/css/magnific-popup.css">
        <link rel="stylesheet" href="temp1/css/jquery-ui.css">
        <link rel="stylesheet" href="temp1/css/owl.carousel.min.css">
        <link rel="stylesheet" href="temp1/css/owl.theme.default.min.css">

        <link rel="stylesheet" href="temp1/css/bootstrap-datepicker.css">

        <link rel="stylesheet" href="temp1/fonts/flaticon/font/flaticon.css">

        <link rel="stylesheet" href="temp1/css/aos.css">

        <link rel="stylesheet" href="temp1/css/style.css">

    </head>
    <body>
        <input type="hidden" id="traffic_unit_code" value="<%= request.getSession().getAttribute("TRAFFIC_UNIT_CODE").toString()%>" />
        <div class="site-wrap">

            <div class="site-mobile-menu">
                <div class="site-mobile-menu-header">
                    <div class="site-mobile-menu-close mt-3">
                        <span class="icon-close2 js-menu-toggle"></span>
                    </div>
                </div>
                <div class="site-mobile-menu-body"></div>
            </div>

            <div style="background-color: #ff5c33;border-bottom-style: solid;border-bottom-color: coral" class="py-2">
                <div class="container">
                    <div class="row">

                        <div class="col-md-6">
                            <span> <a style="color: white" href="/API/logout"> <strong>تسجيـل خـروج</strong> </a> </span>
                            <!--            <ul class="social-media">
                                          <li><a href="#" class="p-2"><span class="icon-facebook"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-twitter"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-instagram"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-linkedin"></span></a></li>
                                        </ul>-->
                        </div>
                        <div  class="col-md-6">
                            <span> <a style="color: white" href="#"> <strong style="color: white">مرحبـا </strong> <%= request.getSession().getAttribute("NAME").toString()%></a> </span>

                            <span class="mr-3"> <strong style="color: white"> | </strong> <a style="color: white" href="#"><%= request.getSession().getAttribute("TRAFFIC_UNIT").toString()%></a>  </span>

                        </div>

                    </div>
                </div> 
            </div>


            <div class="container">
                <div class="row align-items-center">

                    <div class="col-11 col-xl-2">
                        <h1 class="mb-0 site-logo"><img style="height: 80px ; width: 140px" src="AMAN_LOGO.png"/></h1>
                    </div>
                    <div class="col-12 col-md-10 d-none d-xl-block">
                        <nav class="site-navigation position-relative text-right" role="navigation">

                            <ul class="site-menu js-clone-nav mr-auto d-none d-lg-block">



                                <!--                                    <li class="has-children">
                                                                        <a href="services.html">خدمات أخرى</a>
                                                                        <ul class="dropdown">
                                                                            <li><a href="services.html">معاملات اليوم</a></li>
                                
                                                                        </ul>
                                                                    </li>-->





                                <img style="height: 100px ; width: 140px;" src="logo_titd.png"/>
                            </ul>
                        </nav>
                    </div>


                    <div class="d-inline-block d-xl-none ml-md-0 mr-auto py-3" style="position: relative; top: 3px;"><a href="#" class="site-menu-toggle js-menu-toggle text-black"><span class="icon-menu h3"></span></a></div>

                </div>

            </div>
        </div>



        <div id="selectEF" class="site-blocks-cover overlay" style="background-image: url(temp1/images/hero_1.jpg);display: 
             block" data-aos="fade" data-stellar-background-ratio="0.5">
            <div class="container">
                <div class="row align-items-center justify-content-center text-center">

                    <div class="col-md-12" data-aos="fade-up" data-aos-delay="400">

                        <div class="row justify-content-center mb-4">
                            <div class="col-md-8 text-center">
                                <h1>برجاء اختيار هوية المواطن</h1>
                                <br>
                                <div><a style="border-radius: 4px;height: 80px ; width: 150px;font-size: 15px;color: white" onclick="showFor()" class="btn btn-primary btn-md">أجنبي</a>    <a style="border-radius: 4px;height: 80px ; width: 150px;font-size: 15px;color: white" onclick="showEgy()" class="btn btn-primary btn-md">مصري</a></div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>  







        <div style="display: none" id="egyForm" class="site-section bg-light">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 mb-5">
                        <div style="display: block" id="BackBtn"  class="p-4 mb-3 bg-white">
                            <!--              <h3 class="h5 text-black mb-3">More Info</h3>-->

                            <p><a onclick="goHome()" style="border-radius: 4px" href="#" class="btn btn-primary btn-md text-white">رجوع</a></p>
                        </div>


                        <form method="get" action="internist" class="p-5 bg-white">

                            <center><h2  class="h4 text-black mb-5">الكشـف الباطني</h2> </center>

                            <!--                            <div class="row form-group">
                                                            <div class="col-md-6 mb-3 mb-md-0">
                                                                <label class="text-black" for="fname">First Name</label>
                                                                <input type="text" id="fname" class="form-control">
                                                            </div>
                                                            <div class="col-md-6">
                                                                <label class="text-black" for="lname">Last Name</label>
                                                                <input type="text" id="lname" class="form-control">
                                                            </div>
                                                        </div>-->

                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="email">الرقم القومي</label> 
                                    <input onblur="getName()" name="transID" type="text" id="nid" class="form-control" required="يرجاء ملئ هذا الحقل">
                                </div>
                            </div>
                            <div style="display: block" id="requestIDsDiv" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">رقم المعاملة</label> 
                                    <select id="requestIDsID"  class="form-control mdb-select colorful-select dropdown-primary"   name="requestIDs"   required="يرجاء ملئ هذا الحقل">
                                        <!--                                        <option disabled selected value>الحالة الطبية</option>-->


                                    </select>

                                </div>
                            </div>

                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="email">الإسـم</label> 
                                    <input name="citizenName" type="text" id="citizenNameID" class="form-control" disabled>
                                </div>
                            </div>

                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">النتيجة</label> 
                                    <select  onclick="showMedicalConditions()" class="form-control" type="" spellcheck="false" id="selectedResultID" name="result" tabindex="2" data-sizePolicy="fixed" data-pintopage="page_fixedLeft" required="يرجاء ملئ هذا الحقل">
                                        <option disabled selected value>نتيجة الكشف</option>
                                        <option  value="acc">لائـق</option>
                                        <option value="nacc">غير لائـق</option>
                                        <option  value="acc">لائق بشرط</option>
                                    </select>

                                </div>
                            </div>


                            <div style="display: none" id="MedicalDivID" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">الحالات الطبية</label> 
                                    <select id="medCondID"  class="form-control mdb-select colorful-select dropdown-primary"   name="medical_conditions"   required="يرجاء ملئ هذا الحقل" multiple>
                                        <option disabled selected value>الحالة الطبية</option>
                                        <%
                                            Connection Con = null;
                                            Statement stmt = null;

                                            getcon c = new getcon();
                                            Con = c.myconnection();
                                            stmt = Con.createStatement();
                                            ResultSet rs = stmt.executeQuery("select * from mi.medicalconditions");
                                            while (rs.next()) {
                                        %><option value="<%= rs.getString("lookUp_ID")%>"> <%= rs.getString("description")%> </option><%
                                            }
                                            stmt.close();
                                        %>

                                    </select>

                                </div>
                            </div>


                            <div id="BloodDivID" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">فصيلة الدم</label> 
                                    <select id="bloodID"  class="form-control mdb-select colorful-select dropdown-primary"   name="blood_group">
                                        <option disabled selected value>اختر فصيلة الدم</option>
                                        <%
                                            
                                            Statement stmt4 = null;

                                            //getcon c4 = new getcon();
                                           // Con = c4.myconnection();
                                            stmt4 = Con.createStatement();
                                            ResultSet rs4 = stmt4.executeQuery("select * from mi.bloodgroup");
                                            while (rs4.next()) {
                                        %><option value="<%= rs4.getString("lookUp_ID")%>"><%= rs4.getString("description")%></option><%
                                            }
                                            stmt4.close();
                                        %>

                                    </select>

                                </div>
                            </div>

                            <div class="row form-group">
                                <div class="col-md-12">
                                    <label class="text-black" for="message">ملاحظــات</label> 
                                    <textarea name="message" id="message" cols="30" rows="7" class="form-control" placeholder="أكتب ملاحظات ..."></textarea>
                                </div>
                            </div>


                            <center>
                                <input type="submit" style="border-radius: 4px;font-size: 14px" value="تسجيل نتيجة الكشف" class="btn btn-primary btn-md text-white">
                            </center>


                        </form>
                    </div>

                </div>
            </div>
        </div>



        <div style="display: none" id="forForm" class="site-section bg-light">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 mb-5">

                        <div style="display: block" id="BackBtn"  class="p-4 mb-3 bg-white">
                            <!--              <h3 class="h5 text-black mb-3">More Info</h3>-->

                            <p><a onclick="goHome()" href="#" class="btn btn-primary btn-md text-white">رجوع</a></p>
                        </div>

                        <form method="get" onsubmit=""  action="internist" class="p-5 bg-white">

                            <center><h2  class="h4 text-black mb-5">الكشـف الباطني</h2> </center>
                            <br>
                                    <center><div id="wait" class="bg-light" style="display:none;"><img src='demo_wait.gif'  /><br>..جاري تحميل البيانات </div>
                    </center>

                            <!--                            <div class="row form-group">
                                                            <div class="col-md-6 mb-3 mb-md-0">
                                                                <label class="text-black" for="fname">First Name</label>
                                                                <input type="text" id="fname" class="form-control">
                                                            </div>
                                                            <div class="col-md-6">
                                                                <label class="text-black" for="lname">Last Name</label>
                                                                <input type="text" id="lname" class="form-control">
                                                            </div>
                                                        </div>-->



                            <div style="display: block" id="CountryDivID" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">البلد</label> 
                                    <select  id="CountryID"  class="form-control mdb-select colorful-select dropdown-primary"   name="theCountry"   required="يرجاء ملئ هذا الحقل">
                                        <option disabled selected value>اختر البلد</option>
                                        <%
                                            Statement stmt2 = null;
                                            stmt2 = Con.createStatement();
                                            ResultSet rs2 = stmt2.executeQuery("select * from mi.passport_issue_country");
                                            while (rs2.next()) {
                                        %><option value="<%= rs2.getString("lookUp_ID")%>"><%= rs2.getString("description")%></option><%
                                            }
                                            stmt2.close();
                                        %>

                                    </select>

                                </div>
                            </div>


                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="email">رقم جواز السفر</label> 
                                    <input onblur="getName()" name="passNo" type="text" id="passId" class="form-control" required="يرجاء ملئ هذا الحقل">
                                </div>
                            </div>



                            <div style="display: block" id="requestIDsDivFor" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">رقم المعاملة</label> 
                                    <select id="requestIDsIDFor"  class="form-control mdb-select colorful-select dropdown-primary"   name="requestIDs"   required="يرجاء ملئ هذا الحقل">
                                        <!--                                        <option disabled selected value>الحالة الطبية</option>-->


                                    </select>

                                </div>
                            </div>



                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="email">الإسـم</label> 
                                    <input name="citizenName" type="text" id="citizenNameIDFor" class="form-control" disabled>
                                </div>
                            </div>


                            <div id="BloodDivIDFor" class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">فصيلة الدم</label> 
                                    <select id="bloodID"  class="form-control mdb-select colorful-select dropdown-primary"   name="blood_group">
                                        <option disabled selected value>اختر فصيلة الدم</option>
                                        <%
                                            
                                            Statement stmt5 = null;

                                          //  getcon c5 = new getcon();
                                          //  Con = c5.myconnection();
                                            stmt5 = Con.createStatement();
                                            ResultSet rs5 = stmt5.executeQuery("select * from mi.bloodgroup");
                                            while (rs5.next()) {
                                        %><option value="<%= rs5.getString("lookUp_ID")%>"><%= rs5.getString("description")%></option><%
                                            }
                                            stmt5.close();
                                            Con.close();
                                        %>

                                    </select>

                                </div>
                            </div>

                            <div class="row form-group">

                                <div class="col-md-12">
                                    <label class="text-black" for="subject">النتيجة</label> 
                                    <select class="form-control" type="tel" spellcheck="false" id="widgetu1609_input" name="result" tabindex="2" data-sizePolicy="fixed" data-pintopage="page_fixedLeft" required="يرجاء ملئ هذا الحقل"><option disabled selected value>نتيجة الكشف</option><option value="acc">لائـق</option>
                                        <option value="nacc">غير لائـق</option><option value="sacc">كوميسيون</option></select>

                                </div>
                            </div>

                            <div class="row form-group">
                                <div class="col-md-12">
                                    <label class="text-black" for="message">ملاحظــات</label> 
                                    <textarea name="message" id="message" cols="30" rows="7" class="form-control" placeholder="أكتب ملاحظات ..."></textarea>
                                </div>
                            </div>

                            <center>
                                <input style="text-align: center;border-radius: 4px;font-size: 14px" type="submit" value="تسجيل نتيجة الكشف" class="btn btn-primary btn-md text-white">
                            </center>



                        </form>
                    </div>

                </div>
            </div>
        </div>






        <!--    <footer class="site-footer">
              <div class="container">
                <div class="row">
                  <div class="col-md-9">
                    <div class="row">
                      <div class="col-md-5">
                        <h2 class="footer-heading mb-4">About Us</h2>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Neque facere laudantium magnam voluptatum autem. Amet aliquid nesciunt veritatis aliquam.</p>
                      </div>
                      <div class="col-md-3 ml-auto">
                        <h2 class="footer-heading mb-4">Features</h2>
                        <ul class="list-unstyled">
                          <li><a href="#">About Us</a></li>
                          <li><a href="#">Services</a></li>
                          <li><a href="#">Testimonials</a></li>
                          <li><a href="#">Contact Us</a></li>
                        </ul>
                      </div>
                      <div class="col-md-3">
                        <h2 class="footer-heading mb-4">Follow Us</h2>
                        <a href="#" class="pl-0 pr-3"><span class="icon-facebook"></span></a>
                        <a href="#" class="pl-3 pr-3"><span class="icon-twitter"></span></a>
                        <a href="#" class="pl-3 pr-3"><span class="icon-instagram"></span></a>
                        <a href="#" class="pl-3 pr-3"><span class="icon-linkedin"></span></a>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <h2 class="footer-heading mb-4">Subscribe Newsletter</h2>
                    <form action="#" method="post">
                      <div class="input-group mb-3">
                        <input type="text" class="form-control border-secondary text-white bg-transparent" placeholder="Enter Email" aria-label="Enter Email" aria-describedby="button-addon2">
                        <div class="input-group-append">
                          <button class="btn btn-primary text-white" type="button" id="button-addon2">Send</button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
                <div class="row pt-5 mt-5 text-center">
                  <div class="col-md-12">
                    <div class="border-top pt-5">
                    <p>
                     Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. 
                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank" >Colorlib</a>
                     Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. 
                    </p>
                    </div>
                  </div>
                  
                </div>
              </div>
            </footer>-->

        <script>


            const player = document.getElementById('player');
            const canvas = document.getElementById('canvas');
            const context = canvas.getContext('2d');
            const captureButton = document.getElementById('capture');




            const constraints = {
                video: true,
            };

            captureButton.addEventListener('click', () => {
                // Draw the video frame to the canvas.
                context.drawImage(player, 0, 0, canvas.width, canvas.height);
                var dataURL = canvas.toDataURL("image/png");
                dataURL.replace(/^data:image\/(png|jpg);base64,/, "");


                // player.srcObject.getVideoTracks().forEach(track => track.stop());
            });

            // Attach the video stream to the video element and autoplay.
            navigator.mediaDevices.getUserMedia(constraints)
                    .then((stream) => {
                        player.srcObject = stream;
                    });



            function checkOnline(event) {


                var xhttp = new XMLHttpRequest();
               
                xhttp.onreadystatechange = function () {
                    alert(this.status);
                    if (this.status !== 200) {
                        alert("يوجد عطل بالإتصال بالخادم يرجى اعاده المحاولة");
                        event.preventDefault();
                       
                        return false;
                    }
                     };
                     xhttp.open("GET", "/API/internist", true);
                    xhttp.send();
                }



            
            function showEgy() {


                document.getElementById("selectEF").style.display = "none";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "block";


            }
            function getName() {
                $("#wait").css("display", "block");
                var nid = document.getElementById("nid").value;
                var passNo = document.getElementById("passId").value;
                var CountryID = document.getElementById("CountryID").value;
                var select;
                if (nid !== '' && nid !== null)
                {
                    select = document.getElementById('requestIDsID');
                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            $("#wait").css("display", "none");
//                      alert('national ID');
//                      alert(this.responseText.toString());
                            //  document.getElementById("citizenNameID").value = this.responseText;
                            var myObj = JSON.parse(this.responseText.toString());
//                    alert(myObj);

                            for (var i = 0; i < myObj.requestIDs.length; i++) {
                                var opt = document.createElement('option');
//                  alert(myObj.requestIDs[i]);
                                opt.value = myObj.requestIDs[i];
                                opt.innerHTML = myObj.requestIDs[i];
                                select.appendChild(opt);
                            }
                            document.getElementById("citizenNameID").value = myObj.name;
                        }
                    };
                    xhttp.open("GET", "/API/getName?NationalID=" + nid, true);
                    xhttp.send();
                } else if ((passNo !== '' && CountryID !== '') && (passNo !== null && CountryID !== null))
                {
                    select = document.getElementById('requestIDsIDFor');
                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            $("#wait").css("display", "none");
//                      alert(this.responseText.toString());
                            //  document.getElementById("citizenNameID").value = this.responseText;
                            var myObj = JSON.parse(this.responseText.toString());
//                    alert(myObj);

                            for (var i = 0; i < myObj.requestIDs.length; i++) {
                                var opt = document.createElement('option');
//                  alert(myObj.requestIDs[i]);
                                opt.value = myObj.requestIDs[i];
                                opt.innerHTML = myObj.requestIDs[i];
                                select.appendChild(opt);
                            }
                            document.getElementById("citizenNameIDFor").value = myObj.name;
                        }
                    };
                    xhttp.open("GET", "/API/getName?theCountry=" + CountryID + "&passNo=" + passNo, true);
                    xhttp.send();
                }
            }

            function showMedicalConditions() {


                var selectBox = document.getElementById("selectedResultID");
                var selectedIndex = selectBox.selectedIndex;
                if (selectedIndex == 3) {
                    document.getElementById("MedicalDivID").style.display = "block";
                }
            }

            function showFor() {

                document.getElementById("selectEF").style.display = "none";
                document.getElementById("forForm").style.display = "block";

                document.getElementById("egyForm").style.display = "none";


            }
            function goHome() {

                document.getElementById("selectEF").style.display = "block";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "none";
//                document.getElementById("EgyDialoug").style.display = "none";
//                document.getElementById("ForDialoug").style.display = "none";


            }

            function showCustomer() {
                var str = document.getElementById("nid").value;
                var xhttp;
                if (str === "") {
                    document.getElementById("citizenFees").innerHTML = "";
                    document.getElementById("citizenName").innerHTML = "";
                    document.getElementById("citizenBD").innerHTML = "";
                    document.getElementById("citizenLicense").innerHTML = "";
                    document.getElementById("citizenTU").innerHTML = "";
                    document.getElementById("citizenReqNo").innerHTML = "";
                    return;
                }
                xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {

                        // document.getElementById("fullNameLabel").innerHTML = "";
                        var obj = JSON.parse(this.responseText);
                        if (obj.err != '') {
                            document.getElementById("citizenFees").innerHTML = obj.err;
                            document.getElementById("citizenName").innerHTML = obj.err;
                            document.getElementById("citizenBD").innerHTML = obj.err;
                            document.getElementById("citizenLicense").innerHTML = obj.err;
                            document.getElementById("citizenTU").innerHTML = obj.err;
                            document.getElementById("citizenReqNo").innerHTML = obj.err;
                        } else {
                            document.getElementById("citizenFees").innerHTML = obj.requestFees;
                            document.getElementById("citizenName").innerHTML = obj.FName + ' ' + obj.MName + ' ' + obj.LName + ' ' + obj.ExName;
                            document.getElementById("citizenBD").innerHTML = obj.BirthDate;
                            document.getElementById("citizenLicense").innerHTML = obj.LicenseType;
                            document.getElementById("citizenTU").innerHTML = obj.TrafficUnit;
                            document.getElementById("citizenReqNo").innerHTML = obj.requestID;
                        }

                        // document.getElementById("widgetu1410_input").value = this.responseText;

                    }
                };
                xhttp.open("GET", "/API/MedicalCheckup/InquiryRequest?NationalID=" + str, true);
                xhttp.send();
            }


            function showCameraandBio() {

                document.getElementById("NextBtn").style.display = "none";
                document.getElementById("EgyCitizenInfo").style.display = "none";
                document.getElementById("BackBtn").style.display = "block";
                document.getElementById("EgyCitizenCameraAndBio").style.display = "block";
                document.getElementById("ConfirmBtn").style.display = "block";



            }

            function removeCameraandBio() {

                document.getElementById("NextBtn").style.display = "block";
                document.getElementById("EgyCitizenInfo").style.display = "block";
                document.getElementById("EgyCitizenCameraAndBio").style.display = "none";
                document.getElementById("ConfirmBtn").style.display = "none";
                document.getElementById("BackBtn").style.display = "none";



            }


            function saveData() {
                var nid = document.getElementById("nid").value;
                var citizenFees = document.getElementById("citizenFees").innerHTML;
                var citizenName = document.getElementById("citizenName").innerHTML;
                var citizenBD = document.getElementById("citizenBD").innerHTML;
                var citizenLicense = document.getElementById("citizenLicense").innerHTML;
                var citizenTU = document.getElementById("citizenTU").innerHTML;
                var citizenReqNo = document.getElementById("citizenReqNo").innerHTML;

                var xhttp;
                if (citizenFees === "" || citizenName === "" || citizenBD === "" || citizenBD === "" || citizenLicense === "" || citizenTU === "" || citizenReqNo === "") {
                    alert('يرجي اكمال معلومات المواطن');
                    return;
                }
                xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {

                        // document.getElementById("fullNameLabel").innerHTML = "";
                        var obj = this.responseText.toString();
//                        if (obj.equals('done')) {
                        alert(' تم التأكيد');
                        location = 'inquiry.jsp';
                        // } 

                        // document.getElementById("widgetu1410_input").value = this.responseText;

                    }
                };
                xhttp.open("GET", "/confirm_data?citizenFees=" + citizenFees + "&citizenName=" + citizenName + "&citizenBD=" + citizenBD + "&citizenLicense=" + citizenLicense + "&citizenTU=" + citizenTU + "&citizenReqNo=" + citizenReqNo + "&nid=" + nid, true);
                xhttp.send();
            }

        </script>
        <script src="temp1/js/jquery-3.3.1.min.js"></script>
        <script src="temp1/js/jquery-migrate-3.0.1.min.js"></script>
        <script src="temp1/js/jquery-ui.js"></script>
        <script src="temp1/js/popper.min.js"></script>
        <script src="temp1/js/bootstrap.min.js"></script>
        <script src="temp1/js/owl.carousel.min.js"></script>
        <script src="temp1/js/jquery.stellar.min.js"></script>
        <script src="temp1/js/jquery.countdown.min.js"></script>
        <script src="temp1/js/jquery.magnific-popup.min.js"></script>
        <script src="temp1/js/bootstrap-datepicker.min.js"></script>
        <script src="temp1/js/aos.js"></script>

        <script src="temp1/js/typed.js"></script>
        <script>
            var typed = new Typed('.typed-words', {
                strings: ["Web Apps", " WordPress", " Mobile Apps"],
                typeSpeed: 80,
                backSpeed: 80,
                backDelay: 4000,
                startDelay: 1000,
                loop: true,
                showCursor: true
            });
        </script>

        <script src="temp1/js/main.js"></script>

    </body>
</html>