$(document).ready(function(){
	var userId;
	$('#userIdc').val('');
	$('#passWordc').val('');
	$('#passWordmorec').val('');
	$('#verificationCodec').val('');
	$('#shorMessageNumc').val('');
	$(document).keydown(function(e) {
		//debugger;
		var evt = window.event || e;
		if(evt.keyCode == 13) {
			evt.preventDefault();
			var passWordcVal = $('#passWordc').val();//第一次密码
			var passWordmorecVal = $('#passWordmorec').val();//第二次密码
			var sMobile = $('#userIdc').val();
			var sMobilePassword = $('#passWordc').val();
			 var reg = /^.{6,}$/;
			if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(sMobile))){ 
				layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('请输入正确的手机号',{icon: 2,
						 time: 2000, 
						 offset:'40px',
						 shift: 6
					   });  
				});
				  return; 
			} 
			if(!reg.test(sMobilePassword)){
				layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('请输入至少6位密码',{icon: 2,
						 time: 2000, 
						 offset:'40px',
						 shift: 6
					   });  
				});
			    return;
			}
			if(passWordcVal != passWordmorecVal){
				layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('两次密码不一样',{icon: 2,
						 time: 2000, 
						 offset:'40px',
						 shift: 6
					   });  
				});
				return;
			}else{
				getVerificationCode();
			}
		}
	});
	var verificationCodecVal,passWordmorecVal;
	//立即注册登录
	$('#registerClick').click(function(){
		var passWordcVal = $('#passWordc').val();//第一次密码
		passWordmorecVal = $('#passWordmorec').val();//第二次密码
		verificationCodecVal = $('#verificationCodec').val();//验证码
		userId = $('#userIdc').val();
		var sMobile = $('#userIdc').val();
		var sMobilePassword = $('#passWordc').val();
		 var reg = /^.{6,}$/;
		if(!(/^1[3|4|5|7|9|8][0-9]\d{8}$/.test(sMobile))){ 
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('请输入正确的手机号',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
			  return; 
		} 
		if(!reg.test(sMobilePassword)){
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('请输入至少6位密码',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
		    return;
		}
		if(passWordcVal != passWordmorecVal){
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('两次密码不一样',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
			return;
		}else{
			getVerificationCode();
		}
	});
	function getVerificationCode(){
		$.ajax({
			type: "post",
			url: "../../auth/mobile/register",
			data: {
				'mobile':userId,'smsCode':verificationCodecVal,'password':passWordmorecVal,
			},
			success: function(obj) {
				var code =obj.code;
                var message = obj.message;
				if(code==0){
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg('注册成功,3s后进入登录页面',{icon: 1,
                            time: 3000,
                            offset:'40px',
                            shift: 6
                        });
                    });
                    setTimeout(function(){
                        location.href = '../../templates/login.html';
                    },3000)

                }else {
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg(message,{
                            time: 1000,
                            offset:'40px',
                            shift: 6
                        });
                    });
				}
			},
			error: function() {
				console.log("请求异常！");
			}
		});
	}
	//获取短信验证码
	$('#getShorMessageNumc').click(function(){
		var passWordcVal = $.trim($('#passWordc').val());//第一次密码
		var passWordmorecVal = $.trim($('#passWordmorec').val());//第二次密码
		userId = $.trim($('#userIdc').val());
		var sMobile = $.trim($('#userIdc').val());
		var sMobilePassword = $.trim($('#passWordc').val());
		var reg = /^.{6,}$/;
		if(!(/^1[3|4|5|7|9|8][0-9]\d{8}$/.test(sMobile))){ 
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('请输入正确的手机号',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
			  return; 
		} 
		if(userId ==""||passWordcVal==""||passWordmorecVal==""){
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('请先完整填写账号密码',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
			  return; 
		}
		if(!reg.test(sMobilePassword)){
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('请输入至少6位密码',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
		    return;
		}
		if(passWordcVal != passWordmorecVal){
			layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.msg('两次密码不一样',{icon: 2,
					 time: 2000, 
					 offset:'40px',
					 shift: 6
				   });  
			});
			return;
		}else{
			vrifySMS();
		}
	})
	function vrifySMS(){

		  $.ajax({
	            url: "../../code/sms",
	            type: "get",
	            async: true,
	            data: {
	                "phone": userId,
	            },
	            dataType: "text",
	            contentType: "application/x-www-form-urlencoded",
	            success: function (data) {
	            	if(data == "OK"){
	            		var num =60;
						$('#getShorMessageNumlater').show();
						$('#getShorMessageNumc').hide();
						var t = setInterval(function(){
							num-=1;
							if(num == 0){
								$('#getShorMessageNumlater').hide();
								$('#getShorMessageNumc').show();
								num = 60;
								clearInterval(t);
							}
							$('#getShorMessageNumlater').text('倒计时:'+num+'s');
						},1000)
		            	layui.use('layer', function(){
						  var layer = layui.layer;
						  layer.msg('发送成功',{
							 time: 2000, 
							 offset:'40px'
						   });  
						});
						return;
	            	}else{
	            		layui.use('layer', function(){
						  var layer = layui.layer;
						  layer.msg('发送短信太频繁,请稍后再试',{
							 time: 2000, 
							 offset:'40px'
						   });  
						});
						return;
	            	}
	            },
	            error: function (XMLHttpRequest, textStatus, errorThrown) {
	                alert('获取短信异常');
	            }

	        });
	}
	//已有账号，立即登录
	$('#registerc').click(function(){
		location.href="../../templates/login.html";
	});
	$('.form_text_ipt input').focus(function(){
		$(this).addClass('inputFocus').siblings().removeClass('inputFocus');
	});
	$('.form_text_ipt input').blur(function(){
		$(this).removeClass('inputFocus');
	});
})