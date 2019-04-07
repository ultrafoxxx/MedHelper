$(".inputhints").hide();

inputName = $("#inputName");

// $("#look").click(function () {
//     city = $(inputCity).val();
//     address = $("#inputStreet").val();
//     $("iframe").attr("src", mapConstant+city+"+"+address);
//
// });



// inputCity.keypress(function (event) {
//
// });
// inputAdress.keypress(function () {
//
// });
inputName.keyup(function () {
    ajaxgetNames();

});




function ajaxgetNames() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/receptionistPanel/getPatient?name="+$(inputName).val(),
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#hints1").empty();
            $.each(data, function (index, value) {
                console.log(value);
                newListElement = $("<button type=\"button\" class=\"cities list-group-item list-group-item-action list-group-item-success\">");
                newListElement.text(value.firstName + " " + value.lastName + " "+value.pesel);
                $("#hints1").append(newListElement);
            });
            if ($(inputName).val() === "") {

                $(".inputhints").hide();
            } else {
                $("#hints1").show();
            }


            $("button.cities").click(function (event) {
                $(inputName).val($(this).text());
                $("#hints1").hide();
            });
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

