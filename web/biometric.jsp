<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html >
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
        <style>

            img{
                max-width:350px;
                /*  display: none;*/
                visibility: hidden;
            }
            /*input[type=file]{
            padding:10px;
            background:#2d2d2d;}*/

        </style>
    </head>
    <body>
        <% request.setCharacterEncoding("UTF-8"); %>
        <div class="site-wrap">

            <div class="site-mobile-menu">
                <div class="site-mobile-menu-header">
                    <div class="site-mobile-menu-close mt-3">
                        <span class="icon-close2 js-menu-toggle"></span>
                    </div>
                </div>
                <div class="site-mobile-menu-body"></div>
            </div>

            <div class="border-bottom top-bar py-2">
                <div class="container">
                    <div class="row">

                        <div class="col-md-6">
                            <span> <a href="logout?sId=<%= request.getSession().getAttribute("SESSION_ID").toString()%>"> <strong>تسجيـل خـروج</strong> </a> </span>
                            <!--            <ul class="social-media">
                                          <li><a href="#" class="p-2"><span class="icon-facebook"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-twitter"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-instagram"></span></a></li>
                                          <li><a href="#" class="p-2"><span class="icon-linkedin"></span></a></li>
                                        </ul>-->
                        </div>
                        <div  class="col-md-6">
                            <span> <a href="#"> <strong>مرحبـا </strong> <%= request.getSession().getAttribute("NAME").toString()%></a> </span>

                            <span class="mr-3"> <strong> | </strong> <a href="#"><%= request.getSession().getAttribute("TRAFFIC_UNIT").toString()%></a>  </span>
                            <span class="mr-3"> <strong> | </strong>  <a onclick="goHome()" href="#">الصفحة الرئيسية</a> </span>

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



                                <img style="height: 100px ; width: 140px" src="logo_titd.png"/>


                            </ul>
                        </nav>
                    </div>


                    <div class="d-inline-block d-xl-none ml-md-0 mr-auto py-3" style="position: relative; top: 3px;"><a href="#" class="site-menu-toggle js-menu-toggle text-black"><span class="icon-menu h3"></span></a></div>

                </div>

            </div>
        </div>









        <div style="display: block" id="egyForm" class="site-section bg-light">
            <div class="container">
                <div class="row">



                    <div class="col-md-12 mb-5">
                        <!--<div style="display: block" id="BackBtn"  class="p-4 mb-3 bg-white">
                                                                  <h3 class="h5 text-black mb-3">More Info</h3>
                        
                                                    <p><a onclick="goHome()" href="#" class="btn btn-primary btn-md text-white">رجوع</a></p>
                                                </div>-->


                        <form method="post" onsubmit=""  action="biometric" enctype="multipart/form-data" class="p-5 bg-white">

                            <center><h2  class="h4 text-black mb-5">إلتقاط الصورة</h2> </center>

                            <%
                                String QueueNumber = request.getParameter("QueueNumber");
                                String nationalID = request.getParameter("nationalID");
                                String FullName = request.getParameter("FullName");
                                String passportNo = request.getParameter("passportNo");
                                String passportIssueCountry = request.getParameter("passportIssueCountry");
                                String requestID = request.getParameter("requestID");
                                String ServiceType = request.getParameter("ServiceType");
                            %>

                            <input type="hidden"  name="QueueNumber" value="<%= QueueNumber%>">
                            <input type="hidden" id="nid"  name="nationalID" value="<%= nationalID%>">
                            <input type="hidden" id="passportID"  name="passportNo" value="<%= passportNo%>">
                            <input type="hidden" id="issueCountryID"  name="passportIssueCountry" value="<%= passportIssueCountry%>">
                            <input type="hidden"  name="requestID" value="<%= requestID%>">
                            <input type="hidden"  name="ServiceType" value="<%= ServiceType%>">
                            <input type="hidden" id="FullNameID"  name="FullName" value="<%= FullName%>">



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

                            <!--                            <div class="row form-group">
                            
                                                            <div class="col-md-12">
                                                                <label class="text-black" for="email">ملف البصمات</label> 
                                                                <input name="bioFile" type="file" id="Bionid" class="form-control" required="يرجاء ملئ هذا الحقل">
                                                            </div>
                                                        </div>-->
                            <div ng-controller="demoCtrl">
                                <!--                                <label class="text-black" for="email">ملف الصور</label> <br/>
                                                                <input  id="imgnid" type="file" accept="image/*" ng-model="image1" name="imgFile" image="image1"
                                                                       resize-max-height="400" resize-max-width="400" resize-quality="0.5" resize-type="image/jpg" ng-image-compress required/>-->

                                <!--                                <img ng-src="{{watchedImgData}}" height="100" name="compressedAgain" />-->

                                <input onchange="readURL(this);"  id="imgnid" type="file" accept="image/*"  name="imgFile" required/>
                                <img id="blah" src="http://placehold.it/180" alt="your image" />

                                <!-- just do a console.log of {{image1}} to see what other options are in the file object -->
                            </div>
                            <!--                            <div style="display: block" id="EgyCitizenCameraAndBio"  class="p-4 mb-3 bg-white">
                                                            <div class="container">
                                                                <div class="row">
                            
                                                                    <div class="col-md-4"> <video width=320 height=240 id="player" controls autoplay></video></div>
                                                                    <div class="col-md-4 alert-light"><a id="capture" class="btn-primary btn  btn-block btn-md text-white">إلتقـاط</a></div>
                                                                    <div class="col-md-4"><canvas id="canvas" width=320 height=240 required="يرجاء ملئ هذا الحقل"></canvas></div>
                            
                            
                                                                </div>
                            
                                                            </div>
                            
                            
                            
                                                        </div>-->




                            <div class="row form-group">
                                <div class="col-md-6">
                                    <input type="submit" value=" حفظ الصوره" class="btn btn-primary btn-md text-white">
                                </div>

                            </div>


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

//                   var citizenId = document.getElementById("nid").value;
//                if(citizenId == '' || citizenId == null){
//                    citizenId = document.getElementById("issueCountryID").value.toString() + document.getElementById("passportID").value.toString();
//                }
//                 if(citizenId == '' || citizenId == null){
//                     alert('برجاء التأكد من وجود الرقم القومي');
//                 }
//                var dataURL = canvas.toDataURL();
//                // dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
//                // alert(dataURL);
//                var xhttp = new XMLHttpRequest();
//                xhttp.onreadystatechange = function () {
//                    if (this.readyState == 4 && this.status == 200) {
//                        // document.getElementById("demo").innerHTML = this.responseText;
//                    }
//                };
//                xhttp.open("POST", "/API/ImageServlet", true);
//                var data = citizenId + dataURL;
//                xhttp.send(data);

                // player.srcObject.getVideoTracks().forEach(track => track.stop());
            });

            // Attach the video stream to the video element and autoplay.
            navigator.mediaDevices.getUserMedia(constraints)
                    .then((stream) => {
                        player.srcObject = stream;
                    });





            function readURL(input) {
                document.getElementById("blah").style.visibility = "visible";
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#blah')
                                .attr('src', e.target.result);
                    };

                    reader.readAsDataURL(input.files[0]);
                }
            }

            function Filevalidation() {
                const fi = document.getElementById('imgnid');
                // Check if any file is selected. 
                if (fi.files.length > 0) {
                    for (const i = 0; i <= fi.files.length - 1; i++) {

                        const fsize = fi.files.item(i).size;
                        const file = Math.round((fsize / 1024));
                        // The size of the file. 
                        if (file >= 150) {
                            alert(" ادخل صورة اقل من   150 كيلوبايت");
                            document.getElementById('imgnid').value = "";
                            // return false;
                        }
//                else if (file < 2048) { 
//                    alert( 
//                      "File too small, please select a file greater than 2mb"); 
//                } else { 
//                    document.getElementById('size').innerHTML = '<b>'
//                    + file + '</b> KB'; 
//                } 
                    }
                }
            }

            function showEgy() {

                document.getElementById("selectEF").style.display = "none";
                document.getElementById("forForm").style.display = "none";
                document.getElementById("egyForm").style.display = "block";


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

            function  sendImage() {
                // function  sendImage(nid,passId,cId){
                var citizenId = document.getElementById("nid").value;
                if (citizenId == '' || citizenId == null) {
                    citizenId = document.getElementById("issueCountryID").value.toString() + document.getElementById("passportID").value.toString();
                }
                if (citizenId == '' || citizenId == null) {
                    alert('برجاء التأكد من وجود الرقم القومي');
                }
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