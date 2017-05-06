<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html class="bg-black">
    <head>
        <base href="<%=basePath%>">
        <meta  http-equiv="charset" content="utf-8"/>
        <title>AdminLTE | Log in</title>
        
    </head>
    <body class="bg-black">

        <div class="form-box" id="login-box">
            <div class="header">接口测试页面</div>
            <form action="platform/login" method="post">
                <div class="body bg-gray">
                    <div class="form-group">
                        <input type="text" name="controllerName" id="controllerName" class="form-control" placeholder="controllerName" value="LoginService/login"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey1" id="paramKey1" class="form-control" placeholder="paramKey1"/>
                        <input type="text" name="paramValue1" id="paramValue1" class="form-control" placeholder="paramValue1"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey2" id="paramKey2" class="form-control" placeholder="paramKey2" />
                        <input type="text" name="paramValue2" id="paramValue2" class="form-control" placeholder="paramValue2"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey3" id="paramKey3" class="form-control" placeholder="paramKey3"/>
                        <input type="text" name="paramValue3" id="paramValue3" class="form-control" placeholder="paramValue3"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey4" id="paramKey4" class="form-control" placeholder="paramKey4"/>
                        <input type="text" name="paramValue4" id="paramValue4" class="form-control" placeholder="paramValue4"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey5" id="paramKey5" class="form-control" placeholder="paramKey5"/>
                        <input type="text" name="paramValue5" id="paramValue5" class="form-control" placeholder="paramValue5"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey6" id="paramKey6" class="form-control" placeholder="paramKey6"/>
                        <input type="text" name="paramValue6" id="paramValue6" class="form-control" placeholder="paramValue6"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey7" id="paramKey7" class="form-control" placeholder="paramKey7"/>
                        <input type="text" name="paramValue7" id="paramValue7" class="form-control" placeholder="paramValue7"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey8" id="paramKey8" class="form-control" placeholder="paramKey8"/>
                        <input type="text" name="paramValue8" id="paramValue8" class="form-control" placeholder="paramValue8"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey9" id="paramKey9" class="form-control" placeholder="paramKey9"/>
                        <input type="text" name="paramValue9" id="paramValue9" class="form-control" placeholder="paramValue9"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey10" id="paramKey10" class="form-control" placeholder="paramKey10"/>
                        <input type="text" name="paramValue10" id="paramValue10" class="form-control" placeholder="paramValue10"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey11" id="paramKey11" class="form-control" placeholder="paramKey11"/>
                        <input type="text" name="paramValue11" id="paramValue11" class="form-control" placeholder="paramValue11"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey12" id="paramKey12" class="form-control" placeholder="paramKey12"/>
                        <input type="text" name="paramValue12" id="paramValue12" class="form-control" placeholder="paramValue12"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey13" id="paramKey13" class="form-control" placeholder="paramKey13"/>
                        <input type="text" name="paramValue13" id="paramValue13" class="form-control" placeholder="paramValue13"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey14" id="paramKey14" class="form-control" placeholder="paramKey14"/>
                        <input type="text" name="paramValue14" id="paramValue14" class="form-control" placeholder="paramValue14"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey15" id="paramKey15" class="form-control" placeholder="paramKey15"/>
                        <input type="text" name="paramValue15" id="paramValue15" class="form-control" placeholder="paramValue15"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey16" id="paramKey16" class="form-control" placeholder="paramKey16"/>
                        <input type="text" name="paramValue16" id="paramValue16" class="form-control" placeholder="paramValue16"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey17" id="paramKey17" class="form-control" placeholder="paramKey17"/>
                        <input type="text" name="paramValue17" id="paramValue17" class="form-control" placeholder="paramValue17"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey18" id="paramKey18" class="form-control" placeholder="paramKey18"/>
                        <input type="text" name="paramValue18" id="paramValue18" class="form-control" placeholder="paramValue18"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey19" id="paramKey19" class="form-control" placeholder="paramKey19"/>
                        <input type="text" name="paramValue19" id="paramValue19" class="form-control" placeholder="paramValue19"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey20" id="paramKey20" class="form-control" placeholder="paramKey20"/>
                        <input type="text" name="paramValue20" id="paramValue20" class="form-control" placeholder="paramValue20"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey21" id="paramKey21" class="form-control" placeholder="paramKey21"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey21_11" id="paramKey21_11" class="form-control" placeholder="paramKey21_11"/>
                        <input type="text" name="paramValue21_11" id="paramValue21_11" class="form-control" placeholder="paramValue21_11"/>
                        <input type="text" name="paramKey21_12" id="paramKey21_12" class="form-control" placeholder="paramKey21_12"/>
                        <input type="text" name="paramValue21_12" id="paramValue21_12" class="form-control" placeholder="paramValue21_12"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey21_21" id="paramKey21_21" class="form-control" placeholder="paramKey21_21"/>
                        <input type="text" name="paramValue21_21" id="paramValue21_21" class="form-control" placeholder="paramValue21_21"/>
                        <input type="text" name="paramKey21_22" id="paramKey21_22" class="form-control" placeholder="paramKey21_22"/>
                        <input type="text" name="paramValue21_22" id="paramValue21_22" class="form-control" placeholder="paramValue21_22"/>
                    </div>
                    <div class="form-group">
                        <input type="text" name="paramKey22" id="paramKey22" class="form-control" placeholder="paramKey22"/>
                        <input type="text" name="paramValue22_1" id="paramValue22_1" class="form-control" placeholder="paramValue22_1"/>
                        <input type="text" name="paramValue22_2" id="paramValue22_2" class="form-control" placeholder="paramValue22_2"/>
                    </div>
                </div>
                <div class="footer">                                                               
                    <button type="button" id="test" class="btn bg-olive btn-block">测试</button>
                </div>
                <div id="result">                                                               
                </div>
                <div id="errorContent">                                                               
                </div>
            </form>
        </div>
    </body>
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/jquery.json.js"></script>
<script type="text/javascript"> 
$(document).ready(function(){ 
    $("#test").click(function(){
    	 $("#result").text("");
    
         var controllerName = $("#controllerName").val();
         var paramKey1 = $("#paramKey1").val();
         var paramValue1 = $("#paramValue1").val();
         var paramKey2 = $("#paramKey2").val();
         var paramValue2 = $("#paramValue2").val();
         var paramKey3 = $("#paramKey3").val();
         var paramValue3 = $("#paramValue3").val();
         var paramKey4 = $("#paramKey4").val();
         var paramValue4 = $("#paramValue4").val();
         var paramKey5 = $("#paramKey5").val();
         var paramValue5 = $("#paramValue5").val();
         var paramKey6 = $("#paramKey6").val();
         var paramValue6 = $("#paramValue6").val();
         var paramKey7 = $("#paramKey7").val();
         var paramValue7 = $("#paramValue7").val();
         var paramKey8 = $("#paramKey8").val();
         var paramValue8 = $("#paramValue8").val();
         var paramKey9 = $("#paramKey9").val();
         var paramValue9 = $("#paramValue9").val();
         var paramKey10 = $("#paramKey10").val();
         var paramValue10 = $("#paramValue10").val();
         var paramKey11 = $("#paramKey11").val();
         var paramValue11 = $("#paramValue11").val();
         var paramKey12 = $("#paramKey12").val();
         var paramValue12 = $("#paramValue12").val();
         var paramKey13 = $("#paramKey13").val();
         var paramValue13 = $("#paramValue13").val();
         var paramKey14 = $("#paramKey14").val();
         var paramValue14 = $("#paramValue14").val();
         var paramKey15 = $("#paramKey15").val();
         var paramValue15 = $("#paramValue15").val();
         var paramKey16 = $("#paramKey16").val();
         var paramValue16 = $("#paramValue16").val();
         var paramKey17 = $("#paramKey17").val();
         var paramValue17 = $("#paramValue17").val();
         var paramKey18 = $("#paramKey18").val();
         var paramValue18 = $("#paramValue18").val();
         var paramKey19 = $("#paramKey19").val();
         var paramValue19 = $("#paramValue19").val();
         var paramKey20 = $("#paramKey20").val();
         var paramValue20 = $("#paramValue20").val();
         var paramKey21 = $("#paramKey21").val();
         var paramKey21_11 = $("#paramKey21_11").val();
         var paramValue21_11 = $("#paramValue21_11").val();
         var paramKey21_12 = $("#paramKey21_12").val();
         var paramValue21_12 = $("#paramValue21_12").val();
         var paramKey21_21 = $("#paramKey21_21").val();
         var paramValue21_21 = $("#paramValue21_21").val();
         var paramKey21_22 = $("#paramKey21_22").val();
         var paramValue21_22 = $("#paramValue21_22").val();
         var paramKey22 = $("#paramKey22").val();
         var paramValue22_1 = $("#paramValue22_1").val();
         var paramValue22_2 = $("#paramValue22_2").val();
         var param21List = new Array();
         var param21 = {};
         param21[paramKey21_11] = paramValue21_11; 
         param21[paramKey21_12] = paramValue21_12;
         param21List.push(param21);
         var param21_2 = {};
         param21_2[paramKey21_21] = paramValue21_21; 
         param21_2[paramKey21_22] = paramValue21_22;
         param21List.push(param21_2);
         
         var param22List = new Array();
         param22List.push(paramValue22_1);
         param22List.push(paramValue22_2);
         
         var param = {};
         if (paramKey1 != ""){
             param[paramKey1] = paramValue1;
         }
         if (paramKey2 != ""){
             param[paramKey2] = paramValue2;
         }
         if (paramKey3 != ""){
             param[paramKey3] = paramValue3;
         }
         if (paramKey4 != ""){
             param[paramKey4] = paramValue4;
         }
         if (paramKey5 != ""){
             param[paramKey5] = paramValue5;
         }
         if (paramKey6 != ""){
             param[paramKey6] = paramValue6;
         }
         if (paramKey7 != ""){
             param[paramKey7] = paramValue7;
         }
         if (paramKey8 != ""){
             param[paramKey8] = paramValue8;
         }
         if (paramKey9 != ""){
             param[paramKey9] = paramValue9;
         }
         if (paramKey10 != ""){
             param[paramKey10] = paramValue10;
         }
         if (paramKey11 != ""){
             param[paramKey11] = paramValue11;
         }
         if (paramKey12 != ""){
             param[paramKey12] = paramValue12;
         }
         if (paramKey13 != ""){
             param[paramKey13] = paramValue13;
         }
         if (paramKey14 != ""){
             param[paramKey14] = paramValue14;
         }
         if (paramKey15 != ""){
             param[paramKey15] = paramValue15;
         }
         if (paramKey16 != ""){
             param[paramKey16] = paramValue16;
         }
         if (paramKey17 != ""){
             param[paramKey17] = paramValue17;
         }
         if (paramKey18 != ""){
             param[paramKey18] = paramValue18;
         }
         if (paramKey19 != ""){
             param[paramKey19] = paramValue19;
         }
         if (paramKey20 != ""){
             param[paramKey20] = paramValue20;
         }
         if (paramKey21 != ""){
             param[paramKey21] = param21List;
         }
         if (paramKey22 != ""){
             param[paramKey22] = param22List;
         }
         
         
         alert($.toJSON(param));
         $.ajax({ 
             url:controllerName, 
             type:"post",
             dataType: "json",
             data:$.toJSON(param),
             contentType:"application/json",
			 error:function(data){
			 	alert("出错了！！:"+data);
			 },
             success: function(data){
                $("#result").text(JSON.stringify(data));
             }
         });
    });
});
</script>
</html>