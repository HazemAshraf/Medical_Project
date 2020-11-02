<!DOCTYPE html>
<%@page import="com.aman.medical.db.getcon"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
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
            <header class="site-navbar py-4 bg-white" role="banner">

                <div class="container">
                    <div class="row align-items-center">

                        <div class="col-11 col-xl-2">
                            <h1 class="mb-0 site-logo"><img style="height: 80px ; width: 140px" src="AMAN_LOGO.png"/></h1>
                        </div>
                        <div class="col-12 col-md-10 d-none d-xl-block">
                            <nav class="site-navigation position-relative text-right" role="navigation">

                                <ul class="site-menu js-clone-nav mr-auto d-none d-lg-block">



                                    <li class="has-children">
                                        <a href="#">خدمات أخرى</a>
                                        <ul class="dropdown">
                                            <li><a href="#">معاملات اليوم</a></li>

                                        </ul>
                                    </li>

                                    <li >
                                        <a onclick="goHome()" href="#">الصفحة الرئيسية</a>

                                    </li>
                                    <li ><img style="height: 100px ; width: 140px;" src="logo_titd.png"/></li>

                                </ul>
                            </nav>
                        </div>


                        <div class="d-inline-block d-xl-none ml-md-0 mr-auto py-3" style="position: relative; top: 3px;"><a href="#" class="site-menu-toggle js-menu-toggle text-black"><span class="icon-menu h3"></span></a></div>

                    </div>

                </div>
        </div>





        <!--        <div id="selectEF" class="site-blocks-cover overlay" style="background-image: url(temp1/images/hero_1.jpg);display:
                     block" data-aos="fade" data-stellar-background-ratio="0.5">
                    <div class="container">
                        <div class="row align-items-center justify-content-center text-center">

                            <div class="col-md-12" data-aos="fade-up" data-aos-delay="400">

                                <div class="row justify-content-center mb-4">
                                    <div class="col-md-8 text-center">
                                        <h1>برجاء اختيار هوية المواطن</h1>
                                        <br>
                                                                        <div><a style="height: 80px ; width: 150px;font-size: 15px;color: white" onclick="showFor()" class="btn btn-primary btn-md">أجنبي</a>  |  <a style="height: 80px ; width: 150px;font-size: 15px;color: white" onclick="showEgy()" class="btn btn-primary btn-md">مصري</a></div>
                                        <div>
                                            <a class="btn-primary btn-block btn-md" onclick="showEgy()" style="background:#d86b2a;margin: auto;
                                               width: 50%;
                                               padding: 10px;height: 80px ; width: 500px;font-size: 30px;color: white;:hover {background: yellow}">مصري</a>

                                            <br> <br>

                                            <a style="background-color:#d86b2a;margin: auto;
                                               width: 50%;
                                               padding: 10px;height: 80px ; width: 500px;font-size: 30px;color: white" onclick="showFor()" class="btn-primary btn-block btn-md">أجنبي</a></div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>  -->


        <!--        <div style="display: block" id="egyForm" class="site-section bg-light">-->
        <div class="container">
            <div class="row">
                <input type="hidden" id="traffic_unit_code" value="<%= request.getSession().getAttribute("TRAFFIC_UNIT_CODE").toString()%>" />


                <div class="col-md-12 mb-5">
                    <div style="display: block;float: right" id="BackBtn"  class="p-4 mb-3 bg-light">
                        <!--              <h3 class="h5 text-black mb-3">More Info</h3>-->

                        <!--                        <div >   <input style="border-radius: 20px;text-align: right" id="myInput" onkeyup="myFunction()"  type="number" placeholder="بحث بالرقم القومي"></div>
                                                <br><div >   <input style="border-radius: 20px;text-align: right" id="myInput1" onkeyup="myFunctionFilterRequestID()"  type="number" placeholder="بحث بـرقم المعـاملة"></div>
                                                <br><div >   <input style="border-radius: 20px;text-align: right" id="myInput2" onkeyup="myFunctionFilterName()"  type="text" placeholder="بحث بالإسم"></div>
                        -->

                        <div >   <input style="border-radius: 20px;text-align: right" id="myInputNID"  type="text" placeholder="جواز سفر/رقم قومي"></div>
                        <br><div >   <input style="border-radius: 20px;text-align: right" id="myInputRID"  type="number" placeholder="بحث بـرقم المعـاملة"></div>
                        <br><div >   <button style="border-radius: 20px;text-align: center;width: 100%" onclick="getClient()" type="text">بحــث</button></div>

                    </div>

                    <div style="visibility: hidden;display: block;float: left" id="BackBtn"  class="p-4 mb-3 bg-light">
                        <!--              <h3 class="h5 text-black mb-3">More Info</h3>-->

                        <div ><a onclick="nextClient()" href="" class="btn btn-block btn-danger">العميل التالي</a>   </div>
                    </div>
                    <div style="visibility: hidden;display: block;float: left" id="BackBtn"  class="p-4 mb-3 bg-light">
                        <!--              <h3 class="h5 text-black mb-3">More Info</h3>-->

                        <div ><a href="infoData.xhtml" class="btn btn-block btn-danger">صفحة الدور</a>   </div>
                    </div>
                    <!--                        <input type="hidden" id="traffic_unit_code" data-value="@Request.RequestContext.HttpContext.Session['TRAFFIC_UNIT_CODE']" />-->
                    <center><div id="wait" class="bg-light" style="display:none;"><img src='demo_wait.gif'  /><br>..جاري تحميل البيانات </div>
                    </center>


                    <div id="EgyCitizenInfo"  class="p-4 mb-3 bg-light">



                        <table id="myTable" class="table table-hover text-right">




                            <tr class="bg-white">
                                <th>الحالة</th>
                                <th>عميل رقم</th>
                                <th>الرقم القومي</th>
                                <th>الرسوم</th>
                                <th>وحدة المرور</th>
                                <th>نوع الرخصة</th>
                                <th>رقم الطلب</th>
                                <th>الإســم</th>
                                <th></th>
                            </tr>


                            <tr>
                                <td id="statusID"></td>
                                <td id="queueNumberID"></td>
                                <td id="identity_showID"></td>
                                <td id="TotalAmountID"></td>
                                <td id="TrafficUnitID"></td>
                                <td id="LicenseTypeID"></td>
                                <td id="requestIDID"></td>
                                <td id="ApplicantNameID"></td>
                                <td>
                                    <form method="post" action="/API/biometric.jsp">
                                        <input id="queueNumberFormID" type="hidden"  name="QueueNumber" value="">
                                        <input  type="hidden" id="nid"  name="nationalID" value="">
                                        <input id="requestIDFormID"  type="hidden"  name="requestID" value="">
                                        <input id="ApplicantNameFormID" type="hidden"  name="FullName" value="">
                                        <input style="visibility: hidden" id="photoActionID" type="submit" value="">
                                    </form>
                                </td>
                            </tr>




                        </table>
                       
                    </div>

                </div>


            </div>
        </div>




        <script>



            function getClient() {
                $("#wait").css("display", "block");
                var nid = document.getElementById("myInputNID").value;
                var rid = document.getElementById("myInputRID").value;
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        $("#wait").css("display", "none");
                      //      alert(this.responseText.toString());
                        var myObj = JSON.parse(this.responseText);
                        document.getElementById("statusID").innerHTML = myObj.status;
                        document.getElementById("queueNumberID").innerHTML = myObj.queueNumber;
                        document.getElementById("identity_showID").innerHTML = myObj.identity_show;
                        document.getElementById("TotalAmountID").innerHTML = myObj.TotalAmount;
                        document.getElementById("TrafficUnitID").innerHTML = myObj.TrafficUnit;
                        document.getElementById("LicenseTypeID").innerHTML = myObj.LicenseType;
                        document.getElementById("requestIDID").innerHTML = myObj.requestID;
                        document.getElementById("ApplicantNameID").innerHTML = myObj.ApplicantName;
                        document.getElementById("nid").value = myObj.identity;
                        document.getElementById("photoActionID").value = myObj.photoAction;
                        document.getElementById("queueNumberFormID").value = myObj.queueNumber;
                        document.getElementById("requestIDFormID").value = myObj.requestID;
                        document.getElementById("ApplicantNameFormID").value = myObj.ApplicantName;
                        if (myObj.photoAction !== 'لا يوجد') {
                            document.getElementById("photoActionID").style.visibility = 'visible';
                        }
                    }
                };
                xhttp.open("GET", "/API/GetClient?nid=" + nid + "&rid=" + rid, true);
                xhttp.send();

            }

            function nextClient() {

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        // document.getElementById("demo").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "/API/NextClient", true);
                xhttp.send();


            }

            function myFunction() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[2];
                    //alert(td);
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

            function myFunctionFilterRequestID() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput1");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[6];
                    //alert(td);
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

            function myFunctionFilterName() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput2");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[7];
                    //alert(td);
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

            function filterByNID() {
                var nid_filter = document.getElementById("nationalIdFilter").value;
                if (nid_filter == '') {
                    alert('ادخل الرقم القومي');
                    return false;
                }
                var table = document.getElementById("myTable");
                var now = new Date();
                var month = now.getMonth() + 1;
                //               var from = "" + now.getFullYear() + "-" + month + "-" + now.getUTCDate();
                //var to = "" + now.getFullYear() + "-" + month + "-" + now.getUTCDate()+1;
                var from = "2020-01-01";
                var to = "2026-02-27";
                var trafficUnit = document.getElementById("traffic_unit_code").value;
                var params = JSON.stringify({
                    "header": {
                        "version": "1.0",
                        "category": "request",
                        "service": " TIT_Medical_Inquiry",
                        "timestamp": "03-09-2018 13:19",
                        "tid": "594f2c57-e0d6-4311-87ffac491c4337dd"
                    },
                    "body": {
                        "To": to,
                        "From": from,
                        "TrafficUnit": trafficUnit
                    }
                }
                );
//alert(params);
                // var params = '{ "body" : ' +'{ "From":'+from+' , "To":'+to+' , "TrafficUnit":'+trafficUnit+' }}';
                // var params = "From="+from+"&To="+to+"&TrafficUnit="+trafficUnit;
                var xhttp = new XMLHttpRequest();

                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        //alert(this.responseText.toString());
                        var myObj = JSON.parse(this.responseText);
                        //remove current queue to make a new one
                        var rowCount = table.rows.length;
                        for (var i = rowCount - 1; i > 0; i--) {
                            table.deleteRow(i);
                        }
                        // loop for all requests jsons
                        for (let i = 0; i < myObj.body.requests.length; i++) {
                            var nationalID = myObj.body.requests[i].nationalID;
                            if (nationalID == nid_filter) {
                                var requestID = myObj.body.requests[i].requestID;
                                var requestFees = myObj.body.requests[i].requestFees;
                                var fName = myObj.body.requests[i].fName;
                                var mName = myObj.body.requests[i].mName;
                                var lName = myObj.body.requests[i].lName;
                                var exName = myObj.body.requests[i].exName;

                                var passportNo = myObj.body.requests[i].passportNo;
                                var passportIssueCountry = myObj.body.requests[i].passportIssueCountry;
                                var licenseType = myObj.body.requests[i].licenseType;
                                var trafficUnit = myObj.body.requests[i].trafficUnit;
                                var birthDate = myObj.body.requests[i].birthDate;
                                var QueueNumber = myObj.body.requests[i].queueNumber;
                                var ServiceType = myObj.body.requests[i].serviceType;
                                if (ServiceType == 1) {
                                    ServiceType = "كشف طبي";
                                } else if (ServiceType == 2) {
                                    ServiceType = "فيش";

                                } else {
                                    ServiceType = "كشف طبي /فيش";
                                }

                                var row = table.insertRow(-1);
                                var cell1 = row.insertCell(0);
                                var cell2 = row.insertCell(1);
                                var cell3 = row.insertCell(2);
                                var cell4 = row.insertCell(3);
                                var cell5 = row.insertCell(4);
                                var cell6 = row.insertCell(5);
                                var cell7 = row.insertCell(6);
                                var cell8 = row.insertCell(7);
                                var cell9 = row.insertCell(8);

                                cell1.innerHTML = ServiceType;
                                cell2.innerHTML = QueueNumber;
                                cell3.innerHTML = requestID;
                                cell4.innerHTML = requestFees;
                                cell5.innerHTML = trafficUnit;
                                cell6.innerHTML = licenseType;
                                cell7.innerHTML = nationalID;
                                cell8.innerHTML = fName + " " + mName + " " + lName + " " + exName;
                                var fullName = fName;
                                //add button navigate for each record
                                var container = document.createElement("form");
                                container.method = "post";
                                container.action = "/API/biometric.jsp";

                                var nationalIDinput = document.createElement("input");
                                nationalIDinput.type = "hidden";
                                nationalIDinput.name = "nationalID";
                                nationalIDinput.value = nationalID;
                                container.appendChild(nationalIDinput);
                                var passportNoinput = document.createElement("input");
                                passportNoinput.type = "hidden";
                                passportNoinput.name = "passportNo";
                                passportNoinput.value = passportNo;
                                container.appendChild(passportNoinput);
                                var passportIssueCountryinput = document.createElement("input");
                                passportIssueCountryinput.type = "hidden";
                                passportIssueCountryinput.name = "passportIssueCountry";
                                passportIssueCountryinput.value = passportIssueCountry;
                                container.appendChild(passportIssueCountryinput);
                                var requestIDinput = document.createElement("input");
                                requestIDinput.type = "hidden";
                                requestIDinput.name = "requestID";
                                requestIDinput.value = requestID;
                                container.appendChild(requestIDinput);
                                var QueueNumberinput = document.createElement("input");
                                QueueNumberinput.type = "hidden";
                                QueueNumberinput.name = "QueueNumber";
                                QueueNumberinput.value = QueueNumber;
                                container.appendChild(QueueNumberinput);

                                var FullNameinput = document.createElement("input");

                                FullNameinput.type = "hidden";
                                FullNameinput.name = "FullName";
                                FullNameinput.value = fullName;
                                container.appendChild(FullNameinput);

                                var ServiceTypeinput = document.createElement("input");
                                ServiceTypeinput.type = "hidden";
                                ServiceTypeinput.name = "ServiceType";
                                ServiceTypeinput.value = ServiceType;
                                container.appendChild(ServiceTypeinput);

                                var input1 = document.createElement("input");
                                input1.type = "submit";
                                input1.name = "member1";
                                input1.value = "بصمات";
                                container.appendChild(input1);
                                // document.body.appendChild(container);
                                cell9.appendChild(container);




                                // var btn = document.createElement("BUTTON");
                                // btn.innerHTML =
                                //document.body.appendChild(btn);
                                // document.getElementById("txt").innerHTML = myObj.body.requests[0].nationalID;
                            }
                        }
                    }
                };

                xhttp.open("POST", "http://192.168.235.50/drvintegration_test/API/MedicalCheckup/GetAllRequest?", true);

                // xhttp.open("POST", "/API/MedicalCheckup/GetAllRequest?", true);
                xhttp.setRequestHeader('Content-type', 'application/json;charset=utf-8');

                xhttp.send(params);





            }

            function refresh() {

                var table = document.getElementById("myTable");
                var now = new Date();
                var month = now.getMonth() + 1;
                //               var from = "" + now.getFullYear() + "-" + month + "-" + now.getUTCDate();
                //var to = "" + now.getFullYear() + "-" + month + "-" + now.getUTCDate()+1;
                var from = "2020-01-01";
                var to = "2026-02-27";
                var trafficUnit = document.getElementById("traffic_unit_code").value;
                var params = JSON.stringify({
                    "header": {
                        "version": "1.0",
                        "category": "request",
                        "service": " TIT_Medical_Inquiry",
                        "timestamp": "03-09-2018 13:19",
                        "tid": "594f2c57-e0d6-4311-87ffac491c4337dd"
                    },
                    "body": {
                        "To": to,
                        "From": from,
                        "TrafficUnit": trafficUnit
                    }
                }
                );
//alert(params);
                // var params = '{ "body" : ' +'{ "From":'+from+' , "To":'+to+' , "TrafficUnit":'+trafficUnit+' }}';
                // var params = "From="+from+"&To="+to+"&TrafficUnit="+trafficUnit;
                var xhttp = new XMLHttpRequest();

                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        //alert(this.responseText.toString());
                        var myObj = JSON.parse(this.responseText);
                        //remove current queue to make a new one
                        var rowCount = table.rows.length;
                        for (var i = rowCount - 1; i > 0; i--) {
                            table.deleteRow(i);
                        }
                        // loop for all requests jsons
                        for (let i = 0; i < myObj.body.requests.length; i++) {

                            var requestID = myObj.body.requests[i].requestID;
                            var requestFees = myObj.body.requests[i].requestFees;
                            var fName = myObj.body.requests[i].fName;
                            var mName = myObj.body.requests[i].mName;
                            var lName = myObj.body.requests[i].lName;
                            var exName = myObj.body.requests[i].exName;
                            var nationalID = myObj.body.requests[i].nationalID;
                            var passportNo = myObj.body.requests[i].passportNo;
                            var passportIssueCountry = myObj.body.requests[i].passportIssueCountry;
                            var licenseType = myObj.body.requests[i].licenseType;
                            var trafficUnit = myObj.body.requests[i].trafficUnit;
                            var birthDate = myObj.body.requests[i].birthDate;
                            var QueueNumber = myObj.body.requests[i].queueNumber;
                            var ServiceType = myObj.body.requests[i].serviceType;
                            if (ServiceType == 1) {
                                ServiceType = "كشف طبي";
                            } else if (ServiceType == 2) {
                                ServiceType = "فيش";

                            } else {
                                ServiceType = "كشف طبي /فيش";
                            }

                            var row = table.insertRow(-1);
                            var cell1 = row.insertCell(0);
                            var cell2 = row.insertCell(1);
                            var cell3 = row.insertCell(2);
                            var cell4 = row.insertCell(3);
                            var cell5 = row.insertCell(4);
                            var cell6 = row.insertCell(5);
                            var cell7 = row.insertCell(6);
                            var cell8 = row.insertCell(7);
                            var cell9 = row.insertCell(8);

                            cell1.innerHTML = ServiceType;
                            cell2.innerHTML = QueueNumber;
                            cell3.innerHTML = requestID;
                            cell4.innerHTML = requestFees;
                            cell5.innerHTML = trafficUnit;
                            cell6.innerHTML = licenseType;
                            cell7.innerHTML = nationalID;
                            cell8.innerHTML = fName + " " + mName + " " + lName + " " + exName;
                            var fullName = fName;
                            //add button navigate for each record
                            var container = document.createElement("form");
                            container.method = "post";
                            container.action = "/API/biometric.jsp";

                            var nationalIDinput = document.createElement("input");
                            nationalIDinput.type = "hidden";
                            nationalIDinput.name = "nationalID";
                            nationalIDinput.value = nationalID;
                            container.appendChild(nationalIDinput);
                            var passportNoinput = document.createElement("input");
                            passportNoinput.type = "hidden";
                            passportNoinput.name = "passportNo";
                            passportNoinput.value = passportNo;
                            container.appendChild(passportNoinput);
                            var passportIssueCountryinput = document.createElement("input");
                            passportIssueCountryinput.type = "hidden";
                            passportIssueCountryinput.name = "passportIssueCountry";
                            passportIssueCountryinput.value = passportIssueCountry;
                            container.appendChild(passportIssueCountryinput);
                            var requestIDinput = document.createElement("input");
                            requestIDinput.type = "hidden";
                            requestIDinput.name = "requestID";
                            requestIDinput.value = requestID;
                            container.appendChild(requestIDinput);
                            var QueueNumberinput = document.createElement("input");
                            QueueNumberinput.type = "hidden";
                            QueueNumberinput.name = "QueueNumber";
                            QueueNumberinput.value = QueueNumber;
                            container.appendChild(QueueNumberinput);

                            var ServiceTypeinput = document.createElement("input");
                            ServiceTypeinput.type = "hidden";
                            ServiceTypeinput.name = "ServiceType";
                            ServiceTypeinput.value = ServiceType;
                            container.appendChild(ServiceTypeinput);
                            var FullNameinput = document.createElement("input");

                            FullNameinput.type = "hidden";
                            FullNameinput.name = "FullName";
                            FullNameinput.value = fullName;
                            container.appendChild(FullNameinput);

                            var input1 = document.createElement("input");
                            input1.type = "submit";
                            input1.name = "member1";
                            input1.value = "التقاط صوره";
                            container.appendChild(input1);
                            // document.body.appendChild(container);
                            cell9.appendChild(container);




                            // var btn = document.createElement("BUTTON");
                            // btn.innerHTML =
                            //document.body.appendChild(btn);
                            // document.getElementById("txt").innerHTML = myObj.body.requests[0].nationalID;
                        }
                    }
                };

                //xhttp.open("POST", "http://192.168.235.50/drvintegration_test/API/MedicalCheckup/GetAllRequest?", true);

                xhttp.open("POST", "/API/MedicalCheckup/GetAllRequest?", true);
                xhttp.setRequestHeader('Content-type', 'application/json;charset=utf-8');
                xhttp.send(params);





            }
            function showEgy() {

                document.getElementById("selectEF").style.display = "none";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "block";
                document.getElementById("EgyDialoug").style.display = "block";
                document.getElementById("ForDialoug").style.display = "none";

            }

            function showFor() {

                document.getElementById("selectEF").style.display = "none";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "block";
                document.getElementById("EgyDialoug").style.display = "none";
                document.getElementById("ForDialoug").style.display = "block";


            }
            function goHome() {

                document.getElementById("selectEF").style.display = "block";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "none";
                document.getElementById("EgyDialoug").style.display = "none";
                document.getElementById("ForDialoug").style.display = "none";


            }

            function showCustomer() {
                var str = document.getElementById("nid").value;
                var str1 = document.getElementById("passId").value;
                var str2 = document.getElementById("cId").value;
                var xhttp;
                if (str === "" && str1 === "" && str2 === "") {
                    document.getElementById("citizenFees").innerHTML = "";
                    document.getElementById("citizenName").innerHTML = "";
                    document.getElementById("citizenBD").innerHTML = "";
                    document.getElementById("citizenLicense").innerHTML = "";
                    document.getElementById("citizenTU").innerHTML = "";
                    document.getElementById("citizenReqNo").innerHTML = "";
                    return;
                }
//                if (str !== '') {
//                    document.getElementById("passId").value = '';
//                    document.getElementById("cId").value = '';
//                }
//                if (str1 !== '' && str2 !== '') {
//                    document.getElementById("nid").value = '';
//                }
                xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {

                        // document.getElementById("fullNameLabel").innerHTML = "";
                        var obj = JSON.parse(this.responseText);
                        if (obj.Error_Message === '1') {
                            document.getElementById("citizenFees").innerHTML = "لا يوجد";
                            document.getElementById("citizenName").innerHTML = "لا يوجد";
                            document.getElementById("citizenBD").innerHTML = "لا يوجد";
                            document.getElementById("citizenLicense").innerHTML = "لا يوجد";
                            document.getElementById("citizenTU").innerHTML = "لا يوجد";
                            document.getElementById("citizenReqNo").innerHTML = "لا يوجد";
                            document.getElementById("nid").value = '';
                            document.getElementById("passId").value = '';
                            document.getElementById("cId").value = '';
                            document.getElementById("queue").value = '';
                        } else {
                            document.getElementById("citizenFees").innerHTML = obj.RequestFees;
                            document.getElementById("citizenName").innerHTML = obj.FName + ' ' + obj.MName + ' ' + obj.LName + ' ' + obj.ExName;
                            document.getElementById("citizenBD").innerHTML = obj.BirthDate;
                            document.getElementById("citizenLicense").innerHTML = obj.LicenseType;
                            document.getElementById("citizenTU").innerHTML = obj.TrafficUnit;
                            document.getElementById("citizenReqNo").innerHTML = obj.RequestID;
                            document.getElementById("nid").value = obj.NationalID;
                            document.getElementById("passId").value = obj.PassportNo;
                            document.getElementById("cId").value = obj.PassportIssueCountry;
                            document.getElementById("queue").innerHTML = obj.queue;
                            document.getElementById("nid").value = '';
                            document.getElementById("passId").value = '';
                            document.getElementById("cId").value = '';
                        }

                        // document.getElementById("widgetu1410_input").value = this.responseText;

                    }
                };
                if (str1 === '' && str2 === '') {

                    xhttp.open("GET", "/API/MedicalCheckup/InquiryRequest?NationalID=" + str, true);

                } else if (str === '') {
                    document.getElementById("passId").value = '';
                    document.getElementById("cId").value = '';
                    xhttp.open("GET", "/API/MedicalCheckup/InquiryRequest?PassportNo=" + str1 + "&PassportIssueCountry=" + str2, true);

                }
//        xhttp.setRequestHeader("version", "1.0");
//         xhttp.setRequestHeader("category", "Request");
//          xhttp.setRequestHeader("Service", "TIT_Medical_Inquiry");
//           xhttp.setRequestHeader("Timestamp", new Date.getTime().toString());
//            xhttp.setRequestHeader("TrackID", "1");
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
            function  sendImage(citizenId) {
                // function  sendImage(nid,passId,cId){

                var dataURL = canvas.toDataURL();
                // dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
                // alert(dataURL);
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        // document.getElementById("demo").innerHTML = this.responseText;
                    }
                };
                xhttp.open("POST", "/API/ImageServlet", true);
                var data = citizenId + dataURL;
                xhttp.send(data);
//  xhttp.send("dataURL="dataURL+"&nid="+nid"&passId="+passId+"&cId="+cId);


            }
            function saveData() {

                var nid = document.getElementById("nid").value;
                var passId = document.getElementById("passId").value;
                var cId = document.getElementById("cId").value;
                var foreignComp = cId + passId;
                var citizenFees = document.getElementById("citizenFees").innerHTML;
                var citizenName = document.getElementById("citizenName").innerHTML;
                var citizenBD = document.getElementById("citizenBD").innerHTML;
                var citizenLicense = document.getElementById("citizenLicense").innerHTML;
                var citizenTU = document.getElementById("citizenTU").innerHTML;
                var citizenReqNo = document.getElementById("citizenReqNo").innerHTML;
                var queue = document.getElementById("queue").innerHTML;

                var xhttp;
//                if (citizenFees === "" || citizenName === "" || citizenBD === "" || citizenBD === "" || citizenLicense === "" || citizenTU === "" || citizenReqNo === "" || passId === "" || cId === "") {
//          alert('يرجي اكمال معلومات المواطن');
//                    return;
//                }
                xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {

                        // document.getElementById("fullNameLabel").innerHTML = "";
                        var obj = this.responseText.toString();
//                        if (obj.equals('done')) {
                        alert(' تم التأكيد');
                        goHome();
                        //location='inquiry.jsp';
                        // }

                        // document.getElementById("widgetu1410_input").value = this.responseText;

                    }
                };
                xhttp.open("GET", "/API/confirm_data?citizenFees=" + citizenFees + "&citizenName=" + citizenName + "&citizenBD=" + citizenBD + "&citizenLicense=" + citizenLicense + "&citizenTU=" + citizenTU + "&citizenReqNo=" + citizenReqNo + "&nid=" + nid + "&passId=" + passId + "&cId=" + cId + "&foreignComp=" + foreignComp + "&queue=" + queue, true);
                xhttp.send();
                if (foreignComp != null && foreignComp != '') {
                    sendImage(foreignComp);
                } else {
                    sendImage(nid);
                }

                // sendImage(nid,passId,cId);
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