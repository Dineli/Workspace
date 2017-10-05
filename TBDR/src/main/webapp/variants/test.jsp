<%-- 
    Document   : test
    Created on : Feb 8, 2017, 3:19:08 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <select id="example-getting-started" multiple="multiple">
            <option value="cheese">Cheese</option>
            <option value="tomatoes">Tomatoes</option>
            <option value="mozarella">Mozzarella</option>
            <option value="mushrooms">Mushrooms</option>
            <option value="pepperoni">Pepperoni</option>
            <option value="onions">Onions</option>
        </select>

        <select id="food" multiple="multiple">
            <option value="cheese">Cheese</option>
            <option value="tomatoes">Tomatoes</option>
            <option value="mozarella">Mozzarella</option>
            <option value="mushrooms">Mushrooms</option>
            <option value="pepperoni">Pepperoni</option>
            <option value="onions">Onions</option>
        </select>

        <select id="example-getting-started2" multiple="multiple">
            <option value="cheese">Cheese</option>
            <option value="tomatoes">Tomatoes</option>
            <option value="mozarella">Mozzarella</option>
            <option value="mushrooms">Mushrooms</option>
            <option value="pepperoni">Pepperoni</option>
            <option value="onions">Onions</option>
        </select>
    </body>
</html>

<script type="text/javascript">

    $(document).ready(function () {
        $('#example-getting-started').multiselect({
            onChange: function (option, checked) {
                // Get selected options.
                var selectedOptions = $('#example-getting-started option:selected');

                if (selectedOptions.length >= 2) {
                    // Disable all other checkboxes.
                    var nonSelectedOptions = $('#example-getting-started option').filter(function () {
                        return !$(this).is(':selected');
                    });

                    var dropdown = $('#example-getting-started').siblings('.multiselect-container');
                    nonSelectedOptions.each(function () {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', true);
                        input.parent('li').addClass('disabled');
                    });
                } else {
                    // Enable all checkboxes.
                    var dropdown = $('#example-getting-started').siblings('.multiselect-container');
                    $('#example-getting-started option').each(function () {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', false);
                        input.parent('li').addClass('disabled');
                    });
                }
            }
        });

        $('#food').multiselect({
            onChange: function (option, checked) {
                // Get selected options.
                var selectedOptions = $('#food option:selected');

                if (selectedOptions.length >= 2) {
                    // Disable all other checkboxes.
                    var nonSelectedOptions = $('#food option').filter(function () {
                        return !$(this).is(':selected');
                    });

                    var dropdown = $('#food').siblings('.multiselect-container');
                    nonSelectedOptions.each(function () {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', true);
                        input.parent('li').addClass('disabled');
                    });
                } else {
                    // Enable all checkboxes.
                    var dropdown = $('#food').siblings('.multiselect-container');
                    $('#food').each(function () {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', false);
                        input.parent('li').addClass('disabled');
                    });
                }
            }

        });



        $('#example-getting-started2').multiselect();
    });

</script>
