<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500</title>
	<link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/animate.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">内部服务器错误！</h3>

        <div class="error-desc">
                                服务器遇到意外事件，不允许完成请求。我们抱歉。您可以返回主页面。
            <a href="javascript:index()" class="btn btn-primary m-t">主页</a>
        </div>
    </div>
    <script>
      function index() {
          window.parent.frames.location.href = "/";
      }
    </script>
</body>
</html>
