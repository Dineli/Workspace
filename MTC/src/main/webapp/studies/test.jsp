<%-- 
    Document   : test
    Created on : Feb 12, 2016, 1:45:10 PM
    Author     : ephaadk
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Bootstrap Case</title>
        <meta charset="utf-8">
        <!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
        <!--  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
          <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>-->
<!--        <script src="js/jquery.easing.min.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet">-->
    </head>
    <body>

        <div class="container1">
            <h2>Dynamic Tabs with jQuery</h2>
            <p>Click on the Tabs to display the active and previous tab.</p>
            <ul class="nav nav-tabs">
                <li class="active"><a href="#home">Home</a></li>
                <li><a href="#menu1">Menu 1</a></li>
                <li><a href="#menu2">Menu 2</a></li>
                <li><a href="#menu3">Menu 3</a></li>
            </ul>

            <div class="tab-content">
                <div id="home" class="tab-pane fade in active">
                    <h3>HOME</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                </div>
                <div id="menu1" class="tab-pane fade">
                    <h3>Menu 1</h3>
                    <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                </div>
            </div>

            <script>
                $(document).ready(function () {
                    $(".nav-tabs a").click(function () {
                        $(this).tab('show');
                    });
                    //    $('.nav-tabs a').on('shown.bs.tab', function(event){
                    //        var x = $(event.target).text();         // active tab
                    //        var y = $(event.relatedTarget).text();  // previous tab
                    //        $(".act span").text(x);
                    //        $(".prev span").text(y);
                    //    });
                });
            </script>

    </body>
</html>
