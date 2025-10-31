<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    String eSignRequest = (String) session.getAttribute("eSignRequest");
    String aspTxnID = (String) session.getAttribute("aspTxnID");
    String url =(String)session.getAttribute("esignUrl");
    String reqParam = (String)session.getAttribute("reqparam");
    
    

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <head>
        <title>Redirecting to  portal</title>


    </head>
</head>




<body onload='document.forms[0].submit()'>
    <form id="contact_form" action='<%=url%>' method="post">
        <input type='hidden' name='<%=reqParam%>' value='<%=eSignRequest%>'>
        <input type='hidden' name='aspTxnID' value="<%=aspTxnID%>">
        <input type='hidden' id = "Content-Type" name="Content-Type" value="application/xml"/>
        
        




    </form>

<!--    <script type="text/javascript" language="javascript">
        //script to set value in hidden file


        function testrequest() {
        alert('inside test function..');
        var formData = new FormData(document.getElementById("contact_form"));
        alert(formData);
        $.ajax({
        url: 'https://es-staging.cdac.in/esign2.1level1/2.1/form/signdoc',
                //   url: '/asp/echofile',
                type: "POST",
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                

                xhrFields: {
                withCredentials: true
                },
                success: function (eSignRequestXML) {
                // alert(eSignRequestXML.responseUrl);
                alert('success');
//alert(requestInfo);
//var eSignRequestXML=requestInfo.reqXml;
//var eSignRequestTxn=requestInfo.reqTxn;
                //alert(eSignRequestXML.reqXml);
                alert(eSignRequestXML);
                console.log(eSignRequestXML);
                // $("#eSignRequest").val(eSignRequestXML);
                // $("#aspTxnID").val("bb50dc54-79e8-42sresffrad-b86b-7ec097000137");
// $("#aspTxnID").val("ESIGN46");
                $("contact_form").submit();
                },
                error: function (ts) {
                alert("Hello frm js err");
                alert("Error " + ts.responseText);
                }

        });
        // e.preventDefault();
        }




    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->

</body>


</html>