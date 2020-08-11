<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>权限不足 - 403</title>
	<link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/animate.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>403</h1>
        <h3 class="font-bold">您没有访问权限！</h3>

        <div class="error-desc">
                                对不起，您没有访问权限，请不要进行非法操作！您可以返回主页面
            <a href="javascript:index()" class="btn btn-outline btn-primary btn-xs">返回主页</a>
        </div>
    </div>
    <script>
      function index() {
          window.parent.frames.location.href = "/";
      }
    </script>
</body>
</html>