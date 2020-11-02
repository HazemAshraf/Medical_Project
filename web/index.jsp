<!DOCTYPE html>

<%@page import="com.aman.medical.db.getcon"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en-GB">
    <head>
     <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Medical Inspection | welcome</title>



        <link rel="icon" href="css4/favicon.png">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/style1.css">
        <link rel="stylesheet" href="css/reset.css">




        <style>

            body, html {
                height: 100%;
                margin: 0;
                font: 400 15px/1.8 "Lato", sans-serif;
                color: #777;
            }

            .bgimg-1, .bgimg-2, .bgimg-3 {
                position: relative;
                opacity: 0.65;
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;

            }
            .bgimg-1 {
                background-image: url("css4/2554452comp.jpg");
                height: 100%;
            }
            footer {
                position: absolute;
                bottom: 0;
                height: 34px;
                width: 100%;
                background-color: #333333;
            }

            p.copyright {
                position: relative;
                width: 100%;
                color: #fff;
                line-height: 40px;
                font-size: 0.7em;
                text-align: center;
                bottom:0;
            }
        </style>



    </head>

    <body>


        <div class ="bgimg-1">
            <!--            <p id="demo"></p>-->
<!--            <ul style="z-index:1;position: sticky;list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #333;">
              
                <li id="inqOp" style="float: right" ><a onclick="Administrator()"   style="  display: block;
                                                        text-align: center;
                                                        padding: 14px 16px;
                                                        color: white;
                                                        text-decoration: none;">مـستعلم بيانـات</a></li>
                <li id="intOp" style="float: right"><a onclick="Internist()" style="  display: block;
                                                       text-align: center;
                                                       color: white;
                                                       padding: 14px 16px;
                                                       text-decoration: none;">طبيب بـاطني</a></li>
                <li id="ocuOp" style="float: right"><a onclick="Oculist()"  style="  display: block;
                                                       color: white;
                                                       text-align: center;
                                                       padding: 14px 16px;
                                                       text-decoration: none;">طبيب عيــون</a></li>
                                     <li id="BioOp" style="float: right"><a onclick="Biometric()"  style="  display: block;
                                                                       color: white;
                                                                       text-align: center;
                                                                       padding: 14px 16px;
                                                                       text-decoration: none;">فاحص بصمات</a></li>

            </ul>-->
            <div style="float:left;margin-left:100px;width:400px"  class="container" >
                <img style="height: 130px ; width: 180px;margin-bottom: 40px" src="logo_titd.png"/><br> 
                <img style="width:400px" src="AMAN_LOGO.png"/><hr>
                <h5 style="float: left">Medical-Inspection</h5></div>

            <div id="dataInquiry"  class = "container">

                <h1 style="color:white">Welcome</h1><br/><h1 style="color:white">مرحبــا يرجي تسجيل الدخول</h1>

                <form  class="form" action="login" method="post">
                    <input type="text" name="mail" placeholder="Username">
                    <input type="password" name="pass" placeholder="Password">
                    
                    
                    <input placeholder="وحدة المرور" name="theUnit" list="brow">
<datalist id="brow">
      <%
                                             Connection Con = null;
                                            getcon c = new getcon();
                                            Con = c.myconnection();
                                            Statement stmt2 = null;
                                            stmt2 = Con.createStatement();
                                            ResultSet rs2 = stmt2.executeQuery("select * from mi.trafficunits");
                                            while (rs2.next()) {
                                        %><option value="<%= rs2.getString("description")%>"><%
                                            }
                                            stmt2.close();
                                            Con.close();
                                        %>
</datalist>  

                                    <button style="margin-left: 40px" type="submit" id="login-button">Login</button>

                </form>


            </div>
<!--            <div id="internist" style="display:none" class = "container" >

                <h1  style="color:white">Welcome</h1><br/><h2 style="color:white">تسجيل دخول كاطبيب باطني</h2>

                <form  class="form" action="login" method="post">
                    <input type="text" name="mail" placeholder="Username">
                    <input type="password" name="pass" placeholder="Password">
                    <input type="hidden" name="type" value="internist" />
                    <button type="submit" id="login-button">Login</button><br><br>

                </form>


            </div>
            <div id="oculist" style="display:none" class = "container">

                <h1 style="color:white">Welcome</h1><br/><h2 style="color:white">تسجيل دخول كاطبيب عيون</h2>

                <form  class="form" action="login" method="post">
                    <input type="text" name="mail" placeholder="Username">
                    <input type="password" name="pass" placeholder="Password">
                    <input type="hidden" name="type" value="oculist" />
                    <button type="submit" id="login-button">Login</button><br><br>

                </form>


            </div>-->

        
<!--            <ul style="z-index: -1" class="bg-bubbles">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>-->

            
          
<!--                <img style="height: 130px ; width: 180px;float: right;margin-top: 60px" src="logo_titd.png"/>-->
       
            <footer>
                
                <p class="copyright">© 2019 - Medical Inspection</p>
            </footer>
        
        </div>

                                        
<!--                                        <script src="droplivesearch/jquery.min.js"></script>
<script src="droplivesearch/bootstrap.min.js"></script>
<link href="droplivesearch/bootstrap.min.css" rel="stylesheet" />
<script src="droplivesearch/bootstrap-select.min.js"></script>
<link href="droplivesearch/bootstrap-select.min.css" rel="stylesheet" />

-->



        <script>
            function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

function filterFunction() {
  var input, filter, ul, li, a, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  div = document.getElementById("myDropdown");
  a = div.getElementsByTagName("a");
  for (i = 0; i < a.length; i++) {
    txtValue = a[i].textContent || a[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      a[i].style.display = "";
    } else {
      a[i].style.display = "none";
    }
  }
}
            
//                        $(function() {
//  $('.selectpicker').selectpicker();
//});
//
//            
            
            function callLookups() {
                var lookupsResponse;
                var now = new Date();
                var timestamp = now.getDay() + "-" + now.getMonth() +"-"+ now.getYear() + " " + now.getHours() + ":" + now.getMinutes();
                var tid = Math.floor((Math.random() * 100000000000000) + 1);
//                var obj = {
//                    "header": {
//                        "version": "1.0",
//                        "category": "request",
//                        "service": "1",
//                        "timestamp": timestamp, // "03-09-2018 13:19"
//                        "tid": tid //"594f2c57-e0d6-4311-87ffac491c4337dd"
//                    },
//                    "body": {
//                        "LookupName": ""
//                    }
//                }
//                ;
                var params = JSON.stringify({
"header": {
"version": "1.0",
"category": "request",
"service": "1",
"timestamp": "03-09-2018 13:19",
"tid": "594f2c57-e0d6-4311-87ff-ac491c4337dd"
},
"body": {
"LookupName": ""
}
});
               // var params = myJSON;
                var xhttp = new XMLHttpRequest();
               // XMLHttpRequest();
               // xhttp.setRequestHeader('Content-type', 'application/json');
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                       // alert(this.responseText.toString());
                         lookupsResponse = this.responseText.toString();
                         saveLookup(lookupsResponse);
                    }
                };
                xhttp.open("POST", "http://192.168.235.50/drvintegration_test/API/Lookups/GetLookup?", true);
                 xhttp.setRequestHeader('Content-type', 'application/json');
                // xhttp.open("POST", "/API/Lookups/GetLookup?", true);
                xhttp.send(params);
                
            }
            function saveLookup(lookupsResponse){
                 var params = lookupsResponse;
                var xhttp = new XMLHttpRequest();
               // XMLHttpRequest();
                
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                         var resp = this.responseText.toString();
                         
                    }
                };
                xhttp.open("POST", "/API/Lookups/SaveLookups?", true);
                xhttp.setRequestHeader('Content-type', 'application/json');
                xhttp.send(params);
                
            }
             function Administrator() {
                document.getElementById("inqOp").style.backgroundColor = "#111";
                document.getElementById("intOp").style.backgroundColor = "#333";
                document.getElementById("ocuOp").style.backgroundColor = "#333";
                document.getElementById("dataInquiry").style.display = "block";
                document.getElementById("internist").style.display = "none";
                document.getElementById("oculist").style.display = "none";
            }
            function Internist() {
                document.getElementById("intOp").style.backgroundColor = "#111";
                document.getElementById("inqOp").style.backgroundColor = "#333";
                document.getElementById("ocuOp").style.backgroundColor = "#333";
                document.getElementById("dataInquiry").style.display = "none";
                document.getElementById("internist").style.display = "block";
                document.getElementById("oculist").style.display = "none";
            }
            function Oculist() {
                document.getElementById("ocuOp").style.backgroundColor = "#111";
                document.getElementById("inqOp").style.backgroundColor = "#333";
                document.getElementById("intOp").style.backgroundColor = "#333";
                document.getElementById("dataInquiry").style.display = "none";
                document.getElementById("internist").style.display = "none";
                document.getElementById("oculist").style.display = "block";
            }
           
        </script>
        <script src="js/index.js"></script>

        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src='http://thecodeplayer.com/uploads/js/jquery.easing.min.js'></script>

        <script src="js/index1.js"></script>

    </body>
</html>
