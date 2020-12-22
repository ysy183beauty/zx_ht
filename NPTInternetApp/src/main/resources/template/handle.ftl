<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>

<body>

<script>
    function  pseth() {
        var iObj = parent.parent.document.getElementById('frame_content');
        var iObj2 = parent.parent.document.getElementById('frame_content2');
        var iObjH = parent.frames["handleFrame"].location.hash;
        iObj.style.height = iObjH.split("#")[1]+"px";
        iObj2.style.height = iObjH.split("#")[1]+"px";
    }
    pseth();
</script>

</body>
</html>