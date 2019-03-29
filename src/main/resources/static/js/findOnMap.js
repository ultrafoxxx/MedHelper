mapConstant = "https://www.google.com/maps/embed/v1/place?key=AIzaSyDdwvaJ3WLXoSk1gB0DYWa9jRGHoPt926w&q="
$(".inputhints").hide();

inputCity = $("#inputCity");
inputAdress = $("#inputStreet")

$("#look").click(function () {
    city = $(inputCity).val();
    address = $("#inputStreet").val();
    $("iframe").attr("src", mapConstant+city+"+"+address);

});



// inputCity.keypress(function (event) {
//
// });
// inputAdress.keypress(function () {
//
// });
inputCity.keyup(function (event) {
    ajaxgetCities();

});
inputAdress.keyup(function (event) {
    ajaxgetAdresses();

});



function ajaxgetCities() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/map/getCities?city="+$(inputCity).val(),
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log($(inputCity).val());
            $("#hints1").empty();
            $.each(data, function (index, value) {
                newListElement = $("<button type=\"button\" class=\"cities list-group-item list-group-item-action list-group-item-success\">");
                newListElement.text(value);
                $("#hints1").append(newListElement);
            });
            if ($(inputCity).val() === "") {

                $(".inputhints").hide();
            } else {
                $("#hints1").show();
            }


            $("button.cities").click(function (event) {
                $(inputCity).val($(this).text());
                $("#hints1").hide();
                ajaxgetAdresses();
            });
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

function ajaxgetAdresses() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/map/getAddresses?city="+$(inputCity).val()+"&adress="+$(inputAdress).val(),
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#hints2").empty();
            $.each(data, function (index, value) {
                newListElement = $("<button type=\"button\" class=\"addresses list-group-item list-group-item-action list-group-item-success\">");
                newListElement.text(value);
                $("#hints2").append(newListElement);
            });
            $("#hints2").show();

            $("button.addresses").click(function (event) {
                $(inputAdress).val($(this).text());
                $("#hints2").hide()
            });
        }
    })

}