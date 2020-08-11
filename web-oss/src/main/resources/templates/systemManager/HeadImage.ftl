<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="修改头像">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/cropper/cropper.min.css" rel="stylesheet"/>
	<style type='text/css'>
	/* avator css start */
	.container {
		margin: 10px 5px 5px 5px;
	}
	
	.action {
		padding: 5px 0px;
	}
	
	.cropped {
		width: 200px;
		height: 345px;
		border: 1px #ddd solid;
		box-shadow: 0px 0px 12px #ddd;
	}
	
	.img-preview {
		border-radius: 50%;
		box-shadow: 0px 0px 12px #7e7e7e;
		display: inline-block;
	}
	
	.preview-box {
		text-align: center;
		margin: 0px auto;
		margin-top: 10px;
		color: #bbb;
	}
	
	.preview-md {
		width: 128px;
		height: 128px;
	}
	
	.preview-sm {
		width: 96px;
		height: 96px;
	}
	
	.preview-xs {
		width: 64px;
		height: 64px;
	}
	
	.imageBox {
		border: 1px solid #aaa;
		overflow: hidden;
		cursor: move;
		box-shadow: 4px 4px 12px #B0B0B0;
		margin: 0px auto;
	}
	
	.btn-custom {
		float: right;
		width: 46px;
		display: inline-block;
		margin-bottom: 10px;
		height: 37px;
		line-height: 37px;
		font-size: 14px;
		color: #FFFFFF;
		margin: 0px 2px;
		background-color: #f38e81;
		border-radius: 3px;
		text-decoration: none;
		cursor: pointer;
		box-shadow: 0px 0px 5px #B0B0B0;
		border: 0px #fff solid;
	}
    /*选择文件上传*/
	.new-contentarea {
		width: 165px;
		overflow: hidden;
		margin: 0 auto;
		position: relative;
		float: left;
	}
	
	.new-contentarea label {
		width: 100%;
		height: 100%;
		display: block;
	}
	
	.new-contentarea input[type=file] {
		width: 188px;
		height: 60px;
		background: #333;
		margin: 0 auto;
		position: absolute;
		right: 50%;
		margin-right: -94px;
		top: 0;
		right/*\**/: 0px\9;
		margin-right/*\**/: 0px\9;
		width/*\**/: 10px\9;
		opacity: 0;
		filter: alpha(opacity=0);
		z-index: 2;
	}
	
	a.upload-img {
		width: 165px;
		display: inline-block;
		margin-bottom: 10px;
		height: 37px;
		line-height: 37px;
		font-size: 14px;
		color: #FFFFFF;
		background-color: #f38e81;
		border-radius: 3px;
		text-decoration: none;
		cursor: pointer;
		border: 0px #fff solid;
		box-shadow: 0px 0px 5px #B0B0B0;
	}
	
	a.upload-img:hover {
		background-color: #ec7e70;
	}
	
	.tc {
		text-align: center;
	}
    /* avator css end */
	</style>
</head>
<body class="white-bg">
	<div class="row container">
		<div class="col-md-10">
			<div class="imageBox">
				<#if user.headImage?? && user.headImage !="">
					<img id="avatar" src="${user.headImage!}" >
				<#else>
					<img id="avatar" src="/img/profile.jpg" >
				</#if>
				
			</div>
			<div class="action">
				<div class="new-contentarea tc">
					<a href="javascript:void(0)" class="upload-img"><label for="inputImage">上传图像</label> </a>
					<input type="file" name="avatar" id="inputImage" accept="image/*"/>
				</div>
				<button type="button" class="btn-custom" data-method="zoom" data-option="0.1"><i class="fa fa-search-plus"></i></button>
				<button type="button" class="btn-custom" data-method="zoom" data-option="-0.1"><i class="fa fa-search-minus"></i></button>
				<button type="button" class="btn-custom" data-method="rotate" data-option="-45"><i class="fa fa-rotate-left"></i></button>
				<button type="button" class="btn-custom" data-method="rotate" data-option="45"><i class="fa fa-rotate-right"></i></button>
				<button type="button" class="btn-custom" data-method="scaleX" data-option="-1"><i class="fa fa-arrows-h"></i></button>
				<button type="button" class="btn-custom" data-method="scaleY" data-option="-1"><i class="fa fa-arrows-v"></i></button>
				<button type="button" class="btn-custom" data-method="reset"><i class="fa fa-refresh"></i></button>
			</div>
		</div>
		<div class="col-md-2">
			<div class="cropped">
				<div class="preview-box">
					<div class="img-preview preview-xs"></div>
				</div>
				<div class="preview-box">
					<div class="img-preview preview-sm"></div>
				</div>
				<div class="preview-box">
					<div class="img-preview preview-md"></div>
				</div>
			</div>
		</div>
	</div>
<#include "/common/CommonJsImport.ftl">
<script src="/libs/cropper/cropper.min.js"></script>
<script type="text/javascript">
var cropper;
var croppable = false;
$(window).load(function() {
	var image = document.getElementById('avatar');
	cropper = new Cropper(image, {
		aspectRatio: 1,
		viewMode: 1,
		autoCropArea: 0.9,
		preview: '.img-preview',
		ready: function () {
			croppable = true;
		}
	})

	$('#inputImage').on('change', function() {
		var reader = new FileReader();
		var file = $('#inputImage')[0].files[0];
		if (/^image\/\w+$/.test(file.type)) {
			reader.onload = function(e) {
				if(croppable){
					cropper.replace(e.target.result)
				}
			}
			reader.readAsDataURL(this.files[0]);
		} else {
			$.modal.alertWarning('请选择一个图片文件。');
		}
	});

	$('.btn-custom').on('click',function (e) {
		if (!croppable) {
			$.modal.alertWarning("裁剪框加载中,请稍后...");
			return;
		}
		var data = {
			method: $(this).data('method'),
			option: $(this).data('option') || undefined,
		};
		var result = cropper[data.method](data.option, data.secondOption);
		if(['scaleX','scaleY'].indexOf(data.method) !== -1){
			$(this).data('option', -data.option)
		}
	})
});

function submitHandler() {
    if (!croppable) {
        $.modal.alertWarning("裁剪框加载中,请稍后...");
        return
    }
    cropper.getCroppedCanvas().toBlob(function(img) {
        var formdata = new FormData();
        formdata.append("file", img);
        $.ajax({
            url: "${fileServer!}/image/upload?app=manager",
            data: formdata,
            type: "post",
            processData: false,
            contentType: false,
            success: function(result) {
            	if(result.code==200){
            		$.operate.saveModal("/systemManager/saveHeadImage", {"headImage":result.result},saveCallBack);
            	}else{
            		 $.operate.saveSuccess(result);
            	}
            }
        })
    });
}

function saveCallBack(result){
	$.operate.saveSuccess(result);
}
$(window).resize(function() {
    $('.imageBox').height($(window).height() - 80);
}).resize();

if (!HTMLCanvasElement.prototype.toBlob) {
    Object.defineProperty(HTMLCanvasElement.prototype, 'toBlob', {
        value: function(callback, type, quality) {
            var canvas = this;
            setTimeout(function() {
                var binStr = atob(canvas.toDataURL(type, quality).split(',')[1]);
                var len = binStr.length;
                var arr = new Uint8Array(len);
                for (var i = 0; i < len; i++) {
                    arr[i] = binStr.charCodeAt(i);
                }
                callback(new Blob([arr], {
                    type: type || 'image/png'
                }));
            });
        }
    });
}
</script>
</body>
</html>
